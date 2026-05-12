package com.zeromax.service;

import com.zeromax.model.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 待办服务 - 处理待办事项的增删改查
 */
@Service
public class TodoService {

    private final JdbcTemplate jdbc;

    public TodoService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * 获取某个用户的所有待办
     */
    public List<Todo> findByUserId(Integer userId) {
        return jdbc.query(
                "SELECT * FROM todos WHERE user_id = ? ORDER BY created_at DESC",
                new TodoRowMapper(),
                userId
        );
    }

    /**
     * 按状态筛选待办
     * @param done true=已完成, false=未完成, null=全部
     */
    public List<Todo> findByUserIdAndStatus(Integer userId, Boolean done) {
        if (done == null) {
            return findByUserId(userId);
        }
        return jdbc.query(
                "SELECT * FROM todos WHERE user_id = ? AND is_done = ? ORDER BY created_at DESC",
                new TodoRowMapper(),
                userId, done
        );
    }

    /**
     * 添加待办
     */
    public Todo add(Integer userId, String title) {
        jdbc.update(
                "INSERT INTO todos (user_id, title) VALUES (?, ?)",
                userId, title
        );
        // 返回刚创建的待办（PostgreSQL 用 LIMIT 1 代替 TOP 1）
        List<Todo> todos = jdbc.query(
                "SELECT * FROM todos WHERE user_id = ? ORDER BY id DESC LIMIT 1",
                new TodoRowMapper(),
                userId
        );
        return todos.get(0);
    }

    /**
     * 标记待办为已完成/未完成
     */
    public boolean toggleDone(Integer id, Integer userId) {
        int rows = jdbc.update(
                "UPDATE todos SET is_done = NOT is_done WHERE id = ? AND user_id = ?",
                id, userId
        );
        return rows > 0;
    }

    /**
     * 删除待办（只能删除自己的）
     */
    public boolean delete(Integer id, Integer userId) {
        int rows = jdbc.update(
                "DELETE FROM todos WHERE id = ? AND user_id = ?",
                id, userId
        );
        return rows > 0;
    }

    /**
     * 把数据库查询结果转成 Todo 对象
     */
    private static class TodoRowMapper implements RowMapper<Todo> {
        @Override
        public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Todo todo = new Todo();
            todo.setId(rs.getInt("id"));
            todo.setUserId(rs.getInt("user_id"));
            todo.setTitle(rs.getString("title"));
            todo.setDone(rs.getBoolean("is_done"));
            todo.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return todo;
        }
    }
}
