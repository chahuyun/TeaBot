package cn.chahuyun.teabot.api.contact;

import cn.chahuyun.teabot.api.message.MessageChain;

/**
 * 联系人
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:26
 */
public interface Contact extends ContactOrBot{

    /**
     * 主动发送消息
     * @param message 消息
     */
    void sendMessage(MessageChain message);

}
