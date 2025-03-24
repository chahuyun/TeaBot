package cn.chahuyun.teabot.api.config;

import cn.chahuyun.teabot.conf.bot.BotType;

/**
 * bot配置
 *
 * @author Moyuyanli
 * @date 2025-3-18 17:21
 */
public interface BotConfig {

    /**
     * 获取bot类型
     * @return BotType
     */
    BotType getBotType();

    /**
     * 获取用户id
     * @return 用户id
     */
    String getUserId();

    /**
     * 获取baseUrl
     * @return baseUrl
     */
    String getBaseUrl();

    /**
     * 获取其他配置
     * @param key key
     * @return value
     */
    String get(String key);

    /**
     * 保存其他配置
     * @param key key
     * @param value value
     */
    void set(String key, String value);

}
