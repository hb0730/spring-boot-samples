package com.hb0730.spring.boot.redis.mq.sample.consumer;

import com.hb0730.spring.boot.redis.mq.sample.stream.MyGroupEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

/**
 * @author bing_huang
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageGroupStreamConsumerListener implements StreamListener<String, ObjectRecord<String, MyGroupEvent>> {
    private final RedisTemplate<String, Object> redisTemplate;
    @Getter
    private String groupName = "first-group";
    @Getter
    private String consumerName = "first";
    @Getter
    private final ErrorHandler errorHandler = (error) -> {
        log.error("MyGroupStreamListener error", error);
    };

    @Override
    public void onMessage(ObjectRecord<String, MyGroupEvent> message) {
        log.info("StreamListener received Stream[{}], event[{}]", message.getStream(), message.getValue());
        //确认
        redisTemplate.opsForStream().acknowledge(groupName, message);
    }
}
