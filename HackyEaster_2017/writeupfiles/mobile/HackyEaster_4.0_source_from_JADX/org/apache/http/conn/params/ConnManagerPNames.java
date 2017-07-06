package org.apache.http.conn.params;

@Deprecated
public interface ConnManagerPNames {
    public static final String MAX_CONNECTIONS_PER_ROUTE = "http.conn-manager.max-per-route";
    public static final String MAX_TOTAL_CONNECTIONS = "http.conn-manager.max-total";
    public static final String TIMEOUT = "http.conn-manager.timeout";
}
