package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.contact.Contact;
import cn.chahuyun.teabot.api.message.MessageChain;
import cn.chahuyun.teabot.api.message.MessageReceipt;

/**
 * 发送消息源实现
 *
 * @author Moyuyanli
 * @date 2025-3-25 14:30
 */

public class MessageReceiptImpl<C extends Contact> implements MessageReceipt<C> {

    MessageChain messageChain;
    C target;

    public MessageReceiptImpl(MessageChain messageChain, C target) {
        this.messageChain = messageChain;
        this.target = target;
    }

    /**
     * 获取消息链
     *
     * @return 发送消息的消息链
     */
    @Override
    public MessageChain getMessageChain() {
        return messageChain;
    }

    /**
     * 获取消息目标
     *
     * @return 发送消息的目标
     */
    @Override
    public C getTarget() {
        return target;
    }
}
