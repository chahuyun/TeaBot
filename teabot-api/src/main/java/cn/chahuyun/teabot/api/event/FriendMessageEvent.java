package cn.chahuyun.teabot.api.event;

import cn.chahuyun.teabot.api.contact.Friend;
import cn.chahuyun.teabot.api.event.MessageEvent;

/**
 * 好友消息事件
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:00
 */
public interface FriendMessageEvent extends MessageEvent {

    /**
     * 获取好友
     * @return Friend
     */
    Friend getSubject();

}
