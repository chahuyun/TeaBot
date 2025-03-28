package cn.chahuyun.teabot.core.message;


import cn.chahuyun.teabot.api.message.MessageChain;
import cn.chahuyun.teabot.api.message.MessageKey;
import cn.chahuyun.teabot.api.message.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:37
 */
public class MessageChainImpl extends AbstractMessageChain implements MessageChain {
    private final List<SingleMessage> messages;

    public MessageChainImpl(List<SingleMessage> messages) {
        this.messages = new ArrayList<>(messages);
    }

    @Override
    public String content() {
        // 实现消息链的文本拼接逻辑
        return messages.stream()
                .map(SingleMessage::content)
                .reduce("", String::concat);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <M extends SingleMessage> M get(MessageKey key) {
        for (SingleMessage message : messages) {
            if (message.getType().equals(key)) {
                return (M) message;
            }
        }
        return null;
    }

    // 实现 List 接口的方法
    @Override
    public int size() {
        return messages.size();
    }

    @Override
    public SingleMessage get(int index) {
        return messages.get(index);
    }
}
