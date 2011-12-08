package com.raddle.swing.hosts.switcher.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.raddle.swing.hosts.switcher.model.Host;
import com.raddle.swing.hosts.switcher.model.Hosts;

/**
 * 功能描述：
 * 
 * @author xurong time : 2011-11-23 下午06:14:44
 */
public class Db4oDao {

    private static String dbfile = "hosts.db4o";
    private static ObjectContainer db;

    synchronized public static void init() {
        if (db == null) {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbfile);
        }
    }

    synchronized public static void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public Db4oDao(){
    }

    synchronized public Hosts getHosts(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("id is empty");
        }
        Hosts qh = new Hosts();
        qh.setId(id);
        return queryHosts(db, qh);
    }

    synchronized public List<Hosts> getAllHosts() {
        if (new File(dbfile).exists()) {
            return retrieveAllFromObjectSet(db.queryByExample(Hosts.class), Hosts.class);
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
        Hosts qh = new Hosts();
        qh.setId(hosts.getId());
        Hosts existHost = queryHosts(db, qh);
        if (existHost != null) {
            Hosts update = existHost.clone();
            update.removeAll();
            update.setEnv(hosts.getEnv());
            update.setParentId(hosts.getParentId());
            for (Host host : hosts.getHostList()) {
                update.setHost(host);
            }
            db.delete(existHost);
            db.store(update);
            db.commit();
        } else {
            db.store(hosts);
            db.commit();
        }
    }

    synchronized public void deleteHosts(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("id is empty");
        }
        Hosts qh = new Hosts();
        qh.setId(id);
        Hosts existHost = queryHosts(db, qh);
        db.delete(existHost);
        db.commit();
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
