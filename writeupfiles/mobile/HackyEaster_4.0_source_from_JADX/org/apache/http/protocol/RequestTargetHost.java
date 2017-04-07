package org.apache.http.protocol;

import java.io.IOException;
import java.net.InetAddress;
import org.apache.http.HttpConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpInetConnection;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.annotation.Immutable;
import org.apache.http.util.Args;

@Immutable
public class RequestTargetHost implements HttpRequestInterceptor {
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        Args.notNull(request, "HTTP request");
        HttpCoreContext corecontext = HttpCoreContext.adapt(context);
        ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
        if ((!request.getRequestLine().getMethod().equalsIgnoreCase("CONNECT") || !ver.lessEquals(HttpVersion.HTTP_1_0)) && !request.containsHeader(HTTP.TARGET_HOST)) {
            HttpHost targethost = corecontext.getTargetHost();
            if (targethost == null) {
                HttpConnection conn = corecontext.getConnection();
                if (conn instanceof HttpInetConnection) {
                    InetAddress address = ((HttpInetConnection) conn).getRemoteAddress();
                    int port = ((HttpInetConnection) conn).getRemotePort();
                    if (address != null) {
                        targethost = new HttpHost(address.getHostName(), port);
                    }
                }
                if (targethost == null) {
                    if (!ver.lessEquals(HttpVersion.HTTP_1_0)) {
                        throw new ProtocolException("Target host missing");
                    }
                    return;
                }
            }
            request.addHeader(HTTP.TARGET_HOST, targethost.toHostString());
        }
    }
}
