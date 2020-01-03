package com.hb0730.rabbitmq.spring.boot.producer;

import com.hb0730.rabbitmq.spring.boot.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 * topic 生产者
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class TopicProducer {
    Logger logger = LoggerFactory.getLogger(TopicProducer.class);
    @Resource
    private RabbitTemplate template;

    @RabbitListener(queues = Constants.QUEUE_NAME)
    public void send() {
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                logger.info("confirm回调方法>>>>>>>>>>>>>回调消息ID为: " + correlationData.getId());
                if (ack) {
                    logger.info("confirm回调方法>>>>>>>>>>>>>消息发送成功");
                } else {
                    logger.info("confirm回调方法>>>>>>>>>>>>>消息发送失败" + cause);
                }
            }
        });
        String context = "rabbit topic";
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        template.convertAndSend(Constants.TOPIC_EXCHANGE_NAME, Constants.TOPIC_QUEUE_ROUTE_KEY, context, correlationData);
        logger.info("SendMessageServiceImpl() >>> 发送消息到RabbitMQ, 消息内容: " + context);
    }
}
