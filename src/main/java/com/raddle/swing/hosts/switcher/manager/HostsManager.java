package com.raddle.swing.hosts.switcher.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

import com.raddle.swing.hosts.switcher.model.Host;
import com.raddle.swing.hosts.switcher.model.Hosts;

/**
 * 功能描述：
 * @author xurong
 * time : 2011-11-23 下午03:52:37
 */
public class HostsManager {
    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\s+");

    /**
     * 从文件解析hosts
     * @param reader
     * @return
     */
    public Hosts parseHosts(Reader reader) {
        Hosts hosts = new Hosts();
        BufferedReader br = new BufferedReader(reader);
        try {
            String line = br.readLine();
            while (line != null) {
                line = line.trim();
                // 不为#开头
                if (line.length() > 0 && !"#".startsWith(line)) {
                    // 尾上的注释
                    int index = line.indexOf("#");
                    if (index != -1) {
                        line = line.substring(0, index).trim();
                    }
                    // 可能尾上有注释
                    if (line.length() > 0) {
                        String[] hds = SPLIT_PATTERN.split(line);
                        if (hds.length > 2) {
                            String ip = hds[0];
                            for (int i = 1; i < hds.length; i++) {
                                hosts.setHost(new Host(ip, hds[i]));
                            }
                        }
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return hosts;
    }
}
