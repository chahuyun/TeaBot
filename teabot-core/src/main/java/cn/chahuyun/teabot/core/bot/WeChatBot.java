package cn.chahuyun.teabot.core.bot;


import cn.chahuyun.teabot.api.config.BotAdapter;
import cn.chahuyun.teabot.api.config.BotConfig;
import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Friend;
import cn.chahuyun.teabot.conf.bot.BotType;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:38
 */
@Slf4j
public class WeChatBot extends AbstractBot {

    private final boolean isOnline;

    private String name;

    private String avatar;

    public WeChatBot(BotConfig config, BotAdapter adapter) {
        super(config.getUserId(), adapter, BotType.PAD_PLUS);

        if (!this.getAdapter().login()) {
            log.error("登录失败！");
            throw new RuntimeException("创建bot失败!");
        }

        isOnline = true;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    /**
     * 是否在线
     *
     * @return true 在线
     */
    @Override
    public boolean isOnline() {
        return isOnline;
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
