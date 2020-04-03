package cn.hb0730.alibaba.rocketmq.spring.test;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQTransProducer;
import com.aliyun.mq.http.model.TopicMessage;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import org.junit.Test;

import java.util.Properties;

/**
 * <p>
 * 普通生产者
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class Producer {

    @Test
    public void tcpTest(){
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey,"");
        properties.setProperty(PropertyKeyConst.SecretKey,"");
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis,"3000");
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR,"");
        properties.setProperty(PropertyKeyConst.GROUP_ID,"demoRocket");
        properties.setProperty(PropertyKeyConst.isVipChannelEnabled,"false");
        properties.setProperty(PropertyKeyConst.EXACTLYONCE_DELIVERY,"false");
        properties.setProperty(PropertyKeyConst.InstanceName,"test-rocket");
        properties.setProperty(PropertyKeyConst.MsgTraceSwitch,"true");
        com.aliyun.openservices.ons.api.Producer producer = ONSFactory.createProducer(properties);
        producer.start();
        Message message=new Message();
        //设置消息主题
        message.setTopic("string-topic");
        message.setTag("demo");
        message.setBody("hell alibaba rocketmq".getBytes());

        SendResult send = producer.send(message);
    }

    @Test
    public void httpTest(){
        MQClient mqClient=new MQClient("","","");
         String topic ="string-topic";
        String instanceId="MQ_INST_1317287729996725_Bb74x3UY";
        String instanceName="test-rocket";
        MQTransProducer producer = mqClient.getTransProducer(instanceId, topic, instanceName);
        TopicMessage topicMessage=new TopicMessage();
        topicMessage.setMessageTag("demo");
        topicMessage.setMessageBody("hell alibaba rocketmq".getBytes());
        topicMessage = producer.publishMessage(topicMessage);
        System.out.printf("result:", topicMessage);
    }
}
