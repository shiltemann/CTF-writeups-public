package org.apache.http.client.params;

import java.net.InetAddress;
import java.util.Collection;
import org.apache.http.HttpHost;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

@Deprecated
public final class HttpClientParamConfig {
    private HttpClientParamConfig() {
    }

    public static RequestConfig getRequestConfig(HttpParams params) {
        return RequestConfig.custom().setSocketTimeout(params.getIntParameter(CoreConnectionPNames.SO_TIMEOUT, 0)).setStaleConnectionCheckEnabled(params.getBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true)).setConnectTimeout(params.getIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 0)).setExpectContinueEnabled(params.getBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false)).setProxy((HttpHost) params.getParameter(ConnRoutePNames.DEFAULT_PROXY)).setLocalAddress((InetAddress) params.getParameter(ConnRoutePNames.LOCAL_ADDRESS)).setProxyPreferredAuthSchemes((Collection) params.getParameter(AuthPNames.PROXY_AUTH_PREF)).setTargetPreferredAuthSchemes((Collection) params.getParameter(AuthPNames.TARGET_AUTH_PREF)).setAuthenticationEnabled(params.getBooleanParameter(ClientPNames.HANDLE_AUTHENTICATION, true)).setCircularRedirectsAllowed(params.getBooleanParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false)).setConnectionRequestTimeout((int) params.getLongParameter(ConnManagerPNames.TIMEOUT, 0)).setCookieSpec((String) params.getParameter(ClientPNames.COOKIE_POLICY)).setMaxRedirects(params.getIntParameter(ClientPNames.MAX_REDIRECTS, 50)).setRedirectsEnabled(params.getBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true)).setRelativeRedirectsAllowed(!params.getBooleanParameter(ClientPNames.REJECT_RELATIVE_REDIRECT, false)).build();
    }
}
