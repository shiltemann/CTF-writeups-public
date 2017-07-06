package org.apache.http.client.protocol;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Lookup;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Immutable
public class RequestAddCookies implements HttpRequestInterceptor {
    private final Log log;

    public RequestAddCookies() {
        this.log = LogFactory.getLog(getClass());
    }

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        Args.notNull(request, "HTTP request");
        Args.notNull(context, "HTTP context");
        if (!request.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            CookieStore cookieStore = clientContext.getCookieStore();
            if (cookieStore == null) {
                this.log.debug("Cookie store not specified in HTTP context");
                return;
            }
            Lookup<CookieSpecProvider> registry = clientContext.getCookieSpecRegistry();
            if (registry == null) {
                this.log.debug("CookieSpec registry not specified in HTTP context");
                return;
            }
            HttpHost targetHost = clientContext.getTargetHost();
            if (targetHost == null) {
                this.log.debug("Target host not set in the context");
                return;
            }
            RouteInfo route = clientContext.getHttpRoute();
            if (route == null) {
                this.log.debug("Connection route not set in the context");
                return;
            }
            String policy = clientContext.getRequestConfig().getCookieSpec();
            if (policy == null) {
                policy = CookieSpecs.DEFAULT;
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("CookieSpec selected: " + policy);
            }
            URI requestURI = null;
            if (request instanceof HttpUriRequest) {
                requestURI = ((HttpUriRequest) request).getURI();
            } else {
                try {
                    requestURI = new URI(request.getRequestLine().getUri());
                } catch (URISyntaxException e) {
                }
            }
            String path = requestURI != null ? requestURI.getPath() : null;
            String hostName = targetHost.getHostName();
            int port = targetHost.getPort();
            if (port < 0) {
                port = route.getTargetHost().getPort();
            }
            if (port < 0) {
                port = 0;
            }
            if (TextUtils.isEmpty(path)) {
                path = "/";
            }
            CookieOrigin cookieOrigin = new CookieOrigin(hostName, port, path, route.isSecure());
            CookieSpecProvider provider = (CookieSpecProvider) registry.lookup(policy);
            if (provider == null) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Unsupported cookie policy: " + policy);
                    return;
                }
                return;
            }
            Header header;
            CookieSpec cookieSpec = provider.create(clientContext);
            List<Cookie> cookies = cookieStore.getCookies();
            List<Cookie> matchedCookies = new ArrayList();
            Date now = new Date();
            boolean expired = false;
            for (Cookie cookie : cookies) {
                if (cookie.isExpired(now)) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Cookie " + cookie + " expired");
                    }
                    expired = true;
                } else if (cookieSpec.match(cookie, cookieOrigin)) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Cookie " + cookie + " match " + cookieOrigin);
                    }
                    matchedCookies.add(cookie);
                }
            }
            if (expired) {
                cookieStore.clearExpired(now);
            }
            if (!matchedCookies.isEmpty()) {
                for (Header header2 : cookieSpec.formatCookies(matchedCookies)) {
                    request.addHeader(header2);
                }
            }
            if (cookieSpec.getVersion() > 0) {
                header2 = cookieSpec.getVersionHeader();
                if (header2 != null) {
                    request.addHeader(header2);
                }
            }
            context.setAttribute(HttpClientContext.COOKIE_SPEC, cookieSpec);
            context.setAttribute(HttpClientContext.COOKIE_ORIGIN, cookieOrigin);
        }
    }
}
