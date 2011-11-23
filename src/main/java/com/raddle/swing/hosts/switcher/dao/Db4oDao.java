package com.raddle.swing.hosts.switcher.dao;

import com.raddle.swing.hosts.switcher.model.Hosts;

public class Db4oDao {
    private String dbfile;

    private Db4oDao(String dbfile) {
        this.dbfile = dbfile;
    }

    public void saveHosts(Hosts hosts) {

    }

    public void deleteHosts(String env) {

    }
}
