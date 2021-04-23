package com.hb0730.spring.boot.sample.web.ssh.pojo;

import java.io.Serializable;

/**
 * @author bing_huang
 * @date 2021/4/23
 * @since 1.0.0
 */
public class HostInfo implements Serializable {
    private String host;
    private Integer port=22;
    private String username;
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
