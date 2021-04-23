package com.hb0730.spring.boot.sample.web.ssh.ssh.impl;

import com.hb0730.commons.json.exceptions.JsonException;
import com.hb0730.commons.json.utils.Jsons;
import com.hb0730.commons.lang.StringUtils;
import com.hb0730.commons.ssh.jsch.JschChannelShell;
import com.hb0730.commons.ssh.jsch.JschRuntimeException;
import com.hb0730.commons.ssh.jsch.JschSessionBuilder;
import com.hb0730.commons.ssh.jsch.JschUtils;
import com.hb0730.spring.boot.sample.web.ssh.constant.ConstantPool;
import com.hb0730.spring.boot.sample.web.ssh.pojo.HostInfo;
import com.hb0730.spring.boot.sample.web.ssh.pojo.SSHConnectInfo;
import com.hb0730.spring.boot.sample.web.ssh.pojo.WebSSHData;
import com.hb0730.spring.boot.sample.web.ssh.ssh.HostQuery;
import com.hb0730.spring.boot.sample.web.ssh.ssh.SessionService;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SSH Session
 *
 * @author bing_huang
 * @date 2021/4/23
 * @since 1.0.0
 */
@Component
public class SessionServiceImpl implements SessionService {
    @Autowired
    private HostQuery hostQuery;
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionServiceImpl.class);
    private final Map<String, SSHConnectInfo> sshMap = new ConcurrentHashMap<>();
    //线程池
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void initConnection(WebSocketSession session) {
        String userKey = String.valueOf(session.getAttributes().get(ConstantPool.USER_UUID_KEY));
        LOGGER.info("保存用户 [{}] 当前Session", userKey);
        SSHConnectInfo connectInfo = new SSHConnectInfo();
        connectInfo.setWebSocketSession(session);
        sshMap.put(userKey, connectInfo);
    }

    @Override
    public void recvHandle(String buffer, final WebSocketSession session) {
        WebSSHData data = null;
        try {
            data = Jsons.JSONS.jsonToObject(buffer, WebSSHData.class);
        } catch (JsonException e) {
            LOGGER.error("Json转换异常");
            LOGGER.error("异常信息:{}", e.getMessage());
            return;
        }
        switch (data.getOperate()) {
            case ConstantPool.WEBSSH_OPERATE_CONNECT:
                final WebSSHData finalData = data;
                executorService.execute(() -> {
                    try {
                        connectionSSH(session, finalData);
                    } catch (JSchException | JschRuntimeException | IOException e) {
                        LOGGER.error("webssh连接异常");
                        LOGGER.error("异常信息:{}", e.getMessage());
                        close(session);
                    }
                });
                break;
            case ConstantPool.WEBSSH_OPERATE_COMMAND:
                String userKey = getUserKey(session);
                SSHConnectInfo connectInfo = sshMap.get(userKey);
                String command = data.getCommand();
                try {
                    transToSSH(connectInfo.getChannel(), command);
                } catch (IOException e) {
                    LOGGER.error("webssh连接异常");
                    LOGGER.error("异常信息:{}", e.getMessage());
                    close(session);
                }
                break;
            default:
                LOGGER.error("不支持的操作");
                close(session);
                break;
        }
    }

    @Override
    public void sendMessage(WebSocketSession session, byte[] buffer) throws IOException {
        session.sendMessage(new TextMessage(buffer));
    }

    @Override
    public void close(WebSocketSession session) {
        String userId = String.valueOf(session.getAttributes().get(ConstantPool.USER_UUID_KEY));
        SSHConnectInfo sshConnectInfo = (SSHConnectInfo) sshMap.get(userId);
        if (sshConnectInfo != null) {
            //断开连接
            JschUtils.close(sshConnectInfo.getChannel());
            JschUtils.close(sshConnectInfo.getSession());
            //map中移除
            sshMap.remove(userId);
        }
    }

    private String getUserKey(WebSocketSession session) {
        return String.valueOf(session.getAttributes().get(ConstantPool.USER_UUID_KEY));
    }

    private void connectionSSH(WebSocketSession session, WebSSHData data) throws IOException, JSchException {
        //查询唯一的IP对应的SSH
        String host = data.getHost();
        if (StringUtils.isBlank(host)) {
            sendMessage(session, "host为空".getBytes(StandardCharsets.UTF_8));
            close(session);
        }
        HostInfo info = hostQuery.query(host);
        if (null == info) {
            sendMessage(session, "未找到对应的主机".getBytes(StandardCharsets.UTF_8));
            close(session);
            return;
        }
        Session sshSession = JschSessionBuilder.builder()
                .sshHost(host)
                .sshPort(info.getPort())
                .sshUser(info.getUsername())
                .sshPass(info.getPassword())
                .build();
        ChannelShell channel = JschChannelShell.builder(sshSession)
                .openShell();
        channel.connect();
        String userKey = getUserKey(session);
        SSHConnectInfo connectInfo = sshMap.get(userKey);

        connectInfo.setChannel(channel);
        connectInfo.setSession(sshSession);
        //读取终端返回的信息流
        InputStream inputStream = channel.getInputStream();
        try {
            //循环读取
            byte[] buffer = new byte[1024];
            int i = 0;
            //如果没有数据来，线程会一直阻塞在这个地方等待数据。
            while ((i = inputStream.read(buffer)) != -1) {
                sendMessage(session, Arrays.copyOfRange(buffer, 0, i));
            }

        } finally {
            //断开连接后关闭会话
            sshSession.disconnect();
            channel.disconnect();
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 将消息转发到终端
     */
    private void transToSSH(Channel channel, String command) throws IOException {
        if (channel != null) {
            OutputStream outputStream = channel.getOutputStream();
            outputStream.write(command.getBytes());
            outputStream.flush();
        }
    }
}
