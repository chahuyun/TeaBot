package cn.chahuyun.teabot.conf.system;

import cn.chahuyun.teabot.conf.system.entity.SystemConfig;
import cn.chahuyun.teabot.util.GsonUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.ResourceUtil;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-25 10:58
 */
@Slf4j
public class ConfigService {

    /**
     * 服务配置
     */
    private SystemConfig config;

    /**
     * 唯一实例
     */
    @Getter
    private static ConfigService instance;

    /**
     * 私有构造函数，防止外部实例化
     */
    private ConfigService() {
    }

    /**
     * 加载配置
     * @param clazz Class
     */
    public static void load(Class<?> clazz) {
        if (instance == null) {
            instance = new ConfigService();
        } else {
            throw new RuntimeException("ConfigService already loaded.");
        }

        // 从clazz的resources中拿到config.json文件，读取配置
        URL resource = ResourceUtil.getResource("/config.json", clazz);
        log.info("resource -> {}", resource);
        if (resource == null) {
            throw new RuntimeException("config.json not found.");
        }

        try (InputStream inputStream = resource.openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
            String configContent = contentBuilder.toString();

            // 使用Gson将JSON字符串转换为SystemConfig对象
            instance.config = GsonUtil.fromJson(configContent, SystemConfig.class);
            log.info("Configuration loaded successfully.");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取配置
     * @return SystemConfig
     */
    public static SystemConfig getConfig() {
        if (instance == null) {
            throw new RuntimeException("ConfigService not loaded.");
        }
        return instance.config;
    }
}
