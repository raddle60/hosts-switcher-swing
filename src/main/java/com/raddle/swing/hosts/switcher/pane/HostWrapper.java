package com.raddle.swing.hosts.switcher.pane;

import com.raddle.swing.hosts.switcher.manager.HostsManager;
import com.raddle.swing.hosts.switcher.model.Host;
import com.raddle.swing.hosts.switcher.model.Hosts;

/**
 * 类HostItem.java的实现描述：
 * @author xurong 2011-12-4 下午9:24:56
 */
public class HostWrapper {

    private HostsManager hostsManager;
    private Hosts hosts;
    private Host host;

    public HostWrapper(HostsManager hostsManager, Hosts hosts, Host host){
        this.hostsManager = hostsManager;
        this.hosts = hosts;
        this.host = host;
    }

    public String getDomain() {
        return host.getDomain();
    }

    public String getIp() {
        return host.getIp();
    }

    public String getInheritIp() {
        return null;
    }

    public String getFinalIp() {
        return host.getIp();
    }

    public boolean isActive() {
        return host.isActive();
    }

    @Override
    public String toString() {
        return host.getDomain();
    }
}
