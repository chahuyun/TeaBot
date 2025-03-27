package cn.chahuyun.teabot.api.event;

/**
 * 被动bot事件
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:23
 */
public interface BotPassiveEvent extends BotEvent{

    /**
     * 获取动作时间
     * @return long
     */
    long getTime();


}
