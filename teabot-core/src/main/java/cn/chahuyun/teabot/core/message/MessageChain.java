package cn.chahuyun.teabot.core.message;

import java.util.List;

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
    <M extends SingleMessage> M get(MessageKey<M> key);

}
