package com.zeromax.controller;

import com.zeromax.model.User;
import com.zeromax.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户控制器 - 处理注册和登录的 HTTP 请求
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册接口
     * 请求：POST /api/register  body: {"username": "xxx", "password": "xxx"}
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        // 参数校验
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "用户名和密码不能为空"));
        }

        User user = userService.register(username, password);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "用户名已存在"));
        }

        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "message", "注册成功"
        ));
    }

    /**
     * 登录接口
     * 请求：POST /api/login  body: {"username": "xxx", "password": "xxx"}
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        User user = userService.login(username, password);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "用户名或密码错误"));
        }

        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "message", "登录成功"
        ));
    }
}
