package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.annotation.Immutable;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@Immutable
public class RequestUserAgent implements HttpRequestInterceptor {
    private final String userAgent;

    public RequestUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public RequestUserAgent() {
        this(null);
    }

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        Args.notNull(request, "HTTP request");
        if (!request.containsHeader(HTTP.USER_AGENT)) {
            String s = null;
            HttpParams params = request.getParams();
            if (params != null) {
                s = (String) params.getParameter(CoreProtocolPNames.USER_AGENT);
            }
            if (s == null) {
                s = this.userAgent;
            }
            if (s != null) {
                request.addHeader(HTTP.USER_AGENT, s);
            }
        }
    }
}
