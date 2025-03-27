package cn.chahuyun.teabot.adapter.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-27 14:18
 */
public class HttpUtil {

    public static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .serializeNulls()
            .create();
    public static Retrofit getRetrofit(String baseurl) {
        return new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }


}
