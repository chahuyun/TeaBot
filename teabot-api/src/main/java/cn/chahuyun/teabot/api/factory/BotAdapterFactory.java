package cn.chahuyun.teabot.api.factory;

import cn.chahuyun.teabot.api.bot.BotAdapter;
import cn.chahuyun.teabot.api.config.BotConfig;
import cn.chahuyun.teabot.common.conf.bot.BotType;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 14:22
 */
public interface BotAdapterFactory {

    BotAdapter createAdapter(BotConfig config);
    BotType getSupportedType(); // 用于 SPI 或注册时识别类型

}
