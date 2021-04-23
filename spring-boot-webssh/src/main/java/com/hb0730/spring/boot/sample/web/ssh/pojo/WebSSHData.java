package com.hb0730.spring.boot.sample.web.ssh.pojo;

import java.io.Serializable;

/**
 * webssh数据传输
 *
 * @author bing_huang
 * @date 2021/4/23
 * @since 1.0.0
 */
public class WebSSHData implements Serializable {
    private static final long serialVersionUID = -4762059555687748933L;
    /**
     * 操作 connection，command
     */
    private String operate;
    private String host;
    private String command = "";

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
