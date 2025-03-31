package cn.chahuyun.teabot.common.conf.system.entity;

import ch.qos.logback.classic.Level;
import lombok.Getter;

import java.util.List;


public class LoggerConfig {

    private String level;

    @Getter
    private List<LoggersConfig> loggers;

    public LoggerConfig setLevel(String level) {
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

    public LoggerConfig setLoggers(List<LoggersConfig> loggers) {
        this.loggers = loggers;
        return this;
    }
}