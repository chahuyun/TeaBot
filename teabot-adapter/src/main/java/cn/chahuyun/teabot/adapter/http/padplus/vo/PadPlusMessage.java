package cn.chahuyun.teabot.adapter.http.padplus.vo;

import lombok.Data;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-7 10:48
 */
@Data
public class PadPlusMessage {


    private long MsgId;

    private FromUserName FromUserName;

    private ToUserName ToUserName;

    private int MsgType;

    private Content Content;

    private int Status;

    private int ImgStatus;

    private ImgBuf ImgBuf;

    private long CreateTime;

    private String MsgSource;

    private long NewMsgId;

    private int MsgSeq;

    @Data
    public static class FromUserName {
        private String string;
    }

    @Data
    public static class ToUserName {
        private String string;
    }

    @Data
    public static class Content {
        private String string;
    }

    @Data
    public static class ImgBuf {
        private int iLen;
    }

}
