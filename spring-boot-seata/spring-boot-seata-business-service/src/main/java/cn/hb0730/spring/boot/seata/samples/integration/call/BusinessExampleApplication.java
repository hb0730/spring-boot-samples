package cn.hb0730.spring.boot.seata.samples.integration.call;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@SpringBootApplication(scanBasePackages = "cn.hb0730.spring.boot.seata.samples.integration.call", exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo(scanBasePackages = "cn.hb0730.spring.boot.seata.samples.integration.call")
public class BusinessExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessExampleApplication.class, args);
    }

}

