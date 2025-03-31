plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("kapt") version "1.9.22"

//    id("com.google.auto.service") version "1.1.1"
}

group = "cn.chahuyun"
version = "1.0"
description ="teabot-processor"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))


    implementation(project(":teabot-api"))  // 依赖 API 模块中的注解定义
    implementation("com.google.auto.service:auto-service:1.1.1")
    kapt("com.google.auto.service:auto-service:1.1.1")
}

kotlin {
    jvmToolchain(17)
}