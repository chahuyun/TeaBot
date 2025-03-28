package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.factory.SingleMessageFactory;
import cn.chahuyun.teabot.api.message.PlainText;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 17:20
 */
public class SingleMessageFactoryImpl implements SingleMessageFactory {
    @Override
    public PlainText of(String content) {
        return new PlainTextImpl(content);
    }
}
