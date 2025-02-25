package cn.chahuyun.teabot.api.dto.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @Description : 返回参数，包含了通用的返回信息
 * @Author :Obi
 */
@Data
public class ResParam {

    @SerializedName("Code")
    private Integer code;

    @SerializedName("Success")
    private Boolean success;

    @SerializedName("Message")
    private String message;

    private String data62;

}
