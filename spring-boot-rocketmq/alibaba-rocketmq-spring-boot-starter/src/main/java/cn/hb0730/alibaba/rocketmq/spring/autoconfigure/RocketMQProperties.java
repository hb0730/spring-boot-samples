package cn.hb0730.alibaba.rocketmq.spring.autoconfigure;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@SuppressWarnings("WeakerAccess")
public class RocketMQProperties {

    /**
     * <p>
     * Name Server地址
     * </p>
     */
    private String nameSrvAddr;

    /**
     * 实例id
     */
    private String instanceId;
    /**
     * <p>
     * AccessKey 阿里云身份验证
     * </p>
     */
    private String accessKey;
    /**
     * <p>
     * SecretKey 阿里云身份验证
     * </p>
     */
    private String secretKey;

    /**
     * <p>
     * 设置客户端接入来源
     * </p>
     */
    private String onsChannel;
    /**
     * 是否启动vip channel 布尔类型
     */
    private String isVipChannelEnabled;

    /**
     * 设置实例名
     */
    private String instanceName;

    /**
     * MQ消息轨迹开关
     */
    private String msgTraceSwitch;

    /**
     * 提供者
     */
    private Producer producer;

    /**
     * 消费者
     */
    private Consumer consumer;

    public String getNameSrvAddr() {
        return nameSrvAddr;
    }

    public void setNameSrvAddr(String nameSrvAddr) {
        this.nameSrvAddr = nameSrvAddr;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getOnsChannel() {
        return onsChannel;
    }

    public void setOnsChannel(String onsChannel) {
        this.onsChannel = onsChannel;
    }

    public String getIsVipChannelEnabled() {
        return isVipChannelEnabled;
    }

    public void setIsVipChannelEnabled(String isVipChannelEnabled) {
        this.isVipChannelEnabled = isVipChannelEnabled;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getMsgTraceSwitch() {
        return msgTraceSwitch;
    }

    public void setMsgTraceSwitch(String msgTraceSwitch) {
        this.msgTraceSwitch = msgTraceSwitch;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    /**
     * <p>
     * 提供者
     * </p>
     */
    public static class Producer {
        /**
         * <p>
         * 设置发送超时时间，单位毫秒
         * </p>
         */
        private String sendMsgTimeoutMillis = "3000";
        /**
         * <p>
         * 客户端ID
         * </p>
         */
        private String groupId;

        /**
         * <p>
         * 是否开启mqtransaction，用于使用exactly-once投递语义
         * </p>
         */
        private String exactlyonceDelivery;

        public String getSendMsgTimeoutMillis() {
            return sendMsgTimeoutMillis;
        }

        public void setSendMsgTimeoutMillis(String sendMsgTimeoutMillis) {
            this.sendMsgTimeoutMillis = sendMsgTimeoutMillis;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getExactlyonceDelivery() {
            return exactlyonceDelivery;
        }

        public void setExactlyonceDelivery(String exactlyonceDelivery) {
            this.exactlyonceDelivery = exactlyonceDelivery;
        }
    }

    /**
     * 消费者
     */
    public static class Consumer {
        /**
         * <p>
         * 设置发送超时时间，单位毫秒
         * </p>
         */
        private String sendMsgTimeoutMillis = "3000";
        /**
         * <p>
         * 客户端ID
         * </p>
         */
        private String groupId;
        /**
         * 消息消费失败时的最大重试次数
         */
        private String maxReconsumeTimes;

        /**
         * 每次获取最大消息数量
         */
        private String maxBatchMessageCount;

        /**
         * <p>
         * 设置每条消息消费的最大超时时间,超过这个时间,这条消息将会被视为消费失败,等下次重新投递再次消费. 每个业务需要设置一个合理的值. 单位(分钟)
         * </p>
         */
        private String consumeTimeout;

        public String getSendMsgTimeoutMillis() {
            return sendMsgTimeoutMillis;
        }

        public void setSendMsgTimeoutMillis(String sendMsgTimeoutMillis) {
            this.sendMsgTimeoutMillis = sendMsgTimeoutMillis;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getMaxReconsumeTimes() {
            return maxReconsumeTimes;
        }

        public void setMaxReconsumeTimes(String maxReconsumeTimes) {
            this.maxReconsumeTimes = maxReconsumeTimes;
        }

        public String getMaxBatchMessageCount() {
            return maxBatchMessageCount;
        }

        public void setMaxBatchMessageCount(String maxBatchMessageCount) {
            this.maxBatchMessageCount = maxBatchMessageCount;
        }

        public String getConsumeTimeout() {
            return consumeTimeout;
        }

        public void setConsumeTimeout(String consumeTimeout) {
            this.consumeTimeout = consumeTimeout;
        }
    }
}
