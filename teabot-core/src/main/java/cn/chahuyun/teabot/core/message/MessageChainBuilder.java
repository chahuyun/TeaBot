package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.message.MessageChain;
import cn.chahuyun.teabot.api.message.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 16:45
 */
public class MessageChainBuilder implements MessageChain.Builder {
    private final List<SingleMessage> messages = new ArrayList<>();

    @Override
    public MessageChain.Builder add(SingleMessage message) {
        messages.add(message);
        return this;
    }

    @Override
    public MessageChain build() {
        return new MessageChainImpl(messages);
    }
}
