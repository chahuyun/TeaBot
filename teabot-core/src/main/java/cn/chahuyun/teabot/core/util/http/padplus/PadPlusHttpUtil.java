package cn.chahuyun.teabot.core.util.http.padplus;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import cn.chahuyun.teabot.core.util.http.HttpUtil;
import cn.chahuyun.teabot.core.util.http.padplus.vo.GetQrReq;
import cn.chahuyun.teabot.core.util.http.padplus.vo.GetQrRes;
import cn.chahuyun.teabot.core.util.http.padplus.vo.Results;
import com.google.gson.Gson;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-27 14:35
 */
public class PadPlusHttpUtil {

    public static GetQrRes getQrCode(BotConfiguration configuration) {
        Retrofit retrofit = HttpUtil.getRetrofit(configuration.getBaseUrl());

        PadPlusService service = retrofit.create(PadPlusService.class);

        GetQrReq params = new GetQrReq();
        params.setDeviceID(configuration.getUserId());
        params.setDeviceName(configuration.getDriveName());

        try {
            Response<Results> execute = service.getQr(params).execute();

            if (execute.isSuccessful()) {
                Results body = execute.body();
                if (body != null) {
                    return new Gson().fromJson(body.getData(), GetQrRes.class);
                }
            }
            throw new RuntimeException("获取二维码失败");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
