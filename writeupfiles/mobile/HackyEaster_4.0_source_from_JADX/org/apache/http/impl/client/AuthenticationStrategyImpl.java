package org.apache.http.impl.client;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AuthOption;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.AuthCache;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Lookup;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Immutable
abstract class AuthenticationStrategyImpl implements AuthenticationStrategy {
    private static final List<String> DEFAULT_SCHEME_PRIORITY;
    private final int challengeCode;
    private final String headerName;
    private final Log log;

    abstract Collection<String> getPreferredAuthSchemes(RequestConfig requestConfig);

    static {
        DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList(Arrays.asList(new String[]{AuthPolicy.SPNEGO, AuthPolicy.KERBEROS, AuthPolicy.NTLM, AuthPolicy.DIGEST, AuthPolicy.BASIC}));
    }

    AuthenticationStrategyImpl(int challengeCode, String headerName) {
        this.log = LogFactory.getLog(getClass());
        this.challengeCode = challengeCode;
        this.headerName = headerName;
    }

    public boolean isAuthenticationRequested(HttpHost authhost, HttpResponse response, HttpContext context) {
        Args.notNull(response, "HTTP response");
        return response.getStatusLine().getStatusCode() == this.challengeCode;
    }

    public Map<String, Header> getChallenges(HttpHost authhost, HttpResponse response, HttpContext context) throws MalformedChallengeException {
        Args.notNull(response, "HTTP response");
        Header[] headers = response.getHeaders(this.headerName);
        Map<String, Header> map = new HashMap(headers.length);
        for (Header header : headers) {
            CharArrayBuffer buffer;
            int pos;
            if (header instanceof FormattedHeader) {
                buffer = ((FormattedHeader) header).getBuffer();
                pos = ((FormattedHeader) header).getValuePos();
            } else {
                String s = header.getValue();
                if (s == null) {
                    throw new MalformedChallengeException("Header value is null");
                }
                buffer = new CharArrayBuffer(s.length());
                buffer.append(s);
                pos = 0;
            }
            while (pos < buffer.length() && HTTP.isWhitespace(buffer.charAt(pos))) {
                pos++;
            }
            int beginIndex = pos;
            while (pos < buffer.length() && !HTTP.isWhitespace(buffer.charAt(pos))) {
                pos++;
            }
            map.put(buffer.substring(beginIndex, pos).toLowerCase(Locale.ROOT), header);
        }
        return map;
    }

    public Queue<AuthOption> select(Map<String, Header> challenges, HttpHost authhost, HttpResponse response, HttpContext context) throws MalformedChallengeException {
        Args.notNull(challenges, "Map of auth challenges");
        Args.notNull(authhost, HTTP.TARGET_HOST);
        Args.notNull(response, "HTTP response");
        Args.notNull(context, "HTTP context");
        HttpClientContext clientContext = HttpClientContext.adapt(context);
        Queue<AuthOption> options = new LinkedList();
        Lookup<AuthSchemeProvider> registry = clientContext.getAuthSchemeRegistry();
        if (registry == null) {
            this.log.debug("Auth scheme registry not set in the context");
        } else {
            CredentialsProvider credsProvider = clientContext.getCredentialsProvider();
            if (credsProvider == null) {
                this.log.debug("Credentials provider not set in the context");
            } else {
                Collection<String> authPrefs = getPreferredAuthSchemes(clientContext.getRequestConfig());
                if (authPrefs == null) {
                    authPrefs = DEFAULT_SCHEME_PRIORITY;
                }
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Authentication schemes in the order of preference: " + authPrefs);
                }
                for (String id : authPrefs) {
                    Header challenge = (Header) challenges.get(id.toLowerCase(Locale.ROOT));
                    if (challenge != null) {
                        AuthSchemeProvider authSchemeProvider = (AuthSchemeProvider) registry.lookup(id);
                        if (authSchemeProvider == null) {
                            if (this.log.isWarnEnabled()) {
                                this.log.warn("Authentication scheme " + id + " not supported");
                            }
                        } else {
                            AuthScheme authScheme = authSchemeProvider.create(context);
                            authScheme.processChallenge(challenge);
                            Credentials credentials = credsProvider.getCredentials(new AuthScope(authhost.getHostName(), authhost.getPort(), authScheme.getRealm(), authScheme.getSchemeName()));
                            if (credentials != null) {
                                options.add(new AuthOption(authScheme, credentials));
                            }
                        }
                    } else {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Challenge for " + id + " authentication scheme not available");
                        }
                    }
                }
            }
        }
        return options;
    }

    public void authSucceeded(HttpHost authhost, AuthScheme authScheme, HttpContext context) {
        Args.notNull(authhost, HTTP.TARGET_HOST);
        Args.notNull(authScheme, "Auth scheme");
        Args.notNull(context, "HTTP context");
        HttpClientContext clientContext = HttpClientContext.adapt(context);
        if (isCachable(authScheme)) {
            AuthCache authCache = clientContext.getAuthCache();
            if (authCache == null) {
                authCache = new BasicAuthCache();
                clientContext.setAuthCache(authCache);
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Caching '" + authScheme.getSchemeName() + "' auth scheme for " + authhost);
            }
            authCache.put(authhost, authScheme);
        }
    }

    protected boolean isCachable(AuthScheme authScheme) {
        if (authScheme == null || !authScheme.isComplete()) {
            return false;
        }
        String schemeName = authScheme.getSchemeName();
        if (schemeName.equalsIgnoreCase(AuthPolicy.BASIC) || schemeName.equalsIgnoreCase(AuthPolicy.DIGEST)) {
            return true;
        }
        return false;
    }

    public void authFailed(HttpHost authhost, AuthScheme authScheme, HttpContext context) {
        Args.notNull(authhost, HTTP.TARGET_HOST);
        Args.notNull(context, "HTTP context");
        AuthCache authCache = HttpClientContext.adapt(context).getAuthCache();
        if (authCache != null) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Clearing cached auth scheme for " + authhost);
            }
            authCache.remove(authhost);
        }
    }
}
