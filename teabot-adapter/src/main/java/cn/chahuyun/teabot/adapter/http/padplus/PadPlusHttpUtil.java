package cn.chahuyun.teabot.adapter.http.padplus;

import cn.chahuyun.teabot.adapter.bot.padplus.PadPlusBotConfig;
import cn.chahuyun.teabot.adapter.http.padplus.vo.*;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;

import java.io.IOException;

import static cn.chahuyun.teabot.adapter.http.HttpUtil.gson;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-27 14:35
 */
@SuppressWarnings("SpellCheckingInspection")
@Slf4j
public class PadPlusHttpUtil {



    public static GetQrRes getQrCode(PadPlusService service, PadPlusBotConfig configuration) {


        GetQrReq params = new GetQrReq();
        params.setDeviceID(configuration.getUserId());
        params.setDeviceName(configuration.getDriverName());

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

    //发送文本消息
    public static SendTextMessageRes SendMessage(PadPlusService service, SendTextMessageReq req){
        try {
            Response<Results> execute = service.sendTextMessage(req).execute();
            if (execute.isSuccessful()) {
                Results body = execute.body();
                if (body != null) {
                    if (body.getCode() == 0 && body.getData() != null && body.getData().has("AddMsgs")) {
                        return gson.fromJson(body.getData(), SendTextMessageRes.class);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //发送语音消息
    public static SendVideoMessageRes SendVoiceMessage(PadPlusService service,SendVoiceMessageReq req){
        try {
            Response<Results> execute = service.sendVoiceMessage(req).execute();
            if (execute.isSuccessful()) {
                Results body = execute.body();
                if (body != null) {
                    if (body.getCode() == 0 && body.getData() != null && body.getData().has("AddMsgs")) {
                        return gson.fromJson(body.getData(), SendVideoMessageRes.class);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //发送图片消息
    public static SendImageMessageRes SendImageMessage(PadPlusService service,SendImageMessageReq req){
        try {
            Response<Results> execute = service.sendImageMessage(req).execute();
            if (execute.isSuccessful()) {
                Results body = execute.body();
                if (body != null) {
                    if (body.getCode() == 0 && body.getData() != null && body.getData().has("AddMsgs")) {
                        return gson.fromJson(body.getData(), SendImageMessageRes.class);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
