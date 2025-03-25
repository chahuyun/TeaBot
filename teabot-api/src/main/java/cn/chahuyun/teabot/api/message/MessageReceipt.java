package cn.chahuyun.teabot.api.message;

import cn.chahuyun.teabot.api.contact.Contact;

/**
 * 发送消息源
 *
 * @author Moyuyanli
 * @date 2025-3-25 14:40
 */
public interface MessageReceipt<C extends Contact> {

    /**
     * 获取消息链
     * @return 发送消息的消息链
     */
    MessageChain getMessageChain();

    /**
     * 获取消息目标
     * @return 发送消息的目标
     */
    C getTarget();

}
