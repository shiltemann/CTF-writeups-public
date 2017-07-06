package org.apache.http.impl.conn;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.annotation.GuardedBy;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Lookup;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionOperator;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;
import org.apache.http.util.LangUtils;

@ThreadSafe
public class BasicHttpClientConnectionManager implements HttpClientConnectionManager, Closeable {
    @GuardedBy("this")
    private ManagedHttpClientConnection conn;
    @GuardedBy("this")
    private ConnectionConfig connConfig;
    private final HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory;
    private final HttpClientConnectionOperator connectionOperator;
    @GuardedBy("this")
    private long expiry;
    private final AtomicBoolean isShutdown;
    @GuardedBy("this")
    private boolean leased;
    private final Log log;
    @GuardedBy("this")
    private HttpRoute route;
    @GuardedBy("this")
    private SocketConfig socketConfig;
    @GuardedBy("this")
    private Object state;
    @GuardedBy("this")
    private long updated;

    /* renamed from: org.apache.http.impl.conn.BasicHttpClientConnectionManager.1 */
    class C01591 implements ConnectionRequest {
        final /* synthetic */ HttpRoute val$route;
        final /* synthetic */ Object val$state;

        C01591(HttpRoute httpRoute, Object obj) {
            this.val$route = httpRoute;
            this.val$state = obj;
        }

        public boolean cancel() {
            return false;
        }

        public HttpClientConnection get(long timeout, TimeUnit tunit) {
            return BasicHttpClientConnectionManager.this.getConnection(this.val$route, this.val$state);
        }
    }

    private static Registry<ConnectionSocketFactory> getDefaultRegistry() {
        return RegistryBuilder.create().register(HttpHost.DEFAULT_SCHEME_NAME, PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
    }

    public BasicHttpClientConnectionManager(Lookup<ConnectionSocketFactory> socketFactoryRegistry, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory, SchemePortResolver schemePortResolver, DnsResolver dnsResolver) {
        this(new DefaultHttpClientConnectionOperator(socketFactoryRegistry, schemePortResolver, dnsResolver), (HttpConnectionFactory) connFactory);
    }

    public BasicHttpClientConnectionManager(HttpClientConnectionOperator httpClientConnectionOperator, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory) {
        this.log = LogFactory.getLog(getClass());
        this.connectionOperator = (HttpClientConnectionOperator) Args.notNull(httpClientConnectionOperator, "Connection operator");
        if (connFactory == null) {
            connFactory = ManagedHttpClientConnectionFactory.INSTANCE;
        }
        this.connFactory = connFactory;
        this.expiry = Long.MAX_VALUE;
        this.socketConfig = SocketConfig.DEFAULT;
        this.connConfig = ConnectionConfig.DEFAULT;
        this.isShutdown = new AtomicBoolean(false);
    }

    public BasicHttpClientConnectionManager(Lookup<ConnectionSocketFactory> socketFactoryRegistry, HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory) {
        this(socketFactoryRegistry, connFactory, null, null);
    }

    public BasicHttpClientConnectionManager(Lookup<ConnectionSocketFactory> socketFactoryRegistry) {
        this(socketFactoryRegistry, null, null, null);
    }

    public BasicHttpClientConnectionManager() {
        this(getDefaultRegistry(), null, null, null);
    }

    protected void finalize() throws Throwable {
        try {
            shutdown();
        } finally {
            super.finalize();
        }
    }

    public void close() {
        shutdown();
    }

    HttpRoute getRoute() {
        return this.route;
    }

    Object getState() {
        return this.state;
    }

    public synchronized SocketConfig getSocketConfig() {
        return this.socketConfig;
    }

    public synchronized void setSocketConfig(SocketConfig socketConfig) {
        if (socketConfig == null) {
            socketConfig = SocketConfig.DEFAULT;
        }
        this.socketConfig = socketConfig;
    }

    public synchronized ConnectionConfig getConnectionConfig() {
        return this.connConfig;
    }

    public synchronized void setConnectionConfig(ConnectionConfig connConfig) {
        if (connConfig == null) {
            connConfig = ConnectionConfig.DEFAULT;
        }
        this.connConfig = connConfig;
    }

    public final ConnectionRequest requestConnection(HttpRoute route, Object state) {
        Args.notNull(route, "Route");
        return new C01591(route, state);
    }

    private void closeConnection() {
        if (this.conn != null) {
            this.log.debug("Closing connection");
            try {
                this.conn.close();
            } catch (IOException iox) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("I/O exception closing connection", iox);
                }
            }
            this.conn = null;
        }
    }

    private void shutdownConnection() {
        if (this.conn != null) {
            this.log.debug("Shutting down connection");
            try {
                this.conn.shutdown();
            } catch (IOException iox) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("I/O exception shutting down connection", iox);
                }
            }
            this.conn = null;
        }
    }

    private void checkExpiry() {
        if (this.conn != null && System.currentTimeMillis() >= this.expiry) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connection expired @ " + new Date(this.expiry));
            }
            closeConnection();
        }
    }

    synchronized HttpClientConnection getConnection(HttpRoute route, Object state) {
        HttpClientConnection httpClientConnection;
        boolean z = true;
        synchronized (this) {
            Asserts.check(!this.isShutdown.get(), "Connection manager has been shut down");
            if (this.log.isDebugEnabled()) {
                this.log.debug("Get connection for route " + route);
            }
            if (this.leased) {
                z = false;
            }
            Asserts.check(z, "Connection is still allocated");
            if (!(LangUtils.equals(this.route, (Object) route) && LangUtils.equals(this.state, state))) {
                closeConnection();
            }
            this.route = route;
            this.state = state;
            checkExpiry();
            if (this.conn == null) {
                this.conn = (ManagedHttpClientConnection) this.connFactory.create(route, this.connConfig);
            }
            this.leased = true;
            httpClientConnection = this.conn;
        }
        return httpClientConnection;
    }

    public synchronized void releaseConnection(HttpClientConnection conn, Object state, long keepalive, TimeUnit tunit) {
        boolean z = false;
        synchronized (this) {
            Args.notNull(conn, HTTP.CONN_DIRECTIVE);
            if (conn == this.conn) {
                z = true;
            }
            Asserts.check(z, "Connection not obtained from this manager");
            if (this.log.isDebugEnabled()) {
                this.log.debug("Releasing connection " + conn);
            }
            if (!this.isShutdown.get()) {
                try {
                    this.updated = System.currentTimeMillis();
                    ManagedHttpClientConnection isOpen = this.conn.isOpen();
                    if (isOpen == null) {
                        this.conn = isOpen;
                        this.route = null;
                        this.conn = null;
                        this.expiry = Long.MAX_VALUE;
                        this.leased = false;
                    } else {
                        this.state = state;
                        if (this.log.isDebugEnabled()) {
                            String s;
                            if (keepalive > 0) {
                                s = "for " + keepalive + " " + tunit;
                            } else {
                                s = "indefinitely";
                            }
                            this.log.debug("Connection can be kept alive " + s);
                        }
                        if (keepalive > 0) {
                            this.expiry = this.updated + tunit.toMillis(keepalive);
                        } else {
                            this.expiry = Long.MAX_VALUE;
                        }
                        this.leased = false;
                    }
                } finally {
                    this.leased = false;
                }
            }
        }
    }

    public void connect(HttpClientConnection conn, HttpRoute route, int connectTimeout, HttpContext context) throws IOException {
        HttpHost host;
        Args.notNull(conn, HTTP.CONN_DIRECTIVE);
        Args.notNull(route, "HTTP route");
        Asserts.check(conn == this.conn, "Connection not obtained from this manager");
        if (route.getProxyHost() != null) {
            host = route.getProxyHost();
        } else {
            host = route.getTargetHost();
        }
        this.connectionOperator.connect(this.conn, host, route.getLocalSocketAddress(), connectTimeout, this.socketConfig, context);
    }

    public void upgrade(HttpClientConnection conn, HttpRoute route, HttpContext context) throws IOException {
        Args.notNull(conn, HTTP.CONN_DIRECTIVE);
        Args.notNull(route, "HTTP route");
        Asserts.check(conn == this.conn, "Connection not obtained from this manager");
        this.connectionOperator.upgrade(this.conn, route.getTargetHost(), context);
    }

    public void routeComplete(HttpClientConnection conn, HttpRoute route, HttpContext context) throws IOException {
    }

    public synchronized void closeExpiredConnections() {
        if (!this.isShutdown.get()) {
            if (!this.leased) {
                checkExpiry();
            }
        }
    }

    public synchronized void closeIdleConnections(long idletime, TimeUnit tunit) {
        Args.notNull(tunit, "Time unit");
        if (!this.isShutdown.get()) {
            if (!this.leased) {
                long time = tunit.toMillis(idletime);
                if (time < 0) {
                    time = 0;
                }
                if (this.updated <= System.currentTimeMillis() - time) {
                    closeConnection();
                }
            }
        }
    }

    public synchronized void shutdown() {
        if (this.isShutdown.compareAndSet(false, true)) {
            shutdownConnection();
        }
    }
}
