package cn.chahuyun.teabot.core.contact;

import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Group;
import cn.chahuyun.teabot.api.contact.Member;
import cn.chahuyun.teabot.api.message.MessageChain;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-24 16:25
 */
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class GroupImpl implements Group {

    private final Bot bot;

    private final String groupId;

    private String groupName;

    private String groupAvatar;

    private String groupOwner;

    private List<String> groupMembers;

    public GroupImpl(Bot bot, String groupId, String groupName, String groupAvatar, String groupOwner, List<String> groupMembers) {
        this.bot = bot;
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupAvatar = groupAvatar;
        this.groupOwner = groupOwner;
        this.groupMembers = groupMembers;
    }

    /**
     * 用户id，不限于微信id
     *
     * @return 用户id
     */
    @Override
    public String getId() {
        return groupId;
    }

    /**
     * 所属bot
     *
     * @return bot
     */
    @Override
    public Bot getBot() {
        return bot;
    }

    @Override
    public String getName() {
        return groupName;
    }

    public String getGroupAvatar() {
        return groupAvatar;
    }

    @Override
    public Member getMember(String id) {
        return bot.getAdapter().getMember(groupId, id);
    }

    @Override
    public List<Member> getMembers() {
        return groupMembers.stream().map(it -> bot.getAdapter().getMember(groupId, it)).collect(Collectors.toList());
    }

    @Override
    public Member getOwner() {
        return bot.getAdapter().getMember(groupId, groupOwner);
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
