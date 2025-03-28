package cn.chahuyun.teabot.core.contact;

import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Group;
import cn.chahuyun.teabot.api.contact.Member;
import cn.chahuyun.teabot.api.message.MessageChain;
import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-28 17:13
 */
@Getter
@Setter
public class MemberImpl implements Member {

    private final Bot bot;

    private final String groupId;

    private final String memberId;

    private String memberName;

    private String memberAvatar;

    public MemberImpl(Bot bot, String groupId, String memberId, String memberName, String memberAvatar) {
        this.bot = bot;
        this.groupId = groupId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberAvatar = memberAvatar;
    }

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
    public String getGroupAvatar() {
        return null;
    }

    @Override
    public Group getGroup() {
        return null;
    }

    /**
     * 主动发送消息
     *
     * @param message 消息
     */
    @Override
    public void sendMessage(MessageChain message) {

    }
}
