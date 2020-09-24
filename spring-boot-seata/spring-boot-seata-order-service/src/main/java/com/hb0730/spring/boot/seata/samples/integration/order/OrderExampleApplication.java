package com.hb0730.spring.boot.seata.samples.integration.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */

@SpringBootApplication(scanBasePackages = "cn.hb0730.spring.boot.seata.samples.integration.order")
@MapperScan({"cn.hb0730.spring.boot.seata.samples.integration.order.mapper"})
@EnableDubbo(scanBasePackages = "cn.hb0730.spring.boot.seata.samples.integration.order")
public class OrderExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderExampleApplication.class, args);
    }

}
