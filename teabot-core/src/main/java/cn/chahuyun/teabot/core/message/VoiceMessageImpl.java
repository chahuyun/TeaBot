package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.message.Message;
import cn.chahuyun.teabot.api.message.SingleMessage;
import cn.chahuyun.teabot.api.message.VoiceMessage;
import cn.chahuyun.teabot.common.message.MessageKey;

/**
 * @Description : 语音消息
 * @Author :Obi
 * @Date: 2025/3/25 15:46
 */

public class VoiceMessageImpl extends AbstractMessageKey implements Message, SingleMessage, VoiceMessage {

    private String base64;

    //被缓存后的路径
    private String url;

    private String imageBase64;

    //音频长度
    private Integer playLength;

    public VoiceMessageImpl(String base64) {
        super(MessageKey.VOICE);
        //todo base64转文件并保存到缓存目录
        this.base64 = base64;
    }

    /**
     * 以文本形式返回消息
     */
    @Override
    public String content() {
        return "[语音]";
    }

    /**
     * 获取图片的url
     *
     * @return String
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * 获取图片的base64
     *
     * @return String
     */
    @Override
    public String getBase64() {
        return base64;
    }

    @Override
    public String getImageBase64() {
        return imageBase64;
    }

    @Override
    public Integer getPlayLength() {
        return playLength;
    }
}
