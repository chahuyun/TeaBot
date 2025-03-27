package cn.chahuyun.teabot.api.event;

import cn.chahuyun.teabot.api.contact.Group;

/**
 * 群消息事件
 *
 * @author Moyuyanli
 * @date 2025-3-6 14:59
 */
public interface GroupMessageEvent extends MessageEvent {

    /**
     * 获取群
     * @return Group
     */
    Group getSubject();

}
