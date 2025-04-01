package cn.chahuyun.teabot.repository.bot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-4-1 14:15
 */
@Data
@Entity
@Table(name = "tb_user")
public class UserEntity {

    /**
     * 用户编号，唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户独有id
     */
    @Column(name = "user_id", nullable = false)
    private String userId;

    /**
     * botid
     */
    @ManyToOne
    @JoinColumn(name = "bot_id", nullable = false)
    private BotEntity bot;

    /**
     * 用户名称
     */
    private String nickname;

    /**
     * 头像链接
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 性别(0,未知;1.男;2,女;)
     */
    private Integer gender;


    /**
     * 好友类型(0,未知;1,好友;2,群好友;3,群管理员;4,群主;5,临时好友;6,陌生人)
     * 3，4，可不详细划分
     */
    private Integer friendType;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

}
