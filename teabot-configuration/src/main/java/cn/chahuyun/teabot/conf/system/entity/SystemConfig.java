package cn.chahuyun.teabot.conf.system.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统配置
 *
 * @author Moyuyanli
 * @date 2025-2-26 14:25
 */
@Getter
@Setter
public class SystemConfig {

    /**
     * 服务配置
     */
    private ServeConfig server;

    /**
     * 数据库配置
     */
    private DataConfig data;

}


