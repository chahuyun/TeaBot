package cn.chahuyun.teabot.api.message;

import cn.chahuyun.teabot.api.factory.SingleMessageFactory;
import cn.chahuyun.teabot.util.SpiUtil;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-25 16:35
 */
public interface PlainText extends SingleMessage {

    static PlainText of(String content) {
        SingleMessageFactory factory = SpiUtil.getImpl(SingleMessageFactory.class);
        return factory.of(content);
    }

}
