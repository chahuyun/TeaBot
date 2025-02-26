plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.sparkjava:spark-core:2.9.4") // 确保选择最新版本

    implementation(project(":teabot-repository"))
    implementation(project(":teabot-configuration"))
    implementation(project(":teabot-core"))

}

