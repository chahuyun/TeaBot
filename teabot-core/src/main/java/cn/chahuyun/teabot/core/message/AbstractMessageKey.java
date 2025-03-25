package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.message.MessageKey;
import cn.chahuyun.teabot.api.message.SingleMessage;

import java.util.function.Function;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:44
 */
public abstract class AbstractMessageKey implements SingleMessage {
    protected final MessageKey type;

    public AbstractMessageKey(MessageKey type) {
        this.type = type;
    }

    @Override
    public MessageKey getType() {
        return type;
    }
}