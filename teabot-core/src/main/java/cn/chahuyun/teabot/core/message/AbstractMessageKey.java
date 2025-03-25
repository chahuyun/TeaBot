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
public abstract class AbstractMessageKey<M extends SingleMessage> implements MessageKey<M> {
    private final Function<SingleMessage, M> safeCastFunction;

    public AbstractMessageKey(Function<SingleMessage, M> safeCastFunction) {
        this.safeCastFunction = safeCastFunction;
    }

    @Override
    public M safeCast(SingleMessage message) {
        return safeCastFunction.apply(message);
    }

    String getKey() {
        return MessageKey.class.getSimpleName();
    }

}