package com.hb0730.spring.boot.redis.stream.queue2.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author bing_huang
 * @date 2021/8/18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CustomEntity implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private LocalDateTime date;

    public static final String QUEUE_NAME = "queue_user";
    public static final String CONSUMER_NAME="consumer_user";
}
