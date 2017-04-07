package org.apache.http.client.methods;

import java.io.Closeable;
import org.apache.http.HttpResponse;

public interface CloseableHttpResponse extends HttpResponse, Closeable {
}
