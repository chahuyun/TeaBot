package cn.chahuyun.teabot.core.adapter.bot;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import cn.chahuyun.teabot.core.event.MessageEvent;
import cn.chahuyun.teabot.core.message.MessageChain;

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
     * @return 任意消息事件类型
     */
    public <E extends MessageEvent> E messageSource();





}
