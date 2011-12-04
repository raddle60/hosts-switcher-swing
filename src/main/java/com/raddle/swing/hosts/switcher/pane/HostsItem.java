package com.raddle.swing.hosts.switcher.pane;

import java.util.ArrayList;
import java.util.List;

import com.raddle.swing.hosts.switcher.model.Hosts;

public class HostsItem {

    private String hostsId;
    private String hostsEnv;

    public HostsItem(String hostsId, String hostsEnv){
        this.hostsId = hostsId;
        this.hostsEnv = hostsEnv;
    }

    public String getHostsId() {
        return hostsId;
    }

    public void setHostsId(String hostsId) {
        this.hostsId = hostsId;
    }

    public String getHostsEnv() {
        return hostsEnv;
    }

    public void setHostsEnv(String hostsEnv) {
        this.hostsEnv = hostsEnv;
    }

    @Override
    public String toString() {
        return hostsEnv;
    }

    public static List<HostsItem> fromHostsList(List<Hosts> hostsList) {
        List<HostsItem> list = new ArrayList<HostsItem>();
        for (Hosts hosts : hostsList) {
            list.add(new HostsItem(hosts.getId(), hosts.getEnv()));
        }
        return list;
    }
}
