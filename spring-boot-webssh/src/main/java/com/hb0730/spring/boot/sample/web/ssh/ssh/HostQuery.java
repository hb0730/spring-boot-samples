package com.hb0730.spring.boot.sample.web.ssh.ssh;

import com.hb0730.spring.boot.sample.web.ssh.pojo.HostInfo;

/**
 * @author bing_huang
 */
public interface HostQuery {

    HostInfo query(String host);
}
