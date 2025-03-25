package cn.chahuyun.teabot.adapter.http.padplus.vo;

import com.google.gson.JsonObject;
import lombok.Data;

/**
 * @Description : 文本消息返回
 * @Author :Obi
 * @Date: 2025/3/25 13:57
 */
@Data
public class SendTextMessageRes {

    private Integer code;

    private Boolean Success;

    private String Message;

    private JsonObject Data;

    private String Data62;

}
