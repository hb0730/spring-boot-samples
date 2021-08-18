package com.hb0730.spring.boot.redis.stream.queue2.sample.service;

import com.hb0730.spring.boot.redis.stream.queue2.sample.entity.CustomEntity;
import com.hb0730.spring.boot.redis.stream.queue2.sample.listener.AbstractMessageConsumerListener;
import com.hb0730.spring.boot.redis.stream.queue2.sample.message.MessageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author bing_huang
 * @date 2021/8/18
 */
@Service
public class Custom2ConsumerService extends AbstractMessageConsumerListener<CustomEntity> {
    private final static Logger LOGGER = LoggerFactory.getLogger(Custom2ConsumerService.class);

    public Custom2ConsumerService(RedisConnectionFactory redisConnectionFactory, RedisTemplate redisTemplate) {
        super(redisConnectionFactory, redisTemplate);
    }

    @Override
    public String getQueueName() {
        return CustomEntity.QUEUE_NAME;
    }

    @Override
    public String getConsumerName() {
        return CustomEntity.CONSUMER_NAME + "2";
    }

    @Override
    public boolean onMessage(MessageObject<CustomEntity> messageObject) throws Exception {
        if (null == messageObject) {
            return false;
        }
        LOGGER.info(messageObject.toString());
        return true;
    }
}
