package com.hb0730.spring.boot.redis.stream.queue2.sample.listener;

import cn.hutool.core.util.ClassUtil;
import com.hb0730.spring.boot.redis.stream.queue2.sample.message.MessageObject;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.PendingMessagesSummary;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Executor;

/**
 * @param <T> 需要放入队列的对象
 * @author bing_huang
 * @date 2021/8/18
 */
public abstract class AbstractMessageConsumerListener<T> implements StreamListener<String, ObjectRecord<String, T>>, IMessageConsumerListener<T>, Closeable {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractMessageConsumerListener.class);
    /**
     * 泛型的类型
     */
    private final Class<T> genericClass = (Class<T>) ClassUtil.getTypeArgument(getClass());
    /**
     * 监听的容器
     */
    private StreamMessageListenerContainer<String, ObjectRecord<String, T>> container = null;

    private final RedisConnectionFactory redisConnectionFactory;
    private final RedisTemplate redisTemplate;

    public AbstractMessageConsumerListener(RedisConnectionFactory redisConnectionFactory, RedisTemplate redisTemplate) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 默认batchSize
     */
    private final int defaultBatchSize = 100;

    /**
     * 批量消费的数量
     *
     * @return 批量消费的数量
     */
    public int getBatchSize() {
        return defaultBatchSize;
    }

    /**
     * 获取队列名称
     *
     * @return 队列名称
     */
    public abstract String getQueueName();


    /**
     * 消费者的名称
     *
     * @return 消费者的名称
     */
    public abstract String getConsumerName();

    /**
     * group的名称,如果为空,默认是 getQueueName()+"_defaultGroupName"
     *
     * @return Group name
     */
    public String getGroupName() {
        return getQueueName() + "_defaultGroupName";
    }

    /**
     * 指定监听器的线程池
     *
     * @return {@link Executor}
     */
    public Executor getExecutor() {
        return null;
    }

    /**
     * 初始化监听器
     */
    @PostConstruct
    private void registerConsumerListener() {
        String className = getClass().toString();
        if (StringUtils.isBlank(getQueueName())) {
            LOGGER.error(className + "的getQueueName()为空,registerConsumerListener()方法执行失败.");
            return;
        }
        if (StringUtils.isBlank(getGroupName())) {
            LOGGER.error(className + "的getGroupName()为空,registerConsumerListener()方法执行失败.");
            return;
        }
        if (StringUtils.isBlank(getConsumerName())) {
            LOGGER.error(className + "的getConsumerName()为空,registerConsumerListener()方法执行失败.");
            return;
        }
        int batchSize = getBatchSize();
        if (batchSize < 1) {
            batchSize = defaultBatchSize;
        }

        Executor executor = getExecutor();
        if (executor == null) {
            executor = new SimpleAsyncTaskExecutor();
        }

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, T>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                        .batchSize(batchSize) //一批次拉取的最大count数
                        .executor(executor) //线程池
                        .pollTimeout(Duration.ZERO) //阻塞式轮询
                        .objectMapper(new ObjectHashMapper())
                        .serializer(new StringRedisSerializer())
                        .targetType(genericClass)  //目标类型(消息内容的类型),如果objectMapper为空,会设置默认的ObjectHashMapper
                        .build();

        container = StreamMessageListenerContainer.create(redisConnectionFactory, options);

        //检查创建group组
        prepareChannelAndGroup(redisTemplate.opsForStream(), getQueueName(), getGroupName());

        // 通过xread命令也就是非消费者组模式直接读取,或者使用xreadgroup命令在消费者组中命令一个消费者去消费一条记录,
        // 我们可以通过0、>、$分别表示第一条记录、最后一次未被消费的记录和最新一条记录,
        // 比如创建消费者组时不能使用>表示最后一次未被消费的记录,比如0表示从第一条开始并且包括第一条,
        // $表示从最新一条开始但并不是指当前Stream的最后一条记录,是表示下一个xadd添加的那一条记录,所以说$在非消费者组模式的阻塞读取下才有意义!

        // 消费者
        Consumer consumer = Consumer.from(getGroupName(), getConsumerName());
        container.receive(consumer, StreamOffset.create(getQueueName(), ReadOffset.lastConsumed()), this);
        container.start();

    }

    /**
     * spring-data-redis 实现的 stream 原生消费者回调方法,依赖Redis ObjectRecord API,业务中不要直接调用!!!!!!.
     * 使用自行实现的onMessage(T value, String queueName, String messageId, Long messageTime) 方法
     *
     * @param message 需要消费者处理的消息
     */
    @Override
    public void onMessage(ObjectRecord<String, T> message) {
        try {
            RecordId recordId = messageRecordId(message);
            if (null != recordId) {
                redisTemplate.opsForStream().acknowledge(getQueueName(), getGroupName(), recordId);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 消息消费是否成功,成功返回RecordId,失败返回null
     *
     * @param message
     * @return
     */
    private RecordId messageRecordId(ObjectRecord<String, T> message) {
        RecordId recordId = message.getId();
        MessageObject<T> messageObject = objectRecord2MessageObject(message);
        try {
            boolean ok = onMessage(messageObject);
            if (ok) {
                return recordId;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * ObjectRecord2MessageObject 类型转换
     *
     * @param message
     * @return
     */
    private MessageObject<T> objectRecord2MessageObject(ObjectRecord<String, T> message) {
        RecordId recordId = message.getId();
        String messageId = recordId.getValue();
        Long timestamp = recordId.getTimestamp();
        String queueName = message.getStream();
        T messageObject = message.getValue();
        return new MessageObject<>(messageId, timestamp, queueName, messageObject);
    }

    /**
     * 在初始化容器时,如果key对应的stream或者group不存在时会抛出异常,所以我们需要提前检查并且初始化.
     *
     * @param ops
     * @param queueName
     * @param group
     */
    private void prepareChannelAndGroup(StreamOperations<String, ?, ?> ops, String queueName, String group) {
        String status = "OK";
        try {
            StreamInfo.XInfoGroups groups = ops.groups(queueName);
            if (groups.stream().noneMatch(xInfoGroup -> group.equals(xInfoGroup.groupName()))) {
                status = ops.createGroup(queueName, ReadOffset.from("0-0"), group);
            }
        } catch (Exception exception) {
//            RecordId initialRecord = ops.add(ObjectRecord.create(queueName, "Initial Record"));
//            Assert.notNull(initialRecord, "Cannot initialize stream with key '" + queueName + "'");
            status = ops.createGroup(queueName, ReadOffset.from("0-0"), group);
        } finally {
            Assert.isTrue("OK".equals(status), "Cannot create group with name '" + group + "'");
        }
    }

    protected void cleanAckMessage() {
        PendingMessagesSummary pending = redisTemplate.opsForStream().pending(getQueueName(), getGroupName());
    }

    @Override
    public void close() throws IOException {
        if (null != this.container) {
            this.container.stop();
        }
    }
}
