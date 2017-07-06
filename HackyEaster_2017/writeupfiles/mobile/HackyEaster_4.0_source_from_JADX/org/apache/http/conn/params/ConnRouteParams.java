package org.apache.http.conn.params;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@Immutable
@Deprecated
public class ConnRouteParams implements ConnRoutePNames {
    public static final HttpHost NO_HOST;
    public static final HttpRoute NO_ROUTE;

    static {
        NO_HOST = new HttpHost("127.0.0.255", 0, "no-host");
        NO_ROUTE = new HttpRoute(NO_HOST);
    }

    private ConnRouteParams() {
    }

    public static HttpHost getDefaultProxy(HttpParams params) {
        Args.notNull(params, "Parameters");
        HttpHost proxy = (HttpHost) params.getParameter(ConnRoutePNames.DEFAULT_PROXY);
        if (proxy == null || !NO_HOST.equals(proxy)) {
            return proxy;
        }
        return null;
    }

    public static void setDefaultProxy(HttpParams params, HttpHost proxy) {
        Args.notNull(params, "Parameters");
        params.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
    }

    public static HttpRoute getForcedRoute(HttpParams params) {
        Args.notNull(params, "Parameters");
        HttpRoute route = (HttpRoute) params.getParameter(ConnRoutePNames.FORCED_ROUTE);
        if (route == null || !NO_ROUTE.equals(route)) {
            return route;
        }
        return null;
    }

    public static void setForcedRoute(HttpParams params, HttpRoute route) {
        Args.notNull(params, "Parameters");
        params.setParameter(ConnRoutePNames.FORCED_ROUTE, route);
    }

    public static InetAddress getLocalAddress(HttpParams params) {
        Args.notNull(params, "Parameters");
        return (InetAddress) params.getParameter(ConnRoutePNames.LOCAL_ADDRESS);
    }

    public static void setLocalAddress(HttpParams params, InetAddress local) {
        Args.notNull(params, "Parameters");
        params.setParameter(ConnRoutePNames.LOCAL_ADDRESS, local);
    }
}
