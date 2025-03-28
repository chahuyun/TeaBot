package cn.chahuyun.teabot.core.processor;

import cn.chahuyun.teabot.api.annotation.SPI;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes("cn.chahuyun.teabot.api.annotation.SPI")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SPIProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, List<Implementation>> serviceToImplementations = new HashMap<>();

        for (var element : roundEnv.getElementsAnnotatedWith(SPI.class)) {
            SPI spi = element.getAnnotation(SPI.class);
            TypeElement implElement = (TypeElement) element;
            String implName = elementUtils.getBinaryName(implElement).toString();

            // 获取接口类型
            Class<?> declaredInterface = spi.interfaceClass();
            String interfaceName;
            if (declaredInterface == void.class) { // 用户未指定接口
                // 自动推断接口：检查实现类实现的所有接口
                List<TypeElement> interfaces = getImplementedInterfaces(implElement);
                if (interfaces.isEmpty()) {
                    error(implElement, "No interfaces implemented. Please specify interfaceClass.");
                    continue;
                }
                if (interfaces.size() > 1) {
                    error(implElement, "Multiple interfaces implemented. Please specify interfaceClass.");
                    continue;
                }
                interfaceName = interfaces.get(0).getQualifiedName().toString();
            } else {
                interfaceName = declaredInterface.getName();
            }

            // 处理别名
            String alias = spi.value();
            Implementation impl = new Implementation(implName, alias);

            serviceToImplementations.computeIfAbsent(interfaceName, k -> new ArrayList<>())
                    .add(impl);
        }

        // 生成服务文件
        for (Map.Entry<String, List<Implementation>> entry : serviceToImplementations.entrySet()) {
            String serviceName = entry.getKey();
            List<Implementation> implementations = entry.getValue();

            // 按别名排序：非空别名优先（默认实现），其他按名称排序
            implementations.sort(Comparator.<Implementation,Boolean>comparing(impl -> impl.alias.isEmpty())
                    .thenComparing(impl -> impl.implName));

            try {
                FileObject resource = processingEnv.getFiler().createResource(
                        StandardLocation.CLASS_OUTPUT,
                        "",
                        "META-INF/services/" + serviceName
                );
                try (PrintWriter writer = new PrintWriter(resource.openWriter())) {
                    for (Implementation impl : implementations) {
                        writer.println(impl.implName);
                    }
                }
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        "Error writing SPI file for " + serviceName + ": " + e.getMessage());
            }
        }
        return true;
    }

    // 辅助方法：获取实现类实现的所有接口
    private List<TypeElement> getImplementedInterfaces(TypeElement implElement) {
        List<TypeElement> interfaces = new ArrayList<>();
        for (var interfaceType : implElement.getInterfaces()) {
            if (interfaceType.getKind() == TypeKind.DECLARED) {
                DeclaredType declaredType = (DeclaredType) interfaceType;
                interfaces.add((TypeElement) declaredType.asElement());
            }
        }
        return interfaces;
    }

    // 辅助方法：统一报错
    private void error(TypeElement element, String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                message + " in class: " + element.getQualifiedName(), element);
    }

    // 辅助类：存储实现类和别名
    private record Implementation(String implName, String alias) {
    }
}