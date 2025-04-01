package cn.chahuyun.teabot.core.event;


import cn.chahuyun.teabot.api.event.Event;
import cn.chahuyun.teabot.core.event.lamda.EventChainContext;
import cn.chahuyun.teabot.core.event.lamda.EventHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

/**
 * 全局唯一的事件通道，用于事件发布-订阅管理
 *
 * @author Moyuyanli
 * @date 2025-3-6 16:40
 */
public class GlobalEventChannel {

    // 单例实例，使用volatile保证可见性
    private static volatile GlobalEventChannel instance;

    // 事件处理器存储结构
    private final Map<Class<? extends Event>, List<HandlerRegistration<?>>> handlers = new ConcurrentHashMap<>();
    // 优先级比较器（降序排列）
    private final Comparator<HandlerRegistration<?>> priorityComparator =
            Comparator.<HandlerRegistration<?>>comparingInt(HandlerRegistration::getPriority).reversed();

    /**
     * 私有化构造函数以防止外部实例化
     */
    private GlobalEventChannel() {
    }

    /**
     * 获取全局唯一实例（双重校验锁实现线程安全）
     *
     * @return 事件通道实例
     */
    public static GlobalEventChannel getInstance() {
        if (instance == null) {
            synchronized (GlobalEventChannel.class) {
                if (instance == null) {
                    instance = new GlobalEventChannel();
                }
            }
        }
        return instance;
    }

    /**
     * 静态订阅入口
     *
     * @param eventType 事件类型
     * @param <E>       事件泛型
     * @return 订阅构建器
     */
    public static <E extends Event> SubscriptionBuilder<E> subscribe(Class<E> eventType) {
        return getInstance().doSubscribe(eventType);
    }

    /**
     * 实例级订阅方法
     *
     * @param eventType 事件类型
     * @param <E>       事件泛型
     * @return 订阅构建器
     */
    private <E extends Event> SubscriptionBuilder<E> doSubscribe(Class<E> eventType) {
        return new SubscriptionBuilder<>(eventType);
    }


    public void fire(Event event) {
        Class<? extends Event> eventType = event.getClass();
        List<Class<? extends Event>> eventTypes = collectEventHierarchy(eventType);

        // 合并所有匹配的处理器并排序
        List<HandlerRegistration<?>> allHandlers = new ArrayList<>();
        for (Class<? extends Event> type : eventTypes) {
            List<HandlerRegistration<?>> registrations = handlers.get(type);
            if (registrations != null) {
                allHandlers.addAll(registrations);
            }
        }
        allHandlers.sort(priorityComparator);

        // 执行处理器链
        EventChainContext context = new EventChainContext();
        for (HandlerRegistration<?> registration : allHandlers) {
            if (context.isInterrupted()) break;
            processRegistration(event, context, registration);
        }
    }

    /**
     * 收集事件类的完整类型层次结构（包含超类和接口）
     */
    private List<Class<? extends Event>> collectEventHierarchy(Class<? extends Event> eventClass) {
        Set<Class<? extends Event>> hierarchy = new LinkedHashSet<>();
        Queue<Class<? extends Event>> queue = new LinkedList<>();
        queue.add(eventClass);

        while (!queue.isEmpty()) {
            Class<? extends Event> current = queue.poll();

            if (current == null || current == Event.class) continue;

            if (hierarchy.add(current)) {
                // 处理父类
                Class<?> superClass = current.getSuperclass();
                // 添加空检查，并确保是Event子类
                if (superClass != null && Event.class.isAssignableFrom(superClass)) {
                    queue.add(superClass.asSubclass(Event.class));
                }

                // 处理接口
                for (Class<?> interfaceClass : current.getInterfaces()) {
                    // 添加接口类型检查
                    if (Event.class.isAssignableFrom(interfaceClass)) {
                        queue.add(interfaceClass.asSubclass(Event.class));
                    }
                }
            }
        }
        return new ArrayList<>(hierarchy);
    }
    /**
     * 处理事件注册信息（带类型安全检查）
     */
    @SuppressWarnings("unchecked")
    private <E extends Event> void processRegistration(Event event, EventChainContext context, HandlerRegistration<?> registration) {
        HandlerRegistration<E> typedReg = (HandlerRegistration<E>) registration;
        if (typedReg.filter.test((E) event)) {
            typedReg.handler.handle((E) event, context);
        }
    }

    /**
     * 订阅构建器（支持链式配置）
     */
    @SuppressWarnings("unused")
    public class SubscriptionBuilder<E extends Event> {
        private final Class<E> eventType;
        private Predicate<E> filter = e -> true;
        private EventPriorityEnum priority = EventPriorityEnum.NORMAL;
        private EventHandler<E> handler;

        private SubscriptionBuilder(Class<E> eventType) {
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

        /**
         * 注册处理器到事件通道
         */
        private void registerHandler() {
            handlers.compute(eventType, (key, existing) -> {
                List<HandlerRegistration<?>> list = existing != null ? existing : new CopyOnWriteArrayList<>();
                list.add(new HandlerRegistration<>(filter, priority, handler));
                list.sort(priorityComparator);
                return list;
            });
        }
    }

    /**
     * 处理器注册信息封装
     *
     * @param <E> 事件类型
     */
    private record HandlerRegistration<E extends Event>(
            Predicate<E> filter,
            EventPriorityEnum priority,
            EventHandler<E> handler) {

        int getPriority() {
            return priority.getPriority();
        }
    }
}
