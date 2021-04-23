package com.hb0730.spring.boot.sample.web.ssh.ssh.impl;

import com.hb0730.commons.lang.StringUtils;
import com.hb0730.spring.boot.sample.web.ssh.pojo.HostInfo;
import com.hb0730.spring.boot.sample.web.ssh.ssh.HostQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author bing_huang
 * @date 2021/4/23
 * @since 1.0.0
 */
@Service
public class HostQueryImpl implements HostQuery {
    @Value("${web.ssh.host}")
    private String host;
    @Value("${web.ssh.port:22}")
    private Integer port;
    @Value("${web.ssh.username:root}")
    private String username;
    @Value("${web.ssh.password:123456}")
    private String password;

    @Override
    public HostInfo query(String host) {
        if (StringUtils.isBlank(host) || StringUtils.isBlank(this.host)) {
            return null;
        }
        if (!host.equals(this.host)) {
            return null;
        }
        HostInfo info = new HostInfo();
        info.setHost(host);
        info.setPassword(password);
        info.setPort(port);
        info.setUsername(username);
        return info;
    }
}
