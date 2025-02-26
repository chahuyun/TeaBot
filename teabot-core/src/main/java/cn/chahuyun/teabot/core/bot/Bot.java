package cn.chahuyun.teabot.core.bot;


import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import cn.chahuyun.teabot.core.contact.ContactOrBot;
import cn.chahuyun.teabot.core.contact.Friend;

/**
 * bot抽象层
 *
 * @author Moyuyanli
 * @date 2025-2-21 13:48
 */
public interface Bot extends ContactOrBot {

    /**
     * 机器人配置
     * @return BotConfiguration 配置
     */
    public BotConfiguration getConfiguration();

    /**
     * 所属bot
     * @return bot
     */
    public Bot getBot();

    /**
     * 是否在线
     * @return true 在线
     */
    public boolean isOnline();


    /**
     * 获取好友
     * @param id id
     * @return Friend 好友
     */
    public Friend getFriend(String id);


    //todo 发送消息的抽象方法

}
