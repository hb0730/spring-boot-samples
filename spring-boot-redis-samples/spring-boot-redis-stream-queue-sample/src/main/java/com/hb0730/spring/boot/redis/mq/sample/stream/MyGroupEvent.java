package com.hb0730.spring.boot.redis.mq.sample.stream;

import lombok.Value;

import java.util.Map;

/**
 * @author bing_huang
 */
@Value
public class MyGroupEvent {
    private String type;
    private Map<String, Object> data;
    private long timestamp;
}
