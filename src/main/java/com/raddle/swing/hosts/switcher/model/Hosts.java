package com.raddle.swing.hosts.switcher.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 * @author xurong
 * time : 2011-11-23 下午03:52:08
 */
public class Hosts implements Serializable {
    private static final long serialVersionUID = 1L;
    private String curEnv;
    private String parentEnv;
    private Map<String, Host> hostMap = new HashMap<String, Host>();

    public Host getHost(String domain) {
        return hostMap.get(domain);
    }

    public void setHost(Host host) {
        hostMap.put(host.getDomain(), host);
    }

    public void removeHost(String domain) {
        hostMap.remove(domain);
    }

    public List<Host> getHostList() {
        List<String> domains = new ArrayList<String>(hostMap.keySet());
        Collections.sort(domains);
        List<Host> hostList = new ArrayList<Host>();
        for (String domain : domains) {
            hostList.add(hostMap.get(domain));
        }
        return hostList;
    }

    public String getCurEnv() {
        return curEnv;
    }

    public void setCurEnv(String curEnv) {
        this.curEnv = curEnv;
    }

    public String getParentEnv() {
        return parentEnv;
    }

    public void setParentEnv(String parentEnv) {
        this.parentEnv = parentEnv;
    }

}
