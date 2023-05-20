package org.apache.http.pool;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@ThreadSafe
public abstract class AbstractConnPool<T, C, E extends PoolEntry<T, C>> implements ConnPool<T, E>, ConnPoolControl<T> {
    private final LinkedList<E> available;
    private final ConnFactory<T, C> connFactory;
    private volatile int defaultMaxPerRoute;
    private volatile boolean isShutDown;
    private final Set<E> leased;
    private final Lock lock;
    private final Map<T, Integer> maxPerRoute;
    private volatile int maxTotal;
    private final LinkedList<PoolEntryFuture<E>> pending;
    private final Map<T, RouteSpecificPool<T, C, E>> routeToPool;
    private volatile int validateAfterInactivity;

    /* renamed from: org.apache.http.pool.AbstractConnPool.1 */
    class C01551 extends RouteSpecificPool<T, C, E> {
        final /* synthetic */ Object val$route;

        C01551(Object x0, Object obj) {
            this.val$route = obj;
            super(x0);
        }

        protected E createEntry(C conn) {
            return AbstractConnPool.this.createEntry(this.val$route, conn);
        }
    }

    /* renamed from: org.apache.http.pool.AbstractConnPool.2 */
    class C01562 extends PoolEntryFuture<E> {
        final /* synthetic */ Object val$route;
        final /* synthetic */ Object val$state;

        C01562(Lock x0, FutureCallback x1, Object obj, Object obj2) {
            this.val$route = obj;
            this.val$state = obj2;
            super(x0, x1);
        }

        public E getPoolEntry(long timeout, TimeUnit tunit) throws InterruptedException, TimeoutException, IOException {
            E entry = AbstractConnPool.this.getPoolEntryBlocking(this.val$route, this.val$state, timeout, tunit, this);
            AbstractConnPool.this.onLease(entry);
            return entry;
        }
    }

    /* renamed from: org.apache.http.pool.AbstractConnPool.3 */
    class C01573 implements PoolEntryCallback<T, C> {
        final /* synthetic */ long val$deadline;

        C01573(long j) {
            this.val$deadline = j;
        }

        public void process(PoolEntry<T, C> entry) {
            if (entry.getUpdated() <= this.val$deadline) {
                entry.close();
            }
        }
    }

    /* renamed from: org.apache.http.pool.AbstractConnPool.4 */
    class C01584 implements PoolEntryCallback<T, C> {
        final /* synthetic */ long val$now;

        C01584(long j) {
            this.val$now = j;
        }

        public void process(PoolEntry<T, C> entry) {
            if (entry.isExpired(this.val$now)) {
                entry.close();
            }
        }
    }

    protected abstract E createEntry(T t, C c);

    public AbstractConnPool(ConnFactory<T, C> connFactory, int defaultMaxPerRoute, int maxTotal) {
        this.connFactory = (ConnFactory) Args.notNull(connFactory, "Connection factory");
        this.defaultMaxPerRoute = Args.positive(defaultMaxPerRoute, "Max per route value");
        this.maxTotal = Args.positive(maxTotal, "Max total value");
        this.lock = new ReentrantLock();
        this.routeToPool = new HashMap();
        this.leased = new HashSet();
        this.available = new LinkedList();
        this.pending = new LinkedList();
        this.maxPerRoute = new HashMap();
    }

    protected void onLease(E e) {
    }

    protected void onRelease(E e) {
    }

    protected void onReuse(E e) {
    }

    protected boolean validate(E e) {
        return true;
    }

    public boolean isShutdown() {
        return this.isShutDown;
    }

    public void shutdown() throws IOException {
        if (!this.isShutDown) {
            this.isShutDown = true;
            this.lock.lock();
            try {
                Iterator i$ = this.available.iterator();
                while (i$.hasNext()) {
                    ((PoolEntry) i$.next()).close();
                }
                for (PoolEntry entry : this.leased) {
                    entry.close();
                }
                for (RouteSpecificPool<T, C, E> pool : this.routeToPool.values()) {
                    pool.shutdown();
                }
                this.routeToPool.clear();
                this.leased.clear();
                this.available.clear();
            } finally {
                this.lock.unlock();
            }
        }
    }

    private RouteSpecificPool<T, C, E> getPool(T route) {
        RouteSpecificPool<T, C, E> pool = (RouteSpecificPool) this.routeToPool.get(route);
        if (pool != null) {
            return pool;
        }
        pool = new C01551(route, route);
        this.routeToPool.put(route, pool);
        return pool;
    }

    public Future<E> lease(T route, Object state, FutureCallback<E> callback) {
        Args.notNull(route, "Route");
        Asserts.check(!this.isShutDown, "Connection pool shut down");
        return new C01562(this.lock, callback, route, state);
    }

    public Future<E> lease(T route, Object state) {
        return lease(route, state, null);
    }

    private E getPoolEntryBlocking(T route, Object state, long timeout, TimeUnit tunit, PoolEntryFuture<E> future) throws IOException, InterruptedException, TimeoutException {
        Date deadline = null;
        if (timeout > 0) {
            deadline = new Date(System.currentTimeMillis() + tunit.toMillis(timeout));
        }
        this.lock.lock();
        RouteSpecificPool<T, C, E> pool = getPool(route);
        E entry = null;
        while (entry == null) {
            Asserts.check(!this.isShutDown, "Connection pool shut down");
            while (true) {
                entry = pool.getFree(state);
                if (entry != null) {
                    if (entry.isExpired(System.currentTimeMillis())) {
                        entry.close();
                    } else if (this.validateAfterInactivity > 0) {
                        if (entry.getUpdated() + ((long) this.validateAfterInactivity) <= System.currentTimeMillis() && !validate(entry)) {
                            entry.close();
                        }
                    }
                    if (!entry.isClosed()) {
                        break;
                    }
                    this.available.remove(entry);
                    pool.free(entry, false);
                } else {
                    break;
                }
            }
            if (entry != null) {
                this.available.remove(entry);
                this.leased.add(entry);
                onReuse(entry);
                this.lock.unlock();
                return entry;
            }
            int maxPerRoute = getMax(route);
            int excess = Math.max(0, (pool.getAllocatedCount() + 1) - maxPerRoute);
            if (excess > 0) {
                for (int i = 0; i < excess; i++) {
                    E lastUsed = pool.getLastUsed();
                    if (lastUsed == null) {
                        break;
                    }
                    lastUsed.close();
                    this.available.remove(lastUsed);
                    pool.remove(lastUsed);
                }
            }
            if (pool.getAllocatedCount() < maxPerRoute) {
                int freeCapacity = Math.max(this.maxTotal - this.leased.size(), 0);
                if (freeCapacity > 0) {
                    if (this.available.size() > freeCapacity - 1) {
                        if (!this.available.isEmpty()) {
                            PoolEntry lastUsed2 = (PoolEntry) this.available.removeLast();
                            lastUsed2.close();
                            getPool(lastUsed2.getRoute()).remove(lastUsed2);
                        }
                    }
                    entry = pool.add(this.connFactory.create(route));
                    this.leased.add(entry);
                    this.lock.unlock();
                    return entry;
                }
            }
            try {
                pool.queue(future);
                this.pending.add(future);
                boolean success = future.await(deadline);
                pool.unqueue(future);
                this.pending.remove(future);
                if (!success && deadline != null && deadline.getTime() <= System.currentTimeMillis()) {
                    break;
                }
            } catch (Throwable th) {
                this.lock.unlock();
            }
        }
        throw new TimeoutException("Timeout waiting for connection");
    }

    public void release(E entry, boolean reusable) {
        this.lock.lock();
        try {
            if (this.leased.remove(entry)) {
                RouteSpecificPool<T, C, E> pool = getPool(entry.getRoute());
                pool.free(entry, reusable);
                if (!reusable || this.isShutDown) {
                    entry.close();
                } else {
                    this.available.addFirst(entry);
                    onRelease(entry);
                }
                PoolEntryFuture<E> future = pool.nextPending();
                if (future != null) {
                    this.pending.remove(future);
                } else {
                    future = (PoolEntryFuture) this.pending.poll();
                }
                if (future != null) {
                    future.wakeup();
                }
            }
            this.lock.unlock();
        } catch (Throwable th) {
            this.lock.unlock();
        }
    }

    private int getMax(T route) {
        Integer v = (Integer) this.maxPerRoute.get(route);
        if (v != null) {
            return v.intValue();
        }
        return this.defaultMaxPerRoute;
    }

    public void setMaxTotal(int max) {
        Args.positive(max, "Max value");
        this.lock.lock();
        try {
            this.maxTotal = max;
        } finally {
            this.lock.unlock();
        }
    }

    public int getMaxTotal() {
        this.lock.lock();
        try {
            int i = this.maxTotal;
            return i;
        } finally {
            this.lock.unlock();
        }
    }

    public void setDefaultMaxPerRoute(int max) {
        Args.positive(max, "Max per route value");
        this.lock.lock();
        try {
            this.defaultMaxPerRoute = max;
        } finally {
            this.lock.unlock();
        }
    }

    public int getDefaultMaxPerRoute() {
        this.lock.lock();
        try {
            int i = this.defaultMaxPerRoute;
            return i;
        } finally {
            this.lock.unlock();
        }
    }

    public void setMaxPerRoute(T route, int max) {
        Args.notNull(route, "Route");
        Args.positive(max, "Max per route value");
        this.lock.lock();
        try {
            this.maxPerRoute.put(route, Integer.valueOf(max));
        } finally {
            this.lock.unlock();
        }
    }

    public int getMaxPerRoute(T route) {
        Args.notNull(route, "Route");
        this.lock.lock();
        try {
            int max = getMax(route);
            return max;
        } finally {
            this.lock.unlock();
        }
    }

    public PoolStats getTotalStats() {
        this.lock.lock();
        try {
            PoolStats poolStats = new PoolStats(this.leased.size(), this.pending.size(), this.available.size(), this.maxTotal);
            return poolStats;
        } finally {
            this.lock.unlock();
        }
    }

    public PoolStats getStats(T route) {
        Args.notNull(route, "Route");
        this.lock.lock();
        try {
            RouteSpecificPool<T, C, E> pool = getPool(route);
            PoolStats poolStats = new PoolStats(pool.getLeasedCount(), pool.getPendingCount(), pool.getAvailableCount(), getMax(route));
            return poolStats;
        } finally {
            this.lock.unlock();
        }
    }

    public Set<T> getRoutes() {
        this.lock.lock();
        try {
            Set<T> hashSet = new HashSet(this.routeToPool.keySet());
            return hashSet;
        } finally {
            this.lock.unlock();
        }
    }

    protected void enumAvailable(PoolEntryCallback<T, C> callback) {
        this.lock.lock();
        try {
            Iterator<E> it = this.available.iterator();
            while (it.hasNext()) {
                PoolEntry entry = (PoolEntry) it.next();
                callback.process(entry);
                if (entry.isClosed()) {
                    getPool(entry.getRoute()).remove(entry);
                    it.remove();
                }
            }
            purgePoolMap();
        } finally {
            this.lock.unlock();
        }
    }

    protected void enumLeased(PoolEntryCallback<T, C> callback) {
        this.lock.lock();
        try {
            for (PoolEntry entry : this.leased) {
                callback.process(entry);
            }
        } finally {
            this.lock.unlock();
        }
    }

    private void purgePoolMap() {
        Iterator<Entry<T, RouteSpecificPool<T, C, E>>> it = this.routeToPool.entrySet().iterator();
        while (it.hasNext()) {
            RouteSpecificPool<T, C, E> pool = (RouteSpecificPool) ((Entry) it.next()).getValue();
            if (pool.getPendingCount() + pool.getAllocatedCount() == 0) {
                it.remove();
            }
        }
    }

    public void closeIdle(long idletime, TimeUnit tunit) {
        Args.notNull(tunit, "Time unit");
        long time = tunit.toMillis(idletime);
        if (time < 0) {
            time = 0;
        }
        enumAvailable(new C01573(System.currentTimeMillis() - time));
    }

    public void closeExpired() {
        enumAvailable(new C01584(System.currentTimeMillis()));
    }

    public int getValidateAfterInactivity() {
        return this.validateAfterInactivity;
    }

    public void setValidateAfterInactivity(int ms) {
        this.validateAfterInactivity = ms;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[leased: ");
        buffer.append(this.leased);
        buffer.append("][available: ");
        buffer.append(this.available);
        buffer.append("][pending: ");
        buffer.append(this.pending);
        buffer.append("]");
        return buffer.toString();
    }
}
