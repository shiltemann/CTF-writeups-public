package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthState;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.Args;

@Immutable
@Deprecated
public class RequestProxyAuthentication extends RequestAuthenticationBase {
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        Args.notNull(request, "HTTP request");
        Args.notNull(context, "HTTP context");
        if (!request.containsHeader(AUTH.PROXY_AUTH_RESP)) {
            HttpRoutedConnection conn = (HttpRoutedConnection) context.getAttribute(HttpCoreContext.HTTP_CONNECTION);
            if (conn == null) {
                this.log.debug("HTTP connection not set in the context");
            } else if (!conn.getRoute().isTunnelled()) {
                AuthState authState = (AuthState) context.getAttribute(HttpClientContext.PROXY_AUTH_STATE);
                if (authState == null) {
                    this.log.debug("Proxy auth state not set in the context");
                    return;
                }
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Proxy auth state: " + authState.getState());
                }
                process(authState, request, context);
            }
        }
    }
}
