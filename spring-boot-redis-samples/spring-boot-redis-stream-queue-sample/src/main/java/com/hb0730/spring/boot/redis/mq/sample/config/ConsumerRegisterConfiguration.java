package com.hb0730.spring.boot.redis.mq.sample.config;

import com.hb0730.spring.boot.redis.mq.sample.consumer.MessageGroupStreamConsumerListener;
import com.hb0730.spring.boot.redis.mq.sample.consumer.MessageStreamConsumerListener;
import com.hb0730.spring.boot.redis.mq.sample.producer.MyStreamService;
import com.hb0730.spring.boot.redis.mq.sample.stream.MyGroupEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

import java.time.Duration;

/**
 * @author bing_huang
 */
@Configuration
public class ConsumerRegisterConfiguration {

    /**
     * 简单
     */
    @Bean
    public StreamMessageListenerContainer<String, MapRecord<String, String, String>> registryConsumer(RedisConnectionFactory factory, MessageStreamConsumerListener listener) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .executor(messageExecutor())
                .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container = StreamMessageListenerContainer
                .create(factory, options);

        container.receive(StreamOffset.fromStart(MyStreamService.STREAM_KEY), listener);
        container.start();
        return container;
    }

    public ThreadPoolTaskExecutor messageExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setThreadNamePrefix("streamTask-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * 注册消费组,非自动确认
     */
    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, MyGroupEvent>> registryGroupConsumer(RedisTemplate redisTemplate, RedisConnectionFactory factory, MessageGroupStreamConsumerListener listener) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, MyGroupEvent>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                .pollTimeout(Duration.ofSeconds(1))
                .executor(messageExecutor())
                .targetType(MyGroupEvent.class)
                .build();
        StreamMessageListenerContainer<String, ObjectRecord<String, MyGroupEvent>> container = StreamMessageListenerContainer.create(factory, options);
        //消费组
        Consumer consumer = Consumer.from(listener.getGroupName(), listener.getConsumerName());
        //注册消费组
        prepareChannelAndGroup(redisTemplate.opsForStream(), MyStreamService.STREAM_GROUP_KEY, listener.getGroupName());
        //读取策略
        StreamOffset<String> offset = StreamOffset.create(MyStreamService.STREAM_GROUP_KEY, ReadOffset.lastConsumed());
        StreamMessageListenerContainer.ConsumerStreamReadRequest<String> request = StreamMessageListenerContainer.StreamReadRequest
                .builder(offset)
                .consumer(consumer)
                //是否自动确认
                .autoAcknowledge(false)
                .errorHandler(listener.getErrorHandler())
                .build();
        container.register(request, listener);

        container.start();
        return container;
    }

    /**
     * 注册消费组
     */
    private void prepareChannelAndGroup(StreamOperations<String, ?, ?> ops, String key, String group) {
        String status = "OK";
        try {

            StreamInfo.XInfoGroups groups = ops.groups(key);
            if (groups.stream().noneMatch(xInfoGroup -> group.equals(xInfoGroup.groupName()))) {
                status = ops.createGroup(key, group);
            }
        } catch (Exception e) {
            RecordId initialRecord = ops.add(ObjectRecord.create(key, "Initial Record"));
            Assert.notNull(initialRecord, "Cannot initialize stream with key '" + key + "'");
            status = ops.createGroup(key, ReadOffset.from(initialRecord), group);
        } finally {
            Assert.isTrue("OK".equals(status), "Cannot create group with name '" + group + "'");
        }
    }
}
