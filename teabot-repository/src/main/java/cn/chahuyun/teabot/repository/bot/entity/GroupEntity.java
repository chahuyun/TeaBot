package cn.chahuyun.teabot.repository.bot.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-4-1 14:21
 */
@Data
@Entity
@Table(name = "tb_group")
public class GroupEntity {

    /**
     * 群编号，唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 群独有id
     */
    @Column(name = "group_id", nullable = false)
    private String groupId;

    /**
     * 机器人
     */
    @ManyToOne
    @JoinColumn(name = "bot_id", nullable = false)
    private BotEntity bot;

    /**
     * 群名称
     */
    private String name;

    /**
     * 群头像
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 群描述
     */
    private String description;

    /**
     * 群主
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity onwer;


}
