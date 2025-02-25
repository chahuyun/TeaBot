package cn.chahuyun.teabot.core.bot;


import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-21 13:59
 */
@Getter
@Setter
public class BotConfiguration {

    /**
     * 机器人类型
     */
    private BotType type;

    //这里或许应该做类型区分再接入详细配置？

    /**
     * 模拟用户id
     */
    private String userId;
    /**
     * 模拟设备id
     */
    private String driveName;
    /**
     * 请求地址
     */
    private String baseUrl;
    /**
     * 回调地址
     */
    private String callback;


}
