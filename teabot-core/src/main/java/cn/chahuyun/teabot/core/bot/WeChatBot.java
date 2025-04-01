package cn.chahuyun.teabot.core.bot;


import cn.chahuyun.teabot.api.bot.BotAdapter;
import cn.chahuyun.teabot.api.config.BotConfig;
import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Friend;
import cn.chahuyun.teabot.common.conf.bot.BotType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:38
 */
@Slf4j
@Setter
@Getter
public class WeChatBot extends AbstractBot {


    public WeChatBot(BotConfig config, BotAdapter adapter) {
        super(adapter, BotType.PAD_PLUS);
    }


    /**
     * 所属bot
     *
     * @return bot
     */
    @Override
    public Bot getBot() {
        return this;
    }

    /**
     * 是否在线
     *
     * @return true 在线
     */
    @Override
    public boolean isOnline() {
        return getAdapter().isOnline();
    }

    /**
     * 获取好友
     *
     * @param id id
     * @return Friend 好友
     */
    @Override
    public Friend getFriend(String id) {
        return getAdapter().getFriend(id);
    }
}
