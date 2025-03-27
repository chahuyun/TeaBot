package cn.chahuyun.teabot.adapter.bot.gewe;

import cn.chahuyun.teabot.api.config.BotAdapter;
import cn.chahuyun.teabot.api.contact.Contact;
import cn.chahuyun.teabot.api.contact.Friend;
import cn.chahuyun.teabot.api.message.MessageReceipt;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-26 17:30
 */
public abstract class GeweBotAdapter implements BotAdapter {


    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

    /**
     * 发送消息
     *
     * @param message 消息
     * @return true 成功
     */
    @Override
    public <C extends Contact> boolean sendMessage(MessageReceipt<C> message) {
        return false;
    }


    /**
     * 监听消息
     */
    @Override
    public void listeningMessages() {

    }

    /**
     * 获取好友信息
     *
     * @param id 好友id
     * @return 好友
     */
    @Override
    public Friend getFriend(String id) {
        return null;
    }


}
