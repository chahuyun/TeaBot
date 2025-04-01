package cn.chahuyun.teabot.core.message;


import cn.chahuyun.teabot.api.message.MessageChain;
import cn.chahuyun.teabot.api.message.SingleMessage;
import cn.chahuyun.teabot.common.message.MessageKey;

import java.util.List;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:37
 */

public class MessageChainImpl extends AbstractMessageChain implements MessageChain {

    public MessageChainImpl(List<SingleMessage> messages) {
        super.addAll(messages); // 将元素添加到父类ArrayList
    }

    @Override
    public String content() {
        StringBuilder builder = new StringBuilder();
        for (SingleMessage message : this) { // 遍历父类元素
            builder.append(message.content());
        }
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <M extends SingleMessage> M get(MessageKey key) {
        for (SingleMessage message : this) { // 遍历父类元素
            if (message.getType().equals(key)) {
                return (M) message;
            }
        }
        return null;
    }

}
