package org.apache.http.conn.ssl;

import java.io.IOException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;

@Deprecated
public interface X509HostnameVerifier extends HostnameVerifier {
    void verify(String str, X509Certificate x509Certificate) throws SSLException;

    void verify(String str, SSLSocket sSLSocket) throws IOException;

    void verify(String str, String[] strArr, String[] strArr2) throws SSLException;
}
