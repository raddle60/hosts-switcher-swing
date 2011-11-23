package com.raddle.swing.hosts.switcher.model;

import java.io.Serializable;

/**
 * 功能描述：
 * @author xurong
 * time : 2011-11-23 下午03:52:29
 */
public class Host implements Serializable {
    private static final long serialVersionUID = 1L;
    private String domain;
    private String ip;
    private boolean active = true;

    public Host(String ip, String domain) {
        this.domain = domain;
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
