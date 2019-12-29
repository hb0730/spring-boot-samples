package alibaba.spring.boot.batch;

import alibaba.spring.boot.config.MqConfig;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.batch.BatchMessageListener;
import com.aliyun.openservices.ons.api.bean.BatchConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class BatchConsumerClient {
    @Autowired
    private MqConfig mqConfig;

    @Autowired
    private BatchDemoMessageListener messageListener;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public BatchConsumerBean buildBatchConsumer() {
        BatchConsumerBean batchConsumerBean = new BatchConsumerBean();
        //配置文件
        Properties properties = mqConfig.getMqPropertie();
        properties.setProperty(PropertyKeyConst.GROUP_ID, mqConfig.getGroupId());
        //将消费者线程数固定为20个 20为默认值
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, "20");
        batchConsumerBean.setProperties(properties);
        //订阅关系
        Map<Subscription, BatchMessageListener> subscriptionTable = new HashMap<Subscription, BatchMessageListener>();
        Subscription subscription = new Subscription();
        subscription.setTopic(mqConfig.getTopic());
        subscription.setExpression(mqConfig.getTag());
        subscriptionTable.put(subscription, messageListener);
        //订阅多个topic如上面设置

        batchConsumerBean.setSubscriptionTable(subscriptionTable);
        return batchConsumerBean;
    }
}
