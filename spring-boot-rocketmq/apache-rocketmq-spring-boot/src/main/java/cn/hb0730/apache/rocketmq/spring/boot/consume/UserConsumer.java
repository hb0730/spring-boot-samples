package cn.hb0730.apache.rocketmq.spring.boot.consume;

import cn.hb0730.apache.rocketmq.spring.boot.producer.domain.User;
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
@RocketMQMessageListener(nameServer = "${test1.rocketmq.myNameServer}", topic = "${test1.rocketmq.topic.user}", consumerGroup = "user_consumer")
public class UserConsumer implements RocketMQListener<User> {
    @Override
    public void onMessage(User message) {
        System.out.printf("######## user_consumer received: %s ; age: %s ; name: %s \n", message, message.getUserAge(), message.getUserName());
    }
}
