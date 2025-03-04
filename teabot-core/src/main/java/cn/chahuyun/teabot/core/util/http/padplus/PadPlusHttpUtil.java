package cn.chahuyun.teabot.core.util.http.padplus;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import cn.chahuyun.teabot.core.util.http.padplus.vo.CheckQrRes;
import cn.chahuyun.teabot.core.util.http.padplus.vo.GetQrReq;
import cn.chahuyun.teabot.core.util.http.padplus.vo.GetQrRes;
import cn.chahuyun.teabot.core.util.http.padplus.vo.Results;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;

import java.io.IOException;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-27 14:35
 */
@Slf4j
public class PadPlusHttpUtil {


    public static GetQrRes getQrCode(PadPlusService service, BotConfiguration configuration) {


        GetQrReq params = new GetQrReq();
        params.setDeviceID(configuration.getUserId());
        params.setDeviceName(configuration.getDriveName());

        try {
            Response<Results> execute = service.getQr(params).execute();

            if (execute.isSuccessful()) {
                Results body = execute.body();
                if (body != null) {
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss") // 根据你的日期格式调整
                            .create();
                    return gson.fromJson(body.getData(), GetQrRes.class);
                }
            }
            throw new RuntimeException("获取二维码失败");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CheckQrRes checkQrCode(PadPlusService service, String uuid) {
        try {
            Response<Results> execute = service.checkQr(uuid).execute();
            if (execute.isSuccessful()) {
                Results body = execute.body();
                if (body != null) {
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss") // 根据你的日期格式调整
                            .create();
//                    log.debug("body {}",body);
                    if (body.getCode() == 0 && body.getData() != null && body.getData().has("acctSectResp")) {
                        return gson.fromJson(body.getData(), CheckQrRes.class);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
