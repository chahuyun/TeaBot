import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("java-library")
//    kotlin("jvm") version "1.9.22"

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

    outputs.files(
        project.rootProject.layout.projectDirectory.file("run/${sourceFile.name}")
    )

    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from(sourceFile)
    into(project.rootProject.layout.projectDirectory.dir("run"))

    doFirst {
        val targetDir = project.rootProject.layout.projectDirectory.dir("run").asFile
        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }
    }

    dependsOn(shadowJarTask)
}
// 运行任务：使用复制后的路径
tasks.register<JavaExec>("run") {
    group = "teabot"
    mainClass.set("cn.chahuyun.teabot.TeaBot")
    dependsOn("shadowJar", "copyShadowJarToRunDir")

    val copiedJar = project.rootProject.layout.projectDirectory
        .file("run/${tasks.shadowJar.get().archiveFileName.get()}")

    classpath = files(copiedJar)

    doFirst {
        logger.lifecycle("实际运行路径：${copiedJar.asFile.absolutePath}")
    }
}