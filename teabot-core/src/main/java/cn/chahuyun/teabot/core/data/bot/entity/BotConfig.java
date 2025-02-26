package cn.chahuyun.teabot.core.data.bot.entity;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import cn.chahuyun.teabot.conf.bot.BotType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库配置信息
 *
 * @author Moyuyanli
 * @date 2025-2-25 17:16
 */
@Setter
@Getter
@Entity
@Table(name = "b_bot_config")
public class BotConfig {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * bot类型
     */
    private BotType type;

    /**
     * bot名称
     */
    private String name;
    /**
     * bot基础连接地址
     */
    private String baseurl;

    /**
     * 回调地址
     */
    private String callback;

    /**
     * 驱动名称
     */
    private String driverName;

    /**
     * 转换为BotConfiguration
     * @return BotConfiguration
     */
    public BotConfiguration botConfiguration() {
        return new BotConfiguration(type, name, baseurl, callback, driverName);
    }
}
