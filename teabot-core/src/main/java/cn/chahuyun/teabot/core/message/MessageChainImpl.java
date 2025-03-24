package cn.chahuyun.teabot.core.message;


import cn.chahuyun.teabot.api.message.MessageChain;
import cn.chahuyun.teabot.api.message.MessageKey;
import cn.chahuyun.teabot.api.message.SingleMessage;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:37
 */
public class MessageChainImpl extends AbstractMessageChain implements MessageChain {

    /**
     * 以文本形式返回消息
     */
    @Override
    public String content() {
        return null;
    }

    /**
     * 根据给定的 {@link MessageKey } 获取对应类型的第一个消息元素
     *
     * @param key 消息类型的标识符
     * @return 对应类型的消息元素，如果没有找到则返回 null
     */
    @Override
    public <M extends SingleMessage> M get(MessageKey<M> key) {
        return null;
    }
}
