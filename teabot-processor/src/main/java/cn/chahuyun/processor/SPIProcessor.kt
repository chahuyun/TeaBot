package cn.chahuyun.processor

import cn.chahuyun.teabot.api.annotation.SPI
import com.google.auto.service.AutoService
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic
import javax.tools.StandardLocation

@AutoService(Processor::class)
@SupportedAnnotationTypes("cn.chahuyun.teabot.api.annotation.SPI")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
class SPIProcessor : AbstractProcessor() {

    // region 初始化工具类
    private lateinit var elementUtils: Elements
    private lateinit var typeUtils: Types

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        elementUtils = processingEnv.elementUtils
        typeUtils = processingEnv.typeUtils
    }
    // endregion

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        if (roundEnv.processingOver()) return false
//        log("[SPI] 开始处理")

        // 收集所有服务接口与实现的映射关系
        val serviceMap = mutableMapOf<String, MutableSet<Implementation>>()

        roundEnv.getElementsAnnotatedWith(SPI::class.java).forEach { element ->
            try {
                processSpiElement(element, serviceMap)
            } catch (e: Exception) {
                processingEnv.messager.printMessage(
                    Diagnostic.Kind.ERROR, "处理 @SPI 元素失败: ${e.message}", element
                )
            }
        }

        // 生成所有 SPI 配置文件
        generateServiceFiles(serviceMap)

        return true
    }

    // region 核心处理逻辑
    /**
     * 处理单个被 @SPI 注解的元素
     */
    private fun processSpiElement(element: Element, serviceMap: MutableMap<String, MutableSet<Implementation>>) {
        check(element.kind == ElementKind.CLASS) { "@SPI 只能用于类" }

        val implElement = element as TypeElement
        val spiAnnotation = implElement.getAnnotation(SPI::class.java)

//        log("[SPIProcessor] 处理类: ${implElement.simpleName}")

        // 1. 获取实现类全限定名
        val implName = elementUtils.getBinaryName(implElement).toString()

        // 2. 获取服务接口名（自动推断或注解指定）
        val interfaceName = getServiceInterface(implElement)

        // 3. 收集到映射表
        serviceMap.computeIfAbsent(interfaceName) { LinkedHashSet() }.add(Implementation(implName, spiAnnotation.value))
    }

    /**
     * 获取服务接口名称（优先使用注解指定，其次自动推断）
     */
    private fun getServiceInterface(implElement: TypeElement): String {
        val interfaceTypeMirror = getAnnotationValue(implElement, "interfaceClass")?.value as? TypeMirror
            ?: throw AnnotationProcessingException("@SPI 注解缺少 interfaceClass 属性")

        return when {
            isVoidType(interfaceTypeMirror) -> inferInterface(implElement)
            else -> validateInterfaceType(implElement, interfaceTypeMirror)
        }
    }

    /**
     * 验证是否为有效接口类型
     */
    private fun validateInterfaceType(implElement: TypeElement, typeMirror: TypeMirror): String {
        val interfaceElement = typeUtils.asElement(typeMirror) as? TypeElement
            ?: throw AnnotationProcessingException("无效的接口类型: $typeMirror")

        check(interfaceElement.kind == ElementKind.INTERFACE) {
            "${interfaceElement.qualifiedName} 必须是接口类型"
        }

        return interfaceElement.qualifiedName.toString()
    }
    // endregion

    // region 自动推断接口逻辑
    /**
     * 检查是否为 void 类型（未指定 interfaceClass）
     */
    private fun isVoidType(typeMirror: TypeMirror): Boolean {
        return typeMirror.toString().let { it == "void" || it == "java.lang.Void" }
    }

    /**
     * 从实现类自动推断接口
     */
    private fun inferInterface(implElement: TypeElement): String {
        val candidateInterfaces = implElement.interfaces.mapNotNull { getRawInterfaceName(it) }.distinct()

//        log("自动推断接口候选: ${candidateInterfaces.joinToString()}")

        return when {
            candidateInterfaces.isEmpty() -> throw AnnotationProcessingException(
                "类未实现任何接口，请使用 @SPI(interfaceClass=...) 显式指定"
            )

            candidateInterfaces.size > 1 -> throw AnnotationProcessingException(
                "检测到多个接口: [${candidateInterfaces.joinToString()}], 需要明确指定"
            )

            else -> candidateInterfaces.first()
        }
    }

    /**
     * 获取原始接口名称（处理泛型）
     */
    private fun getRawInterfaceName(typeMirror: TypeMirror): String? {
        return (typeMirror as? DeclaredType)?.let {
            (it.asElement() as? TypeElement)?.qualifiedName?.toString()
        }
    }
    // endregion

    // region 注解处理工具
    /**
     * 安全获取注解属性值
     */
    private fun getAnnotationValue(element: Element, name: String): AnnotationValue? {
        val spiAnnotationMirror = element.annotationMirrors.firstOrNull {
            it.annotationType.toString() == SPI::class.java.name
        }

        // 获取显式设置的值
        val explicitValue = spiAnnotationMirror?.elementValues?.entries?.firstOrNull {
            it.key.simpleName.toString() == name
        }?.value
        if (explicitValue != null) {
            return explicitValue
        }

        // 获取默认值：通过元素API查找注解属性默认值
        val elements = processingEnv.elementUtils // 假设可访问processingEnv
        val spiTypeElement = elements.getTypeElement(SPI::class.java.canonicalName)
        return spiTypeElement?.enclosedElements
            ?.filterIsInstance<ExecutableElement>()
            ?.firstOrNull { it.simpleName.toString() == name }
            ?.defaultValue
    }
    // endregion

    // region 文件生成
    /**
     * 生成所有 SPI 配置文件
     */
    private fun generateServiceFiles(serviceMap: Map<String, Set<Implementation>>) {
        serviceMap.forEach { (interfaceName, implementations) ->
            try {
//                log("生成服务文件: META-INF/services/$interfaceName")

                processingEnv.filer.createResource(
                    StandardLocation.CLASS_OUTPUT, "", "META-INF/services/$interfaceName"
                ).openWriter().use { writer ->
                    implementations.sortedBy { it.alias }.forEach { writer.write("${it.implName}\n") }
                }
            } catch (e: Exception) {
                processingEnv.messager.printMessage(
                    Diagnostic.Kind.ERROR, "生成文件失败: ${e.message}"
                )
            }
        }
    }
    // endregion

    // region 辅助类
    /**
     * 实现类信息包装
     */
    private data class Implementation(
        val implName: String, // 实现类全限定名
        val alias: String,     // 别名（用于排序）
    )

    /**
     * 自定义注解处理异常
     */
    private class AnnotationProcessingException(message: String) : RuntimeException(message)
    // endregion


    private fun log(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, message)
    }
}
