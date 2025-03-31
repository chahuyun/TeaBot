package cn.chahuyun.teabot.common.conf.system;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import cn.chahuyun.teabot.common.conf.system.entity.LoggersConfig;
import cn.chahuyun.teabot.common.conf.system.entity.SystemConfig;
import cn.chahuyun.teabot.common.util.GsonUtil;
import cn.hutool.core.io.FileUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class ConfigService {

    private SystemConfig config;
    @Getter
    private static ConfigService instance;

    private ConfigService() {
    }

    public static void load(Class<?> clazz) {
        if (instance != null) {
            throw new RuntimeException("ConfigService already loaded.");
        }
        instance = new ConfigService();

        // 初始化路径管理
        TeaBotFileCore.init(clazz);

        // 配置文件的最终路径（根据环境不同）
        Path configPath = TeaBotFileCore.getConfigPath().resolve("system").resolve("config.json");

        // 确保配置目录存在
        try {
            Files.createDirectories(configPath.getParent());
        } catch (IOException e) {
            throw new RuntimeException("无法创建配置目录", e);
        }

        // 如果配置文件不存在，则从类路径复制默认配置
        if (!Files.exists(configPath)) {
            copyDefaultConfigTo(configPath);
        }

        // 从最终路径加载配置文件
        try (InputStream inputStream = Files.newInputStream(configPath)) {
            String configContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            instance.config = GsonUtil.fromJson(configContent, SystemConfig.class);
            log.debug("从加载的配置: {}", configPath);
        } catch (IOException e) {
            throw new RuntimeException("无法读取配置文件", e);
        }

        // 1. 获取 LoggerContext
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        // 2. 设置根日志级别为 ERROR
        Logger rootLogger = loggerContext.getLogger("root");
        rootLogger.setLevel(instance.config.getLogger().getLevel());

        for (LoggersConfig loggerConfig : instance.config.getLogger().getLoggers()) {
            Logger targetLogger = loggerContext.getLogger(loggerConfig.getName());
            targetLogger.setLevel(loggerConfig.getLevel());
        }

    }

    private static void copyDefaultConfigTo(Path targetPath) {
        // 从类路径读取默认的 config.json（假设在 resources/config/system/ 目录下）
        URL defaultConfigResource = TeaBotFileCore.getClassLoader().getResource("config.json");
        if (defaultConfigResource == null) {
            throw new RuntimeException("默认配置文件 config.json 未找到");
        }

        // 复制到目标路径
        try (InputStream in = defaultConfigResource.openStream()) {
            FileUtil.writeFromStream(in, targetPath.toFile()
            );
            log.info("默认配置文件已复制到: {}", targetPath);
        } catch (IOException e) {
            throw new RuntimeException("无法复制默认配置文件", e);
        }
    }

    public static SystemConfig getConfig() {
        if (instance == null) {
            throw new RuntimeException("ConfigService 未加载");
        }
        return instance.config;
    }
}