package org.apache.http.pool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@NotThreadSafe
abstract class RouteSpecificPool<T, C, E extends PoolEntry<T, C>> {
    private final LinkedList<E> available;
    private final Set<E> leased;
    private final LinkedList<PoolEntryFuture<E>> pending;
    private final T route;

    protected abstract E createEntry(C c);

    RouteSpecificPool(T route) {
        this.route = route;
        this.leased = new HashSet();
        this.available = new LinkedList();
        this.pending = new LinkedList();
    }

    public final T getRoute() {
        return this.route;
    }

    public int getLeasedCount() {
        return this.leased.size();
    }

    public int getPendingCount() {
        return this.pending.size();
    }

    public int getAvailableCount() {
        return this.available.size();
    }

    public int getAllocatedCount() {
        return this.available.size() + this.leased.size();
    }

    public E getFree(Object state) {
        if (!this.available.isEmpty()) {
            Iterator<E> it;
            PoolEntry entry;
            if (state != null) {
                it = this.available.iterator();
                while (it.hasNext()) {
                    entry = (PoolEntry) it.next();
                    if (state.equals(entry.getState())) {
                        it.remove();
                        this.leased.add(entry);
                        return entry;
                    }
                }
            }
            it = this.available.iterator();
            while (it.hasNext()) {
                entry = (PoolEntry) it.next();
                if (entry.getState() == null) {
                    it.remove();
                    this.leased.add(entry);
                    return entry;
                }
            }
        }
        return null;
    }

    public E getLastUsed() {
        if (this.available.isEmpty()) {
            return null;
        }
        return (PoolEntry) this.available.getLast();
    }

    public boolean remove(E entry) {
        Args.notNull(entry, "Pool entry");
        if (this.available.remove(entry) || this.leased.remove(entry)) {
            return true;
        }
        return false;
    }

    public void free(E entry, boolean reusable) {
        Args.notNull(entry, "Pool entry");
        Asserts.check(this.leased.remove(entry), "Entry %s has not been leased from this pool", (Object) entry);
        if (reusable) {
            this.available.addFirst(entry);
        }
    }

    public E add(C conn) {
        E entry = createEntry(conn);
        this.leased.add(entry);
        return entry;
    }

    public void queue(PoolEntryFuture<E> future) {
        if (future != null) {
            this.pending.add(future);
        }
    }

    public PoolEntryFuture<E> nextPending() {
        return (PoolEntryFuture) this.pending.poll();
    }

    public void unqueue(PoolEntryFuture<E> future) {
        if (future != null) {
            this.pending.remove(future);
        }
    }

    public void shutdown() {
        Iterator i$ = this.pending.iterator();
        while (i$.hasNext()) {
            ((PoolEntryFuture) i$.next()).cancel(true);
        }
        this.pending.clear();
        i$ = this.available.iterator();
        while (i$.hasNext()) {
            ((PoolEntry) i$.next()).close();
        }
        this.available.clear();
        for (PoolEntry entry : this.leased) {
            entry.close();
        }
        this.leased.clear();
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[route: ");
        buffer.append(this.route);
        buffer.append("][leased: ");
        buffer.append(this.leased.size());
        buffer.append("][available: ");
        buffer.append(this.available.size());
        buffer.append("][pending: ");
        buffer.append(this.pending.size());
        buffer.append("]");
        return buffer.toString();
    }
}
