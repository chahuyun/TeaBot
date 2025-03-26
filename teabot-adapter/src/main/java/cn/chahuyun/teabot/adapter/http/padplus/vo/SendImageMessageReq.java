package cn.chahuyun.teabot.adapter.http.padplus.vo;

import lombok.Data;

/**
 * @Description : 发送图片消息
 * @Author :Obi
 * @Date: 2025/3/26 11:39
 */
@Data
public class SendImageMessageReq {

    String base64;

    String toWxid;

    String imageBase64;

    String wxid;

}
