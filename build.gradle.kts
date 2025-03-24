plugins {
    id("java")
    id("java-library")
}

group = "cn.chahuyun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    // 在每个子项目中应用java插件
    apply(plugin = "java")
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("com.google.code.gson:gson:2.9.0")

        implementation("org.slf4j:slf4j-simple:1.7.36") // 添加日志支持

        implementation("cn.hutool:hutool-all:5.8.36")

        implementation("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")

        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks.test {
        useJUnitPlatform()
    }
}