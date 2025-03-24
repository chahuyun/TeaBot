package cn.chahuyun.teabot.api.contact;

import java.util.List;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-18 16:18
 */
public interface Group extends Contact {

    Member getMember(String id);

    List<Member> getMembers();

    Member getOwner();

    List<Member> getAdmins();

}
