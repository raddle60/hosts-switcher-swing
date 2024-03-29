package com.raddle.swing.hosts.switcher.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.raddle.swing.hosts.switcher.dao.Db4oDao;
import com.raddle.swing.hosts.switcher.model.Host;
import com.raddle.swing.hosts.switcher.model.Hosts;
import com.raddle.swing.hosts.switcher.pane.HostWrapper;

/**
 * 功能描述：
 *
 * @author xurong time : 2011-11-23 下午03:52:37
 */
public class HostsManager {

    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\s+");
    private Db4oDao db4oDao = new Db4oDao();

    /**
     * 从文件解析hosts
     *
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
                    // 可能尾上有注释
                    int index = line.indexOf("#");
                    if (index != -1) {
                        line = line.substring(0, index).trim();
                    }
                    if (line.length() > 0) {
                        String[] hds = SPLIT_PATTERN.split(line);
                        if (hds.length > 1) {
                            // ip 后面有多个域名
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

    public void writeHosts(Hosts hosts, Writer writer) {
        List<Host> allHost = new ArrayList<Host>();
        allHost.addAll(hosts.getHostList());
        if (StringUtils.isNotEmpty(hosts.getParentId())) {
            for (Host host : getAllInheritHost(hosts.getParentId()).values()) {
                if (hosts.getHost(host.getDomain()) == null) {
                    allHost.add(host);
                }
            }
        }
        List<HostWrapper> allWrapperHost = new ArrayList<HostWrapper>();
        for (Host host : allHost) {
            allWrapperHost.add(new HostWrapper(this, hosts, hosts.getHost(host.getDomain()) == null ? null : host, host
                    .getDomain()));
        }
        /// 转成hosts格式
        Map<String, Set<String>> hostMap = new HashMap<String, Set<String>>();
        for (HostWrapper wrapperHost : allWrapperHost) {
            String finalIp = wrapperHost.getFinalIp();
            if (StringUtils.isNotBlank(finalIp)) {
                Set<String> domains = hostMap.get(finalIp);
                if (domains == null) {
                    domains = new HashSet<String>();
                    hostMap.put(finalIp, domains);
                }
                domains.add(wrapperHost.getDomain());
            }
        }
        ///
        PrintWriter bw = new PrintWriter(writer);
        // 输入注释
        bw.println("######generate by raddle hosts manager " + DateFormatUtils.format(new Date(), "yyyy/M/d H:m:s")
                + "######");
        bw.println("######env:" + StringUtils.defaultString(hosts.getEnv()) + "######");
        for (String ip : hostMap.keySet()) {
            List<String> domainList = new ArrayList<String>(hostMap.get(ip));
            Collections.sort(domainList);
            for (String domain : domainList) {
                bw.println(ip + " " + domain);
            }
        }
        bw.flush();
        bw.close();
    }

    public List<Hosts> getAllHosts() {
        List<Hosts> list = db4oDao.getAllHosts();
        if (list == null) {
            list = new ArrayList<Hosts>();
        }
        return list;
    }

    public void saveHosts(Hosts hosts) {
        // 检查是否循环依赖
        Set<String> exists = new HashSet<String>();
        if (StringUtils.isNotEmpty(hosts.getId())) {
            exists.add(hosts.getId());
        }
        if (isCycle(exists, hosts.getParentId())) {
            throw new IllegalStateException("循环依赖");
        }
        db4oDao.saveHosts(hosts);
    }

    public boolean isCycle(Set<String> parents, String parentId) {
        if (StringUtils.isNotEmpty(parentId)) {
            if (parents.contains(parentId)) {
                return true;
            } else {
                parents.add(parentId);
                Hosts parent = db4oDao.getHosts(parentId);
                if (parent != null && parent.getParentId() != null) {
                    return isCycle(parents, parent.getParentId());
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public Hosts getHosts(String id) {
        return db4oDao.getHosts(id);
    }

    public Map<String, Host> getAllInheritHost(String parentId) {
        Map<String, Host> existsHost = new HashMap<String, Host>();
        Hosts parent = db4oDao.getHosts(parentId);
        if (parent != null) {
            for (Host host : parent.getHostList()) {
                existsHost.put(host.getDomain(), host);
            }
            if (StringUtils.isNotEmpty(parent.getParentId())) {
                Map<String, Host> parentHosts = getAllInheritHost(parent.getParentId());
                for (String domain : parentHosts.keySet()) {
                    if (!existsHost.containsKey(domain)) {
                        existsHost.put(domain, parentHosts.get(domain));
                    }
                }
            }
        }
        return existsHost;
    }

    public void deleteHosts(String id) {
        db4oDao.deleteHosts(id);
    }
}
