package cn.chahuyun.teabot.adapter.http.padplus.vo;

import lombok.Data;

import java.util.List;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-28 16:46
 */
@Data
public class GetGroupInfoRes {

    private NickName NickName;

    private String ChatRoomOwner;

    private String SmallHeadImgUrl;

    private ChatroomData NewChatroomData;

    @Data
    public static class NickName {
        private String string;
    }


    @Data
    public static class ChatroomData {
        private Integer MemberCount;
        private List<ChatRoomMember> ChatRoomMember;
    }

    @Data
    public static class ChatRoomMember {
        private String UserName;
        private String NickName;
        private String SmallHeadImgUrl;

        private String InviterUserName;

        private Integer ChatroomMemberFlag;
    }

}
