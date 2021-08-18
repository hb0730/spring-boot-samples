package com.hb0730.spring.boot.redis.stream.queue2.sample.listener;

import com.hb0730.spring.boot.redis.stream.queue2.sample.message.MessageObject;

/**
 * 注入使用IMessageProducerConsumerListener接口,也就屏蔽了StreamListener原生接口,避免调错方法.
 *
 * @author bing_huang
 * @date 2021/8/18
 */
public interface IMessageConsumerListener<T> {
    /**
     * 不要手动调用这个方法!!!!!!!!!不要手动调用这个方法!!!!!!!!!不要手动调用这个方法!!!!!!!!!
     * <p>
     * 消费消息,隔离Redis API,如果返回true则自动应答,如果返回false,认为消息处理失败.
     * 暂时注释这个方法,避免手动挡调用,这个方法是 StreamListener的onMessage主动调用的,正常不需要手动调用
     * 还是要留着这个方法,用于增加 类似 @Transactional 事务注解扩展,因为是面向接口注入的
     *
     * @param messageObject {@link MessageObject}
     * @return 是否成功
     */
    boolean onMessage(MessageObject<T> messageObject) throws Exception;
}
