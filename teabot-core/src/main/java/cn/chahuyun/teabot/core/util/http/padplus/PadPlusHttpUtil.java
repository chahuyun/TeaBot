package cn.chahuyun.teabot.core.util.http.padplus;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import cn.chahuyun.teabot.core.util.http.padplus.vo.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;

import java.io.IOException;

import static cn.chahuyun.teabot.core.util.http.HttpUtil.gson;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-27 14:35
 */
@SuppressWarnings("SpellCheckingInspection")
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


    public static SyncMessageRes syncMessage(PadPlusService service, String wxid) {
        try {
            Response<Results> execute = service.syncMessage(new SyncMessageReq(wxid)).execute();
            if (execute.isSuccessful()) {
                Results body = execute.body();
                if (body != null) {
                    if (body.getCode() == 0 && body.getData() != null && body.getData().has("AddMsgs")) {
                        return gson.fromJson(body.getData(), SyncMessageRes.class);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
