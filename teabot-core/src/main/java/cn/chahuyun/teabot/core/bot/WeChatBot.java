package cn.chahuyun.teabot.core.bot;


import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import cn.chahuyun.teabot.core.adapter.bot.PadPlusBotAdapter;
import cn.chahuyun.teabot.core.contact.Friend;
import cn.chahuyun.teabot.core.data.bot.WeChatUser;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:38
 */
@Slf4j
public class WeChatBot extends AbstractBot {

    private final PadPlusBotAdapter adapter;

    private final WeChatUser user;

    private final boolean isOnline;


    public WeChatBot(BotConfiguration configuration, String id) {
        super(configuration, id);

        //todo 类型校验

        adapter = new PadPlusBotAdapter(configuration);
        if (!adapter.login()) {
            log.error("登录失败！");
            throw new RuntimeException("创建bot失败!");
        }

        isOnline = true;
        user = adapter.getUser();
    }


    @Override
    public String getId() {
        return user.getUserName();
    }

    @Override
    public Bot getBot() {
        return this;
    }


    @Override
    public boolean isOnline() {
        return isOnline;
    }

    /**
     * 消息构建
     *
     * @return true 成功
     */
    @Override
    public boolean messageBuild() {
        return false;
    }

    /**
     * 获取好友
     *
     * @param id id
     * @return Friend 好友
     */
    @Override
    public Friend getFriend(String id) {
        return null;
    }
}
