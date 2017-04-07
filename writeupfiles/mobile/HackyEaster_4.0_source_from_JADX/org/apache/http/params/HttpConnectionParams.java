package org.apache.http.params;

import org.apache.http.util.Args;

@Deprecated
public final class HttpConnectionParams implements CoreConnectionPNames {
    private HttpConnectionParams() {
    }

    public static int getSoTimeout(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return params.getIntParameter(CoreConnectionPNames.SO_TIMEOUT, 0);
    }

    public static void setSoTimeout(HttpParams params, int timeout) {
        Args.notNull(params, "HTTP parameters");
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
    }

    public static boolean getSoReuseaddr(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return params.getBooleanParameter(CoreConnectionPNames.SO_REUSEADDR, false);
    }

    public static void setSoReuseaddr(HttpParams params, boolean reuseaddr) {
        Args.notNull(params, "HTTP parameters");
        params.setBooleanParameter(CoreConnectionPNames.SO_REUSEADDR, reuseaddr);
    }

    public static boolean getTcpNoDelay(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return params.getBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
    }

    public static void setTcpNoDelay(HttpParams params, boolean value) {
        Args.notNull(params, "HTTP parameters");
        params.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, value);
    }

    public static int getSocketBufferSize(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return params.getIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, -1);
    }

    public static void setSocketBufferSize(HttpParams params, int size) {
        Args.notNull(params, "HTTP parameters");
        params.setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, size);
    }

    public static int getLinger(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return params.getIntParameter(CoreConnectionPNames.SO_LINGER, -1);
    }

    public static void setLinger(HttpParams params, int value) {
        Args.notNull(params, "HTTP parameters");
        params.setIntParameter(CoreConnectionPNames.SO_LINGER, value);
    }

    public static int getConnectionTimeout(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return params.getIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 0);
    }

    public static void setConnectionTimeout(HttpParams params, int timeout) {
        Args.notNull(params, "HTTP parameters");
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
    }

    public static boolean isStaleCheckingEnabled(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return params.getBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
    }

    public static void setStaleCheckingEnabled(HttpParams params, boolean value) {
        Args.notNull(params, "HTTP parameters");
        params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, value);
    }

    public static boolean getSoKeepalive(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return params.getBooleanParameter(CoreConnectionPNames.SO_KEEPALIVE, false);
    }

    public static void setSoKeepalive(HttpParams params, boolean enableKeepalive) {
        Args.notNull(params, "HTTP parameters");
        params.setBooleanParameter(CoreConnectionPNames.SO_KEEPALIVE, enableKeepalive);
    }
}
