package cn.chahuyun.teabot.repository.server.bot;

import cn.chahuyun.hibernateplus.HibernateFactory;
import cn.chahuyun.teabot.core.data.bot.entity.BotConfig;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-26 16:00
 */
public class BotConfigService {

    public static BotConfig addBotConfig(BotConfig config) {
        return HibernateFactory.merge(config);
    }


    public static BotConfig getBotConfig(Integer id) {
        HibernateFactory.selectListByHql(BotConfig.class, "from BotConfig where id = ?", Map.of("id", id))


        return HibernateFactory.selectOne(BotConfig.class, id);


    }

}
