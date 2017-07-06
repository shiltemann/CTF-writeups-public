package org.apache.http.impl.client;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.BackoffManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.pool.ConnPoolControl;
import org.apache.http.util.Args;

public class AIMDBackoffManager implements BackoffManager {
    private double backoffFactor;
    private int cap;
    private final Clock clock;
    private final ConnPoolControl<HttpRoute> connPerRoute;
    private long coolDown;
    private final Map<HttpRoute, Long> lastRouteBackoffs;
    private final Map<HttpRoute, Long> lastRouteProbes;

    public AIMDBackoffManager(ConnPoolControl<HttpRoute> connPerRoute) {
        this(connPerRoute, new SystemClock());
    }

    AIMDBackoffManager(ConnPoolControl<HttpRoute> connPerRoute, Clock clock) {
        this.coolDown = 5000;
        this.backoffFactor = 0.5d;
        this.cap = 2;
        this.clock = clock;
        this.connPerRoute = connPerRoute;
        this.lastRouteProbes = new HashMap();
        this.lastRouteBackoffs = new HashMap();
    }

    public void backOff(HttpRoute route) {
        synchronized (this.connPerRoute) {
            int curr = this.connPerRoute.getMaxPerRoute(route);
            Long lastUpdate = getLastUpdate(this.lastRouteBackoffs, route);
            long now = this.clock.getCurrentTime();
            if (now - lastUpdate.longValue() < this.coolDown) {
                return;
            }
            this.connPerRoute.setMaxPerRoute(route, getBackedOffPoolSize(curr));
            this.lastRouteBackoffs.put(route, Long.valueOf(now));
        }
    }

    private int getBackedOffPoolSize(int curr) {
        if (curr <= 1) {
            return 1;
        }
        return (int) Math.floor(this.backoffFactor * ((double) curr));
    }

    public void probe(HttpRoute route) {
        synchronized (this.connPerRoute) {
            int curr = this.connPerRoute.getMaxPerRoute(route);
            int max = curr >= this.cap ? this.cap : curr + 1;
            Long lastProbe = getLastUpdate(this.lastRouteProbes, route);
            Long lastBackoff = getLastUpdate(this.lastRouteBackoffs, route);
            long now = this.clock.getCurrentTime();
            if (now - lastProbe.longValue() < this.coolDown || now - lastBackoff.longValue() < this.coolDown) {
                return;
            }
            this.connPerRoute.setMaxPerRoute(route, max);
            this.lastRouteProbes.put(route, Long.valueOf(now));
        }
    }

    private Long getLastUpdate(Map<HttpRoute, Long> updates, HttpRoute route) {
        Long lastUpdate = (Long) updates.get(route);
        if (lastUpdate == null) {
            return Long.valueOf(0);
        }
        return lastUpdate;
    }

    public void setBackoffFactor(double d) {
        boolean z = d > 0.0d && d < 1.0d;
        Args.check(z, "Backoff factor must be 0.0 < f < 1.0");
        this.backoffFactor = d;
    }

    public void setCooldownMillis(long l) {
        Args.positive(this.coolDown, "Cool down");
        this.coolDown = l;
    }

    public void setPerHostConnectionCap(int cap) {
        Args.positive(cap, "Per host connection cap");
        this.cap = cap;
    }
}
