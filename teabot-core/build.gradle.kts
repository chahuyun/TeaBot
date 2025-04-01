plugins {
    id("java")
    kotlin("jvm") version "1.9.22"
}

group = "cn.chahuyun"
version = "1.0.0"
description = "teabot-core"

repositories {
    mavenCentral()
}

dependencies {
    implementation("cn.chahuyun:hibernate-plus:1.0.15")

    // 项目内模块依赖
    api(project(":teabot-api"))
    compileOnly(project(":teabot-processor"))  // 确保编译时能访问 SPIProcessor 类
    annotationProcessor(project(":teabot-processor"))
    runtimeOnly(project(":teabot-adapter"))
}

// 修改后的任务配置（移除过时的 compilerArgs 并使用 annotationProcessorPath）
tasks.withType<JavaCompile>().configureEach {
    options.annotationProcessorPath = configurations.annotationProcessor.get()

//    // 新增：强制指定处理器类名（解决多模块传递问题）
//    options.compilerArgs.addAll(listOf(
//        "-processor", "cn.chahuyun.teabot.core.processor.SPIProcessor,com.google.auto.service.processor.AutoServiceProcessor"
//    ))
}