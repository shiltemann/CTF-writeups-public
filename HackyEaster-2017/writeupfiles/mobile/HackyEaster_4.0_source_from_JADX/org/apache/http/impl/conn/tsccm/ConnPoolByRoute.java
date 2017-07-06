package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
public class ConnPoolByRoute extends AbstractConnPool {
    protected final ConnPerRoute connPerRoute;
    private final long connTTL;
    private final TimeUnit connTTLTimeUnit;
    protected final Queue<BasicPoolEntry> freeConnections;
    protected final Set<BasicPoolEntry> leasedConnections;
    private final Log log;
    protected volatile int maxTotalConnections;
    protected volatile int numConnections;
    protected final ClientConnectionOperator operator;
    private final Lock poolLock;
    protected final Map<HttpRoute, RouteSpecificPool> routeToPool;
    protected volatile boolean shutdown;
    protected final Queue<WaitingThread> waitingThreads;

    /* renamed from: org.apache.http.impl.conn.tsccm.ConnPoolByRoute.1 */
    class C01521 implements PoolEntryRequest {
        final /* synthetic */ WaitingThreadAborter val$aborter;
        final /* synthetic */ HttpRoute val$route;
        final /* synthetic */ Object val$state;

        C01521(WaitingThreadAborter waitingThreadAborter, HttpRoute httpRoute, Object obj) {
            this.val$aborter = waitingThreadAborter;
            this.val$route = httpRoute;
            this.val$state = obj;
        }

        public void abortRequest() {
            ConnPoolByRoute.this.poolLock.lock();
            try {
                this.val$aborter.abort();
            } finally {
                ConnPoolByRoute.this.poolLock.unlock();
            }
        }

        public BasicPoolEntry getPoolEntry(long timeout, TimeUnit tunit) throws InterruptedException, ConnectionPoolTimeoutException {
            return ConnPoolByRoute.this.getEntryBlocking(this.val$route, this.val$state, timeout, tunit, this.val$aborter);
        }
    }

    public ConnPoolByRoute(ClientConnectionOperator operator, ConnPerRoute connPerRoute, int maxTotalConnections) {
        this(operator, connPerRoute, maxTotalConnections, -1, TimeUnit.MILLISECONDS);
    }

    public ConnPoolByRoute(ClientConnectionOperator operator, ConnPerRoute connPerRoute, int maxTotalConnections, long connTTL, TimeUnit connTTLTimeUnit) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(operator, "Connection operator");
        Args.notNull(connPerRoute, "Connections per route");
        this.poolLock = this.poolLock;
        this.leasedConnections = this.leasedConnections;
        this.operator = operator;
        this.connPerRoute = connPerRoute;
        this.maxTotalConnections = maxTotalConnections;
        this.freeConnections = createFreeConnQueue();
        this.waitingThreads = createWaitingThreadQueue();
        this.routeToPool = createRouteToPoolMap();
        this.connTTL = connTTL;
        this.connTTLTimeUnit = connTTLTimeUnit;
    }

    protected Lock getLock() {
        return this.poolLock;
    }

    @Deprecated
    public ConnPoolByRoute(ClientConnectionOperator operator, HttpParams params) {
        this(operator, ConnManagerParams.getMaxConnectionsPerRoute(params), ConnManagerParams.getMaxTotalConnections(params));
    }

    protected Queue<BasicPoolEntry> createFreeConnQueue() {
        return new LinkedList();
    }

    protected Queue<WaitingThread> createWaitingThreadQueue() {
        return new LinkedList();
    }

    protected Map<HttpRoute, RouteSpecificPool> createRouteToPoolMap() {
        return new HashMap();
    }

    protected RouteSpecificPool newRouteSpecificPool(HttpRoute route) {
        return new RouteSpecificPool(route, this.connPerRoute);
    }

    protected WaitingThread newWaitingThread(Condition cond, RouteSpecificPool rospl) {
        return new WaitingThread(cond, rospl);
    }

    private void closeConnection(BasicPoolEntry entry) {
        OperatedClientConnection conn = entry.getConnection();
        if (conn != null) {
            try {
                conn.close();
            } catch (IOException ex) {
                this.log.debug("I/O error closing connection", ex);
            }
        }
    }

    protected RouteSpecificPool getRoutePool(HttpRoute route, boolean create) {
        this.poolLock.lock();
        try {
            RouteSpecificPool rospl = (RouteSpecificPool) this.routeToPool.get(route);
            if (rospl == null && create) {
                rospl = newRouteSpecificPool(route);
                this.routeToPool.put(route, rospl);
            }
            this.poolLock.unlock();
            return rospl;
        } catch (Throwable th) {
            this.poolLock.unlock();
        }
    }

    public int getConnectionsInPool(HttpRoute route) {
        int i = 0;
        this.poolLock.lock();
        try {
            RouteSpecificPool rospl = getRoutePool(route, false);
            if (rospl != null) {
                i = rospl.getEntryCount();
            }
            this.poolLock.unlock();
            return i;
        } catch (Throwable th) {
            this.poolLock.unlock();
        }
    }

    public int getConnectionsInPool() {
        this.poolLock.lock();
        try {
            int i = this.numConnections;
            return i;
        } finally {
            this.poolLock.unlock();
        }
    }

    public PoolEntryRequest requestPoolEntry(HttpRoute route, Object state) {
        return new C01521(new WaitingThreadAborter(), route, state);
    }

    protected BasicPoolEntry getEntryBlocking(HttpRoute route, Object state, long timeout, TimeUnit tunit, WaitingThreadAborter aborter) throws ConnectionPoolTimeoutException, InterruptedException {
        Date deadline = null;
        if (timeout > 0) {
            deadline = new Date(System.currentTimeMillis() + tunit.toMillis(timeout));
        }
        BasicPoolEntry entry = null;
        this.poolLock.lock();
        RouteSpecificPool rospl = getRoutePool(route, true);
        WaitingThread waitingThread = null;
        while (entry == null) {
            Asserts.check(!this.shutdown, "Connection pool shut down");
            if (this.log.isDebugEnabled()) {
                this.log.debug("[" + route + "] total kept alive: " + this.freeConnections.size() + ", total issued: " + this.leasedConnections.size() + ", total allocated: " + this.numConnections + " out of " + this.maxTotalConnections);
            }
            entry = getFreeEntry(rospl, state);
            if (entry != null) {
                break;
            }
            boolean hasCapacity = rospl.getCapacity() > 0;
            if (this.log.isDebugEnabled()) {
                this.log.debug("Available capacity: " + rospl.getCapacity() + " out of " + rospl.getMaxEntries() + " [" + route + "][" + state + "]");
            }
            if (hasCapacity && this.numConnections < this.maxTotalConnections) {
                entry = createEntry(rospl, this.operator);
            } else if (!hasCapacity || this.freeConnections.isEmpty()) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Need to wait for connection [" + route + "][" + state + "]");
                }
                if (waitingThread == null) {
                    waitingThread = newWaitingThread(this.poolLock.newCondition(), rospl);
                    aborter.setWaitingThread(waitingThread);
                }
                try {
                    rospl.queueThread(waitingThread);
                    this.waitingThreads.add(waitingThread);
                    boolean success = waitingThread.await(deadline);
                    rospl.removeThread(waitingThread);
                    this.waitingThreads.remove(waitingThread);
                    if (!(success || deadline == null || deadline.getTime() > System.currentTimeMillis())) {
                        throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
                    }
                } catch (Throwable th) {
                    this.poolLock.unlock();
                }
            } else {
                deleteLeastUsedEntry();
                rospl = getRoutePool(route, true);
                entry = createEntry(rospl, this.operator);
            }
        }
        this.poolLock.unlock();
        return entry;
    }

    public void freeEntry(BasicPoolEntry entry, boolean reusable, long validDuration, TimeUnit timeUnit) {
        HttpRoute route = entry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Releasing connection [" + route + "][" + entry.getState() + "]");
        }
        this.poolLock.lock();
        try {
            if (this.shutdown) {
                closeConnection(entry);
                return;
            }
            this.leasedConnections.remove(entry);
            RouteSpecificPool rospl = getRoutePool(route, true);
            if (!reusable || rospl.getCapacity() < 0) {
                closeConnection(entry);
                rospl.dropEntry();
                this.numConnections--;
            } else {
                if (this.log.isDebugEnabled()) {
                    String s;
                    if (validDuration > 0) {
                        s = "for " + validDuration + " " + timeUnit;
                    } else {
                        s = "indefinitely";
                    }
                    this.log.debug("Pooling connection [" + route + "][" + entry.getState() + "]; keep alive " + s);
                }
                rospl.freeEntry(entry);
                entry.updateExpiry(validDuration, timeUnit);
                this.freeConnections.add(entry);
            }
            notifyWaitingThread(rospl);
            this.poolLock.unlock();
        } finally {
            this.poolLock.unlock();
        }
    }

    protected BasicPoolEntry getFreeEntry(RouteSpecificPool rospl, Object state) {
        BasicPoolEntry entry = null;
        this.poolLock.lock();
        boolean done = false;
        while (!done) {
            try {
                entry = rospl.allocEntry(state);
                if (entry != null) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Getting free connection [" + rospl.getRoute() + "][" + state + "]");
                    }
                    this.freeConnections.remove(entry);
                    if (entry.isExpired(System.currentTimeMillis())) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Closing expired free connection [" + rospl.getRoute() + "][" + state + "]");
                        }
                        closeConnection(entry);
                        rospl.dropEntry();
                        this.numConnections--;
                    } else {
                        this.leasedConnections.add(entry);
                        done = true;
                    }
                } else {
                    done = true;
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("No free connections [" + rospl.getRoute() + "][" + state + "]");
                    }
                }
            } catch (Throwable th) {
                this.poolLock.unlock();
            }
        }
        this.poolLock.unlock();
        return entry;
    }

    protected BasicPoolEntry createEntry(RouteSpecificPool rospl, ClientConnectionOperator op) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Creating new connection [" + rospl.getRoute() + "]");
        }
        BasicPoolEntry entry = new BasicPoolEntry(op, rospl.getRoute(), this.connTTL, this.connTTLTimeUnit);
        this.poolLock.lock();
        try {
            rospl.createdEntry(entry);
            this.numConnections++;
            this.leasedConnections.add(entry);
            return entry;
        } finally {
            this.poolLock.unlock();
        }
    }

    protected void deleteEntry(BasicPoolEntry entry) {
        HttpRoute route = entry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Deleting connection [" + route + "][" + entry.getState() + "]");
        }
        this.poolLock.lock();
        try {
            closeConnection(entry);
            RouteSpecificPool rospl = getRoutePool(route, true);
            rospl.deleteEntry(entry);
            this.numConnections--;
            if (rospl.isUnused()) {
                this.routeToPool.remove(route);
            }
            this.poolLock.unlock();
        } catch (Throwable th) {
            this.poolLock.unlock();
        }
    }

    protected void deleteLeastUsedEntry() {
        this.poolLock.lock();
        try {
            BasicPoolEntry entry = (BasicPoolEntry) this.freeConnections.remove();
            if (entry != null) {
                deleteEntry(entry);
            } else if (this.log.isDebugEnabled()) {
                this.log.debug("No free connection to delete");
            }
            this.poolLock.unlock();
        } catch (Throwable th) {
            this.poolLock.unlock();
        }
    }

    protected void handleLostEntry(HttpRoute route) {
        this.poolLock.lock();
        try {
            RouteSpecificPool rospl = getRoutePool(route, true);
            rospl.dropEntry();
            if (rospl.isUnused()) {
                this.routeToPool.remove(route);
            }
            this.numConnections--;
            notifyWaitingThread(rospl);
        } finally {
            this.poolLock.unlock();
        }
    }

    protected void notifyWaitingThread(RouteSpecificPool rospl) {
        WaitingThread waitingThread = null;
        this.poolLock.lock();
        if (rospl != null) {
            try {
                if (rospl.hasThread()) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Notifying thread waiting on pool [" + rospl.getRoute() + "]");
                    }
                    waitingThread = rospl.nextThread();
                    if (waitingThread != null) {
                        waitingThread.wakeup();
                    }
                    this.poolLock.unlock();
                }
            } catch (Throwable th) {
                this.poolLock.unlock();
            }
        }
        if (!this.waitingThreads.isEmpty()) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Notifying thread waiting on any pool");
            }
            waitingThread = (WaitingThread) this.waitingThreads.remove();
        } else if (this.log.isDebugEnabled()) {
            this.log.debug("Notifying no-one, there are no waiting threads");
        }
        if (waitingThread != null) {
            waitingThread.wakeup();
        }
        this.poolLock.unlock();
    }

    public void deleteClosedConnections() {
        this.poolLock.lock();
        try {
            Iterator<BasicPoolEntry> iter = this.freeConnections.iterator();
            while (iter.hasNext()) {
                BasicPoolEntry entry = (BasicPoolEntry) iter.next();
                if (!entry.getConnection().isOpen()) {
                    iter.remove();
                    deleteEntry(entry);
                }
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    public void closeIdleConnections(long idletime, TimeUnit tunit) {
        long t = 0;
        Args.notNull(tunit, "Time unit");
        if (idletime > 0) {
            t = idletime;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Closing connections idle longer than " + t + " " + tunit);
        }
        long deadline = System.currentTimeMillis() - tunit.toMillis(t);
        this.poolLock.lock();
        try {
            Iterator<BasicPoolEntry> iter = this.freeConnections.iterator();
            while (iter.hasNext()) {
                BasicPoolEntry entry = (BasicPoolEntry) iter.next();
                if (entry.getUpdated() <= deadline) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Closing connection last used @ " + new Date(entry.getUpdated()));
                    }
                    iter.remove();
                    deleteEntry(entry);
                }
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    public void closeExpiredConnections() {
        this.log.debug("Closing expired connections");
        long now = System.currentTimeMillis();
        this.poolLock.lock();
        try {
            Iterator<BasicPoolEntry> iter = this.freeConnections.iterator();
            while (iter.hasNext()) {
                BasicPoolEntry entry = (BasicPoolEntry) iter.next();
                if (entry.isExpired(now)) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Closing connection expired @ " + new Date(entry.getExpiry()));
                    }
                    iter.remove();
                    deleteEntry(entry);
                }
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    public void shutdown() {
        this.poolLock.lock();
        try {
            if (!this.shutdown) {
                BasicPoolEntry entry;
                this.shutdown = true;
                Iterator<BasicPoolEntry> iter1 = this.leasedConnections.iterator();
                while (iter1.hasNext()) {
                    entry = (BasicPoolEntry) iter1.next();
                    iter1.remove();
                    closeConnection(entry);
                }
                Iterator<BasicPoolEntry> iter2 = this.freeConnections.iterator();
                while (iter2.hasNext()) {
                    entry = (BasicPoolEntry) iter2.next();
                    iter2.remove();
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Closing connection [" + entry.getPlannedRoute() + "][" + entry.getState() + "]");
                    }
                    closeConnection(entry);
                }
                Iterator<WaitingThread> iwth = this.waitingThreads.iterator();
                while (iwth.hasNext()) {
                    WaitingThread waiter = (WaitingThread) iwth.next();
                    iwth.remove();
                    waiter.wakeup();
                }
                this.routeToPool.clear();
                this.poolLock.unlock();
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    public void setMaxTotalConnections(int max) {
        this.poolLock.lock();
        try {
            this.maxTotalConnections = max;
        } finally {
            this.poolLock.unlock();
        }
    }

    public int getMaxTotalConnections() {
        return this.maxTotalConnections;
    }
}
