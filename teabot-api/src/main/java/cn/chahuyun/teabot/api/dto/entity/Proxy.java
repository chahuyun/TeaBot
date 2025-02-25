package cn.chahuyun.teabot.api.dto.entity;

import lombok.Data;

/**
 *
 * @Description : 网络代理类
 * @Author :Obi
 */

@Data
public class Proxy {
    private String ProxyIp;
    private String ProxyPassword;
    private String ProxyUser;
}
