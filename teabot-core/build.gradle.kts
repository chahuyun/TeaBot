plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("cn.chahuyun:hibernate-plus:1.0.15")


    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    implementation(project(":teabot-configuration"))
}

