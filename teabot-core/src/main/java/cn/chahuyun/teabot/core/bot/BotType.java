package cn.chahuyun.teabot.core.bot;

import lombok.Getter;

/**
 * bot类型枚举
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:50
 */
@Getter
public enum BotType {

    WECHAT("WeChat"),

    QQ("QQ");

    private final String type;

    BotType(String type) {
        this.type = type;
    }

}
