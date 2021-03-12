package com.hb0730.spring.boot.redis.mq.sample.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

/**
 * @author bing_huang
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageStreamConsumerListener implements StreamListener<String, MapRecord<String, String, String>> {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        log.info("Receive Stream[{}], message[{}]", message.getStream(), message);
        // 默认需要ack确认
    }
}
