package cn.hb0730.apache.rocketmq.spring.boot.producer.controller;

import cn.hb0730.apache.rocketmq.spring.boot.producer.domain.OrderPaidEvent;
import cn.hb0730.apache.rocketmq.spring.boot.producer.domain.User;
import cn.hb0730.apache.rocketmq.spring.boot.producer.service.ProducerService;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@RestController
@RequestMapping("/producer")
public class ProducerController {
    @Resource
    private ProducerService service;
    private AtomicInteger age = new AtomicInteger();
    private AtomicInteger orderId = new AtomicInteger();

    /**
     * <p>
     * 测试发送请求
     * </p>
     */
    @RequestMapping("/testSyncSend")
    public SendResult testSyncSend() {
        return service.testSyncSend();
    }

    /**
     * <p>
     * 发送请求
     * </p>
     */
    @RequestMapping("/userSyncSend")
    public SendResult userSyncSend() {
        User user = new User();
        user.setUserAge((byte) 18);
        user.setUserName("kitty" + age.incrementAndGet());
        return service.userSyncSend(user);
    }

    /**
     * <p>
     *
     * </p>
     */
    @RequestMapping("/messageBuildByUserSend")
    public SendResult messageBuildByUserSend() {
        User user = new User();
        user.setUserAge((byte) 17);
        user.setUserName("kitty" + age.incrementAndGet());
        return service.messageBuildByUserSend(user);
    }

    @RequestMapping("/messageBuildBytes")
    public SendResult messageBuildBytes() {
        return service.messageBuildBytes();
    }

    @RequestMapping("/messageBuild")
    public SendResult messageBuild() {
        return service.messageBuild();
    }

    @RequestMapping("/convertAndSend")
    public void convertAndSend() {
        service.messageBuild();
    }

    /**
     * <p>
     * 异步
     * </p>
     */
    @RequestMapping("/asyncSend")
    public void asyncSend() {
        OrderPaidEvent event = new OrderPaidEvent();
        event.setOrderId(orderId.incrementAndGet());
        event.setPaidMoney(new BigDecimal(event.getOrderId()));
        service.asyncSend(event);

    }

    @RequestMapping("/testBatchMessages")
    public SendResult testBatchMessages() {
        return service.testBatchMessages();
    }
    @RequestMapping("/testTransaction")
    public void testTransaction() {
         service.testTransaction();
    }
}
