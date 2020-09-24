package com.hb0730.apache.rocketmq.spring.boot.consume;

import com.hb0730.apache.rocketmq.spring.boot.producer.domain.OrderPaidEvent;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Service
@RocketMQMessageListener(topic = "${test1.rocketmq.orderTopic}", consumerGroup = "order-paid-consumer")
public class OrderPaidEventConsumer implements RocketMQListener<OrderPaidEvent> {
    @Override
    public void onMessage(OrderPaidEvent message) {
        System.out.printf("------- OrderPaidEventConsumer received: %s [orderId : %s]\n", message,message.getOrderId());
    }
}
