package cn.chahuyun.teabot.conf.system;

import cn.chahuyun.teabot.conf.system.entity.SystemConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;

/**
 * 对启动类进行加载，创建相关文件，提供基础路径，文件读取方法。
 *
 * @author Moyuyanli
 * @date 2025-3-25 15:18
 */
@Slf4j
@SuppressWarnings("FieldCanBeLocal")
public class TeaBotFileCore {

    private static Class<?> startClass;
    private static Path baseDir; // 基础目录（JAR文件父目录或当前工作目录）
    private static Path configDir; // 配置目录
    private static Path dataDir; // 数据目录
    private static Path cacheDir; // 数据目录

    /**
     * 初始化核心路径结构
     *
     * @param teaBotClass 启动类（用于判断运行方式）
     */
    public static void init(Class<?> teaBotClass) {
        startClass = teaBotClass;
        Path path = Paths.get("");

        try {
            // 判断是否为JAR包运行
            CodeSource codeSource = teaBotClass.getProtectionDomain().getCodeSource();
            if (codeSource != null) {
                URL location = codeSource.getLocation();
                if (location != null) {
                    Path jarPath = Paths.get(location.toURI());
                    if (Files.isRegularFile(jarPath)) {
                        baseDir = jarPath.getParent(); // JAR文件的父目录
                    } else {
                        baseDir = path.toAbsolutePath(); // 非JAR但有codeSource的情况（如某些容器）
                    }
                } else {
                    baseDir = path.toAbsolutePath(); // 无法获取位置
                }
            } else {
                baseDir = path.toAbsolutePath(); // 无codeSource的情况
            }
        } catch (Exception e) {
            baseDir = path.toAbsolutePath(); // 异常时使用当前工作目录
        }

        // 判断是否为IDE环境
        boolean isIdeEnvironment = !isJarRunning();

        if (isIdeEnvironment) {
            // IDE环境：创建 run/config 和 run/data
            Path runDir = baseDir.resolve("run");
            configDir = runDir.resolve("config");
            dataDir = runDir.resolve("data");
            cacheDir = runDir.resolve("cache");
        } else {
            // JAR环境：直接使用 config 和 data
            configDir = baseDir.resolve("config");
            dataDir = baseDir.resolve("data");
            cacheDir = baseDir.resolve("cache");
        }

        // 创建目录
        try {
            Files.createDirectories(configDir);
            Files.createDirectories(dataDir);
            Files.createDirectories(cacheDir);
        } catch (IOException e) {
            throw new RuntimeException("无法创建配置或数据目录", e);
        }
    }

    /**
     * 获取配置目录的路径对象
     */
    public static Path getConfigPath() {
        return configDir;
    }

    /**
     * 获取配置目录的路径字符串
     */
    public static String getConfigPathString() {
        return configDir.toString();
    }

    /**
     * 获取数据目录的路径对象
     */
    public static Path getDataPath() {
        return dataDir;
    }

    /**
     * 获取数据目录的路径字符串
     */
    public static String getDataPathString() {
        return dataDir.toString();
    }

    /**
     * 获取缓存目录的路径对象
     */
    public static Path getCachePath() {
        return cacheDir;
    }

    /**
     * 获取缓存目录
     */
    public static String getCachePathString() {
        return cacheDir.toString();
    }

    /**
     * 获取系统配置（示例实现，需根据实际配置文件格式修改）
     */
    public static SystemConfig getConfig() {
        Path configFilePath = configDir.resolve("system.conf"); // 假设配置文件名为system.conf
        // 读取配置文件并解析为SystemConfig对象
        // 这里需要根据实际配置格式（如JSON、YAML等）实现解析逻辑
        return new SystemConfig(); // 示例返回空对象，需替换为真实逻辑
    }


    public static InputStream getResourceAsStream(String path) {
        // 使用类加载器获取资源输入流
        InputStream in = TeaBotFileCore.getClassLoader().getResourceAsStream(path);
        if (in == null) {
            throw new RuntimeException("Resource not found: " + path);
        }
        return in;
    }

    public static ClassLoader getClassLoader() {
        return startClass.getClassLoader();
    }


    //======================================private=======================================================


    private static boolean isJarRunning() {
        CodeSource codeSource = startClass.getProtectionDomain().getCodeSource();
        if (codeSource == null) {
            return false;
        }
        URL location = codeSource.getLocation();
        if (location == null) {
            return false;
        }
        try {
            Path jarPath = Paths.get(location.toURI());
            return Files.isRegularFile(jarPath); // 检查是否是普通文件（即JAR文件）
        } catch (URISyntaxException e) {
            return false;
        }
    }

}