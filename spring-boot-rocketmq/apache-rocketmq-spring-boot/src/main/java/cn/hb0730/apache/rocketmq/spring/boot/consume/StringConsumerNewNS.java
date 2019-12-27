package cn.hb0730.apache.rocketmq.spring.boot.consume;

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
@RocketMQMessageListener(nameServer = "${test1.rocketmq.myNameServer}", topic = "${test1.rocketmq.topic}", consumerGroup = "string_consumer_newns")
public class StringConsumerNewNS implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.printf("------- StringConsumerNewNS received: %s \n", message);
    }
}
