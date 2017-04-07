package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.GuardedBy;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
@ThreadSafe
public class SingleClientConnManager implements ClientConnectionManager {
    public static final String MISUSE_MESSAGE = "Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
    protected final boolean alwaysShutDown;
    protected final ClientConnectionOperator connOperator;
    @GuardedBy("this")
    protected volatile long connectionExpiresTime;
    protected volatile boolean isShutDown;
    @GuardedBy("this")
    protected volatile long lastReleaseTime;
    private final Log log;
    @GuardedBy("this")
    protected volatile ConnAdapter managedConn;
    protected final SchemeRegistry schemeRegistry;
    @GuardedBy("this")
    protected volatile PoolEntry uniquePoolEntry;

    /* renamed from: org.apache.http.impl.conn.SingleClientConnManager.1 */
    class C01511 implements ClientConnectionRequest {
        final /* synthetic */ HttpRoute val$route;
        final /* synthetic */ Object val$state;

        C01511(HttpRoute httpRoute, Object obj) {
            this.val$route = httpRoute;
            this.val$state = obj;
        }

        public void abortRequest() {
        }

        public ManagedClientConnection getConnection(long timeout, TimeUnit tunit) {
            return SingleClientConnManager.this.getConnection(this.val$route, this.val$state);
        }
    }

    protected class PoolEntry extends AbstractPoolEntry {
        protected PoolEntry() {
            super(SingleClientConnManager.this.connOperator, null);
        }

        protected void close() throws IOException {
            shutdownEntry();
            if (this.connection.isOpen()) {
                this.connection.close();
            }
        }

        protected void shutdown() throws IOException {
            shutdownEntry();
            if (this.connection.isOpen()) {
                this.connection.shutdown();
            }
        }
    }

    protected class ConnAdapter extends AbstractPooledConnAdapter {
        protected ConnAdapter(PoolEntry entry, HttpRoute route) {
            super(SingleClientConnManager.this, entry);
            markReusable();
            entry.route = route;
        }
    }

    @Deprecated
    public SingleClientConnManager(HttpParams params, SchemeRegistry schreg) {
        this(schreg);
    }

    public SingleClientConnManager(SchemeRegistry schreg) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(schreg, "Scheme registry");
        this.schemeRegistry = schreg;
        this.connOperator = createConnectionOperator(schreg);
        this.uniquePoolEntry = new PoolEntry();
        this.managedConn = null;
        this.lastReleaseTime = -1;
        this.alwaysShutDown = false;
        this.isShutDown = false;
    }

    public SingleClientConnManager() {
        this(SchemeRegistryFactory.createDefault());
    }

    protected void finalize() throws Throwable {
        try {
            shutdown();
        } finally {
            super.finalize();
        }
    }

    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }

    protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schreg) {
        return new DefaultClientConnectionOperator(schreg);
    }

    protected final void assertStillUp() throws IllegalStateException {
        Asserts.check(!this.isShutDown, "Manager is shut down");
    }

    public final ClientConnectionRequest requestConnection(HttpRoute route, Object state) {
        return new C01511(route, state);
    }

    public ManagedClientConnection getConnection(HttpRoute route, Object state) {
        ManagedClientConnection managedClientConnection;
        Args.notNull(route, "Route");
        assertStillUp();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Get connection for route " + route);
        }
        synchronized (this) {
            boolean z;
            if (this.managedConn == null) {
                z = true;
            } else {
                z = false;
            }
            Asserts.check(z, MISUSE_MESSAGE);
            boolean recreate = false;
            boolean shutdown = false;
            closeExpiredConnections();
            if (this.uniquePoolEntry.connection.isOpen()) {
                RouteTracker tracker = this.uniquePoolEntry.tracker;
                if (tracker == null || !tracker.toRoute().equals(route)) {
                    shutdown = true;
                } else {
                    shutdown = false;
                }
            } else {
                recreate = true;
            }
            if (shutdown) {
                recreate = true;
                try {
                    this.uniquePoolEntry.shutdown();
                } catch (IOException iox) {
                    this.log.debug("Problem shutting down connection.", iox);
                }
            }
            if (recreate) {
                this.uniquePoolEntry = new PoolEntry();
            }
            this.managedConn = new ConnAdapter(this.uniquePoolEntry, route);
            managedClientConnection = this.managedConn;
        }
        return managedClientConnection;
    }

    public void releaseConnection(ManagedClientConnection conn, long validDuration, TimeUnit timeUnit) {
        Args.check(conn instanceof ConnAdapter, "Connection class mismatch, connection not obtained from this manager");
        assertStillUp();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Releasing connection " + conn);
        }
        ConnAdapter sca = (ConnAdapter) conn;
        synchronized (sca) {
            if (sca.poolEntry == null) {
                return;
            }
            Asserts.check(sca.getManager() == this, "Connection not obtained from this manager");
            try {
                if (sca.isOpen() && (this.alwaysShutDown || !sca.isMarkedReusable())) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Released connection open but not reusable.");
                    }
                    sca.shutdown();
                }
                sca.detach();
                synchronized (this) {
                    this.managedConn = null;
                    this.lastReleaseTime = System.currentTimeMillis();
                    if (validDuration > 0) {
                        this.connectionExpiresTime = timeUnit.toMillis(validDuration) + this.lastReleaseTime;
                    } else {
                        this.connectionExpiresTime = Long.MAX_VALUE;
                    }
                }
            } catch (IOException iox) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Exception shutting down released connection.", iox);
                }
                sca.detach();
                synchronized (this) {
                }
                this.managedConn = null;
                this.lastReleaseTime = System.currentTimeMillis();
                if (validDuration > 0) {
                    this.connectionExpiresTime = timeUnit.toMillis(validDuration) + this.lastReleaseTime;
                } else {
                    this.connectionExpiresTime = Long.MAX_VALUE;
                }
            } catch (Throwable th) {
                sca.detach();
                synchronized (this) {
                }
                this.managedConn = null;
                this.lastReleaseTime = System.currentTimeMillis();
                if (validDuration > 0) {
                    this.connectionExpiresTime = timeUnit.toMillis(validDuration) + this.lastReleaseTime;
                } else {
                    this.connectionExpiresTime = Long.MAX_VALUE;
                }
            }
        }
    }

    public void closeExpiredConnections() {
        if (System.currentTimeMillis() >= this.connectionExpiresTime) {
            closeIdleConnections(0, TimeUnit.MILLISECONDS);
        }
    }

    public void closeIdleConnections(long idletime, TimeUnit tunit) {
        assertStillUp();
        Args.notNull(tunit, "Time unit");
        synchronized (this) {
            if (this.managedConn == null && this.uniquePoolEntry.connection.isOpen()) {
                if (this.lastReleaseTime <= System.currentTimeMillis() - tunit.toMillis(idletime)) {
                    try {
                        this.uniquePoolEntry.close();
                    } catch (IOException iox) {
                        this.log.debug("Problem closing idle connection.", iox);
                    }
                }
            }
        }
    }

    public void shutdown() {
        this.isShutDown = true;
        synchronized (this) {
            try {
                if (this.uniquePoolEntry != null) {
                    this.uniquePoolEntry.shutdown();
                }
                this.uniquePoolEntry = null;
                this.managedConn = null;
            } catch (IOException iox) {
                this.log.debug("Problem while shutting down manager.", iox);
                this.uniquePoolEntry = null;
                this.managedConn = null;
            } catch (Throwable th) {
                this.uniquePoolEntry = null;
                this.managedConn = null;
            }
        }
    }

    protected void revokeConnection() {
        ConnAdapter conn = this.managedConn;
        if (conn != null) {
            conn.detach();
            synchronized (this) {
                try {
                    this.uniquePoolEntry.shutdown();
                } catch (IOException iox) {
                    this.log.debug("Problem while shutting down connection.", iox);
                }
            }
        }
    }
}
