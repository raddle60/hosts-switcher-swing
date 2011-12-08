package com.raddle.swing.hosts.switcher.sort;

import java.util.Comparator;

import com.raddle.swing.hosts.switcher.model.Hosts;

public class HostsComparator implements Comparator<Hosts> {

    @Override
    public int compare(Hosts o1, Hosts o2) {
        if (o1.getEnv() == null) {
            return 1;
        }
        if (o2.getEnv() == null) {
            return -1;
        }
        return o1.getEnv().compareTo(o2.getEnv());
    }
}
