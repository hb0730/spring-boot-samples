package com.hb0730.spring.boot.redis.stream.queue2.sample.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author bing_huang
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class SpringDataRedisConfiguration {
    public static GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
    public static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        // hash的key也采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // key采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // hash的value序列化方式
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        // value序列化方式
        redisTemplate.setValueSerializer(stringRedisSerializer);
        //设置默认的序列化器
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
