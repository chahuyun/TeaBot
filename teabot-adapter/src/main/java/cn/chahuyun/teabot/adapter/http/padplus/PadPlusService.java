package cn.chahuyun.teabot.adapter.http.padplus;

import cn.chahuyun.teabot.adapter.http.padplus.vo.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * padPlus的接口
 *
 * @author Moyuyanli
 * @date 2025-2-27 14:12
 */
public interface PadPlusService {

    /**
     * 获取二维码
     * @param params 参数
     * @return Call<String>
     */
    @POST("api/Login/GetQR")
    Call<Results> getQr(@Body GetQrReq params);

    /**
     * 检查二维码
     * @param uuid uuid
     * @return 结果
     */
    @POST("api/Login/CheckQR")
    Call<Results> checkQr(@Query("uuid") String uuid);

    /**
     * 心跳检测
     * @param wxid 微信id
     * @return 结果
     */
    @POST("api/Login/HeartBeat")
    Call<Results> heartBeat(@Query("wxid") String wxid);

    /**
     * 同步消息
     * @param req 请求
     * @return 结果
     */
    @POST("api/Msg/Sync")
    Call<Results> syncMessage(@Body SyncMessageReq req);


    @POST("api/Msg/SendTxt")
    Call<Results> sendTextMessage(@Body SendTextMessageReq req);

    @POST("api/Msg/SendVoice")
    Call<Results> sendVoiceMessage(@Body SendVoiceMessageReq req);

    @POST("api/Msg/UploadImg")
    Call<Results> sendImageMessage(@Body SendImageMessageReq req);

}
