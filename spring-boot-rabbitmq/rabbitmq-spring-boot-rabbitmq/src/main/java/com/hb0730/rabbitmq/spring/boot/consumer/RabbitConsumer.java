package com.hb0730.rabbitmq.spring.boot.consumer;

import org.springframework.stereotype.Component;

/**
 * <p>
 * 消费
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class RabbitConsumer {

//    @RabbitListener(queues = "cord")
    public void processMessage(String msg) {
        System.out.format("Receiving Message: -----[%s]----- \n.", msg);
    }
}
