plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0-SNAPSHOT"
description = "teabot-common"

repositories {
    mavenCentral()
}

dependencies {
    api("org.slf4j:slf4j-api:1.7.36")
    api("ch.qos.logback:logback-classic:1.2.11")
}

