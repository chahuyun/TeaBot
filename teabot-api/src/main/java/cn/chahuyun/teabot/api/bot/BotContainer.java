package cn.chahuyun.teabot.api.bot;

import cn.chahuyun.teabot.api.contact.Bot;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Bot 容器，管理所有 Bot 实例
 *
 * @author Moyuyanli
 */
@Slf4j
public class BotContainer {

    // 使用线程安全的 ConcurrentHashMap
    private static final ConcurrentMap<String, Bot> botMap = new ConcurrentHashMap<>();

    // 私有构造函数，防止外部实例化
    private BotContainer() {}

    /**
     * 添加 Bot 到容器
     *
     * @param bot Bot 实例
     */
    public static void addBot(Bot bot) {
        if (bot == null) {
            throw new IllegalArgumentException("Bot cannot be null");
        }
        String botId = bot.getId();
        if (!botMap.containsKey(botId)) {
            throw new IllegalArgumentException("Bot with ID [" + botId + "] already exists");
        }
        log.debug("bot 容器添加bot,id->{}",botId);
        botMap.put(botId, bot);
    }

    /**
     * 通过 Bot ID 获取 Bot 实例
     *
     * @param botId Bot 的唯一标识
     * @return Bot 实例，如果不存在则返回 null
     */
    public static Bot getBot(String botId) {
        return botMap.get(botId);
    }

    /**
     * 移除 Bot
     *
     * @param botId Bot 的唯一标识
     */
    public static void removeBot(String botId) {
        botMap.remove(botId);
    }

    /**
     * 获取所有 Bot 实例
     *
     * @return Bot 集合
     */
    public static Collection<Bot> getAllBots() {
        return new ArrayList<>(botMap.values());
    }
}