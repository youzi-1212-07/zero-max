package com.zeromax.model;

import java.time.LocalDateTime;

/**
 * 待办事项实体类 - 对应数据库中的 todos 表
 */
public class Todo {

    private Integer id;
    private Integer userId;
    private String title;
    private boolean done;
    private LocalDateTime createdAt;

    // 无参构造（框架需要）
    public Todo() {}

    // 添加待办时用的构造方法
    public Todo(Integer userId, String title) {
        this.userId = userId;
        this.title = title;
    }

    // getter 和 setter 方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
