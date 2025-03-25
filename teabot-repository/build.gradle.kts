plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0-SNAPSHOT"
description = "teabot-repository"

repositories {
    mavenCentral()
}

dependencies {
    implementation("cn.chahuyun:hibernate-plus:1.0.15")

    implementation(project(":teabot-common"))
    implementation(project(":teabot-api"))
}

