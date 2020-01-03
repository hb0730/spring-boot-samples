package cn.hb0730.http.client.config;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * <p>
 * spring  ResTemplate + http Client Pool
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Configuration
public class RestTemplateConfig {

    @Resource
    private HttpClientPoolAutoConfig poolAutoConfig;

    @Bean(name = "httpClientRestTemplate")
    public RestTemplate restTemplate(ClientHttpRequestFactory requestFactory) {
        return new RestTemplate(requestFactory());
    }

    /**
     * get requestFactory
     *
     * @return ClientHttpRequestFactory
     */
    @Bean
    public ClientHttpRequestFactory requestFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        HttpClientBuilder httpClientBuilder = poolAutoConfig.clientBuilder();
        requestFactory.setHttpClient(httpClientBuilder.build());
        return requestFactory;
    }
}
