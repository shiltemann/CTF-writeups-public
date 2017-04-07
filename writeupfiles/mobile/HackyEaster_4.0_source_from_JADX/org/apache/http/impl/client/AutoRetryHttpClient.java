package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

@Deprecated
@ThreadSafe
public class AutoRetryHttpClient implements HttpClient {
    private final HttpClient backend;
    private final Log log;
    private final ServiceUnavailableRetryStrategy retryStrategy;

    public AutoRetryHttpClient(HttpClient client, ServiceUnavailableRetryStrategy retryStrategy) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(client, "HttpClient");
        Args.notNull(retryStrategy, "ServiceUnavailableRetryStrategy");
        this.backend = client;
        this.retryStrategy = retryStrategy;
    }

    public AutoRetryHttpClient() {
        this(new DefaultHttpClient(), new DefaultServiceUnavailableRetryStrategy());
    }

    public AutoRetryHttpClient(ServiceUnavailableRetryStrategy config) {
        this(new DefaultHttpClient(), config);
    }

    public AutoRetryHttpClient(HttpClient client) {
        this(client, new DefaultServiceUnavailableRetryStrategy());
    }

    public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException {
        return execute(target, request, null);
    }

    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler) throws IOException {
        return execute(target, request, responseHandler, null);
    }

    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException {
        return responseHandler.handleResponse(execute(target, request, context));
    }

    public HttpResponse execute(HttpUriRequest request) throws IOException {
        return execute(request, null);
    }

    public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException {
        URI uri = request.getURI();
        return execute(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()), (HttpRequest) request, context);
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException {
        return execute(request, (ResponseHandler) responseHandler, null);
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException {
        return responseHandler.handleResponse(execute(request, context));
    }

    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws IOException {
        int c = 1;
        while (true) {
            HttpResponse response = this.backend.execute(target, request, context);
            try {
                if (!this.retryStrategy.retryRequest(response, c, context)) {
                    return response;
                }
                EntityUtils.consume(response.getEntity());
                long nextInterval = this.retryStrategy.getRetryInterval();
                this.log.trace("Wait for " + nextInterval);
                Thread.sleep(nextInterval);
                c++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new InterruptedIOException();
            } catch (RuntimeException ex) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ioex) {
                    this.log.warn("I/O error consuming response content", ioex);
                }
                throw ex;
            }
        }
    }

    public ClientConnectionManager getConnectionManager() {
        return this.backend.getConnectionManager();
    }

    public HttpParams getParams() {
        return this.backend.getParams();
    }
}
