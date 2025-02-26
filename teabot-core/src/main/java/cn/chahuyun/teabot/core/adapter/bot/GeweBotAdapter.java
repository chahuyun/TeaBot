package cn.chahuyun.teabot.core.adapter.bot;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-26 17:30
 */
public class GeweBotAdapter implements BotAdapter{
    @Override
    public boolean login(BotConfiguration configuration) {
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

    @Override
    public boolean messageSource() {
        //回调接口返回消息
        return false;
    }
}
