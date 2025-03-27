package cn.chahuyun.teabot.api.factory;

import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Group;
import cn.chahuyun.teabot.api.contact.User;
import cn.chahuyun.teabot.api.event.FriendMessageEvent;
import cn.chahuyun.teabot.api.event.GroupMessageEvent;
import cn.chahuyun.teabot.api.message.MessageChain;

/**
 * 事件类型工厂
 *
 * @author Moyuyanli
 * @date 2025-3-27 14:05
 */
public interface MessageEventFactory {

    /**
     * 创建群消息事件
     * @param bot bot
     * @param group 群
     * @param sender 发送者
     * @param message 消息
     * @param time 时间
     * @return GroupMessageEvent
     */
    GroupMessageEvent createGroupMessageEvent(Bot bot, Group group, User sender, MessageChain message,long time);


    /**
     * 创建好友消息事件
     * @param bot bot
     * @param sender 发送者
     * @param message 消息
     * @param time 时间
     * @return FriendMessageEvent
     */
    FriendMessageEvent createFriendMessageEvent(Bot bot, User sender, MessageChain message,long time);

}
