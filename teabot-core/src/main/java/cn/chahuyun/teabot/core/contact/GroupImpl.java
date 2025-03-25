package cn.chahuyun.teabot.core.contact;

import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Group;
import cn.chahuyun.teabot.api.contact.Member;
import cn.chahuyun.teabot.api.message.MessageChain;

import java.util.List;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-24 16:25
 */
public class GroupImpl implements Group {

    //todo 实现群

    /**
     * 用户id，不限于微信id
     *
     * @return 用户id
     */
    @Override
    public String getId() {
        return null;
    }

    /**
     * 所属bot
     *
     * @return bot
     */
    @Override
    public Bot getBot() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getAvatar() {
        return null;
    }

    @Override
    public Member getMember(String id) {
        return null;
    }

    @Override
    public List<Member> getMembers() {
        return null;
    }

    @Override
    public Member getOwner() {
        return null;
    }

    @Override
    public List<Member> getAdmins() {
        return null;
    }

    /**
     * 主动发送消息
     *
     * @param message 消息
     */
    @Override
    public void sendMessage(MessageChain message) {
        //todo 在这里实现发送消息
    }
}
