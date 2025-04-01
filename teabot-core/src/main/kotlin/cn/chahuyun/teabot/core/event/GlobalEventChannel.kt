@file:Suppress("UNCHECKED_CAST")

package cn.chahuyun.teabot.core.event

import cn.chahuyun.teabot.api.event.Event
import cn.chahuyun.teabot.core.event.lamda.EventChainContext
import cn.chahuyun.teabot.core.event.lamda.EventHandler
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.function.Predicate

/**
 * 全局唯一的事件通道，用于事件发布-订阅管理
 */
class GlobalEventChannel private constructor() {

    companion object {
        @Volatile
        private var instance: GlobalEventChannel? = null

        /**
         * 获取全局唯一实例（双重校验锁实现线程安全）
         */
        @JvmStatic
        fun getInstance(): GlobalEventChannel {
            return instance ?: synchronized(this) {
                instance ?: GlobalEventChannel().also { instance = it }
            }
        }

        /**
         * 静态过滤入口（支持类型过滤的订阅起点）
         */
        @JvmStatic
        fun <E : Event> filterIsInstance(eventClass: Class<E>): SubscriptionBuilder<E> {
            return getInstance().createSubscriptionBuilder(eventClass)
                .filterIsInstance(eventClass)
        }
    }

    // 事件处理器存储结构
    private val handlers = ConcurrentHashMap<Class<out Event>, MutableList<HandlerRegistration<*>>>()

    // 优先级比较器（降序排列）
    private val priorityComparator = compareByDescending<HandlerRegistration<*>> { it.priority.priority }

    /**
     * 实例级订阅方法
     */
    private fun <E : Event> doSubscribe(eventType: Class<E>): SubscriptionBuilder<E> {
        return SubscriptionBuilder(eventType)
    }

    /**
     * 创建新的订阅构建器
     */
    private fun <E : Event> createSubscriptionBuilder(eventType: Class<E>): SubscriptionBuilder<E> {
        return SubscriptionBuilder(eventType)
    }

    fun fire(event: Event) {
        val eventType = event.javaClass
        val eventTypes = collectEventHierarchy(eventType)

        // 合并所有匹配的处理器并排序
        val allHandlers = eventTypes.flatMap { handlers[it] ?: emptyList() }
            .sortedWith(priorityComparator)

        // 执行处理器链
        val context = EventChainContext()
        for (registration in allHandlers) {
            if (context.isInterrupted) break
            processRegistration(event, context, registration)
        }
    }

    /**
     * 收集事件类的完整类型层次结构（包含超类和接口）
     */
    private fun collectEventHierarchy(eventClass: Class<out Event>): List<Class<out Event>> {
        val hierarchy = LinkedHashSet<Class<out Event>>()
        val queue = LinkedList<Class<out Event>>()
        queue.add(eventClass)

        while (queue.isNotEmpty()) {
            val current = queue.poll()

            if (current == Event::class.java) continue

            if (hierarchy.add(current)) {
                // 处理父类
                val superClass = current.superclass?.takeIf { Event::class.java.isAssignableFrom(it) }
                superClass?.let { queue.add(it as Class<out Event>) }

                // 处理接口
                current.interfaces
                    .filter { Event::class.java.isAssignableFrom(it) }
                    .forEach { queue.add(it as Class<out Event>) }
            }
        }
        return hierarchy.toList()
    }

    /**
     * 处理事件注册信息（带类型安全检查）
     */
    @Suppress("UNCHECKED_CAST")
    private fun processRegistration(
        event: Event,
        context: EventChainContext,
        registration: HandlerRegistration<*>,
    ) {
        @Suppress("UNCHECKED_CAST")
        when {
            registration.isMatchingEvent(event) -> {
                val typedRegistration = registration as HandlerRegistration<Event>
                if (typedRegistration.filter.test(event)) {
                    typedRegistration.handler.handle(event, context)
                }
            }
        }
    }

    /**
     * 订阅构建器（支持链式配置）
     */
    inner class SubscriptionBuilder<E : Event>(
        private val eventType: Class<E>,
    ) {
        private var filter: Predicate<E> = Predicate { true }
        private var priority: EventPriorityEnum = EventPriorityEnum.NORMAL
        private var handler: EventHandler<E>? = null

        fun filterIsInstance(eventClass: Class<out E>): SubscriptionBuilder<E> {
            filter = filter.and { eventClass.isInstance(it) }
            return this
        }

        fun filter(predicate: Predicate<E>): SubscriptionBuilder<E> {
            filter = filter.and(predicate)
            return this
        }

        fun priority(priority: EventPriorityEnum): SubscriptionBuilder<E> {
            this.priority = priority
            return this
        }

        /**
         * 永久订阅（需要手动取消）
         */
        @JvmName("subscribe")
        fun subscribe(handler: EventHandler<E>) {
            registerHandler(handler)
        }

        /**
         * 一次性订阅（自动取消）
         */
        @JvmName("subscribeOnce")
        fun subscribeOnce(handler: EventHandler<E>) {
            val onceHandler = object : EventHandler<E> {
                override fun handle(event: E, context: EventChainContext) {
                    handler.handle(event, context)
                    // 执行后立即移除
                    handlers[eventType]?.removeIf { registration ->
                        (registration as HandlerRegistration<E>).handler === this
                    }
                }
            }
            registerHandler(onceHandler)
        }

        /**
         * 条件订阅（通过返回值控制生命周期）
         */
        @JvmName("subscribeWhile")
        fun subscribeWhile(predicate: (E) -> Boolean): EventHandler<E> {
            val conditionHandler = object : EventHandler<E> {
                override fun handle(event: E, context: EventChainContext) {
                    if (predicate(event)) {
                        // 当返回true时继续监听
                    } else {
                        // 返回false时自动取消
                        handlers[eventType]?.removeIf { registration ->
                            (registration as HandlerRegistration<E>).handler === this
                        }
                    }
                }
            }
            registerHandler(conditionHandler)
            return conditionHandler
        }

        private fun registerHandler(handler: EventHandler<E>) {
            handlers.compute(eventType) { _, existing ->
                val list = existing ?: CopyOnWriteArrayList()
                list.add(HandlerRegistration(eventType, filter, priority, handler))
                list.sortWith(priorityComparator)
                list
            }
        }
    }

    /**
     * 处理器注册信息封装
     */
    private data class HandlerRegistration<E : Event>(
        val eventType: Class<E>, // 保留事件类型信息
        val filter: Predicate<E>,
        val priority: EventPriorityEnum,
        val handler: EventHandler<E>,
    ) {
        fun isMatchingEvent(event: Event): Boolean {
            return eventType.isInstance(event)
        }
    }

}
