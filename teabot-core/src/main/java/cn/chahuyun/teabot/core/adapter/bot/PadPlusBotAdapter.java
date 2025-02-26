package cn.chahuyun.teabot.core.adapter.bot;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;

/**
 * padPlus的bot适配
 *
 * @author Moyuyanli
 * @date 2025-2-26 17:17
 */
public class PadPlusBotAdapter implements BotAdapter{


    @Override
    public boolean login(BotConfiguration configuration) {
        configuration.getBaseUrl();
        //通过配置获取二维码，
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
        //心跳调用检查新消息
        return false;
    }
}
