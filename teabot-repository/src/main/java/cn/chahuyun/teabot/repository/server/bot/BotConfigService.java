package cn.chahuyun.teabot.repository.server.bot;

import cn.chahuyun.hibernateplus.HibernateFactory;
import cn.chahuyun.teabot.repository.bot.entity.BotConfigEntity;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-26 16:00
 */
public class BotConfigService {

    public static BotConfigEntity addBotConfig(BotConfigEntity config) {
        return HibernateFactory.merge(config);
    }

    public static BotConfigEntity getBotConfig(String botId) {
        return HibernateFactory.selectOne(BotConfigEntity.class, "botId", botId);
    }


    public static BotConfigEntity getBotConfig(Integer id) {
//        HibernateFactory.selectListByHql(BotConfigEntity.class, "from BotConfig where id = ?", Map.of("id", id));


        return HibernateFactory.selectOne(BotConfigEntity.class, id);
    }


    public static BotConfigEntity updateBotConfig(BotConfigEntity config) {
        return HibernateFactory.merge(config);
    }
}
