package cn.chahuyun.teabot.util;

import java.util.ServiceLoader;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 14:47
 */
public class FactoryUtil {

    /**
     * 获取工厂类
     * @param clazz 工厂类
     * @return T 工厂
     * @param <T> 工厂类
     */
    public static  <T> T getFactory(Class<T> clazz) {
        ServiceLoader<?> load = ServiceLoader.load(clazz);
        for (Object o : load) {
            if (o.getClass() == clazz) {
                return clazz.cast(o);
            }
        }
        throw new RuntimeException("未找到对应的工厂类");
    }

}
