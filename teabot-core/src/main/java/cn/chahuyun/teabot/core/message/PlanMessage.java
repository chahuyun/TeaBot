package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.message.Message;
import cn.chahuyun.teabot.api.message.SingleMessage;

/**
 * 文本消息
 *
 * @author Moyuyanli
 * @date 2025-3-18 16:15
 */
public class PlanMessage implements Message, SingleMessage {

    /**
     * 以文本形式返回消息
     */
    @Override
    public String content() {
        return null;
    }
}
