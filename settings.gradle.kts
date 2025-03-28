rootProject.name = "TeaBot"
include("teabot-core")
include("teabot-api")
include("teabot-repository")
include("teabot-common")
include("teabot-launcher")
include("teabot-web")
include("teabot-adapter")
include("teabot-permission")

pluginManagement {
    repositories {
        // 添加 Gradle 插件仓库
        gradlePluginPortal()
        // 添加 Maven Central（Shadow 插件的来源）
        mavenCentral()
        // 或使用阿里云 Maven 镜像（国内推荐）
        maven { url = uri("https://maven.aliyun.com/repository/public") }
    }
}