package cn.hb0730.http.client.config;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * <p>
 * HttpClientPool
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Configuration
@ConditionalOnClass({HttpClient.class})
@EnableConfigurationProperties(value = HttpClientProperties.class)
public class HttpClientPoolAutoConfig {
    private Logger logger = LoggerFactory.getLogger(HttpClientPoolAutoConfig.class);
    private final HttpClientProperties properties;

    public HttpClientPoolAutoConfig(HttpClientProperties properties) {
        this.properties = properties;
    }

    /**
     * <p>
     * create pool manager
     * </p>
     *
     * @return HttpClient PoolManager
     */
    @Bean
    public HttpClientConnectionManager poolConnectionManager() {
        ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainSocketFactory)
                .register("https", sslSocketFactory).build();
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(registry);
        manager.setMaxTotal(properties.getMaxTotal());
        manager.setDefaultMaxPerRoute(properties.getMaxPerRoute());
        return manager;
    }


    /**
     * httpClientBuilder
     *
     * @return httpClientBuilder
     */
    @Bean
    public HttpClientBuilder clientBuilder() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(properties.getConnectTimeout())
                .setSocketTimeout(properties.getSocketTimeout())
                .setConnectionRequestTimeout(properties.getConnectionRequestTimeout()).build();
        HttpClientBuilder clientBuilder = HttpClients.custom().setConnectionManager(poolConnectionManager());
        clientBuilder.setDefaultRequestConfig(requestConfig);
        clientBuilder.setRetryHandler(createRetryHandler());
        return clientBuilder;
    }

    private HttpRequestRetryHandler createRetryHandler() {
        //请求失败时,进行请求重试
        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                logger.error("request error, message:{}", e.getMessage());
                if (i > properties.getRetryHandler()) {
                    //重试超过5次,放弃请求
                    logger.error("retry has more than 5 time, give up request");
                    return false;
                }
                if (e instanceof NoHttpResponseException) {
                    //服务器没有响应,可能是服务器断开了连接,应该重试
                    logger.error("receive no response from server, retry");
                    return true;
                }
                if (e instanceof SSLHandshakeException) {
                    // SSL握手异常
                    logger.error("SSL hand shake exception");
                    return false;
                }
                if (e instanceof InterruptedIOException) {
                    //超时
                    logger.error("InterruptedIOException");
                    return false;
                }
                if (e instanceof UnknownHostException) {
                    // 服务器不可达
                    logger.error("server host unknown");
                    return false;
                }
                if (e instanceof ConnectTimeoutException) {
                    // 连接超时
                    logger.error("Connection Time out");
                    return false;
                }
                if (e instanceof SSLException) {
                    logger.error("SSLException");
                    return false;
                }

                HttpClientContext context = HttpClientContext.adapt(httpContext);
                HttpRequest request = context.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    //如果请求不是关闭连接的请求
                    return true;
                }
                return false;
            }
        };
    }
}
