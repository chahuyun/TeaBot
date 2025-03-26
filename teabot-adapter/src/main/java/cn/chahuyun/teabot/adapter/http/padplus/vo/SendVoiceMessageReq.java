package cn.chahuyun.teabot.adapter.http.padplus.vo;

import lombok.Data;

/**
 * @Description : 发送语音请求参数
 * Type： AMR = 0, MP3 = 2, SILK = 4, SPEEX = 1, WAVE = 3 VoiceTime ：音频长度 1000为一秒
 * @Author :Obi
 * @Date: 2025/3/25 15:16
 */
@Data
public class SendVoiceMessageReq {

      String base64;

      String toWxid;

      Integer type;

      Integer voiceTime;

      String wxid;
}
