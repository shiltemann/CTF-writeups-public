package org.apache.http.conn.ssl;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import org.apache.http.util.Args;

@Deprecated
public final class PrivateKeyDetails {
    private final X509Certificate[] certChain;
    private final String type;

    public PrivateKeyDetails(String type, X509Certificate[] certChain) {
        this.type = (String) Args.notNull(type, "Private key type");
        this.certChain = certChain;
    }

    public String getType() {
        return this.type;
    }

    public X509Certificate[] getCertChain() {
        return this.certChain;
    }

    public String toString() {
        return this.type + ':' + Arrays.toString(this.certChain);
    }
}
