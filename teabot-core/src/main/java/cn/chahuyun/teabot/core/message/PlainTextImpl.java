package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.message.Message;
import cn.chahuyun.teabot.api.message.PlainText;
import cn.chahuyun.teabot.api.message.SingleMessage;
import cn.chahuyun.teabot.common.message.MessageKey;
import lombok.Getter;

/**
 * 文本消息
 *
 * @author Moyuyanli
 * @date 2025-3-18 16:15
 */
@Getter
public class PlainTextImpl extends AbstractMessageKey implements Message, SingleMessage, PlainText {

    @SuppressWarnings("FieldMayBeFinal")
    private String content;


    public PlainTextImpl(String content) {
        super(MessageKey.PLAIN_TEXT);
        this.content = content;
    }

    /**
     * 以文本形式返回消息
     */
    @Override
    public String content() {
        return content;
    }

}
