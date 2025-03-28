plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0.0"
description = "teabot-core"

repositories {
    mavenCentral()
}

dependencies {
    implementation("cn.chahuyun:hibernate-plus:1.0.15")

    // 注解处理器依赖（Google Auto Service）
    implementation("com.google.auto.service:auto-service-annotations:1.1.1")
    annotationProcessor("com.google.auto.service:auto-service:1.1.1")

    // 项目内模块依赖
    api(project(":teabot-api"))
    annotationProcessor(project(":teabot-api"))  // 确保包含自定义注解处理器
    runtimeOnly(project(":teabot-adapter"))
}

// 修改后的任务配置（移除过时的 compilerArgs 并使用 annotationProcessorPath）
tasks.withType<JavaCompile>().configureEach {
    // 设置注解处理器路径（替代 -processorpath）
    options.annotationProcessorPath = configurations.annotationProcessor.get()

    // 如果需要指定特定的处理器类（如 SPIProcessor），保留 -processor 参数
//    options.compilerArgs.plusAssign(
//        listOf(
//            "-processor", "cn.chahuyun.teabot.core.processor.SPIProcessor"
//        )
//    )
}