package cn.chahuyun.teabot.core.bot;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import cn.chahuyun.teabot.conf.bot.BotType;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:34
 */
@SuppressWarnings("FieldCanBeLocal")
public abstract class AbstractBot implements Bot {

    private final BotConfiguration configuration;

    private final String id;

    public AbstractBot(BotConfiguration configuration, String id) {
        this.configuration = configuration;
        this.id = id;
    }

    @Override
    public BotConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * 获取机器人类型
     * @return BotType
     */
    public BotType getType() {
        return configuration.getType();
    }


}
