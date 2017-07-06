package org.apache.http.conn.scheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpHost;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

@Deprecated
@ThreadSafe
public final class SchemeRegistry {
    private final ConcurrentHashMap<String, Scheme> registeredSchemes;

    public SchemeRegistry() {
        this.registeredSchemes = new ConcurrentHashMap();
    }

    public final Scheme getScheme(String name) {
        Scheme found = get(name);
        if (found != null) {
            return found;
        }
        throw new IllegalStateException("Scheme '" + name + "' not registered.");
    }

    public final Scheme getScheme(HttpHost host) {
        Args.notNull(host, HTTP.TARGET_HOST);
        return getScheme(host.getSchemeName());
    }

    public final Scheme get(String name) {
        Args.notNull(name, "Scheme name");
        return (Scheme) this.registeredSchemes.get(name);
    }

    public final Scheme register(Scheme sch) {
        Args.notNull(sch, "Scheme");
        return (Scheme) this.registeredSchemes.put(sch.getName(), sch);
    }

    public final Scheme unregister(String name) {
        Args.notNull(name, "Scheme name");
        return (Scheme) this.registeredSchemes.remove(name);
    }

    public final List<String> getSchemeNames() {
        return new ArrayList(this.registeredSchemes.keySet());
    }

    public void setItems(Map<String, Scheme> map) {
        if (map != null) {
            this.registeredSchemes.clear();
            this.registeredSchemes.putAll(map);
        }
    }
}
