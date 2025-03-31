package cn.chahuyun.teabot.common.conf.system.entity;

import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-26 14:37
 */
@Getter
@Setter
public class DataConfig {

    /**
     * 数据库地址
     */
    private String address;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;
}
