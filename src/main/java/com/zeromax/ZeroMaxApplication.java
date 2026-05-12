package com.zeromax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Zero-Max 待办清单应用 - 主启动类
 * 运行这个类的 main 方法就能启动整个应用
 */
@SpringBootApplication
public class ZeroMaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeroMaxApplication.class, args);
        System.out.println("=================================");
        System.out.println("  Zero-Max 待办清单启动成功！");
        System.out.println("  访问地址: http://localhost:8080");
        System.out.println("=================================");
    }
}
