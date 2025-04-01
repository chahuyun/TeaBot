package cn.chahuyun.teabot.core.bot.cache;

import cn.chahuyun.teabot.api.bot.BotAdapter;
import cn.chahuyun.teabot.common.conf.system.TeaBotFileCore;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * bot登录状态缓存
 *
 * @author Moyuyanli
 * @date 2025-3-25 11:40
 */
@Slf4j
//@Deprecated
public class BotCache {

    private static final String CACHE_FILE_FORMAT = "%s-%s.ser";

    // 序列化方法
    public static void serializable(BotAdapter botAdapter) {
        Path cachePath = TeaBotFileCore.getCachePath();
        Path botCacheDir = cachePath.resolve("bot")
                .resolve(botAdapter.getConfig().getBotType().getType());

        try {
            // 创建目录（如果不存在）
            Files.createDirectories(botCacheDir);

            // 生成文件名：botType-id.ser（例如：wechat-12345.ser）
            String fileName = String.format(CACHE_FILE_FORMAT,
                    botAdapter.getConfig().getBotType().getType(),
                    botAdapter.getConfig().getUserId());
            Path filePath = botCacheDir.resolve(fileName);

            // 序列化对象到文件
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(filePath.toFile()))) {
                oos.writeObject(botAdapter);
            }

        } catch (IOException e) {
            log.error("序列化对象失败", e);
        }
    }

    // 反序列化方法
    public static BotAdapter deserializable(String botType, String botId) {
        Path cachePath = TeaBotFileCore.getCachePath();
        Path botCacheDir = cachePath.resolve("bot").resolve(botType);
        String fileName = String.format(CACHE_FILE_FORMAT, botType, botId);
        Path filePath = botCacheDir.resolve(fileName);

        BotAdapter botAdapter = null;

        try {
            if (Files.exists(filePath)) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(filePath.toFile()))) {
                    botAdapter = (BotAdapter) ois.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("反序列化对象失败", e);
        }

        return botAdapter;
    }

    /**
     * 删除缓存
     * @param botType bot类型
     * @param botId botId
     */
    public static void deleteCache(String botType, String botId) {
        Path cachePath = TeaBotFileCore.getCachePath();
        Path botCacheDir = cachePath.resolve("bot").resolve(botType);
        Path filePath = botCacheDir.resolve(String.format(CACHE_FILE_FORMAT, botType, botId));
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("删除缓存失败", e);
        }
    }
}