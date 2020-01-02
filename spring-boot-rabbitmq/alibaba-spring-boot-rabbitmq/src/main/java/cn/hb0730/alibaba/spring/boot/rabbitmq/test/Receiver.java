package cn.hb0730.alibaba.spring.boot.rabbitmq.test;

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
public class Receiver {
    @RabbitListener(queues = "queue-rabbit-springboot-advance5")
    public void process(String hello) {
        System.out.println("Receiver : " + hello);
    }
}