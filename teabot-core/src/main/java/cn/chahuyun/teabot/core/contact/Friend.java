package cn.chahuyun.teabot.core.contact;


import cn.chahuyun.teabot.core.bot.Bot;

/**
 * 好友
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:46
 */
public interface Friend extends ContactOrBot {

    /**
     * 用户id，不限于微信id
     * @return 用户id
     */
    public String getId();

    /**
     * 所属bot
     * @return bot
     */
    public Bot getBot();

}
