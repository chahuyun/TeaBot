plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0-SNAPSHOT"
description = "teabot-api"

repositories {
    mavenCentral()
}

dependencies {

    api(project(":teabot-common"))

//    runtimeOnly(project(":teabot-core"))
}

