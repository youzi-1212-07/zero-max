package com.zeromax.service;

import com.zeromax.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户服务 - 处理注册和登录的业务逻辑
 */
@Service
public class UserService {

    private final JdbcTemplate jdbc;

    public UserService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * 注册新用户
     * @return 注册成功返回用户对象，用户名已存在返回 null
     */
    public User register(String username, String password) {
        // 检查用户名是否已存在
        List<User> existing = jdbc.query(
                "SELECT * FROM users WHERE username = ?",
                new UserRowMapper(),
                username
        );
        if (!existing.isEmpty()) {
            return null; // 用户名已存在
        }

        // 插入新用户
        jdbc.update(
                "INSERT INTO users (username, password) VALUES (?, ?)",
                username, password
        );

        // 返回刚创建的用户
        return jdbc.query(
                "SELECT * FROM users WHERE username = ?",
                new UserRowMapper(),
                username
        ).get(0);
    }

    /**
     * 用户登录
     * @return 登录成功返回用户对象，失败返回 null
     */
    public User login(String username, String password) {
        List<User> users = jdbc.query(
                "SELECT * FROM users WHERE username = ? AND password = ?",
                new UserRowMapper(),
                username, password
        );
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * 根据 ID 查找用户
     */
    public User findById(Integer id) {
        List<User> users = jdbc.query(
                "SELECT * FROM users WHERE id = ?",
                new UserRowMapper(),
                id
        );
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * 把数据库查询结果转成 User 对象
     */
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return user;
        }
    }
}
