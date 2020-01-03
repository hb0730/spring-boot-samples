package com.hb0730.rabbitmq.spring.boot.config;

import com.hb0730.rabbitmq.spring.boot.constants.Constants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * rabbitmq 配置
 * <pre>
 * Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 * Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 * Queue:消息的载体,每个消息都会被投到一个或多个队列。
 * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 * Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 * vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 * Producer:消息生产者,就是投递消息的程序.
 * Consumer:消息消费者,就是接受消息的程序.
 * Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 * </pre>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Configuration
public class RabbitConfig {
    /**
     * 声明队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        Map<String, Object> arguments = new HashMap<>(4);
        return new Queue(Constants.QUEUE_NAME, true, false, false, arguments);
    }

    @Bean
    public Queue topicQueue() {
        Map<String, Object> arguments = new HashMap<>(4);
        return new Queue(Constants.TOPIC_QUEUE_NAME, true, false, false, arguments);
    }

    @Bean
    public Queue fanoutQueue() {
        Map<String, Object> arguments = new HashMap<>(4);
        return new Queue(Constants.FANOUT_QUEUE_NAME, true, false, false, arguments);
    }

    /**
     * <p>
     * 声明Direct交换机<br>
     * 路由模式 <br>
     * Direct Exchange ：一对一完全匹配，需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
     * </p>
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(Constants.DIRECT_EXCHANGE_NAME);
    }

    /**
     * <p>
     * 将队列与direct交换机进行绑定，并指定路由键
     * </p>
     *
     * @return
     */
    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(Constants.DIRECT_QUEUE_ROUTE_KEY);
    }

    /**
     * 通配符交换机<br>
     * 声明Topic交换机<br>
     * 通配符模式 <br>
     * Topic Exchange ：多对多正则匹配。此时队列需要绑定要一个模式上。符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*” 只会匹配到“audit.irs”
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(Constants.TOPIC_EXCHANGE_NAME);
    }

    /**
     * 将队列与Topic交换机进行绑定，并指定路由键
     *
     * @return
     */
    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(Constants.TOPIC_QUEUE_ROUTE_KEY);
    }

    /**
     * <p>
     * 声明fanout交换机<br>
     * 发布/订阅模式 <br>
     * Fanout Exchange ：一对多完全匹配。你只需要简单的将队列绑定到交换机上。一个发送到交换机的消息都会被转发到与该交换机绑定的所有队列上。很像子网广播，每台子网内的主机都获得了一份复制的消息。Fanout交换机转发消息是最快的。
     * </p>
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(Constants.FANOUT_EXCHANGE_NAME);
    }

    /**
     * <p>
     * 将队列与fanout交换机进行绑定
     * </p>
     *
     * @return
     */
    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }
//    private static final String topicExchangeName = "topic-exchange";
//    private static final String fanoutExchange = "fanout-exchange";
//    private static final String headersExchange = "headers-exchange";
//    private static final String directExchange = "direct-exchange";
//

//
//    /**
//     * 声明Headers交换机
//     *
//     * @return
//     */
//    @Bean
//    public HeadersExchange headersExchange() {
//        return new HeadersExchange(headersExchange);
//    }
//
//    /**
//     * 将队列与headers交换机进行绑定
//     *
//     * @param queue
//     * @param headersExchange
//     * @return
//     */
//    @Bean
//    Binding headersBinding(Queue queue, HeadersExchange headersExchange) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("First", "A");
//        map.put("Fourth", "D");
//        return BindingBuilder.bind(queue).to(headersExchange).whereAny(map).match();
//    }

}
