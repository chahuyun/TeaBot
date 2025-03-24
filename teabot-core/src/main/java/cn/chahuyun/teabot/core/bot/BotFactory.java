package cn.chahuyun.teabot.core.bot;

import cn.chahuyun.teabot.api.config.BotAdapter;
import cn.chahuyun.teabot.api.config.BotConfig;
import cn.chahuyun.teabot.api.contact.Bot;

public class BotFactory {

    public static Bot newBot(BotConfig config, BotAdapter adapter) {
        AbstractBot bot = createBotInstance(config, adapter);
        BotContainer.addBot(bot); // 确保所有 Bot 都被添加到容器
        return bot;
    }

    private static AbstractBot createBotInstance(BotConfig config, BotAdapter adapter) {
        switch (config.getBotType()) {
            case PAD_PLUS:
                return new WeChatBot(config, adapter);
            // 其他 BotType 的实现
            default:
                throw new IllegalArgumentException("不支持的 BotType: " + config.getBotType());
        }
    }
}