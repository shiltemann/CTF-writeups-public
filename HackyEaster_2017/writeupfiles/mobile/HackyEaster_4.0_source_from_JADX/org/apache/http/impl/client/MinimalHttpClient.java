package org.apache.http.impl.client;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.execchain.MinimalClientExec;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.util.Args;

@ThreadSafe
class MinimalHttpClient extends CloseableHttpClient {
    private final HttpClientConnectionManager connManager;
    private final HttpParams params;
    private final MinimalClientExec requestExecutor;

    /* renamed from: org.apache.http.impl.client.MinimalHttpClient.1 */
    class C01481 implements ClientConnectionManager {
        C01481() {
        }

        public void shutdown() {
            MinimalHttpClient.this.connManager.shutdown();
        }

        public ClientConnectionRequest requestConnection(HttpRoute route, Object state) {
            throw new UnsupportedOperationException();
        }

        public void releaseConnection(ManagedClientConnection conn, long validDuration, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }

        public SchemeRegistry getSchemeRegistry() {
            throw new UnsupportedOperationException();
        }

        public void closeIdleConnections(long idletime, TimeUnit tunit) {
            MinimalHttpClient.this.connManager.closeIdleConnections(idletime, tunit);
        }

        public void closeExpiredConnections() {
            MinimalHttpClient.this.connManager.closeExpiredConnections();
        }
    }

    public MinimalHttpClient(HttpClientConnectionManager connManager) {
        this.connManager = (HttpClientConnectionManager) Args.notNull(connManager, "HTTP connection manager");
        this.requestExecutor = new MinimalClientExec(new HttpRequestExecutor(), connManager, DefaultConnectionReuseStrategy.INSTANCE, DefaultConnectionKeepAliveStrategy.INSTANCE);
        this.params = new BasicHttpParams();
    }

    protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context) throws IOException, ClientProtocolException {
        Args.notNull(target, "Target host");
        Args.notNull(request, "HTTP request");
        HttpExecutionAware execAware = null;
        if (request instanceof HttpExecutionAware) {
            execAware = (HttpExecutionAware) request;
        }
        try {
            HttpRequestWrapper wrapper = HttpRequestWrapper.wrap(request);
            if (context == null) {
                context = new BasicHttpContext();
            }
            HttpClientContext localcontext = HttpClientContext.adapt(context);
            HttpRoute route = new HttpRoute(target);
            RequestConfig config = null;
            if (request instanceof Configurable) {
                config = ((Configurable) request).getConfig();
            }
            if (config != null) {
                localcontext.setRequestConfig(config);
            }
            return this.requestExecutor.execute(route, wrapper, localcontext, execAware);
        } catch (Throwable httpException) {
            throw new ClientProtocolException(httpException);
        }
    }

    public HttpParams getParams() {
        return this.params;
    }

    public void close() {
        this.connManager.shutdown();
    }

    public ClientConnectionManager getConnectionManager() {
        return new C01481();
    }
}
