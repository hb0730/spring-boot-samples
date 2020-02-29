package cn.hb0730.spring.boot.seata.samples.integration.storage;

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
@SpringBootApplication(scanBasePackages = "cn.hb0730.spring.boot.seata.samples.integration.storage")
@MapperScan({"cn.hb0730.spring.boot.seata.samples.integration.storage.mapper"})
@EnableDubbo(scanBasePackages = "cn.hb0730.spring.boot.seata.samples.integration.storage")
public class StorageExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageExampleApplication.class, args);
    }
}
