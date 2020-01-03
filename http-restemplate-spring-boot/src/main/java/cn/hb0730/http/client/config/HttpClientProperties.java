package cn.hb0730.http.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@ConfigurationProperties(prefix = "spring.httpclient")
public class HttpClientProperties {
    /**
     * <p>
     *  连接池最大连接数
     * </p>
     */
    private Integer  maxTotal = 1000;
    /**
     * 每个主机的并发
     */
    private Integer maxPerRoute=100;

    /**
     * 链接超时
     */
    private Integer connectTimeout=2 * 1000;
    /**
     * 服务器返回数据(response)的时间
     */
    private Integer socketTimeout=2 * 1000;
    /**
     * 从连接池中获取连接的超时时间，超时间未拿到可用连接
     */
    private Integer connectionRequestTimeout=200;
    /**
     * 重试次数
     */
    private Integer retryHandler=3;
    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(Integer maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public Integer getRetryHandler() {
        return retryHandler;
    }

    public void setRetryHandler(Integer retryHandler) {
        this.retryHandler = retryHandler;
    }
}
