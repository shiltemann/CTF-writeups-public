package org.apache.http.client.params;

@Deprecated
public interface ClientPNames {
    public static final String ALLOW_CIRCULAR_REDIRECTS = "http.protocol.allow-circular-redirects";
    public static final String CONNECTION_MANAGER_FACTORY_CLASS_NAME = "http.connection-manager.factory-class-name";
    public static final String CONN_MANAGER_TIMEOUT = "http.conn-manager.timeout";
    public static final String COOKIE_POLICY = "http.protocol.cookie-policy";
    public static final String DEFAULT_HEADERS = "http.default-headers";
    public static final String DEFAULT_HOST = "http.default-host";
    public static final String HANDLE_AUTHENTICATION = "http.protocol.handle-authentication";
    public static final String HANDLE_REDIRECTS = "http.protocol.handle-redirects";
    public static final String MAX_REDIRECTS = "http.protocol.max-redirects";
    public static final String REJECT_RELATIVE_REDIRECT = "http.protocol.reject-relative-redirect";
    public static final String VIRTUAL_HOST = "http.virtual-host";
}
