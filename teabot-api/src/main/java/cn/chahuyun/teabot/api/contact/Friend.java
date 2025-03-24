package cn.chahuyun.teabot.api.contact;


/**
 * 好友
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:46
 */
public interface Friend extends Contact {

    /**
     * 用户id，不限于微信id
     * @return 用户id
     */
    public String getId();

    /**
     * 用户名
     * @return 用户名
     */
    public String getName();
    /**
     * 所属bot
     * @return bot
     */
    public Bot getBot();

}
