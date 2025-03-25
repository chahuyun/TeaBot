package cn.chahuyun.teabot.api.contact;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-18 16:21
 */
public interface Member extends Contact, ContactOrBot {

    Group getGroup();


    //todo 添加好友

}
