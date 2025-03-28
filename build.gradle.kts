import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("java-library")

    id("com.gradleup.shadow") version "8.3.6" // 使用最新版本
}

group = "cn.chahuyun"
version = "1.0"

repositories {
    mavenCentral()
}

subprojects {
    // 在每个子项目中应用java插件
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "com.github.johnrengelman.shadow") // 应用 Shadow 插件到每个子模块

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
    // 合并所有依赖和子模块的编译输出
    from(
        project.configurations.runtimeClasspath.get().map {
            if (it.isDirectory) it else zipTree(it)
        }
    )

    // 合并所有子模块的编译输出（排除当前项目）
    subprojects.forEach { subproject ->
        if (subproject != project) {
            from(subproject.sourceSets.main.get().output)
        }
    }

    // 确保当前项目的编译输出也被包含
    from(sourceSets.main.get().output)

    // 配置主类
    manifest {
        attributes(mapOf("Main-Class" to "cn.chahuyun.teabot.TeaBot"))
    }
}

// 创建复制任务：将 JAR 文件移动到 build/run 目录
tasks.register<Copy>("copyShadowJarToRunDir") {
    // 显式指定 ShadowJar 任务类型
    val shadowJarTask = tasks.named<ShadowJar>("shadowJar").get()
    from(shadowJarTask.archiveFile)
    into("${layout.buildDirectory.dir("run")}") // 使用 layout.buildDirectory 替代 buildDir

    // 确保依赖 shadowJar 任务
    dependsOn(shadowJarTask)
}

// 配置运行任务
tasks.register<JavaExec>("run") {
    group = "teabot"
    mainClass.set("cn.chahuyun.teabot.TeaBot")

    dependsOn("shadowJar", "copyShadowJarToRunDir")

    // 获取复制后的 JAR 文件路径
    val targetJar = tasks.named<Copy>("copyShadowJarToRunDir").get().outputs.files.singleFile
    classpath = files(targetJar)
}