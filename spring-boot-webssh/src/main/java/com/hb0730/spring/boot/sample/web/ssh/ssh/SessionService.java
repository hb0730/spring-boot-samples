package com.hb0730.spring.boot.sample.web.ssh.ssh;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * SSH Session
 *
 * @author bing_huang
 * @date 2021/4/23
 * @since 1.0.0
 */
public interface SessionService {
    /**
     * 初始化ssh连接
     *
     * @param session WebSocket Session
     */
    void initConnection(WebSocketSession session);

    /**
     * 处理客户段发的数据
     *
     * @param buffer 消息内容
     * @param session WebSocket Session
     */
    void recvHandle(String buffer, WebSocketSession session);

    /**
     * 数据写回前端
     *
     * @param session
     * @param buffer
     */
    void sendMessage(WebSocketSession session, byte[] buffer) throws IOException;

    /**
     * 关闭连接
     *
     * @param session
     */
    void close(WebSocketSession session);
}
