package cn.chahuyun.teabot.repository.bot.entity;


import cn.chahuyun.teabot.api.config.BotConfig;
import cn.chahuyun.teabot.common.conf.bot.BotType;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
public class BotConfigEntity implements BotConfig, Serializable {

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
     * bot-id
     */
    @Column(unique = true, nullable = false)
    private String botId;

    /**
     * bot基础连接地址
     */
    private String baseurl;

    /**
     * 启动状态
     */
    @ColumnDefault("false")
    private Boolean status = false;

    /**
     * 额外参数
     */
    private String extra;


    @Transient
    @Expose(serialize = false)
    private Map<String, String> unique = new HashMap<>();

    /**
     * 获取bot类型
     *
     * @return BotType
     */
    @Override
    public BotType getBotType() {
        return type;
    }

    @Override
    public String getUserId() {
        return botId;
    }

    @Override
    public String getBaseUrl() {
        return baseurl;
    }

    public void setExtra(String extra) {
        this.extra = extra;
        if (extra == null) {
            return;
        }
        String[] split = extra.split(",");
        for (String s : split) {
            String[] split1 = s.split("=");
            unique.put(split1[0], split1[1]);
        }
    }

    /**
     * 获取其他配置
     *
     * @param key key
     * @return value
     */
    @Override
    public String get(String key) {
        return unique.get(key);
    }

    /**
     * 保存其他配置
     *
     * @param key   key
     * @param value value
     */
    @Override
    public void set(String key, String value) {
        unique.put(key, value);
        extra = unique.toString();
    }
}
