package cn.chahuyun.teabot.api.message;

/**
 * @Description :
 * @Author :Obi
 * @Date: 2025/3/25 17:09
 */
public interface VoiceMessage extends SingleMessage {
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

    /**
     * getImageBase64
     * @return
     */
    String getImageBase64();

    /**
     * 音频长度
     * @return
     */
    Integer getPlayLength();

}
