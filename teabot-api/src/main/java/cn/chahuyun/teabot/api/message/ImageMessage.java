package cn.chahuyun.teabot.api.message;

/**
 * 图片消息
 *
 * @author Moyuyanli
 * @date 2025-3-25 16:46
 */
public interface ImageMessage extends SingleMessage {

    /**
     * 获取图片的url
     * @return String
     */
    String getUrl();

    /**
     * 获取图片的base64
     * @return String
     */
    String getBase64();

}
