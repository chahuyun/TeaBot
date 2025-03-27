package cn.chahuyun.teabot.api.event;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 11:28
 */
public interface EventBus {
    /**
     * 广播事件
     * @param event 事件
     */
    void fire(Event event);

}