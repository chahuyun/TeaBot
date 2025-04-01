package cn.chahuyun.teabot.core.event;

import cn.chahuyun.teabot.api.annotation.SPI;
import cn.chahuyun.teabot.api.event.Event;
import cn.chahuyun.teabot.api.event.EventBus;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-6 16:40
 */
@SPI
public class EventChannelBus implements EventBus {

    /**
     * 广播事件
     *
     * @param event 事件
     */
    @Override
    public void fire(Event event) {
        GlobalEventChannel.getInstance().fire(event);
    }
}