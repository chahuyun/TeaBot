plugins {
}

group = "cn.chahuyun"
version = "1.0-SNAPSHOT"
description = "teabot-core"

repositories {
    mavenCentral()
}

dependencies {
    implementation("cn.chahuyun:hibernate-plus:1.0.15")


    api(project(":teabot-api"))
    runtimeOnly(project(":teabot-adapter"))
}

