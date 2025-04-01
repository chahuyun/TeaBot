package cn.chahuyun.teabot.repository.bot.entity;

import cn.chahuyun.teabot.common.conf.bot.BotType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-4-1 14:10
 */
@Entity
@Data
@Table(name = "tb_bot")
public class BotEntity {

    /**
     * 顺序编号，id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 机器人名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 机器人独有id(wxid,qq)
     */
    @Column(name = "own_id",nullable = false, unique = true)
    private String ownId;

    /**
     * 机器人类型
     */
    @Column(name = "bot_type",nullable = false)
    private BotType type;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false)
    private Date createdTime;

    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;
}
