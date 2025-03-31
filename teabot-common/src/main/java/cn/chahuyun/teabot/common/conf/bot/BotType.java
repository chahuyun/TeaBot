package cn.chahuyun.teabot.common.conf.bot;

import lombok.Getter;

/**
 * bot类型枚举
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:50
 */
@Getter
public enum BotType {

    PAD_PLUS("WeChat"),

    GEWE("WeChat"),

    ONE_BOT("QQ");


    private final String type;

    BotType(String type) {
        this.type = type;
    }

}
