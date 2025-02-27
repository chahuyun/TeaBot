package cn.chahuyun.teabot.core.util.http.padplus.vo;

import lombok.Data;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-27 14:15
 */
@Data
public class GetQrReq {

    private String DeviceID;

    private String DeviceName;

    private Proxy Proxy;

}


@Data
class Proxy {

    private String ProxyIp;

    private String ProxyPassword;

    private String ProxyUser;

}