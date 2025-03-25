package cn.chahuyun.teabot.adapter.http.padplus.vo;

import lombok.Data;

/**
 * @Description : 发送视频请求参数
 * @Author :Obi
 * @Date: 2025/3/25 15:24
 */
@Data
public class SendVideoMessageReq {

    String base64;

    String toWxid;

    Integer playLength;

    String imageBase64;

    String wxid;

}
