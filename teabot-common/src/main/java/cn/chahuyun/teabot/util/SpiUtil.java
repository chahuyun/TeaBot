package cn.chahuyun.teabot.util;

import java.util.ServiceLoader;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 14:47
 */
public class SpiUtil {

    /**
     * 获取interface的实现
     * @param clazz 接口类
     * @return T 实现类
     * @param <T> 接口类
     */
    public static  <T> T getImpl(Class<T> clazz) {
        ServiceLoader<?> load = ServiceLoader.load(clazz);
        for (Object o : load) {
            if (o.getClass() == clazz) {
                return clazz.cast(o);
            }
        }
        throw new RuntimeException("未找到对应的工厂类");
    }

}
