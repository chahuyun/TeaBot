package cn.chahuyun.teabot.core.bot;

import java.util.HashMap;

/**
 * bot容器，管理bot
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:39
 */
public class BotContainer {

    //hashmap储存bot
    private static final HashMap<String, AbstractBot> botMap = new HashMap<>(1);

    static void addBot(AbstractBot bot) {
        botMap.put(bot.getId(), bot);
    }

    /**
     * 通过bot id获取bot
     * @param id botId
     * @return Bot
     */
    static Bot getBot(String id) {
        return botMap.get(id);
    }

    //这里应该还要处理一下bot的在线情况


}
