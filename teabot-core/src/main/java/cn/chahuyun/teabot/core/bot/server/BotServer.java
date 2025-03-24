package cn.chahuyun.teabot.core.bot.server;

import cn.chahuyun.teabot.api.config.BotAdapter;
import cn.chahuyun.teabot.adapter.bot.padplus.PadPlusBotAdapter;
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
        // 根据 BotType 创建适配器实例
        BotAdapter adapter = createAdapter(config);
        if (adapter == null) {
            throw new IllegalArgumentException("不支持的 BotType: " + config.getBotType());
        }

        // 2. 通过工厂创建 Bot 实例
        BotFactory.newBot(config, adapter);
    }

    private static BotAdapter createAdapter(BotConfig config) {
        switch (config.getBotType()) {
            case PAD_PLUS:
                return new PadPlusBotAdapter((cn.chahuyun.teabot.adapter.bot.padplus.PadPlusBotConfig) config);
            // 添加其他类型的适配器
            default:
                return null;
        }
    }

}
