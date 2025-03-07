package cn.chahuyun.teabot.core.event;

/**
 * 事件
 *
 * @author Moyuyanli
 * @date 2025-3-6 14:55
 */
public interface Event {

    /**
     * 事件是否被拦截
     * @return true 已经被拦截
     */
    boolean isIntercepted();

    /**
     * 拦截事件
     */
    void intercept() ;


}
