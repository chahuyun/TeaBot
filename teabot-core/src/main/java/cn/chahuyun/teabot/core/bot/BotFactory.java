package cn.chahuyun.teabot.core.bot;

import cn.chahuyun.teabot.api.bot.BotContainer;
import cn.chahuyun.teabot.api.config.BotAdapter;
import cn.chahuyun.teabot.api.config.BotConfig;
import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.factory.BotAdapterFactory;
import cn.chahuyun.teabot.common.conf.bot.BotType;
import cn.chahuyun.teabot.core.bot.cache.BotCache;
import cn.chahuyun.teabot.common.exp.AdapterException;
import cn.chahuyun.teabot.common.exp.BotNotLoginException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;


@SuppressWarnings("UnusedReturnValue")
@Slf4j
public class BotFactory {

    private final Map<BotType, BotAdapterFactory> adapterFactories = new HashMap<>();

    private static BotFactory instance;

    public BotFactory() {
        ServiceLoader<BotAdapterFactory> loader = ServiceLoader.load(BotAdapterFactory.class);
        for (BotAdapterFactory factory : loader) {
            // 假设工厂实现中可以获取 BotType
            BotType type = factory.getSupportedType(); // 需要在接口中添加此方法
            adapterFactories.put(type, factory);
        }
    }


    private Bot pNewBot(BotConfig config) {
        if (!adapterFactories.containsKey(config.getBotType())) {
            throw new IllegalArgumentException("不支持的 BotType: " + config.getBotType());
        }
        BotAdapter adapter = adapterFactories.get(config.getBotType()).createAdapter(config);
        AbstractBot bot = createBotInstance(config, adapter);
        bot.login();
        BotCache.serializable(bot.getAdapter());
        BotContainer.addBot(bot); // 确保所有 Bot 都被添加到容器
        return bot;
    }

    public static Bot newBot(BotConfig config) {
        if (instance == null) {
            instance = new BotFactory();
        }
        return instance.pNewBot(config);
    }


    private Bot pRestoreBot(BotConfig config) {
        BotAdapter botAdapter = BotCache.deserializable(config.getBotType().getType(), config.getUserId());

        if (botAdapter == null) {
            log.warn("无法还原BotAdapter,userId->{},已准备重新登录!", config.getUserId());
            if (adapterFactories.containsKey(config.getBotType())) {
                BotAdapterFactory factory = adapterFactories.get(config.getBotType());
                if (factory != null) {
                    botAdapter = factory.createAdapter(config);
                }
            }
            if (botAdapter == null) {
                throw new AdapterException("还原适配器错误!");
            }
        }

        //重新创建bot
        AbstractBot bot = createBotInstance(config, botAdapter);

        if (!bot.isOnline()) {
            bot.login();
        }
        BotCache.serializable(bot.getAdapter());
        try {
            botAdapter.listeningMessages();
        } catch (BotNotLoginException e) {
            log.error("从缓存还原bot失败:{}", e.getMessage());
            BotCache.deleteCache(config.getBotType().getType(), config.getUserId());
            return null;
        }
        BotContainer.addBot(bot); // 确保所有 Bot 都被添加到容器
        return bot;
    }

    /**
     * 通过配置还原bot
     * @param config bot配置
     * @return Bot
     */
    public static Bot restoreBot(BotConfig config) {
        if (instance == null) {
            instance = new BotFactory();
        }
        return instance.pRestoreBot(config);
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    private static AbstractBot createBotInstance(BotConfig config, BotAdapter adapter) {
        return switch (config.getBotType()) {
            case PAD_PLUS -> new WeChatBot(config, adapter);
            // 其他 BotType 的实现
            default -> throw new IllegalArgumentException("不支持的 BotType: " + config.getBotType());
        };
    }
}