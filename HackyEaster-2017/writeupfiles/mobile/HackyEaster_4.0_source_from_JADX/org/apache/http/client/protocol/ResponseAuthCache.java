package org.apache.http.client.protocol;

import android.support.v4.app.NotificationCompat.WearableExtender;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AuthProtocolState;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.client.AuthCache;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.Args;

@Immutable
@Deprecated
public class ResponseAuthCache implements HttpResponseInterceptor {
    private final Log log;

    /* renamed from: org.apache.http.client.protocol.ResponseAuthCache.1 */
    static /* synthetic */ class C00711 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$http$auth$AuthProtocolState;

        static {
            $SwitchMap$org$apache$http$auth$AuthProtocolState = new int[AuthProtocolState.values().length];
            try {
                $SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.CHALLENGED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.FAILURE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public ResponseAuthCache() {
        this.log = LogFactory.getLog(getClass());
    }

    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
        Args.notNull(response, "HTTP request");
        Args.notNull(context, "HTTP context");
        AuthCache authCache = (AuthCache) context.getAttribute(HttpClientContext.AUTH_CACHE);
        HttpHost target = (HttpHost) context.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
        AuthState targetState = (AuthState) context.getAttribute(HttpClientContext.TARGET_AUTH_STATE);
        if (!(target == null || targetState == null)) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Target auth state: " + targetState.getState());
            }
            if (isCachable(targetState)) {
                SchemeRegistry schemeRegistry = (SchemeRegistry) context.getAttribute(ClientContext.SCHEME_REGISTRY);
                if (target.getPort() < 0) {
                    target = new HttpHost(target.getHostName(), schemeRegistry.getScheme(target).resolvePort(target.getPort()), target.getSchemeName());
                }
                if (authCache == null) {
                    authCache = new BasicAuthCache();
                    context.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
                }
                switch (C00711.$SwitchMap$org$apache$http$auth$AuthProtocolState[targetState.getState().ordinal()]) {
                    case WearableExtender.SIZE_XSMALL /*1*/:
                        cache(authCache, target, targetState.getAuthScheme());
                        break;
                    case ConnPerRouteBean.DEFAULT_MAX_CONNECTIONS_PER_ROUTE /*2*/:
                        uncache(authCache, target, targetState.getAuthScheme());
                        break;
                }
            }
        }
        HttpHost proxy = (HttpHost) context.getAttribute(ExecutionContext.HTTP_PROXY_HOST);
        AuthState proxyState = (AuthState) context.getAttribute(HttpClientContext.PROXY_AUTH_STATE);
        if (proxy != null && proxyState != null) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Proxy auth state: " + proxyState.getState());
            }
            if (isCachable(proxyState)) {
                if (authCache == null) {
                    authCache = new BasicAuthCache();
                    context.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
                }
                switch (C00711.$SwitchMap$org$apache$http$auth$AuthProtocolState[proxyState.getState().ordinal()]) {
                    case WearableExtender.SIZE_XSMALL /*1*/:
                        cache(authCache, proxy, proxyState.getAuthScheme());
                    case ConnPerRouteBean.DEFAULT_MAX_CONNECTIONS_PER_ROUTE /*2*/:
                        uncache(authCache, proxy, proxyState.getAuthScheme());
                    default:
                }
            }
        }
    }

    private boolean isCachable(AuthState authState) {
        AuthScheme authScheme = authState.getAuthScheme();
        if (authScheme == null || !authScheme.isComplete()) {
            return false;
        }
        String schemeName = authScheme.getSchemeName();
        if (schemeName.equalsIgnoreCase(AuthPolicy.BASIC) || schemeName.equalsIgnoreCase(AuthPolicy.DIGEST)) {
            return true;
        }
        return false;
    }

    private void cache(AuthCache authCache, HttpHost host, AuthScheme authScheme) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Caching '" + authScheme.getSchemeName() + "' auth scheme for " + host);
        }
        authCache.put(host, authScheme);
    }

    private void uncache(AuthCache authCache, HttpHost host, AuthScheme authScheme) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Removing from cache '" + authScheme.getSchemeName() + "' auth scheme for " + host);
        }
        authCache.remove(host);
    }
}
