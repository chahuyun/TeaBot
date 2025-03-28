plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0.0"
description = "teabot-api"

repositories {
    mavenCentral()
}

dependencies {

    api(project(":teabot-common"))

//    runtimeOnly(project(":teabot-core"))
}

