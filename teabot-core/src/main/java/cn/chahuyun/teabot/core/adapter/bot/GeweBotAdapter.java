package cn.chahuyun.teabot.core.adapter.bot;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import cn.chahuyun.teabot.core.event.MessageEvent;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-26 17:30
 */
public class GeweBotAdapter implements BotAdapter{


    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public boolean sendMessage(String message) {
        return false;
    }

    @Override
    public boolean sendImage(String imageUrl) {
        return false;
    }

    /**
     * 消息通道
     *
     * @return 任意消息事件类型
     */
    @Override
    public <E extends MessageEvent> E messageSource() {
        return null;
    }
}
