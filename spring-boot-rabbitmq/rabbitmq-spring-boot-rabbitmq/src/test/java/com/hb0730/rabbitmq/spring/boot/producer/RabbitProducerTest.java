package com.hb0730.rabbitmq.spring.boot.producer;

import com.hb0730.rabbitmq.spring.boot.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RabbitProducerTest {

    @Autowired
    private RabbitProducer producer;

    //Direct
    @Test
    public void sendDirectMsg() {
        producer.sendDirectMsg("cord", String.valueOf(System.currentTimeMillis()));
    }

    //Topic
    @Test
    public void sendtopicMsg() {
        producer.sendExchangeMsg("topic-exchange","org.cord.test", "hello world");
    }

    //Fanout
    @Test
    public void sendFanoutMsg() {
        producer.sendExchangeMsg("fanout-exchange", "abcdefg", String.valueOf(System.currentTimeMillis()));
    }

    //Headers
    @Test
    public void sendHeadersMsg() {
        Map<String, Object> map = new HashMap<>();
        map.put("First","A");
        producer.sendHeadersMsg("headers-exchange", "hello word", map);
    }
}