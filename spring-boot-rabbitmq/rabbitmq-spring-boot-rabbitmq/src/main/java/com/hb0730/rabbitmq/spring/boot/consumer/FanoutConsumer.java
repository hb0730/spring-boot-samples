package com.hb0730.rabbitmq.spring.boot.consumer;

import com.hb0730.rabbitmq.spring.boot.constants.Constants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class FanoutConsumer {

    private static Logger logger = LoggerFactory.getLogger(FanoutConsumer.class);

    @RabbitListener(queues = Constants.QUEUE_NAME)
    public void receiveMessage(Object message) {
        logger.info("成功接收消息，消息内容: {}", message);
    }
    @RabbitListener(queues = Constants.FANOUT_QUEUE_NAME)
    public void processMessage(Message message, Channel channel) {
        String context = message.getBody().toString();
        logger.info("消息接收成功:" + context);
    }
}