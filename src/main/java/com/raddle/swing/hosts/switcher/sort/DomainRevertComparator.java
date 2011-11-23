package com.raddle.swing.hosts.switcher.sort;

import java.util.Comparator;

public class DomainRevertComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }
        return revertDomain(o1).compareTo(revertDomain(o2));
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
