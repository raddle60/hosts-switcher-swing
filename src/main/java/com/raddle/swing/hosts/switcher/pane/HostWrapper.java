package com.raddle.swing.hosts.switcher.pane;

import org.apache.commons.lang.StringUtils;

import com.raddle.swing.hosts.switcher.manager.HostsManager;
import com.raddle.swing.hosts.switcher.model.Host;
import com.raddle.swing.hosts.switcher.model.Hosts;

/**
 * 类HostItem.java的实现描述：
 * 
 * @author xurong 2011-12-4 下午9:24:56
 */
public class HostWrapper {

    private HostsManager hostsManager;
    private Hosts hosts;
    private Host host;
    private String domain;

    public HostWrapper(HostsManager hostsManager, Hosts hosts, Host host, String domain){
        this.hostsManager = hostsManager;
        this.hosts = hosts;
        this.host = host;
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public String getIp() {
        if (isExist()) {
            return host.getIp();
        }
        return null;
    }

    public String getInheritIp() {
        return getInheritIp(hosts);
    }

    private String getInheritIp(Hosts self) {
        if (StringUtils.isNotEmpty(hosts.getParentId())) {
            Hosts parent = hostsManager.getHosts(hosts.getParentId());
            if (parent != null) {
                Host inheritHost = parent.getHost(domain);
                if (inheritHost != null) {
                    if (inheritHost.isActive()) {
                        return inheritHost.getIp();
                    }
                } else {
                    return getInheritIp(parent);
                }
            }
        }
        return null;
    }

    public String getFinalIp() {
        if (isActive()) {
            // 自己的
            return host.getIp();
        } else {
            if (isExist()) {
                // 禁用的直接返回空
                return null;
            } else {
                // 继承的
                return getInheritIp();
            }
        }
    }

    public boolean isActive() {
        return isExist() && host.isActive();
    }

    public boolean isExist() {
        return host != null;
    }

    @Override
    public String toString() {
        return domain;
    }
}
