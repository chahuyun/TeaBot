package cn.chahuyun.teabot.api.message;

import java.util.List;
import java.util.ServiceLoader;

/**
 * 消息链
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:36
 */
public interface MessageChain extends Message, List<SingleMessage> {

    /**
     * 根据给定的 {@link MessageKey } 获取对应类型的第一个消息元素
     *
     * @param key 消息类型的标识符
     * @param <M> 消息类型
     * @return 对应类型的消息元素，如果没有找到则返回 null
     */
    <M extends SingleMessage> M get(MessageKey key);


    /**
     * 消息链构建器
     */
    interface Builder {
        Builder add(SingleMessage message);
        MessageChain build();
    }

    /**
     * 获取消息链构建器
     * @return Builder
     */
    static Builder builder() {
        ServiceLoader<Builder> loader = ServiceLoader.load(Builder.class);
        return loader.findFirst()
                .orElseThrow(() -> new RuntimeException("未找到生成器实现!"));
    }

}
