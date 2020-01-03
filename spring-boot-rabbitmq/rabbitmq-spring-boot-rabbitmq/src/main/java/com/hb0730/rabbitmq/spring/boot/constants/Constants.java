package com.hb0730.rabbitmq.spring.boot.constants;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class Constants {
    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = "queue.name";

    public static final String DIRECT_QUEUE_NAME="direct.queue.name";

    /**
     * DIRECT交换机名称
     */
    public static final String DIRECT_EXCHANGE_NAME = "direct.exchange.name";

    /**
     * DIRECT队列路由键
     */
    public static final String DIRECT_QUEUE_ROUTE_KEY = "queue.route.key";

    /**
     * topic 主题式
     */
    public static final String TOPIC_QUEUE_NAME="topic.queue.name";
    public static final String TOPIC_EXCHANGE_NAME="topic.exchange.name";
    public static final String TOPIC_QUEUE_ROUTE_KEY = "topic.route.#";
    /**
     * fanout (广播式
     */
    public static final String FANOUT_QUEUE_NAME="fanout.queue.name";
    public static final String FANOUT_EXCHANGE_NAME="fanout.exchange.name";
    public static final String FANOUT_QUEUE_ROUTE_KEY="fanout.route.key";
}
