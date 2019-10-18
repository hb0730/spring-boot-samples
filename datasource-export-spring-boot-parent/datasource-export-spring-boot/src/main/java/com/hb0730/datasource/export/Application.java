package com.hb0730.datasource.export;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * springboot启动类
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@SpringBootApplication
@MapperScan("com.hb0730.datasource.export.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
