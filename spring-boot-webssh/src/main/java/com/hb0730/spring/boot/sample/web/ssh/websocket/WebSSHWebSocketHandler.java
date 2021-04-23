package com.hb0730.spring.boot.sample.web.ssh.websocket;

import com.hb0730.spring.boot.sample.web.ssh.constant.ConstantPool;
import com.hb0730.spring.boot.sample.web.ssh.ssh.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * WebSSH的WebSocket处理器
 *
 * @author bing_huang
 * @date 2021/4/23
 * @since 1.0.0
 */
@Component
public class WebSSHWebSocketHandler implements WebSocketHandler {
    private final SessionService service;

    public WebSSHWebSocketHandler(SessionService service) {
        this.service = service;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSSHWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession webSocketSession) throws Exception {
        LOGGER.info("用户:{},连接WebSSH", webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY));
        service.initConnection(webSocketSession);
    }

    @Override
    public void handleMessage(@NonNull WebSocketSession webSocketSession, @NonNull WebSocketMessage<?> webSocketMessage) throws Exception {
        if (webSocketMessage instanceof TextMessage) {
            LOGGER.info("用户:{},发送命令:{}", webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY), webSocketMessage.toString());
            service.recvHandle(((TextMessage) webSocketMessage).getPayload(), webSocketSession);
        } else {
            System.out.println("Unexpected WebSocket message type: " + webSocketMessage);
        }
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession webSocketSession, @NonNull Throwable throwable) throws Exception {
        LOGGER.error("数据传输错误");
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession webSocketSession, @NonNull CloseStatus closeStatus) throws Exception {
        LOGGER.info("用户:{}断开webssh连接", String.valueOf(webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY)));
        service.close(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
