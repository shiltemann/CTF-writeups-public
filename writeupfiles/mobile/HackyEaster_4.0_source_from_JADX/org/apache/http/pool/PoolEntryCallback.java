package org.apache.http.pool;

public interface PoolEntryCallback<T, C> {
    void process(PoolEntry<T, C> poolEntry);
}
