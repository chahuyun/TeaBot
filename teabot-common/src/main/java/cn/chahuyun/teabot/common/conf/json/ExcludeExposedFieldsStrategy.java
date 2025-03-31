package cn.chahuyun.teabot.common.conf.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.Expose;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-24 15:31
 */
public class ExcludeExposedFieldsStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(Expose.class) != null; // 排除带有 @Expose 的字段
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}