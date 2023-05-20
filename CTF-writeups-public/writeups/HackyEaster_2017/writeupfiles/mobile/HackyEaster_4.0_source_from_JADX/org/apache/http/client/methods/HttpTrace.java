package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.annotation.NotThreadSafe;

@NotThreadSafe
public class HttpTrace extends HttpRequestBase {
    public static final String METHOD_NAME = "TRACE";

    public HttpTrace(URI uri) {
        setURI(uri);
    }

    public HttpTrace(String uri) {
        setURI(URI.create(uri));
    }

    public String getMethod() {
        return METHOD_NAME;
    }
}
