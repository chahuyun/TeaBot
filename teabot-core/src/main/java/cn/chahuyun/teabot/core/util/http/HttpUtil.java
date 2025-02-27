package cn.chahuyun.teabot.core.util.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-27 14:18
 */
public class HttpUtil {

    public static Retrofit getRetrofit(String baseurl) {
        return new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
    }


}
