package cn.chahuyun.teabot.repository.bot.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-4-1 14:30
 */
@Data
@Entity
@Table(name = "tb_group_member")
public class GroupMemberEntity {

    /**
     * 成员编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 群成员独有id
     */
    @Column(name = "member_id", nullable = false)
    private String memberId;

    /**
     * 群
     */
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * 是否为管理员
     */
    @Column(name = "is_admin")
    private Boolean admin;


}
