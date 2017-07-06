package org.apache.http.client.protocol;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@NotThreadSafe
@Deprecated
public class ClientContextConfigurer implements ClientContext {
    private final HttpContext context;

    public ClientContextConfigurer(HttpContext context) {
        Args.notNull(context, "HTTP context");
        this.context = context;
    }

    public void setCookieSpecRegistry(CookieSpecRegistry registry) {
        this.context.setAttribute(HttpClientContext.COOKIESPEC_REGISTRY, registry);
    }

    public void setAuthSchemeRegistry(AuthSchemeRegistry registry) {
        this.context.setAttribute(HttpClientContext.AUTHSCHEME_REGISTRY, registry);
    }

    public void setCookieStore(CookieStore store) {
        this.context.setAttribute(HttpClientContext.COOKIE_STORE, store);
    }

    public void setCredentialsProvider(CredentialsProvider provider) {
        this.context.setAttribute(HttpClientContext.CREDS_PROVIDER, provider);
    }
}
