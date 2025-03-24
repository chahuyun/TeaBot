package cn.chahuyun.teabot.core.bot;

import cn.chahuyun.teabot.api.config.BotConfig;
import cn.chahuyun.teabot.conf.bot.BotType;
import lombok.Setter;

import java.util.HashMap;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-19 11:37
 */
@Setter
public abstract class AbstractBotConfig implements BotConfig {

    private String baseUrl;

    private String userId;

    private BotType type;

    private final HashMap<String, String> config = new HashMap<>(2);

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 获取bot类型
     *
     * @return BotType
     */
    @Override
    public BotType getBotType() {
        return null;
    }

    /**
     * 获取其他配置
     *
     * @param key key
     * @return value
     */
    @Override
    public String get(String key) {
        return config.get(key);
    }

    /**
     * 保存其他配置
     *
     * @param key   key
     * @param value value
     */
    @Override
    public void set(String key, String value) {
        config.put(key, value);
    }
}
