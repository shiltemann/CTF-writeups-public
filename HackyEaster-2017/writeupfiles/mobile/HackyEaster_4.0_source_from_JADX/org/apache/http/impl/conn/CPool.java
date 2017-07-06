package org.apache.http.impl.conn;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.pool.AbstractConnPool;
import org.apache.http.pool.ConnFactory;

@ThreadSafe
class CPool extends AbstractConnPool<HttpRoute, ManagedHttpClientConnection, CPoolEntry> {
    private static final AtomicLong COUNTER;
    private final Log log;
    private final long timeToLive;
    private final TimeUnit tunit;

    static {
        COUNTER = new AtomicLong();
    }

    public CPool(ConnFactory<HttpRoute, ManagedHttpClientConnection> connFactory, int defaultMaxPerRoute, int maxTotal, long timeToLive, TimeUnit tunit) {
        super(connFactory, defaultMaxPerRoute, maxTotal);
        this.log = LogFactory.getLog(CPool.class);
        this.timeToLive = timeToLive;
        this.tunit = tunit;
    }

    protected CPoolEntry createEntry(HttpRoute route, ManagedHttpClientConnection conn) {
        return new CPoolEntry(this.log, Long.toString(COUNTER.getAndIncrement()), route, conn, this.timeToLive, this.tunit);
    }

    protected boolean validate(CPoolEntry entry) {
        return !((ManagedHttpClientConnection) entry.getConnection()).isStale();
    }
}
