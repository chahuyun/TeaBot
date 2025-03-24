package cn.chahuyun.teabot.adapter.http.padplus.vo;

import lombok.Data;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-24 16:41
 */
@Data
public class SendTextMessageReq {

    private String At;
    private String Content;

    private String ToWxid;

    private int Type;

    private String Wxid;


}
