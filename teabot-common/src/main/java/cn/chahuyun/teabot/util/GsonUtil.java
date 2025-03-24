package cn.chahuyun.teabot.util;

import cn.chahuyun.teabot.conf.json.ExcludeExposedFieldsStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-24 14:58
 */
public class GsonUtil {

    private static final Gson gson = new GsonBuilder().setExclusionStrategies(new ExcludeExposedFieldsStrategy()).create();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

}
