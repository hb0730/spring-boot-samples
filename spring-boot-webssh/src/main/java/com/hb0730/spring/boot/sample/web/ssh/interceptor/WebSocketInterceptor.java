package com.hb0730.spring.boot.sample.web.ssh.interceptor;

import com.hb0730.spring.boot.sample.web.ssh.constant.ConstantPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.UUID;

/**
 * Socket 拦截
 *
 * @author bing_huang
 * @date 2021/4/23
 * @since 1.0.0
 */
public class WebSocketInterceptor implements HandshakeInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketInterceptor.class);

    /**
     * Handler处理前调用
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            LOGGER.info("开始拦截");
            //生成一个UUID
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //将uuid放到websocketsession中
            attributes.put(ConstantPool.USER_UUID_KEY, uuid);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, Exception exception) {

    }
}
