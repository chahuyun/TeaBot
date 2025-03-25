package cn.chahuyun.teabot.adapter.http.padplus.vo;

import com.google.gson.JsonObject;
import lombok.Data;

/**
 * @Description : 发送语音消息返回参数
 * @Author :Obi
 * @Date: 2025/3/25 15:20
 */
@Data
public class SendVocieMessageRes {
    Integer code;

    Boolean success;

    String Message;

    private JsonObject Data;

    private String Data62;

}
