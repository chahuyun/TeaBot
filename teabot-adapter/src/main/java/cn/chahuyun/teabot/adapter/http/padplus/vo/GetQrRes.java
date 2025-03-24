package cn.chahuyun.teabot.adapter.http.padplus.vo;

import lombok.Data;

import java.util.Date;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-27 14:44
 */
@Data
public class GetQrRes {

    private String QrBase64;

    private String Uuid;

    private String QrUrl;

    private Date ExpiredTime;

}
