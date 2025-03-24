package cn.chahuyun.teabot.api.message;

/**
 * 类型 Key. 由伴生对象实现, 表示一个 [Message] 对象的类型.
 */
public interface MessageKey<M extends SingleMessage> {
    /**
     * 将一个 {@link SingleMessage} 强转为 {@link M} 类型. 在类型不符合时返回 `null`
     */
    M safeCast(SingleMessage message);
}

