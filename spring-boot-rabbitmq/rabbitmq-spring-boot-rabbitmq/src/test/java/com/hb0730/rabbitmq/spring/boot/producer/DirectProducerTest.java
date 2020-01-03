package com.hb0730.rabbitmq.spring.boot.producer;

import com.hb0730.rabbitmq.spring.boot.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DirectProducerTest {

    @Resource
    private DirectProducer producer;

    @Test
    public void send(){
        producer.send();
    }
}