package cn.hb0730.alibaba.spring.boot.rabbitmq.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback {

    Logger log= LoggerFactory.getLogger(RabbitConfirmCallback.class);
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("MessageConfirm correlationData:"+correlationData+",ack:"+ack+",cause:"+cause);
    }
}