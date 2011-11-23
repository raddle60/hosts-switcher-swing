package com.raddle.swing.hosts.switcher.manager;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;

import org.junit.Test;

import com.raddle.swing.hosts.switcher.model.Host;
import com.raddle.swing.hosts.switcher.model.Hosts;

public class HostsManagerTest {

    @Test
    public void testParseHosts() throws Exception {
        HostsManager hostsManager = new HostsManager();
        Hosts parseHosts = hostsManager.parseHosts(new FileReader(
                new File("c:\\WINDOWS\\system32\\drivers\\etc\\hosts")));
        for (Host host : parseHosts.getHostList()) {
            System.out.println(host.getDomain() + " " + host.getIp());
        }
    }

    @Test
    public void testWriteHosts() throws Exception {
        HostsManager hostsManager = new HostsManager();
        Hosts parseHosts = hostsManager.parseHosts(new FileReader(
                new File("c:\\WINDOWS\\system32\\drivers\\etc\\hosts")));
        for (Host host : parseHosts.getHostList()) {
            System.out.println(host.getDomain() + " " + host.getIp());
        }
        System.out.println("********************************");
        StringWriter sw = new StringWriter();
        hostsManager.writeHosts(parseHosts, sw);
        System.out.println(sw.getBuffer());
    }

}
