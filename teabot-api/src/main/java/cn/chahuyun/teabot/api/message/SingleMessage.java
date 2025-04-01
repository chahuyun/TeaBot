package cn.chahuyun.teabot.api.message;

import cn.chahuyun.teabot.common.message.MessageKey;

/**
 * 单个消息
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:35
 */
public interface SingleMessage extends Message {


    /**
     * 获取消息类型
     * @return MessageKey
     */
    MessageKey getType();

    /**
     * 类型转换
     * @param type 类型
     * @return 转换消息类型
     */

    default <T> T as(Class<T> type) {
        if (type.isInstance(this)) {
            return type.cast(this);
        }
        return null;
    }
}

