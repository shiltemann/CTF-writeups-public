package org.apache.http.impl.conn;

import org.apache.http.HttpHost;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;

@Deprecated
@ThreadSafe
public final class SchemeRegistryFactory {
    public static SchemeRegistry createDefault() {
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, 80, PlainSocketFactory.getSocketFactory()));
        registry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        return registry;
    }

    public static SchemeRegistry createSystemDefault() {
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, 80, PlainSocketFactory.getSocketFactory()));
        registry.register(new Scheme("https", 443, SSLSocketFactory.getSystemSocketFactory()));
        return registry;
    }
}
