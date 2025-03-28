import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0.0"
description = "teabot-launcher"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":teabot-core"))
    implementation(project(":teabot-web"))
}


