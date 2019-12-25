package com.letsup.habit.app.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 服务启动类
 */
@EnableSwagger2
@SpringBootApplication
@MapperScan(basePackages = "com.letsup.habit.app.backend.dao.mapper")
public class HabApplication {
    public static void main(String[] args) {
        SpringApplication.run(HabApplication.class, args);
    }
}