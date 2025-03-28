package cn.chahuyun.teabot.core.contact;

import cn.chahuyun.teabot.api.annotation.SPI;
import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Friend;
import cn.chahuyun.teabot.api.contact.Group;
import cn.chahuyun.teabot.api.contact.Member;
import cn.chahuyun.teabot.api.factory.ContactFactory;

import java.util.List;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 17:33
 */
@SPI
public class ContactFactoryImpl implements ContactFactory {


    /**
     * 获取群
     *
     * @param bot          bot
     * @param groupId      群id
     * @param groupName    群名
     * @param groupAvatar  群头像
     * @param groupOwner   群主id
     * @param groupMembers 群成员id
     * @return cn.chahuyun.teabot.api.contact.Group
     * @author Moyuyanli
     * @date 2025-3-28 17:05
     */
    @Override
    public Group getGroup(Bot bot, String groupId, String groupName, String groupAvatar, String groupOwner, List<String> groupMembers) {
        return new GroupImpl(bot, groupId, groupName, groupAvatar, groupOwner, groupMembers);
    }

    /**
     * 获取群成员
     *
     * @param bot          bot
     * @param groupId        群
     * @param memberId     成员id
     * @param memberName   成员名
     * @param memberAvatar 成员头像
     * @return cn.chahuyun.teabot.api.contact.Member
     * @author Moyuyanli
     * @date 2025-3-28 17:18
     */
    @Override
    public Member getMember(Bot bot, String groupId, String memberId, String memberName, String memberAvatar) {
        return new MemberImpl(bot, groupId, memberId, memberName, memberAvatar);
    }



    /**
     * 获取好友
     *
     * @param bot      bot
     * @param friendId 好友id
     * @return Friend
     */
    @Override
    public Friend getFriend(Bot bot, String friendId) {
        return null;
    }
}
