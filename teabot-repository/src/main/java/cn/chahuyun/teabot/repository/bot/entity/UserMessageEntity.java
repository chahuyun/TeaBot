package cn.chahuyun.teabot.repository.bot.entity;

import cn.chahuyun.teabot.common.message.MessageKey;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-4-1 14:47
 */
@Data
@Entity
@Table(name = "tb_messasge_user")
public class UserMessageEntity {

    /**
     * 消息id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 消息id
     */
    @Column(name = "message_id", nullable = false)
    private String messageId;

    /**
     * bot
     */
    @ManyToOne
    @JoinColumn(name = "bot_id", nullable = false)
    private BotEntity bot;

    /**
     * 群聊
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * 消息内容
     */
    @Column(name = "message", nullable = false,columnDefinition = "LONGTEXT")
    private String message;

    /**
     * 消息类型
     */
    @Column(name = "message_type", nullable = false)
    private MessageKey messageType;

    /**
     * 如果为特殊消息，则应该存在文件路径
     */
    private String url;

    /**
     * 消息时间
     */
    private Date time;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false)
    private Date createTime;


}
