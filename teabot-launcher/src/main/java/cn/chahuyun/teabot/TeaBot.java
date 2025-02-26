package cn.chahuyun.teabot;

import cn.chahuyun.teabot.api.app.RestApplication;
import cn.chahuyun.teabot.conf.system.ConfigService;
import cn.chahuyun.teabot.repository.RepositoryLoader;

/**
 * bot启动类
 *
 * @author Moyuyanli
 * @date 2025-2-21 11:35
 */
public class TeaBot {

    public static void main(String[] args) {
        ConfigService.load(TeaBot.class);
        RepositoryLoader.load(TeaBot.class);
        //这里应该启动的是web服务，通过接口操作其他任务
        RestApplication.init();
    }

}
