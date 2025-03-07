package cn.chahuyun.teabot.core.util.http.padplus.vo;

import lombok.Data;

/**
 * 同步消息请求
 *
 * @author Moyuyanli
 * @date 2025-3-7 10:13
 */
@Data
public class SyncMessageReq {

    private int Scene;

    private String Synckey;

    private String Wxid;

    public SyncMessageReq() {
    }

    public SyncMessageReq(String wxid) {
        Scene = 0;
        Synckey = "";
        Wxid = wxid;
    }

    public SyncMessageReq(int scene, String synckey, String wxid) {
        Scene = scene;
        Synckey = synckey;
        Wxid = wxid;
    }
}
