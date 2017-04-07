package org.apache.http.impl.client;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AuthOption;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.AuthCache;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Immutable
@Deprecated
class AuthenticationStrategyAdaptor implements AuthenticationStrategy {
    private final AuthenticationHandler handler;
    private final Log log;

    public AuthenticationStrategyAdaptor(AuthenticationHandler handler) {
        this.log = LogFactory.getLog(getClass());
        this.handler = handler;
    }

    public boolean isAuthenticationRequested(HttpHost authhost, HttpResponse response, HttpContext context) {
        return this.handler.isAuthenticationRequested(response, context);
    }

    public Map<String, Header> getChallenges(HttpHost authhost, HttpResponse response, HttpContext context) throws MalformedChallengeException {
        return this.handler.getChallenges(response, context);
    }

    public Queue<AuthOption> select(Map<String, Header> challenges, HttpHost authhost, HttpResponse response, HttpContext context) throws MalformedChallengeException {
        Args.notNull(challenges, "Map of auth challenges");
        Args.notNull(authhost, HTTP.TARGET_HOST);
        Args.notNull(response, "HTTP response");
        Args.notNull(context, "HTTP context");
        Queue<AuthOption> options = new LinkedList();
        CredentialsProvider credsProvider = (CredentialsProvider) context.getAttribute(HttpClientContext.CREDS_PROVIDER);
        if (credsProvider == null) {
            this.log.debug("Credentials provider not set in the context");
        } else {
            try {
                AuthScheme authScheme = this.handler.selectScheme(challenges, response, context);
                authScheme.processChallenge((Header) challenges.get(authScheme.getSchemeName().toLowerCase(Locale.ROOT)));
                Credentials credentials = credsProvider.getCredentials(new AuthScope(authhost.getHostName(), authhost.getPort(), authScheme.getRealm(), authScheme.getSchemeName()));
                if (credentials != null) {
                    options.add(new AuthOption(authScheme, credentials));
                }
            } catch (AuthenticationException ex) {
                if (this.log.isWarnEnabled()) {
                    this.log.warn(ex.getMessage(), ex);
                }
            }
        }
        return options;
    }

    public void authSucceeded(HttpHost authhost, AuthScheme authScheme, HttpContext context) {
        AuthCache authCache = (AuthCache) context.getAttribute(HttpClientContext.AUTH_CACHE);
        if (isCachable(authScheme)) {
            if (authCache == null) {
                authCache = new BasicAuthCache();
                context.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Caching '" + authScheme.getSchemeName() + "' auth scheme for " + authhost);
            }
            authCache.put(authhost, authScheme);
        }
    }

    public void authFailed(HttpHost authhost, AuthScheme authScheme, HttpContext context) {
        AuthCache authCache = (AuthCache) context.getAttribute(HttpClientContext.AUTH_CACHE);
        if (authCache != null) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Removing from cache '" + authScheme.getSchemeName() + "' auth scheme for " + authhost);
            }
            authCache.remove(authhost);
        }
    }

    private boolean isCachable(AuthScheme authScheme) {
        if (authScheme == null || !authScheme.isComplete()) {
            return false;
        }
        String schemeName = authScheme.getSchemeName();
        if (schemeName.equalsIgnoreCase(AuthPolicy.BASIC) || schemeName.equalsIgnoreCase(AuthPolicy.DIGEST)) {
            return true;
        }
        return false;
    }

    public AuthenticationHandler getHandler() {
        return this.handler;
    }
}
