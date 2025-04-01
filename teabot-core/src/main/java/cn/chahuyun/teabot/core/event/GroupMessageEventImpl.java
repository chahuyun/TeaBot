package cn.chahuyun.teabot.core.event;

import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Group;
import cn.chahuyun.teabot.api.contact.User;
import cn.chahuyun.teabot.api.event.GroupMessageEvent;
import cn.chahuyun.teabot.api.message.MessageChain;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 群消息事件
 *
 * @author Moyuyanli
 * @date 2025-3-27 17:28
 */
@Slf4j
@Getter
public class GroupMessageEventImpl implements GroupMessageEvent {

    private final Bot bot;

    private final Group group;

    private final User sender;

    private final MessageChain message;

    private final long time;

    private boolean isIntercepted = false;


    public GroupMessageEventImpl(Bot bot, Group group, User sender, MessageChain message, long time) {
        this.bot = bot;
        this.group = group;
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    /**
     * 获取bot
     *
     * @return Bot
     */
    @Override
    public Bot bot() {
        return bot;
    }

    /**
     * 获取动作时间
     *
     * @return long
     */
    @Override
    public long time() {
        return time;
    }

    /**
     * 事件是否被拦截
     *
     * @return true 已经被拦截
     */
    @Override
    public boolean isIntercepted() {
        return isIntercepted;
    }

    /**
     * 拦截事件
     */
    @Override
    public void intercept() {
        isIntercepted = true;
    }

    /**
     * 获取群
     *
     * @return Group
     */
    @Override
    public Group getSubject() {
        return group;
    }

    /**
     * 获取消息发送者
     *
     * @return User
     */
    @Override
    public User getSender() {
        return sender;
    }

    /**
     * 获取消息发送者名称
     *
     * @return String
     */
    @Override
    public String getSenderName() {
        return sender.getName();
    }

    /**
     * 获取消息内容
     *
     * @return MessageChain
     */
    @Override
    public MessageChain getMessageChain() {
        return message;
    }

    /**
     * 向那个载体发送消息
     *
     * @param messageChain 消息
     */
    @Override
    public void sendMessage(MessageChain messageChain) {
        getGroup().sendMessage(messageChain);
    }
}
