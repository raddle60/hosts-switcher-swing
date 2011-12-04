package com.raddle.swing.hosts.switcher.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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

    public Db4oDao(){
    }

    public Db4oDao(String dbfile){
        this.dbfile = dbfile;
    }

    synchronized public Hosts getHosts(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("id is empty");
        }
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbfile);
        try {
            Hosts qh = new Hosts();
            qh.setId(id);
            return queryHosts(db, qh);
        } finally {
            db.close();
        }
    }

    synchronized public List<Hosts> getAllHosts() {
        if (new File(dbfile).exists()) {
            ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbfile);
            try {
                return retrieveAllFromObjectSet(db.queryByExample(Hosts.class), Hosts.class);
            } finally {
                db.close();
            }
        }
        return null;
    }

    synchronized public void saveHosts(Hosts hosts) {
        if (StringUtils.isEmpty(hosts.getId())) {
            throw new IllegalArgumentException("id is empty");
        }
        if (StringUtils.isEmpty(hosts.getEnv())) {
            throw new IllegalArgumentException("env is empty");
        }
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbfile);
        try {
            Hosts qh = new Hosts();
            qh.setId(hosts.getId());
            Hosts existHost = queryHosts(db, qh);
            if (existHost != null) {
                db.delete(existHost);
            }
            db.store(hosts);
        } finally {
            db.close();
        }
    }

    synchronized public void deleteHosts(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("id is empty");
        }
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbfile);
        try {
            Hosts qh = new Hosts();
            qh.setId(id);
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

    @SuppressWarnings("unchecked")
    private <T> List<T> retrieveAllFromObjectSet(ObjectSet<Object> objectSet, Class<T> targetClass) {
        if (objectSet != null) {
            List<T> list = new ArrayList<T>();
            for (Object t : objectSet) {
                list.add((T) t);
            }
            return list;
        }
        return null;
    }
}