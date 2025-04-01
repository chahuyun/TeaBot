package cn.chahuyun.teabot.core.bot;

import cn.chahuyun.teabot.api.bot.BotAdapter;
import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.Friend;
import cn.chahuyun.teabot.api.contact.User;
import cn.chahuyun.teabot.common.conf.bot.BotType;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:34
 */
@SuppressWarnings("FieldCanBeLocal")
public abstract class AbstractBot implements Bot, User {

    private final BotType botType;
    private final BotAdapter adapter;
    private String id;
    private String name;
    private String avatar;

    public AbstractBot(BotAdapter adapter, BotType botType) {
        this.adapter = adapter;
        this.botType = botType;
    }

    /**
     * 获取机器人的适配器
     *
     * @return BotAdapter 适配器
     */
    @Override
    public BotAdapter getAdapter() {
        return adapter;
    }


    /**
     * 获取对象的标识符
     * @return 当前对象的唯一标识符
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * 获取机器人类型
     * @return BotType
     */
    public BotType getType() {
        return botType;
    }


    /**
     * 登录
     */
    @Override
    public void login() {
        User login = adapter.login();

        if (login == null) {
            throw new RuntimeException("登录失败");
        }
        this.id = login.getId();
        this.name = login.getName();
        this.avatar = login.getAvatar();
    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        adapter.logout();
    }

    /**
     * 获取好友
     *
     * @param id id
     * @return Friend 好友
     */
    @Override
    public Friend getFriend(String id) {
        return adapter.getFriend(id);
    }

    /**
     * 获取机器人名称
     * @return String
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * 获取机器人头像
     * @return String
     */
    @Override
    public String getAvatar() {
        return this.avatar;
    }
}
