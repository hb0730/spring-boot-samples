package com.hb0730.spring.boot.sample.web.ssh.pojo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;
import org.springframework.web.socket.WebSocketSession;

/**
 * ssh连接信息
 *
 * @author bing_huang
 * @date 2021/4/23
 * @since 1.0.0
 */
public class SSHConnectInfo {
    private WebSocketSession webSocketSession;
    private Channel channel;
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
