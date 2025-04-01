package cn.chahuyun.teabot.adapter.bot.padplus;

import cn.chahuyun.teabot.api.bot.BotAdapter;
import cn.chahuyun.teabot.api.config.BotConfig;
import cn.chahuyun.teabot.api.factory.BotAdapterFactory;
import cn.chahuyun.teabot.common.conf.bot.BotType;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 14:23
 */
public class PadPlusAdapterFactory implements BotAdapterFactory {
    @Override
    public BotAdapter createAdapter(BotConfig config) {
        return new PadPlusBotAdapter(new PadPlusBotConfig(config));
    }

    @Override
    public BotType getSupportedType() {
        return BotType.PAD_PLUS;
    }
}
