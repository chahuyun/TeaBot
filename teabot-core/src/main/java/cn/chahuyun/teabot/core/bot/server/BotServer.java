package cn.chahuyun.teabot.core.bot.server;

import cn.chahuyun.teabot.api.config.BotConfig;
import cn.chahuyun.teabot.core.bot.BotFactory;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-19 10:55
 */
@Slf4j
public class BotServer {

    public static <E extends BotConfig> void newBot(E config) {
        BotFactory.newBot(config);
    }


}
