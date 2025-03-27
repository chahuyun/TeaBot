package cn.chahuyun.teabot.adapter.http.padplus.vo;

import com.google.gson.JsonObject;
import lombok.Data;

/**
 * @Description : 发送图片消息返回
 * @Author :Obi
 * @Date: 2025/3/26 13:53
 */
@Data
public class SendImageMessageRes {

    private Integer code;

    private Boolean Success;

    private String Message;

    private JsonObject Data;

    private String Data62;

}
