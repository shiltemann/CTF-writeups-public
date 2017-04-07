package org.apache.http.impl.auth;

import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Immutable
public class SPNegoSchemeFactory implements AuthSchemeFactory, AuthSchemeProvider {
    private final boolean stripPort;
    private final boolean useCanonicalHostname;

    public SPNegoSchemeFactory(boolean stripPort, boolean useCanonicalHostname) {
        this.stripPort = stripPort;
        this.useCanonicalHostname = useCanonicalHostname;
    }

    public SPNegoSchemeFactory(boolean stripPort) {
        this.stripPort = stripPort;
        this.useCanonicalHostname = true;
    }

    public SPNegoSchemeFactory() {
        this(true, true);
    }

    public boolean isStripPort() {
        return this.stripPort;
    }

    public boolean isUseCanonicalHostname() {
        return this.useCanonicalHostname;
    }

    public AuthScheme newInstance(HttpParams params) {
        return new SPNegoScheme(this.stripPort, this.useCanonicalHostname);
    }

    public AuthScheme create(HttpContext context) {
        return new SPNegoScheme(this.stripPort, this.useCanonicalHostname);
    }
}
