package cn.chahuyun.teabot.core.adapter.bot;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;

/**
 * bot适配器
 *
 * @author Moyuyanli
 * @date 2025-2-26 17:12
 */
public interface BotAdapter {

    public boolean login();

    public boolean logout();

    public boolean sendMessage(String message);

    public boolean sendImage(String imageUrl);

    /**
     * 消息通道
     * @return
     */
    public boolean messageSource();





}
