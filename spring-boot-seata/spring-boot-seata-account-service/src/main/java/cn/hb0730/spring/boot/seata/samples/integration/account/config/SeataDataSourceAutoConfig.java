package cn.hb0730.spring.boot.seata.samples.integration.account.config;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Author: bing_huang
 */
@Configuration
public class SeataDataSourceAutoConfig {



    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new HikariDataSource();
    }
    @Primary
    @Bean("dataSource")
    public DataSourceProxy dataSource(DataSource hikariDataSource) {
        return new DataSourceProxy(hikariDataSource);
    }

    /**
     * init mybatis sqlSessionFactory
     *
     * @Param: dataSourceProxy  datasource proxy
     * @Return: DataSourceProxy  datasource proxy
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper/*.xml"));
        return factoryBean.getObject();
    }
}
