package cn.hb0730.spring.boot.seata.samples.integration.account;

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
@SpringBootApplication(scanBasePackages = "cn.hb0730.spring.boot.seata.samples.integration.account")
@MapperScan({"cn.hb0730.spring.boot.seata.samples.integration.account.mapper"})
@EnableDubbo(scanBasePackages = "cn.hb0730.spring.boot.seata.samples.integration.account")
public class AccountExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountExampleApplication.class, args);
    }

}

