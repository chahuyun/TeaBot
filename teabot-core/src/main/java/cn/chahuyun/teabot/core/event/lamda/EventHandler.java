package cn.chahuyun.teabot.core.event.lamda;

import cn.chahuyun.teabot.api.event.Event;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-6 16:38
 */
@FunctionalInterface
public interface EventHandler<E extends Event> {
    void handle(E event, EventChainContext context);
}
