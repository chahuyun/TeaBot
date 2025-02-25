package cn.chahuyun.teabot.core.data.system.entity;

import jakarta.persistence.*;

/**
 * 数据库配置信息
 *
 * @author Moyuyanli
 * @date 2025-2-25 17:16
 */
@Entity
@Table(name = "s_db_config")
public class DBConfig {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 数据库地址
     */
    private String url;
    /**
     * 数据库端口
     */
    private Integer port;

    /**
     * 数据库名
     */
    private String database;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;


    public Integer getId() {
        return id;
    }

    public DBConfig setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public DBConfig setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public DBConfig setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public DBConfig setDatabase(String database) {
        this.database = database;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public DBConfig setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DBConfig setPassword(String password) {
        this.password = password;
        return this;
    }
}
