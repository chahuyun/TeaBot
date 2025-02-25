plugins {
    id("java")
}

group = "cn.chahuyun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":teabot-api"))
    implementation(project(":teabot-core"))
    implementation(project(":teabot-repository"))
    implementation(project(":teabot-configuration"))


}
