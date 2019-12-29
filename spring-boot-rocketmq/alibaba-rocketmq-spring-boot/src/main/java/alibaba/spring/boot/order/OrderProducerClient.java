package alibaba.spring.boot.order;

import alibaba.spring.boot.config.MqConfig;
import com.aliyun.openservices.ons.api.bean.OrderProducerBean;
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
public class OrderProducerClient {

    @Autowired
    private MqConfig mqConfig;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public OrderProducerBean buildOrderProducer() {
        OrderProducerBean orderProducerBean = new OrderProducerBean();
        orderProducerBean.setProperties(mqConfig.getMqPropertie());
        return orderProducerBean;
    }

}