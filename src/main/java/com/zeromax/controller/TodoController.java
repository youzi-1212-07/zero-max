package com.zeromax.controller;

import com.zeromax.model.Todo;
import com.zeromax.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 待办控制器 - 处理待办事项的 HTTP 请求
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * 获取待办列表
     * 请求：GET /api/todos?userId=1&done=true
     */
    @GetMapping
    public ResponseEntity<?> getTodos(
            @RequestParam Integer userId,
            @RequestParam(required = false) Boolean done) {

        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "缺少 userId"));
        }

        List<Todo> todos = todoService.findByUserIdAndStatus(userId, done);
        return ResponseEntity.ok(todos);
    }

    /**
     * 添加待办
     * 请求：POST /api/todos  body: {"userId": 1, "title": "买菜"}
     */
    @PostMapping
    public ResponseEntity<?> addTodo(@RequestBody Map<String, Object> body) {
        Integer userId = (Integer) body.get("userId");
        String title = (String) body.get("title");

        if (userId == null || title == null || title.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "缺少必要参数"));
        }

        Todo todo = todoService.add(userId, title);
        return ResponseEntity.ok(todo);
    }

    /**
     * 切换待办完成状态
     * 请求：PUT /api/todos/1?userId=1
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> toggleDone(
            @PathVariable Integer id,
            @RequestParam Integer userId) {

        boolean success = todoService.toggleDone(id, userId);
        if (!success) {
            return ResponseEntity.badRequest().body(Map.of("error", "操作失败，待办不存在或无权限"));
        }

        return ResponseEntity.ok(Map.of("message", "操作成功"));
    }

    /**
     * 删除待办
     * 请求：DELETE /api/todos/1?userId=1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(
            @PathVariable Integer id,
            @RequestParam Integer userId) {

        boolean success = todoService.delete(id, userId);
        if (!success) {
            return ResponseEntity.badRequest().body(Map.of("error", "删除失败，待办不存在或无权限"));
        }

        return ResponseEntity.ok(Map.of("message", "删除成功"));
    }
}
