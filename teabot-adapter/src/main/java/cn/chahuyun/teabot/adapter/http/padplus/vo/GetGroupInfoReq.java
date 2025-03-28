package cn.chahuyun.teabot.adapter.http.padplus.vo;

import lombok.Data;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 18:03
 */
@Data
public class GetGroupInfoReq {

    private String QID;

    private String Wxid;

    public GetGroupInfoReq(String QID, String wxid) {
        this.QID = QID;
        Wxid = wxid;
    }
}
