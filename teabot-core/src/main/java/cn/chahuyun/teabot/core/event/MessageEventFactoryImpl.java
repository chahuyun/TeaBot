package cn.chahuyun.teabot.core.event;

import cn.chahuyun.teabot.api.annotation.SPI;
import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Group;
import cn.chahuyun.teabot.api.contact.User;
import cn.chahuyun.teabot.api.event.FriendMessageEvent;
import cn.chahuyun.teabot.api.event.GroupMessageEvent;
import cn.chahuyun.teabot.api.factory.MessageEventFactory;
import cn.chahuyun.teabot.api.message.MessageChain;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 17:27
 */
@SPI
public class MessageEventFactoryImpl implements MessageEventFactory {
    /**
     * 创建群消息事件
     *
     * @param bot     bot
     * @param group   群
     * @param sender  发送者
     * @param message 消息
     * @param time    时间
     * @return GroupMessageEvent
     */
    @Override
    public GroupMessageEvent createGroupMessageEvent(Bot bot, Group group, User sender, MessageChain message, long time) {
        return new GroupMessageEventImpl(bot, group, sender, message, time);
    }

    /**
     * 创建好友消息事件
     *
     * @param bot     bot
     * @param sender  发送者
     * @param message 消息
     * @param time    时间
     * @return FriendMessageEvent
     */
    @Override
    public FriendMessageEvent createFriendMessageEvent(Bot bot, User sender, MessageChain message, long time) {
        return null;
    }
}
