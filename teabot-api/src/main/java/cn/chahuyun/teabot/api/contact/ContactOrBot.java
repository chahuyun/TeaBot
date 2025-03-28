package cn.chahuyun.teabot.api.contact;


/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:29
 */
public interface ContactOrBot {

    /**
     * 用户id，不限于微信id
     * @return 用户id
     */
    String getId();

    /**
     * 所属bot
     * @return bot
     */
    Bot getBot();

    String getName();

    String getGroupAvatar();

}
