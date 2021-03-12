package com.hb0730.spring.boot.redis.mq.sample.producer;

import com.hb0730.spring.boot.redis.mq.sample.stream.MyGroupEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bing_huang
 */
@Service
@RequiredArgsConstructor
public class MyStreamService {
    public static final String STREAM_KEY = "myStream";
    public static final String STREAM_GROUP_KEY = "myGroupStream";
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        Map<String, String> startup = new HashMap<>(3);
        startup.put("type", "send");
        startup.put("data", message);
        startup.put("timestamp", String.valueOf(System.currentTimeMillis()));
        StringRecord record = StreamRecords.string(startup).withStreamKey(STREAM_KEY);
        redisTemplate.opsForStream().add(record);
    }

    public void sendEvent(Map<String, Object> data) {
        MyGroupEvent event = new MyGroupEvent("my-group-event", data, System.currentTimeMillis());
        ObjectRecord<String, MyGroupEvent> record = StreamRecords.objectBacked(event).withStreamKey(STREAM_GROUP_KEY);
        redisTemplate.opsForStream().add(record);
    }
}
