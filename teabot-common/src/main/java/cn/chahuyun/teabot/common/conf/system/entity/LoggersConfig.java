package cn.chahuyun.teabot.common.conf.system.entity;

import ch.qos.logback.classic.Level;
import lombok.Getter;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-27 10:21
 */
@Getter
public class LoggersConfig {

    private String name;

    private String level;

    public LoggersConfig setLevel(String level) {
        // 将传入的字符串转换为大写，并尝试转换为枚举
        String upperLevel = level.trim().toUpperCase();
        try {
            Level.valueOf(upperLevel); // 检查是否为有效枚举值
            this.level = upperLevel;   // 合法则设置
        } catch (IllegalArgumentException e) {
            // 如果无效，设置默认值（INFO）
            this.level = Level.INFO.levelStr;
        }
        return this;
    }

    public Level getLevel() {
        return Level.valueOf(level);
    }

}
