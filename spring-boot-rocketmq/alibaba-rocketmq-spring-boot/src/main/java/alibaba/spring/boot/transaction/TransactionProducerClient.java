package alibaba.spring.boot.transaction;

import alibaba.spring.boot.config.MqConfig;
import com.aliyun.openservices.ons.api.bean.TransactionProducerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */

@Configuration
public class TransactionProducerClient {

    @Autowired
    private MqConfig mqConfig;

    @Autowired
    private DemoLocalTransactionChecker localTransactionChecker;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public TransactionProducerBean buildTransactionProducer() {
        TransactionProducerBean producer = new TransactionProducerBean();
        producer.setProperties(mqConfig.getMqPropertie());
        producer.setLocalTransactionChecker(localTransactionChecker);
        return producer;
    }

}