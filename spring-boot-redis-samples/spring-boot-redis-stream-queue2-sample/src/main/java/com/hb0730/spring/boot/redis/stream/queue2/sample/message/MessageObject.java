package com.hb0730.spring.boot.redis.stream.queue2.sample.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 消息对象的Dto,屏蔽不同MQ之间的差异.
 *
 * @author bing_huang
 * @date 2021/8/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class MessageObject<T> implements Serializable {
    /**
     * 消息的ID
     */
    private String messageId;
    /**
     * 消息的时间戳
     */
    private Long messageTime;
    /**
     * 队列名称
     */
    private String queueName;
    /**
     * 队列中的对象值
     */
    private T messageObject;
}
