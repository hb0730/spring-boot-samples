package com.hb0730.spring.boot.redis.stream.queue2.sample.listener;

import com.hb0730.spring.boot.redis.stream.queue2.sample.message.MessageObject;

/**
 * 生产者
 *
 * @author bing_huang
 * @date 2021/8/18
 */
public interface IMessageProducerListener<T> {

    /**
     * 生产者向消息队列发送消息
     *
     * @param message Message
     * @return {@link MessageObject}
     */
    MessageObject<T> sendProducerMessage(T message) throws Exception;
}
