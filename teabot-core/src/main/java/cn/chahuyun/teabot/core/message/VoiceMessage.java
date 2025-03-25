package cn.chahuyun.teabot.core.message;

import cn.chahuyun.teabot.api.message.Message;
import cn.chahuyun.teabot.api.message.SingleMessage;

/**
 * @Description : 语音消息
 * @Author :Obi
 * @Date: 2025/3/25 15:46
 */
public class VoiceMessage extends AbstractMessageKey<VoiceMessage> implements Message, SingleMessage {

    private String url;


    public VoiceMessage(String url) {
    super(singleMessage -> {
     if (singleMessage instanceof VoiceMessage voiceMessage) {
     return voiceMessage;
    }
   return null;
  });
 }

    @Override
    public String content() {
        return "[音频]";
    }
}
