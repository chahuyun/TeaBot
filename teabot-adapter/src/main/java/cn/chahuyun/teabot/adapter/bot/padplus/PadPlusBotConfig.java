package cn.chahuyun.teabot.adapter.bot.padplus;

import cn.chahuyun.teabot.api.config.BotConfig;
import cn.chahuyun.teabot.conf.bot.BotType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-19 9:54
 */

@Setter
public class PadPlusBotConfig implements BotConfig {

    private String baseUrl;
    private String userId;

    @Getter
    private String driverName;

    private final BotConfig config;

    public PadPlusBotConfig(BotConfig config) {
        if (config.getBotType() != BotType.PAD_PLUS) {
            throw new IllegalArgumentException("不支持的 BotType: " + config.getBotType());
        }
        this.config = config;
        this.baseUrl = config.getBaseUrl();
        this.userId = config.getUserId();
        this.driverName = config.get(driverName);

    }

    /**
     * 获取bot类型
     *
     * @return BotType
     */
    @Override
    public BotType getBotType() {
        return BotType.PAD_PLUS;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
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
        config.set(key, value);
    }
}
