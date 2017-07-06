package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.protocol.HttpContext;

@Immutable
public class RequestAcceptEncoding implements HttpRequestInterceptor {
    private final String acceptEncoding;

    public RequestAcceptEncoding(List<String> encodings) {
        if (encodings == null || encodings.isEmpty()) {
            this.acceptEncoding = "gzip,deflate";
            return;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < encodings.size(); i++) {
            if (i > 0) {
                buf.append(",");
            }
            buf.append((String) encodings.get(i));
        }
        this.acceptEncoding = buf.toString();
    }

    public RequestAcceptEncoding() {
        this(null);
    }

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        RequestConfig requestConfig = HttpClientContext.adapt(context).getRequestConfig();
        if (!request.containsHeader(HttpHeaders.ACCEPT_ENCODING) && requestConfig.isContentCompressionEnabled()) {
            request.addHeader(HttpHeaders.ACCEPT_ENCODING, this.acceptEncoding);
        }
    }
}
