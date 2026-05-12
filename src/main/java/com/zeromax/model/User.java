package com.zeromax.model;

import java.time.LocalDateTime;

/**
 * 用户实体类 - 对应数据库中的 users 表
 */
public class User {

    private Integer id;
    private String username;
    private String password;
    private LocalDateTime createdAt;

    // 无参构造（框架需要）
    public User() {}

    // 注册时用的构造方法
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // getter 和 setter 方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
