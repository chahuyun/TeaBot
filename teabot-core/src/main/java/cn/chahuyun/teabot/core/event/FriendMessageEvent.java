package cn.chahuyun.teabot.core.event;

import cn.chahuyun.teabot.core.bot.Bot;
import cn.chahuyun.teabot.core.contact.Contact;
import cn.chahuyun.teabot.core.contact.User;
import cn.chahuyun.teabot.core.message.MessageChain;

/**
 * 好友消息事件
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:00
 */
public class FriendMessageEvent implements MessageEvent{
    /**
     * 获取bot
     *
     * @return Bot
     */
    @Override
    public Bot getBot() {
        return null;
    }

    /**
     * 事件是否被拦截
     *
     * @return true 已经被拦截
     */
    @Override
    public boolean isIntercepted() {
        return false;
    }

    /**
     * 拦截事件
     */
    @Override
    public void intercept() {

    }

    @Override
    public Contact getSubject() {
        return null;
    }

    @Override
    public User getSender() {
        return null;
    }

    @Override
    public String getSenderName() {
        return null;
    }

    @Override
    public MessageChain getMessage() {
        return null;
    }

    @Override
    public int getTime() {
        return 0;
    }
}
