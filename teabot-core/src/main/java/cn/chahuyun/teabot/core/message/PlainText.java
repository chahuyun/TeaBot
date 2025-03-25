package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.message.Message;
import cn.chahuyun.teabot.api.message.SingleMessage;

/**
 * 文本消息
 *
 * @author Moyuyanli
 * @date 2025-3-18 16:15
 */
public class PlainText extends AbstractMessageKey<PlainText> implements Message, SingleMessage {

    private String content;


    public PlainText(String content) {
        super(singleMessage -> {
            if (singleMessage instanceof PlainText plainText) {
                return plainText;
            }
            return null;
        });
    }

    /**
     * 以文本形式返回消息
     */
    @Override
    public String content() {
        return null;
    }

}
