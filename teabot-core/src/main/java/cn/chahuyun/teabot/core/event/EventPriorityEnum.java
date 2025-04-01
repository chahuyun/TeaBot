package cn.chahuyun.teabot.core.event;

/**
 * 事件优先级枚举
 *
 * @author Moyuyanli
 * @date 2025-3-7 9:53
 */
@SuppressWarnings("LombokGetterMayBeUsed")
public enum EventPriorityEnum {

    /**
     * 最高
     */
    HIGHEST(2),
    /**
     * 高
     */
    HIGH(1),
    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 低
     */
    LOW(-1),
    /**
     * 最低
     */
    LOWEST(-2);

    /**
     * 优先级
     */
    private final int priority;

    EventPriorityEnum(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
