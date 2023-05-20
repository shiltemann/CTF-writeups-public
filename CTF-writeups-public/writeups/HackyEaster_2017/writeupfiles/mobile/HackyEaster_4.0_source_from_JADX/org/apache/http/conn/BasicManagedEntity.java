package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import org.apache.http.HttpEntity;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

@NotThreadSafe
@Deprecated
public class BasicManagedEntity extends HttpEntityWrapper implements ConnectionReleaseTrigger, EofSensorWatcher {
    protected final boolean attemptReuse;
    protected ManagedClientConnection managedConn;

    public BasicManagedEntity(HttpEntity entity, ManagedClientConnection conn, boolean reuse) {
        super(entity);
        Args.notNull(conn, HTTP.CONN_DIRECTIVE);
        this.managedConn = conn;
        this.attemptReuse = reuse;
    }

    public boolean isRepeatable() {
        return false;
    }

    public InputStream getContent() throws IOException {
        return new EofSensorInputStream(this.wrappedEntity.getContent(), this);
    }

    private void ensureConsumed() throws IOException {
        if (this.managedConn != null) {
            try {
                if (this.attemptReuse) {
                    EntityUtils.consume(this.wrappedEntity);
                    this.managedConn.markReusable();
                } else {
                    this.managedConn.unmarkReusable();
                }
                releaseManagedConnection();
            } catch (Throwable th) {
                releaseManagedConnection();
            }
        }
    }

    @Deprecated
    public void consumeContent() throws IOException {
        ensureConsumed();
    }

    public void writeTo(OutputStream outstream) throws IOException {
        super.writeTo(outstream);
        ensureConsumed();
    }

    public void releaseConnection() throws IOException {
        ensureConsumed();
    }

    public void abortConnection() throws IOException {
        if (this.managedConn != null) {
            try {
                this.managedConn.abortConnection();
            } finally {
                this.managedConn = null;
            }
        }
    }

    public boolean eofDetected(InputStream wrapped) throws IOException {
        try {
            if (this.managedConn != null) {
                if (this.attemptReuse) {
                    wrapped.close();
                    this.managedConn.markReusable();
                } else {
                    this.managedConn.unmarkReusable();
                }
            }
            releaseManagedConnection();
            return false;
        } catch (Throwable th) {
            releaseManagedConnection();
        }
    }

    public boolean streamClosed(InputStream wrapped) throws IOException {
        boolean valid;
        try {
            if (this.managedConn != null) {
                if (this.attemptReuse) {
                    valid = this.managedConn.isOpen();
                    wrapped.close();
                    this.managedConn.markReusable();
                } else {
                    this.managedConn.unmarkReusable();
                }
            }
        } catch (SocketException ex) {
            if (valid) {
                throw ex;
            }
        } catch (Throwable th) {
            releaseManagedConnection();
        }
        releaseManagedConnection();
        return false;
    }

    public boolean streamAbort(InputStream wrapped) throws IOException {
        if (this.managedConn != null) {
            this.managedConn.abortConnection();
        }
        return false;
    }

    protected void releaseManagedConnection() throws IOException {
        if (this.managedConn != null) {
            try {
                this.managedConn.releaseConnection();
            } finally {
                this.managedConn = null;
            }
        }
    }
}
