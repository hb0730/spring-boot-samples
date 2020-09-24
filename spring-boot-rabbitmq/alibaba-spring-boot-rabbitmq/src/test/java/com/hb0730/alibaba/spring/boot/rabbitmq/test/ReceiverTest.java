package com.hb0730.alibaba.spring.boot.rabbitmq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 消费
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReceiverTest {

    @Resource
    private Receiver receiver;

    @Test
    public void process() {
    receiver.process("norProduct");
    }
}
