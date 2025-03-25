package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.message.ImageMessage;
import cn.chahuyun.teabot.api.message.Message;
import cn.chahuyun.teabot.api.message.MessageKey;
import cn.chahuyun.teabot.api.message.SingleMessage;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-24 17:31
 */
public class ImageMessageImpl extends AbstractMessageKey implements Message, SingleMessage, ImageMessage {

    private String base64;

    //被缓存后的路劲
    private String url;

    public ImageMessageImpl(String base64) {
        super(MessageKey.IMAGE);
        //todo base64转文件并保存到缓存目录
        this.base64 = base64;
    }

    /**
     * 以文本形式返回消息
     */
    @Override
    public String content() {
        return "[图片]";
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
}
