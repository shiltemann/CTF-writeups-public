package org.apache.http.impl.execchain;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.Args;
import org.apache.http.util.VersionInfo;

@Immutable
public class MinimalClientExec implements ClientExecChain {
    private final HttpClientConnectionManager connManager;
    private final HttpProcessor httpProcessor;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log;
    private final HttpRequestExecutor requestExecutor;
    private final ConnectionReuseStrategy reuseStrategy;

    public MinimalClientExec(HttpRequestExecutor requestExecutor, HttpClientConnectionManager connManager, ConnectionReuseStrategy reuseStrategy, ConnectionKeepAliveStrategy keepAliveStrategy) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(requestExecutor, "HTTP request executor");
        Args.notNull(connManager, "Client connection manager");
        Args.notNull(reuseStrategy, "Connection reuse strategy");
        Args.notNull(keepAliveStrategy, "Connection keep alive strategy");
        this.httpProcessor = new ImmutableHttpProcessor(new RequestContent(), new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent(VersionInfo.getUserAgent("Apache-HttpClient", "org.apache.http.client", getClass())));
        this.requestExecutor = requestExecutor;
        this.connManager = connManager;
        this.reuseStrategy = reuseStrategy;
        this.keepAliveStrategy = keepAliveStrategy;
    }

    static void rewriteRequestURI(HttpRequestWrapper request, HttpRoute route) throws ProtocolException {
        try {
            URI uri = request.getURI();
            if (uri != null) {
                if (uri.isAbsolute()) {
                    uri = URIUtils.rewriteURI(uri, null, true);
                } else {
                    uri = URIUtils.rewriteURI(uri);
                }
                request.setURI(uri);
            }
        } catch (URISyntaxException ex) {
            throw new ProtocolException("Invalid URI: " + request.getRequestLine().getUri(), ex);
        }
    }

    public CloseableHttpResponse execute(HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware) throws IOException, HttpException {
        Args.notNull(route, "HTTP route");
        Args.notNull(request, "HTTP request");
        Args.notNull(context, "HTTP context");
        rewriteRequestURI(request, route);
        ConnectionRequest connRequest = this.connManager.requestConnection(route, null);
        if (execAware != null) {
            if (execAware.isAborted()) {
                connRequest.cancel();
                throw new RequestAbortedException("Request aborted");
            }
            execAware.setCancellable(connRequest);
        }
        RequestConfig config = context.getRequestConfig();
        try {
            long j;
            int timeout = config.getConnectionRequestTimeout();
            if (timeout > 0) {
                j = (long) timeout;
            } else {
                j = 0;
            }
            HttpClientConnection managedConn = connRequest.get(j, TimeUnit.MILLISECONDS);
            ConnectionHolder releaseTrigger = new ConnectionHolder(this.log, this.connManager, managedConn);
            if (execAware != null) {
                try {
                    if (execAware.isAborted()) {
                        releaseTrigger.close();
                        throw new RequestAbortedException("Request aborted");
                    }
                    execAware.setCancellable(releaseTrigger);
                } catch (ConnectionShutdownException ex) {
                    InterruptedIOException ioex = new InterruptedIOException("Connection has been shut down");
                    ioex.initCause(ex);
                    throw ioex;
                } catch (HttpException ex2) {
                    releaseTrigger.abortConnection();
                    throw ex2;
                } catch (IOException ex3) {
                    releaseTrigger.abortConnection();
                    throw ex3;
                } catch (RuntimeException ex4) {
                    releaseTrigger.abortConnection();
                    throw ex4;
                }
            }
            if (!managedConn.isOpen()) {
                timeout = config.getConnectTimeout();
                HttpClientConnectionManager httpClientConnectionManager = this.connManager;
                if (timeout <= 0) {
                    timeout = 0;
                }
                httpClientConnectionManager.connect(managedConn, route, timeout, context);
                this.connManager.routeComplete(managedConn, route, context);
            }
            timeout = config.getSocketTimeout();
            if (timeout >= 0) {
                managedConn.setSocketTimeout(timeout);
            }
            HttpHost target = null;
            HttpRequest original = request.getOriginal();
            if (original instanceof HttpUriRequest) {
                URI uri = ((HttpUriRequest) original).getURI();
                if (uri.isAbsolute()) {
                    HttpHost httpHost = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
                }
            }
            if (target == null) {
                target = route.getTargetHost();
            }
            context.setAttribute(HttpCoreContext.HTTP_TARGET_HOST, target);
            context.setAttribute(HttpCoreContext.HTTP_REQUEST, request);
            context.setAttribute(HttpCoreContext.HTTP_CONNECTION, managedConn);
            context.setAttribute(HttpClientContext.HTTP_ROUTE, route);
            this.httpProcessor.process(request, context);
            HttpResponse response = this.requestExecutor.execute(request, managedConn, context);
            this.httpProcessor.process(response, context);
            if (this.reuseStrategy.keepAlive(response, context)) {
                ConnectionKeepAliveStrategy connectionKeepAliveStrategy = this.keepAliveStrategy;
                releaseTrigger.setValidFor(connectionKeepAliveStrategy.getKeepAliveDuration(response, context), TimeUnit.MILLISECONDS);
                releaseTrigger.markReusable();
            } else {
                releaseTrigger.markNonReusable();
            }
            HttpEntity entity = response.getEntity();
            if (entity != null && entity.isStreaming()) {
                return new HttpResponseProxy(response, releaseTrigger);
            }
            releaseTrigger.releaseConnection();
            return new HttpResponseProxy(response, null);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new RequestAbortedException("Request aborted", interrupted);
        } catch (Throwable ex5) {
            Throwable cause = ex5.getCause();
            if (cause == null) {
                cause = ex5;
            }
            throw new RequestAbortedException("Request execution failed", cause);
        }
    }
}
