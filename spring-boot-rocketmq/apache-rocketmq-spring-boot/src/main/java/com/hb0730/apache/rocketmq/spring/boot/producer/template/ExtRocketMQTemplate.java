package com.hb0730.apache.rocketmq.spring.boot.producer.template;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * <p>
 *     生产者模板
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@ExtRocketMQTemplateConfiguration(nameServer = "${test1.rocktemq.extNameServer}")
public class ExtRocketMQTemplate extends RocketMQTemplate {
}
