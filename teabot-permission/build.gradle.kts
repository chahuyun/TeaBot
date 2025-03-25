plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0-SNAPSHOT"
description = "teabot-permission"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":teabot-core"))
}
