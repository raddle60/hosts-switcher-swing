package com.raddle.swing.hosts.switcher.dao;

import java.io.File;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.raddle.swing.hosts.switcher.model.Hosts;

/**
 * 功能描述：
 * @author xurong
 * time : 2011-11-23 下午06:14:44
 */
public class Db4oDao {
    private String dbfile = "hosts.db4o";

    public Db4oDao() {
    }

    public Db4oDao(String dbfile) {
        this.dbfile = dbfile;
    }

    synchronized public Hosts getHosts(String env) {
        if (env == null || env.length() == 0) {
            throw new IllegalArgumentException("env is empty");
        }
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbfile);
        try {
            Hosts qh = new Hosts();
            qh.setEnv(env);
            return queryHosts(db, qh);
        } finally {
            db.close();
        }
    }

    synchronized public List<Hosts> getAllHosts() {
        if (new File(dbfile).exists()) {
            ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbfile);
            try {
                return db.queryByExample(Hosts.class);
            } finally {
                db.close();
            }
        }
        return null;
    }

    synchronized public void saveHosts(Hosts hosts) {
        if (hosts.getEnv() == null || hosts.getEnv().length() == 0) {
            throw new IllegalArgumentException("env is empty");
        }
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbfile);
        try {
            Hosts qh = new Hosts();
            qh.setEnv(hosts.getEnv());
            Hosts existHost = queryHosts(db, qh);
            db.delete(existHost);
            db.store(hosts);
        } finally {
            db.close();
        }
    }

    synchronized public void deleteHosts(String env) {
        if (env == null || env.length() == 0) {
            throw new IllegalArgumentException("env is empty");
        }
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbfile);
        try {
            Hosts qh = new Hosts();
            qh.setEnv(env);
            Hosts existHost = queryHosts(db, qh);
            db.delete(existHost);
        } finally {
            db.close();
        }
    }

    private Hosts queryHosts(ObjectContainer db, Hosts hosts) {
        ObjectSet<Object> hostsSet = db.queryByExample(hosts);
        if (hostsSet.size() > 1) {
            throw new IllegalStateException("too many results");
        } else if (hostsSet.size() == 1) {
            return (Hosts) hostsSet.get(0);
        }
        return null;
    }
}
