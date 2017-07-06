package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Deprecated
public abstract class AbstractPooledConnAdapter extends AbstractClientConnAdapter {
    protected volatile AbstractPoolEntry poolEntry;

    protected AbstractPooledConnAdapter(ClientConnectionManager manager, AbstractPoolEntry entry) {
        super(manager, entry.connection);
        this.poolEntry = entry;
    }

    public String getId() {
        return null;
    }

    @Deprecated
    protected AbstractPoolEntry getPoolEntry() {
        return this.poolEntry;
    }

    protected void assertValid(AbstractPoolEntry entry) {
        if (isReleased() || entry == null) {
            throw new ConnectionShutdownException();
        }
    }

    @Deprecated
    protected final void assertAttached() {
        if (this.poolEntry == null) {
            throw new ConnectionShutdownException();
        }
    }

    protected synchronized void detach() {
        this.poolEntry = null;
        super.detach();
    }

    public HttpRoute getRoute() {
        AbstractPoolEntry entry = getPoolEntry();
        assertValid(entry);
        return entry.tracker == null ? null : entry.tracker.toRoute();
    }

    public void open(HttpRoute route, HttpContext context, HttpParams params) throws IOException {
        AbstractPoolEntry entry = getPoolEntry();
        assertValid(entry);
        entry.open(route, context, params);
    }

    public void tunnelTarget(boolean secure, HttpParams params) throws IOException {
        AbstractPoolEntry entry = getPoolEntry();
        assertValid(entry);
        entry.tunnelTarget(secure, params);
    }

    public void tunnelProxy(HttpHost next, boolean secure, HttpParams params) throws IOException {
        AbstractPoolEntry entry = getPoolEntry();
        assertValid(entry);
        entry.tunnelProxy(next, secure, params);
    }

    public void layerProtocol(HttpContext context, HttpParams params) throws IOException {
        AbstractPoolEntry entry = getPoolEntry();
        assertValid(entry);
        entry.layerProtocol(context, params);
    }

    public void close() throws IOException {
        AbstractPoolEntry entry = getPoolEntry();
        if (entry != null) {
            entry.shutdownEntry();
        }
        OperatedClientConnection conn = getWrappedConnection();
        if (conn != null) {
            conn.close();
        }
    }

    public void shutdown() throws IOException {
        AbstractPoolEntry entry = getPoolEntry();
        if (entry != null) {
            entry.shutdownEntry();
        }
        OperatedClientConnection conn = getWrappedConnection();
        if (conn != null) {
            conn.shutdown();
        }
    }

    public Object getState() {
        AbstractPoolEntry entry = getPoolEntry();
        assertValid(entry);
        return entry.getState();
    }

    public void setState(Object state) {
        AbstractPoolEntry entry = getPoolEntry();
        assertValid(entry);
        entry.setState(state);
    }
}
