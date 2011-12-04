package com.raddle.swing.hosts.switcher.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.raddle.swing.hosts.switcher.sort.DomainRevertComparator;

/**
 * 功能描述：
 * @author xurong
 * time : 2011-11-23 下午03:52:08
 */
public class Hosts implements Serializable {

    private static final long serialVersionUID = 1L;
    private String            id;
    private String            env;
    private String            parentId;
    private Map<String, Host> hostMap          = new HashMap<String, Host>();

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
        Collections.sort(domains, new DomainRevertComparator());
        List<Host> hostList = new ArrayList<Host>();
        for (String domain : domains) {
            hostList.add(hostMap.get(domain));
        }
        return hostList;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
