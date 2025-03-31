import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("java-library")

    id("com.github.johnrengelman.shadow") version "8.1.1" // 使用最新版本
}

group = "cn.chahuyun"
version = "1.0"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("com.google.code.gson:gson:2.9.0")
        implementation("org.slf4j:slf4j-api:1.7.36")
        implementation("ch.qos.logback:logback-classic:1.2.11")
        implementation("cn.hutool:hutool-all:5.8.36")
        implementation("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks.test {
        useJUnitPlatform()
    }
}

// 配置 Shadow JAR 任务
tasks.withType<ShadowJar> {
    mergeServiceFiles()
    manifest {
        attributes("Main-Class" to "cn.chahuyun.teabot.TeaBot")
    }

    // 合并所有子项目输出
    subprojects.forEach {
        from(it.sourceSets["main"].output)
    }

    // 包含所有运行时依赖（包含子项目的传递依赖）
    configurations = listOf(
        project.configurations.runtimeClasspath.get(),
        *subprojects.map { it.configurations.runtimeClasspath.get() }.toTypedArray()
    )

    // 排除签名文件
    exclude("META-INF/*.DSA", "META-INF/*.RSA", "META-INF/*.SF")

    // 重要：显式合并所有依赖
//    doFirst {
//        println("正在合并的依赖：")
//        configurations.forEach { config ->
//            config.files.forEach { file ->
//                println("-> ${file.name}")
//            }
//        }
//    }
}

// 复制任务：添加输出声明
tasks.register<Copy>("copyShadowJarToRunDir") {
    val shadowJarTask = tasks.named<ShadowJar>("shadowJar").get()
    val sourceFile = shadowJarTask.archiveFile.get().asFile

    // 修复1：使用正确的路径声明方式
    outputs.files(
        layout.buildDirectory.file("run/${sourceFile.name}")
    )

    // 修复2：使用Gradle推荐的覆盖方式
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
//    eachFile {
//        // 正确设置覆盖方式（Gradle 8.x+ 有效）
//        isOverwrite = true
//    }

    from(sourceFile)
    into(layout.buildDirectory.dir("run"))

    doFirst {
        val targetDir = layout.buildDirectory.dir("run").get().asFile
        targetDir.takeIf { !it.exists() }?.mkdirs()
    }

    doLast {
        val copiedFile = layout.buildDirectory.file("run/${sourceFile.name}").get().asFile
        logger.lifecycle("成功复制到：${copiedFile.absolutePath}")
    }

    dependsOn(shadowJarTask)
}
// 运行任务：使用复制后的路径
tasks.register<JavaExec>("run") {
    group = "teabot"
    mainClass.set("cn.chahuyun.teabot.TeaBot")
    dependsOn("shadowJar", "copyShadowJarToRunDir")

    // 动态获取复制后的 JAR 路径（关键修改）
    val copiedJar = tasks.named<Copy>("copyShadowJarToRunDir").get()
        .outputs.files
        .filter { it.extension == "jar" }

    classpath = copiedJar

    doFirst {
        // 安全获取文件路径
        val targetJar = copiedJar.singleFile
        logger.lifecycle("实际运行路径：{}", targetJar.absolutePath)
    }
}