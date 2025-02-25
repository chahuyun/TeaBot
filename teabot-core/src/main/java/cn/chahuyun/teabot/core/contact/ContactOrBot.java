package cn.chahuyun.teabot.core.contact;


import cn.chahuyun.teabot.core.bot.Bot;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:29
 */
public interface ContactOrBot {

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
