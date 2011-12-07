package com.raddle.swing.hosts.switcher.sort;

import java.util.Comparator;

import com.raddle.swing.hosts.switcher.model.Host;

public class HostDomainComparator implements Comparator<Host> {

    @Override
    public int compare(Host o1, Host o2) {
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }
        return revertDomain(o1.getDomain()).compareTo(revertDomain(o2.getDomain()));
    }

    private String revertDomain(String domain) {
        String[] strings = domain.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (int i = strings.length - 1; i >= 0; i--) {
            sb.append(".").append(strings[i]);
        }
        return sb.substring(1);
    }
}
