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
public abstract class AbstractMessageKey<M extends SingleMessage> implements MessageKey<M>, SingleMessage {
    private final Function<SingleMessage, M> safeCastFunction;


    public AbstractMessageKey(Function<SingleMessage, M> safeCastFunction) {
        this.safeCastFunction = safeCastFunction;
    }

    @Override
    public M safeCast(SingleMessage message) {
        return safeCastFunction.apply(message);
    }

    /**
     * 返回泛型 M 的类型名称
     */
    @Override
    public String key() {
        // 获取当前类的泛型参数类型
        Class<?> clazz = getClass();
        while (!clazz.getSuperclass().equals(AbstractMessageKey.class)) {
            clazz = clazz.getSuperclass();
        }
        return clazz.getSimpleName(); // 返回类名
    }

}