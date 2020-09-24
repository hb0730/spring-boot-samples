package com.hb0730.alibaba.spring.boot.rabbitmq.callback;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SenderWithCallbackTest {
    @Resource
    private SenderWithCallback sendWithCallback;
    @Test
    public void send() {
        sendWithCallback.send();
    }
}
