package com.hb0730.apache.rocketmq.spring.boot.producer.service;

import com.hb0730.apache.rocketmq.spring.boot.producer.domain.OrderPaidEvent;
import com.hb0730.apache.rocketmq.spring.boot.producer.domain.User;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Service
public class ProducerService {
    private static final String TX_PGROUP_NAME = "myTxProducerGroup";
    @Value("${test1.rocketmq.topic}")
    private String springTopic;
    @Value("${test1.rocketmq.topic.user}")
    private String userTopic;
    @Value("${test1.rocketmq.orderTopic}")
    private String orderPaidTopic;
    @Value("${test1.rocketmq.msgExtTopic}")
    private String msgExtTopic;
    @Value("${test1.rocketmq.transTopic}")
    private String springTransTopic;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource(name = "extRocketMQTemplate")
    private RocketMQTemplate extRocketMQTemplate;

    /**
     * <p>
     * 测试发送请求
     * </p>
     *
     * @return
     */
    public SendResult testSyncSend() {
        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, "hello,World");
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", springTopic, sendResult);
        return sendResult;
    }

    /**
     * <p>
     * 发送请求
     * </p>
     *
     * @param user
     * @return
     */
    public SendResult userSyncSend(User user) {
        SendResult sendResult = rocketMQTemplate.syncSend(userTopic, user);
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", userTopic, sendResult);
        return sendResult;
    }

    /**
     * <p>
     *
     * </p>
     *
     * @param user
     * @return
     */
    public SendResult messageBuildByUserSend(User user) {
        SendResult sendResult = rocketMQTemplate.syncSend(userTopic, MessageBuilder.withPayload(user).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build());
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", userTopic, sendResult);
        return sendResult;
    }

    public SendResult messageBuildBytes() {
        SendResult sendResult = extRocketMQTemplate.syncSend(springTopic, MessageBuilder.withPayload("Hello, World!2222".getBytes()).build());
        System.out.printf("extRocketMQTemplate.syncSend1 to topic %s sendResult=%s %n", springTopic, sendResult);
        return sendResult;
    }

    public SendResult messageBuild() {
        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        System.out.printf("syncSend2 to topic %s sendResult=%s %n", springTopic, sendResult);
        return sendResult;
    }

    public void convertAndSend(){
        rocketMQTemplate.convertAndSend(msgExtTopic + ":tag0", "I'm from tag0");
        System.out.printf("syncSend topic %s tag %s %n", msgExtTopic, "tag0");
        rocketMQTemplate.convertAndSend(msgExtTopic + ":tag1", "I'm from tag1");
        System.out.printf("syncSend topic %s tag %s %n", msgExtTopic, "tag1");
    }

    /**
     * <p>
     *  异步
     * </p>
     *
     * @param event
     */
    public void asyncSend(OrderPaidEvent event) {
        rocketMQTemplate.asyncSend(orderPaidTopic, event, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("async onSucess SendResult=%s %n", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.printf("async onException Throwable=%s %n", throwable);
            }
        });
    }

    public SendResult testBatchMessages(){
        List<Message> msgs = new ArrayList<Message>();
        for (int i = 0; i < 10; i++) {
            msgs.add(MessageBuilder.withPayload("Hello RocketMQ Batch Msg#" + i).
                    setHeader(RocketMQHeaders.KEYS, "KEY_" + i).build());
        }

        SendResult sr = rocketMQTemplate.syncSend(springTopic, msgs, 60000);

        System.out.printf("--- Batch messages send result :" + sr);
        return sr;
    }

    public void  testTransaction(){
        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {

                Message msg = MessageBuilder.withPayload("Hello RocketMQ " + i).
                        setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + i).build();
                SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(TX_PGROUP_NAME,
                        springTransTopic + ":" + tags[i % tags.length], msg, null);
                System.out.printf("------ send Transactional msg body = %s , sendResult=%s %n",
                        msg.getPayload(), sendResult.getSendStatus());

                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
