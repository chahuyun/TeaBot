plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0.0"
description = "teabot-web"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.sparkjava:spark-core:2.9.4") // 确保选择最新版本

    api(project(":teabot-repository"))
    implementation(project(":teabot-core"))
}

