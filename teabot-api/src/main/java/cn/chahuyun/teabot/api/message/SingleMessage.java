package cn.chahuyun.teabot.api.message;

/**
 * 单个消息
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:35
 */
public interface SingleMessage extends Message {

    /**
     * 返回这个消息类型的类型名称
     * @return 类型名称
     */
    String key();


}
