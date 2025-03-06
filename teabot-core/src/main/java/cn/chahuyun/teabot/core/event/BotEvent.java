package cn.chahuyun.teabot.core.event;

import cn.chahuyun.teabot.core.bot.Bot;

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
    Bot getBot();


}
