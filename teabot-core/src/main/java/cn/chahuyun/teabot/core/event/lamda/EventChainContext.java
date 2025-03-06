package cn.chahuyun.teabot.core.event.lamda;

/**
 * 事件控制上下文
 *
 * @author Moyuyanli
 * @date 2025-3-6 16:39
 */
public class EventChainContext {
    private boolean isInterrupted = false;

    public void stopPropagation() {
        this.isInterrupted = true;
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }
}
