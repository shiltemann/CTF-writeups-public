package org.apache.http.client.protocol;

import java.net.URI;
import java.util.List;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthState;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Lookup;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;

@NotThreadSafe
public class HttpClientContext extends HttpCoreContext {
    public static final String AUTHSCHEME_REGISTRY = "http.authscheme-registry";
    public static final String AUTH_CACHE = "http.auth.auth-cache";
    public static final String COOKIESPEC_REGISTRY = "http.cookiespec-registry";
    public static final String COOKIE_ORIGIN = "http.cookie-origin";
    public static final String COOKIE_SPEC = "http.cookie-spec";
    public static final String COOKIE_STORE = "http.cookie-store";
    public static final String CREDS_PROVIDER = "http.auth.credentials-provider";
    public static final String HTTP_ROUTE = "http.route";
    public static final String PROXY_AUTH_STATE = "http.auth.proxy-scope";
    public static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    public static final String REQUEST_CONFIG = "http.request-config";
    public static final String TARGET_AUTH_STATE = "http.auth.target-scope";
    public static final String USER_TOKEN = "http.user-token";

    public static HttpClientContext adapt(HttpContext context) {
        if (context instanceof HttpClientContext) {
            return (HttpClientContext) context;
        }
        return new HttpClientContext(context);
    }

    public static HttpClientContext create() {
        return new HttpClientContext(new BasicHttpContext());
    }

    public HttpClientContext(HttpContext context) {
        super(context);
    }

    public RouteInfo getHttpRoute() {
        return (RouteInfo) getAttribute(HTTP_ROUTE, HttpRoute.class);
    }

    public List<URI> getRedirectLocations() {
        return (List) getAttribute(REDIRECT_LOCATIONS, List.class);
    }

    public CookieStore getCookieStore() {
        return (CookieStore) getAttribute(COOKIE_STORE, CookieStore.class);
    }

    public void setCookieStore(CookieStore cookieStore) {
        setAttribute(COOKIE_STORE, cookieStore);
    }

    public CookieSpec getCookieSpec() {
        return (CookieSpec) getAttribute(COOKIE_SPEC, CookieSpec.class);
    }

    public CookieOrigin getCookieOrigin() {
        return (CookieOrigin) getAttribute(COOKIE_ORIGIN, CookieOrigin.class);
    }

    private <T> Lookup<T> getLookup(String name, Class<T> cls) {
        return (Lookup) getAttribute(name, Lookup.class);
    }

    public Lookup<CookieSpecProvider> getCookieSpecRegistry() {
        return getLookup(COOKIESPEC_REGISTRY, CookieSpecProvider.class);
    }

    public void setCookieSpecRegistry(Lookup<CookieSpecProvider> lookup) {
        setAttribute(COOKIESPEC_REGISTRY, lookup);
    }

    public Lookup<AuthSchemeProvider> getAuthSchemeRegistry() {
        return getLookup(AUTHSCHEME_REGISTRY, AuthSchemeProvider.class);
    }

    public void setAuthSchemeRegistry(Lookup<AuthSchemeProvider> lookup) {
        setAttribute(AUTHSCHEME_REGISTRY, lookup);
    }

    public CredentialsProvider getCredentialsProvider() {
        return (CredentialsProvider) getAttribute(CREDS_PROVIDER, CredentialsProvider.class);
    }

    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        setAttribute(CREDS_PROVIDER, credentialsProvider);
    }

    public AuthCache getAuthCache() {
        return (AuthCache) getAttribute(AUTH_CACHE, AuthCache.class);
    }

    public void setAuthCache(AuthCache authCache) {
        setAttribute(AUTH_CACHE, authCache);
    }

    public AuthState getTargetAuthState() {
        return (AuthState) getAttribute(TARGET_AUTH_STATE, AuthState.class);
    }

    public AuthState getProxyAuthState() {
        return (AuthState) getAttribute(PROXY_AUTH_STATE, AuthState.class);
    }

    public <T> T getUserToken(Class<T> clazz) {
        return getAttribute(USER_TOKEN, clazz);
    }

    public Object getUserToken() {
        return getAttribute(USER_TOKEN);
    }

    public void setUserToken(Object obj) {
        setAttribute(USER_TOKEN, obj);
    }

    public RequestConfig getRequestConfig() {
        RequestConfig config = (RequestConfig) getAttribute(REQUEST_CONFIG, RequestConfig.class);
        return config != null ? config : RequestConfig.DEFAULT;
    }

    public void setRequestConfig(RequestConfig config) {
        setAttribute(REQUEST_CONFIG, config);
    }
}
