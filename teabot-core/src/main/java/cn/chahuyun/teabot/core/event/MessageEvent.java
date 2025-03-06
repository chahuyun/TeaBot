package cn.chahuyun.teabot.core.event;

import cn.chahuyun.teabot.core.contact.Contact;
import cn.chahuyun.teabot.core.contact.User;
import cn.chahuyun.teabot.core.message.MessageChain;

/**
 * 消息事件
 *
 * @author Moyuyanli
 * @date 2025-3-6 14:56
 */
public interface MessageEvent extends BotPassiveEvent,Event {

    Contact getSubject();

    User getSender();

    String getSenderName();

    MessageChain getMessage();

    int getTime();

    //暂时没有想法实现
//     getSource();

}
