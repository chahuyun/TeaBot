package cn.chahuyun.teabot.api.dto.entity;

import lombok.Data;

/**
 * @Description : 请求参数，包含了通用的请求信息。
 * @Author :Obi
 * */
@Data
public class ReqParam {

    private String uuid;
    private String Url;
    private String Wxid;

}
