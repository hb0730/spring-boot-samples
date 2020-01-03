package com.hb0730.rabbitmq.spring.boot.consumer;

import com.hb0730.rabbitmq.spring.boot.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * direct模式消费者
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class DirectConsumer {
    private static Logger logger = LoggerFactory.getLogger(DirectConsumer.class);

    @RabbitListener(queues = Constants.QUEUE_NAME)
    public void processMessage(String context) {
        logger.info("消息接收成功:" + context);
    }


}
