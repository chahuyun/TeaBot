package cn.chahuyun.teabot.api.factory;

import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Friend;
import cn.chahuyun.teabot.api.contact.Group;
import cn.chahuyun.teabot.api.contact.Member;

import java.util.List;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 14:52
 */
public interface ContactFactory {


    /**
     * 获取群
     *
     * @param bot bot
     * @param groupId 群id
     * @param groupName 群名
     * @param groupAvatar 群头像
     * @param groupOwner 群主id
     * @param groupMembers 群成员id
     * @return cn.chahuyun.teabot.api.contact.Group
     * @author Moyuyanli
     * @date 2025-3-28 17:05
     */
    Group getGroup(Bot bot, String groupId, String groupName, String groupAvatar, String groupOwner, List<String> groupMembers);


    /**
     * 获取群成员
     *
     * @param bot bot
     * @param group 群
     * @param memberId 成员id
     * @param memberName 成员名
     * @param memberAvatar 成员头像
     * @return cn.chahuyun.teabot.api.contact.Member
     * @author Moyuyanli
     * @date 2025-3-28 17:18
     */
    Member getMember(Bot bot, String groupId, String memberId ,String memberName ,String memberAvatar);

    /**
     * 获取好友
     * @param bot bot
     * @param friendId 好友id
     * @return Friend
     */
    Friend getFriend(Bot bot, String friendId);

}
