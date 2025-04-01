package cn.chahuyun.teabot;

import cn.chahuyun.teabot.api.app.RestApplication;
import cn.chahuyun.teabot.api.bot.BotContainer;
import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Friend;
import cn.chahuyun.teabot.bot.BotCacheReload;
import cn.chahuyun.teabot.common.conf.system.ConfigService;
import cn.chahuyun.teabot.core.event.EventChannelLoader;
import cn.chahuyun.teabot.experiment.SimpleExperiment;
import cn.chahuyun.teabot.repository.RepositoryLoader;
import cn.chahuyun.teabot.system.Banner;
import cn.hutool.cron.CronUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * bot启动类
 *
 * @author Moyuyanli
 * @date 2025-2-21 11:35
 */
@Slf4j
public class TeaBot {

    public static void main(String[] args) {
        ConfigService.load(TeaBot.class);
        RepositoryLoader.load(TeaBot.class);
        //这里应该启动的是web服务，通过接口操作其他任务
        RestApplication.init();

        CronUtil.setMatchSecond(true);
        CronUtil.start();

        EventChannelLoader.load();

        Banner.load();
//        log.info("tea-bot 启动成功!");

        BotCacheReload.reload();

        Bot bot = BotContainer.getBot("kemomimi");
        if (bot != null) {
            Friend friend = bot.getFriend("wxid_hia0rq45kssb22");
            if (friend != null) {

            }
        }


        SimpleExperiment.tes();

    }





}
