package cn.chahuyun.teabot.api.event;

import cn.chahuyun.teabot.api.contact.Contact;
import cn.chahuyun.teabot.api.contact.User;
import cn.chahuyun.teabot.api.message.MessageChain;
import cn.chahuyun.teabot.api.message.PlainText;

/**
 * 消息事件
 *
 * @author Moyuyanli
 * @date 2025-3-6 14:56
 */
public interface MessageEvent extends BotPassiveEvent, Event {

    /**
     * 获取消息接受载体
     * @return Contact
     */
    Contact getSubject();

    /**
     * 获取消息发送者
     * @return User
     */
    User getSender();

    /**
     * 获取消息发送者名称
     * @return String
     */
    String getSenderName();

    /**
     * 获取消息内容
     * @return MessageChain
     */
    MessageChain getMessageChain();

    /**
     * 向那个载体发送消息
     * @param messageChain 消息
     */
    default void sendMessage(MessageChain messageChain) {
        getSubject().sendMessage(messageChain);
    }


    /**
     * 向那个载体发送消息
     * @param message 消息
     */
    default void sendMessage(String message) {
        getSubject().sendMessage(MessageChain.builder().add(PlainText.of(message)).build());
    }

    //暂时没有想法实现
//     getSource();

}
