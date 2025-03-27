package cn.chahuyun.teabot.api.config;

import cn.chahuyun.teabot.api.contact.Contact;
import cn.chahuyun.teabot.api.contact.Friend;
import cn.chahuyun.teabot.api.message.MessageReceipt;

import java.io.Serializable;

/**
 * bot适配器
 *
 * @author Moyuyanli
 * @date 2025-2-26 17:12
 */
public interface BotAdapter extends Serializable {

    /**
     * 序列化id
     */
    long serialVersionUID = 1L;

    /**
     * 获取配置信息
     * @return BotConfig
     */
    BotConfig getConfig();

    /**
     * 获取这个适配器服务的botid
     * @return botid
     */
    String getId();

    /**
     * 当前bot是否在线
     * @return true 在线
     */
    boolean isOnline();

    /**
     * 登录
     * @return true 成功
     */
    boolean login();

    /**
     * 登出
     * @return 成功
     */
    boolean logout();

    /**
     * 发送消息
     * @param message 消息
     * @return true 成功
     */
    <C extends Contact> boolean sendMessage(MessageReceipt<C> message);

    /**
     * 监听消息
     */
    void listeningMessages();

    /**
     * 获取好友信息
     * @param id 好友id
     * @return 好友
     */
    Friend getFriend(String id);

}
