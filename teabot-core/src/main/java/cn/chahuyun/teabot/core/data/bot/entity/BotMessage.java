package cn.chahuyun.teabot.core.data.bot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * bot的消息
 *
 * @author Moyuyanli
 * @date 2025-2-26 17:38
 */
@Getter
@Setter
@Entity
@Table("b_bot_messasge")
public class BotMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //其他属性


}
