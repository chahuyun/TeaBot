package cn.chahuyun.teabot.bot;

import cn.chahuyun.hibernateplus.HibernateFactory;
import cn.chahuyun.teabot.core.bot.BotFactory;
import cn.chahuyun.teabot.repository.bot.entity.BotConfigEntity;

import java.util.List;

/**
 * bot缓存复读取
 *
 * @author Moyuyanli
 * @date 2025-3-25 15:51
 */
public class BotCacheReload {

    public static void reload() {
        List<BotConfigEntity> entities = HibernateFactory.selectList(BotConfigEntity.class);
        for (BotConfigEntity entity : entities) {
            if (entity.getStatus()) {
                BotFactory.restoreBot(entity);
            }
        }

    }


}
