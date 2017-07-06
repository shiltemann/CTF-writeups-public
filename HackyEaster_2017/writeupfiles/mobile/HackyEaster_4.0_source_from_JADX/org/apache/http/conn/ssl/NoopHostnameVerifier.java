package org.apache.http.conn.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import org.apache.http.annotation.Immutable;

@Immutable
public class NoopHostnameVerifier implements HostnameVerifier {
    public static final NoopHostnameVerifier INSTANCE;

    static {
        INSTANCE = new NoopHostnameVerifier();
    }

    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }

    public final String toString() {
        return "NO_OP";
    }
}
