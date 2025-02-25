package cn.chahuyun.teabot.core.bot;


import cn.chahuyun.teabot.core.contact.Friend;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:38
 */
public class WeChatBot extends AbstractBot{
    public WeChatBot(BotConfiguration configuration, String id) {
        super(configuration, id);
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
