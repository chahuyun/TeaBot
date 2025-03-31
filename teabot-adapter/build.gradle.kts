plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0.0"
description = "teabot-adapter"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    api(project(":teabot-api"))
    compileOnly(project(":teabot-processor"))
    annotationProcessor(project(":teabot-processor"))
}