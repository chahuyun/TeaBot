package cn.chahuyun.teabot.api.event;

import cn.chahuyun.teabot.api.contact.Bot;

/**
 * bot事件
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:23
 */
public interface BotEvent  extends Event{

    /**
     * 获取bot
     * @return Bot
     */
    Bot bot();


}
