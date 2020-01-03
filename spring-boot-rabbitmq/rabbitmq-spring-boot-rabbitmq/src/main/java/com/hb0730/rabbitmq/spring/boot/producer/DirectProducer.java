package com.hb0730.rabbitmq.spring.boot.producer;

import com.hb0730.rabbitmq.spring.boot.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 * direct模式 生产者
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class DirectProducer {
    Logger logger = LoggerFactory.getLogger(DirectProducer.class);
    @Resource
    private RabbitTemplate template;

    public void send() {
        //回调
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
        String context = "hello rabbit";
        //构建回调返回的数据
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        template.convertAndSend(Constants.DIRECT_EXCHANGE_NAME, Constants.DIRECT_QUEUE_ROUTE_KEY, context, correlationData);
        logger.info("SendMessageServiceImpl() >>> 发送消息到RabbitMQ, 消息内容: " + context);
    }
}
