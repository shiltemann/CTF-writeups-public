package org.apache.http.impl.client;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.util.Args;

public final class IdleConnectionEvictor {
    private final HttpClientConnectionManager connectionManager;
    private volatile Exception exception;
    private final long maxIdleTimeMs;
    private final long sleepTimeMs;
    private final Thread thread;
    private final ThreadFactory threadFactory;

    /* renamed from: org.apache.http.impl.client.IdleConnectionEvictor.1 */
    class C00771 implements Runnable {
        final /* synthetic */ HttpClientConnectionManager val$connectionManager;

        C00771(HttpClientConnectionManager httpClientConnectionManager) {
            this.val$connectionManager = httpClientConnectionManager;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(IdleConnectionEvictor.this.sleepTimeMs);
                    this.val$connectionManager.closeExpiredConnections();
                    if (IdleConnectionEvictor.this.maxIdleTimeMs > 0) {
                        this.val$connectionManager.closeIdleConnections(IdleConnectionEvictor.this.maxIdleTimeMs, TimeUnit.MILLISECONDS);
                    }
                } catch (Exception ex) {
                    IdleConnectionEvictor.this.exception = ex;
                    return;
                }
            }
        }
    }

    static class DefaultThreadFactory implements ThreadFactory {
        DefaultThreadFactory() {
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "Connection evictor");
            t.setDaemon(true);
            return t;
        }
    }

    public IdleConnectionEvictor(HttpClientConnectionManager connectionManager, ThreadFactory threadFactory, long sleepTime, TimeUnit sleepTimeUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        this.connectionManager = (HttpClientConnectionManager) Args.notNull(connectionManager, "Connection manager");
        if (threadFactory == null) {
            threadFactory = new DefaultThreadFactory();
        }
        this.threadFactory = threadFactory;
        if (sleepTimeUnit != null) {
            sleepTime = sleepTimeUnit.toMillis(sleepTime);
        }
        this.sleepTimeMs = sleepTime;
        if (maxIdleTimeUnit != null) {
            maxIdleTime = maxIdleTimeUnit.toMillis(maxIdleTime);
        }
        this.maxIdleTimeMs = maxIdleTime;
        this.thread = this.threadFactory.newThread(new C00771(connectionManager));
    }

    public IdleConnectionEvictor(HttpClientConnectionManager connectionManager, long sleepTime, TimeUnit sleepTimeUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        this(connectionManager, null, sleepTime, sleepTimeUnit, maxIdleTime, maxIdleTimeUnit);
    }

    public IdleConnectionEvictor(HttpClientConnectionManager connectionManager, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        this(connectionManager, null, maxIdleTime > 0 ? maxIdleTime : 5, maxIdleTimeUnit != null ? maxIdleTimeUnit : TimeUnit.SECONDS, maxIdleTime, maxIdleTimeUnit);
    }

    public void start() {
        this.thread.start();
    }

    public void shutdown() {
        this.thread.interrupt();
    }

    public boolean isRunning() {
        return this.thread.isAlive();
    }

    public void awaitTermination(long time, TimeUnit tunit) throws InterruptedException {
        Thread thread = this.thread;
        if (tunit == null) {
            tunit = TimeUnit.MILLISECONDS;
        }
        thread.join(tunit.toMillis(time));
    }
}
