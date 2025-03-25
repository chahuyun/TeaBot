package cn.chahuyun.teabot.message;

/**
 * @Description : 单一消息类型
 * @Author :Obi
 * @Date: 2025/3/25 11:14
 */
public enum MessageKey {
    /**
     * 纯文本消息
     */

    PLAIN_TEXT("PlainText");

    private final String type;

    MessageKey(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
