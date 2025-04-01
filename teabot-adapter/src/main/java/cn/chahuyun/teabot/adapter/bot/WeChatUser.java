package cn.chahuyun.teabot.adapter.bot;

import cn.chahuyun.teabot.api.contact.Bot;
import cn.chahuyun.teabot.api.contact.User;
import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-3 16:09
 */
@Data
public class WeChatUser implements Serializable, User {

    /**
     * 微信id
     */
    private String userName;
    /**
     * 微信昵称
     */
    private String nickName;
    /**
     * 微信号
     */
    private String alias;
    /**
     * 绑定手机号
     */
    private String bindMobile;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户id，不限于微信id
     *
     * @return 用户id
     */
    @Override
    public String getId() {
        return userName;
    }

    /**
     * 所属bot
     *
     * @return bot
     */
    @Override
    public Bot getBot() {
        return null;
    }

    @Override
    public String getName() {
        return nickName;
    }

    @Override
    public String getAvatar() {
        return null;
    }


   /* {
        "userName": "wxid_hia0rq45kssb22",
            "nickName": "陌语言璃",
            "bindUin": 572490972,
            "bindEmail": "Jik.2@qq.com",
            "bindMobile": "15509288972",
            "alias": "Moyuyanli",
            "status": 102503,
            "pluginFlag": 17463424,
            "regType": 2,
            "safeDevice": 0,
            "officialUserName": "weixin",
            "officialNickName": "微信团队",
            "pushMailStatus": 1,
            "fsurl": "https://w.mail.qq.com/cgi-bin/login?uin=572490972&key=44ea2fb72a97f94MTc0MTU5Mzk4Mw&keytype=2&target=setremind&from=weixin&vt=weixin&f=xhtml"
    },
*/

}
