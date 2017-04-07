package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AuthProtocolState;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Immutable
public class RequestAuthCache implements HttpRequestInterceptor {
    private final Log log;

    public RequestAuthCache() {
        this.log = LogFactory.getLog(getClass());
    }

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        Args.notNull(request, "HTTP request");
        Args.notNull(context, "HTTP context");
        HttpClientContext clientContext = HttpClientContext.adapt(context);
        AuthCache authCache = clientContext.getAuthCache();
        if (authCache == null) {
            this.log.debug("Auth cache not set in the context");
            return;
        }
        CredentialsProvider credsProvider = clientContext.getCredentialsProvider();
        if (credsProvider == null) {
            this.log.debug("Credentials provider not set in the context");
            return;
        }
        RouteInfo route = clientContext.getHttpRoute();
        if (route == null) {
            this.log.debug("Route info not set in the context");
            return;
        }
        HttpHost target = clientContext.getTargetHost();
        if (target == null) {
            this.log.debug("Target host not set in the context");
            return;
        }
        AuthScheme authScheme;
        if (target.getPort() < 0) {
            target = new HttpHost(target.getHostName(), route.getTargetHost().getPort(), target.getSchemeName());
        }
        AuthState targetState = clientContext.getTargetAuthState();
        if (targetState != null && targetState.getState() == AuthProtocolState.UNCHALLENGED) {
            authScheme = authCache.get(target);
            if (authScheme != null) {
                doPreemptiveAuth(target, authScheme, targetState, credsProvider);
            }
        }
        HttpHost proxy = route.getProxyHost();
        AuthState proxyState = clientContext.getProxyAuthState();
        if (proxy != null && proxyState != null && proxyState.getState() == AuthProtocolState.UNCHALLENGED) {
            authScheme = authCache.get(proxy);
            if (authScheme != null) {
                doPreemptiveAuth(proxy, authScheme, proxyState, credsProvider);
            }
        }
    }

    private void doPreemptiveAuth(HttpHost host, AuthScheme authScheme, AuthState authState, CredentialsProvider credsProvider) {
        String schemeName = authScheme.getSchemeName();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Re-using cached '" + schemeName + "' auth scheme for " + host);
        }
        Credentials creds = credsProvider.getCredentials(new AuthScope(host, AuthScope.ANY_REALM, schemeName));
        if (creds != null) {
            if ("BASIC".equalsIgnoreCase(authScheme.getSchemeName())) {
                authState.setState(AuthProtocolState.CHALLENGED);
            } else {
                authState.setState(AuthProtocolState.SUCCESS);
            }
            authState.update(authScheme, creds);
            return;
        }
        this.log.debug("No credentials for preemptive authentication");
    }
}
