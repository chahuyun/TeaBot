package cn.chahuyun.teabot.core.event;

import cn.chahuyun.teabot.api.event.Event;
import cn.chahuyun.teabot.core.event.lamda.EventChainContext;
import cn.chahuyun.teabot.core.event.lamda.EventHandler;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-6 16:40
 */
public class EventChannel {
    private final Map<Class<? extends Event>, List<HandlerRegistration<?>>> handlers = new ConcurrentHashMap<>();
    private final Comparator<HandlerRegistration<?>> priorityComparator =
            Comparator.<HandlerRegistration<?>>comparingInt(HandlerRegistration::getPriority).reversed();

    // 订阅事件（带泛型类型推断）
    public <E extends Event> SubscriptionBuilder<E> subscribe(Class<E> eventType) {
        return new SubscriptionBuilder<>(eventType);
    }

    //todo 事件通道

    // 触发事件
    public void fire(Event event) {
        Class<? extends Event> eventType = event.getClass();
        List<HandlerRegistration<?>> registrations = handlers.get(eventType);

        if (registrations != null) {
            EventChainContext context = new EventChainContext();
            for (HandlerRegistration<?> registration : registrations) {
                if (context.isInterrupted()) break;

                // 类型安全转换逻辑
                processRegistration(event, context, registration);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <E extends Event> void processRegistration(Event event, EventChainContext context, HandlerRegistration<?> registration) {
        HandlerRegistration<E> typedReg = (HandlerRegistration<E>) registration;
        if (typedReg.filter.test((E) event)) {
            typedReg.handler.handle((E) event, context);
        }
    }

    // 订阅构建器（支持链式配置）
    public class SubscriptionBuilder<E extends Event> {
        private final Class<E> eventType;
        private Predicate<E> filter = e -> true;
        private EventPriorityEnum priority = EventPriorityEnum.NORMAL;
        private EventHandler<E> handler;

        public SubscriptionBuilder(Class<E> eventType) {
            this.eventType = eventType;
        }

        public SubscriptionBuilder<E> filter(Predicate<E> filter) {
            this.filter = filter;
            return this;
        }

        public SubscriptionBuilder<E> priority(EventPriorityEnum priority) {
            this.priority = priority;
            return this;
        }

        public void handle(EventHandler<E> handler) {
            this.handler = handler;
            registerHandler();
        }

        private void registerHandler() {
            handlers.compute(eventType, (key, existing) -> {
                List<HandlerRegistration<?>> list = (existing != null) ? existing : new CopyOnWriteArrayList<>();
                list.add(new HandlerRegistration<>(filter, priority, handler));
                list.sort(priorityComparator); // 按优先级排序
                return list;
            });
        }
    }

    /**
     *  处理器注册信息封装
     * @param <E>
     */
    private record HandlerRegistration<E extends Event>(Predicate<E> filter,
                                                        EventPriorityEnum priority,
                                                        EventHandler<E> handler) {

        int getPriority() {
            return priority.getPriority();
        }
    }
}