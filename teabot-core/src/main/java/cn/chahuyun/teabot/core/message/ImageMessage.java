package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.message.Message;
import cn.chahuyun.teabot.api.message.SingleMessage;

import java.util.function.Function;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-24 17:31
 */
public class ImageMessage extends AbstractMessageKey<ImageMessage> implements Message, SingleMessage {
    public ImageMessage(Function<SingleMessage, ImageMessage> safeCastFunction) {
        super(safeCastFunction);
    }

    /**
     * 以文本形式返回消息
     */
    @Override
    public String content() {
        return null;
    }
}
