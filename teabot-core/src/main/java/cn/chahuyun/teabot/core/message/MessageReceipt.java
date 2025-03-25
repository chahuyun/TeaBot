package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.contact.Contact;
import cn.chahuyun.teabot.api.message.MessageChain;

/**
 * 发送消息
 *
 * @author Moyuyanli
 * @date 2025-3-25 14:30
 * @param messageChain 消息链
 * @param target 发送消息的目标
 */

public record MessageReceipt<C extends Contact>(MessageChain messageChain, C target) {

}
