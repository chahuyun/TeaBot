package cn.chahuyun.teabot.api.factory;

import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Group;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 14:52
 */
public interface ContactFactory {

    /**
     * 获取群
     * @param bot bot
     * @param groupId 群id
     * @return Group
     */
    Group getGroup(Bot bot,String groupId);

}
