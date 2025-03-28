package cn.chahuyun.teabot.api.factory;

import cn.chahuyun.teabot.api.message.PlainText;

/**
 * 单一消息工厂
 *
 * @author Moyuyanli
 * @date 2025-3-27 17:18
 */
public interface SingleMessageFactory {

    PlainText of(String content);


}
