package org.apache.http.conn.params;

import org.apache.http.annotation.Immutable;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@Immutable
@Deprecated
public final class ConnManagerParams implements ConnManagerPNames {
    private static final ConnPerRoute DEFAULT_CONN_PER_ROUTE;
    public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 20;

    /* renamed from: org.apache.http.conn.params.ConnManagerParams.1 */
    static class C01451 implements ConnPerRoute {
        C01451() {
        }

        public int getMaxForRoute(HttpRoute route) {
            return 2;
        }
    }

    @Deprecated
    public static long getTimeout(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return params.getLongParameter(ConnManagerPNames.TIMEOUT, 0);
    }

    @Deprecated
    public static void setTimeout(HttpParams params, long timeout) {
        Args.notNull(params, "HTTP parameters");
        params.setLongParameter(ConnManagerPNames.TIMEOUT, timeout);
    }

    static {
        DEFAULT_CONN_PER_ROUTE = new C01451();
    }

    public static void setMaxConnectionsPerRoute(HttpParams params, ConnPerRoute connPerRoute) {
        Args.notNull(params, "HTTP parameters");
        params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, connPerRoute);
    }

    public static ConnPerRoute getMaxConnectionsPerRoute(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        ConnPerRoute connPerRoute = (ConnPerRoute) params.getParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE);
        if (connPerRoute == null) {
            return DEFAULT_CONN_PER_ROUTE;
        }
        return connPerRoute;
    }

    public static void setMaxTotalConnections(HttpParams params, int maxTotalConnections) {
        Args.notNull(params, "HTTP parameters");
        params.setIntParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, maxTotalConnections);
    }

    public static int getMaxTotalConnections(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return params.getIntParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, DEFAULT_MAX_TOTAL_CONNECTIONS);
    }
}
