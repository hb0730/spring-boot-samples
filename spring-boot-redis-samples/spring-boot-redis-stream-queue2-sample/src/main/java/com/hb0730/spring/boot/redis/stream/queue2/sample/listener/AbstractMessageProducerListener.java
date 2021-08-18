package com.hb0730.spring.boot.redis.stream.queue2.sample.listener;

import com.hb0730.spring.boot.redis.stream.queue2.sample.message.MessageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author bing_huang
 * @date 2021/8/18
 */
public abstract class AbstractMessageProducerListener<T> implements IMessageProducerListener<T> {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractMessageProducerListener.class);
    private final RedisTemplate redisTemplate;

    public AbstractMessageProducerListener(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public abstract String getQueueName();

    /**
     * 生产者向消息队列发送消息
     *
     * @param message
     * @return
     */
    @Override
    public MessageObject<T> sendProducerMessage(T message) throws Exception {
        if (null == message) {
            return null;
        }
        try {
            ObjectRecord<String, T> record = Record.of(message).withStreamKey(getQueueName());
            RecordId recordId = redisTemplate.opsForStream().add(record);
            if (null != recordId) {
                return new MessageObject<T>(recordId.getValue(), recordId.getTimestamp(), getQueueName(), message);
            }
            return null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }
}
