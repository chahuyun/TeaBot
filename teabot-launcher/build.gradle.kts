plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0-SNAPSHOT"
description = "teabot-launcher"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":teabot-core"))
    implementation(project(":teabot-web"))
}


