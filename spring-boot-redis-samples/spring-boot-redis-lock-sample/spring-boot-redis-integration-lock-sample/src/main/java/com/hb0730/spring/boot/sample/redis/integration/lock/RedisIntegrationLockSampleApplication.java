package com.hb0730.spring.boot.sample.redis.integration.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <a href="https://github.com/spring-projects/spring-integration">Spring Integration Redis Lock</a> demo
 *
 * @author bing_huang
 */
@SpringBootApplication
public class RedisIntegrationLockSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisIntegrationLockSampleApplication.class, args);
    }
}
