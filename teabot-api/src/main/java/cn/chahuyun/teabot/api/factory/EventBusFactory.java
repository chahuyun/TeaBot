package cn.chahuyun.teabot.api.factory;

import cn.chahuyun.teabot.api.event.Event;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 11:30
 */
public interface EventBusFactory {

    /**
     * 广播事件
     * @param event 事件
     */
    void fire(Event event);

}
