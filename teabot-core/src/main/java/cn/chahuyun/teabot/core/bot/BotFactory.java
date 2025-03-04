package cn.chahuyun.teabot.core.bot;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;

/**
 * bot工厂
 *
 * @author Moyuyanli
 * @date 2025-2-21 13:47
 */
public class BotFactory {

    public static Bot newBot(BotConfiguration configuration) {
        //todo 登录

        WeChatBot bot = new WeChatBot(configuration, configuration.getUserId());

        //创建后应该管理起来
        BotContainer.addBot(bot);

        return bot;
    }

}
