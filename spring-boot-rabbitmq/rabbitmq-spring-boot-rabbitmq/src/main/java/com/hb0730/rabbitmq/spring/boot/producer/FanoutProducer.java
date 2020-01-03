package com.hb0730.rabbitmq.spring.boot.producer;

import com.hb0730.rabbitmq.spring.boot.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 *     发布/订阅
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class FanoutProducer {
    private static Logger logger = LoggerFactory.getLogger(FanoutProducer.class);
    @Resource
    private RabbitTemplate rabbitTemplate;
    /**
     * 发送消息
     *
     * @param message 消息内容
     * 说明: routingKey可以指定也可以不指定，这里我们给一个空字符串""
     */
    public void sendMessage(Object message) {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
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
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                logger.info(message+":"+replyCode+":"+replyText+":"+exchange+":"+routingKey);
            }
        });
        logger.info("【消息发送者】发送消息到fanout交换机，消息内容为: {}", message);
        //构建回调返回的数据
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(Constants.FANOUT_EXCHANGE_NAME, "", message,correlationData);
    }
}
