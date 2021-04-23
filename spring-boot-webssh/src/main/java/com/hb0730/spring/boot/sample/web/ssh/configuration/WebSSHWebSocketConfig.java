package com.hb0730.spring.boot.sample.web.ssh.configuration;

import com.hb0730.spring.boot.sample.web.ssh.interceptor.WebSocketInterceptor;
import com.hb0730.spring.boot.sample.web.ssh.ssh.SessionService;
import com.hb0730.spring.boot.sample.web.ssh.websocket.WebSSHWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket配置
 *
 * @author bing_huang
 * @date 2021/4/23
 * @since 1.0.0
 */
@Configuration
@EnableWebSocket
public class WebSSHWebSocketConfig implements WebSocketConfigurer {
    private final SessionService service;

    public WebSSHWebSocketConfig(SessionService service) {
        this.service = service;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new WebSSHWebSocketHandler(service), "/webssh")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");
    }
}
