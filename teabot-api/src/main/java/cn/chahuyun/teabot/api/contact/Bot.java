package cn.chahuyun.teabot.api.contact;


import cn.chahuyun.teabot.api.config.BotAdapter;
import cn.chahuyun.teabot.api.message.MessageChain;

/**
 * bot抽象层
 *
 * @author Moyuyanli
 * @date 2025-2-21 13:48
 */
public interface Bot extends ContactOrBot {

    /**
     * 获取机器人的适配器
     * @return BotAdapter 适配器
     */
    BotAdapter getAdapter();

    /**
     * 是否在线
     * @return true 在线
     */
    boolean isOnline();

    /**
     * 登录
     */
    void login();

    /**
     * 登出
     */
    void logout();

    /**
     * 获取好友
     * @param id id
     * @return Friend 好友
     */
    Friend getFriend(String id);

    /**
     * 发送消息
     * @param message 消息
     */
    void sendMessage(MessageChain message);

    //todo 发送消息的抽象方法

}
