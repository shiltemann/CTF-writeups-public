package org.apache.http.impl.client;

import java.security.Principal;
import javax.net.ssl.SSLSession;
import org.apache.http.HttpConnection;
import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.protocol.HttpContext;

@Immutable
public class DefaultUserTokenHandler implements UserTokenHandler {
    public static final DefaultUserTokenHandler INSTANCE;

    static {
        INSTANCE = new DefaultUserTokenHandler();
    }

    public Object getUserToken(HttpContext context) {
        HttpClientContext clientContext = HttpClientContext.adapt(context);
        Principal userPrincipal = null;
        AuthState targetAuthState = clientContext.getTargetAuthState();
        if (targetAuthState != null) {
            userPrincipal = getAuthPrincipal(targetAuthState);
            if (userPrincipal == null) {
                userPrincipal = getAuthPrincipal(clientContext.getProxyAuthState());
            }
        }
        if (userPrincipal != null) {
            return userPrincipal;
        }
        HttpConnection conn = clientContext.getConnection();
        if (!conn.isOpen() || !(conn instanceof ManagedHttpClientConnection)) {
            return userPrincipal;
        }
        SSLSession sslsession = ((ManagedHttpClientConnection) conn).getSSLSession();
        if (sslsession != null) {
            return sslsession.getLocalPrincipal();
        }
        return userPrincipal;
    }

    private static Principal getAuthPrincipal(AuthState authState) {
        AuthScheme scheme = authState.getAuthScheme();
        if (scheme != null && scheme.isComplete() && scheme.isConnectionBased()) {
            Credentials creds = authState.getCredentials();
            if (creds != null) {
                return creds.getUserPrincipal();
            }
        }
        return null;
    }
}
