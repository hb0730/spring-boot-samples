package com.hb0730.spring.boot.mybatis.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author bing_huang
 */
@SpringBootApplication
@MapperScan("com.hb0730.spring.boot.mybatis.sample.**.mapper")
public class MybatisParamsTypeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisParamsTypeApplication.class, args);
    }
}
