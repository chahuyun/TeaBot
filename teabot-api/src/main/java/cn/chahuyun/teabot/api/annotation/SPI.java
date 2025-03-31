package cn.chahuyun.teabot.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface SPI {

    /**
     * 默认实现别名（可选）
     */
    String value() default "";

    /**
     * 对应的接口类（可选，若实现类只实现一个接口则可省略）
     */
    Class<?> interfaceClass() default Void.class; // 默认值改为 void.class 表示未指定
}