package cn.chahuyun.teabot.api.event;

import cn.chahuyun.teabot.api.contact.Contact;
import cn.chahuyun.teabot.api.contact.User;
import cn.chahuyun.teabot.api.message.MessageChain;

/**
 * 消息事件
 *
 * @author Moyuyanli
 * @date 2025-3-6 14:56
 */
public interface MessageEvent extends BotPassiveEvent,Event {

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
    MessageChain getMessage();



    //暂时没有想法实现
//     getSource();

}
