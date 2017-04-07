package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.annotation.Immutable;
import org.apache.http.util.Args;

@Immutable
public class ResponseServer implements HttpResponseInterceptor {
    private final String originServer;

    public ResponseServer(String originServer) {
        this.originServer = originServer;
    }

    public ResponseServer() {
        this(null);
    }

    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
        Args.notNull(response, "HTTP response");
        if (!response.containsHeader(HTTP.SERVER_HEADER) && this.originServer != null) {
            response.addHeader(HTTP.SERVER_HEADER, this.originServer);
        }
    }
}
