package android.support.v4.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap(0, 0.75f, true);
    }

    public void resize(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        synchronized (this) {
            this.maxSize = maxSize;
        }
        trimToSize(maxSize);
    }

    public final V get(K key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            V mapValue = this.map.get(key);
            if (mapValue != null) {
                this.hitCount++;
                return mapValue;
            }
            this.missCount++;
            V createdValue = create(key);
            if (createdValue == null) {
                return null;
            }
            synchronized (this) {
                this.createCount++;
                mapValue = this.map.put(key, createdValue);
                if (mapValue != null) {
                    this.map.put(key, mapValue);
                } else {
                    this.size += safeSizeOf(key, createdValue);
                }
            }
            if (mapValue != null) {
                entryRemoved(false, key, createdValue, mapValue);
                return mapValue;
            }
            trimToSize(this.maxSize);
            return createdValue;
        }
    }

    public final V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        V previous;
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf(key, value);
            previous = this.map.put(key, value);
            if (previous != null) {
                this.size -= safeSizeOf(key, previous);
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, value);
        }
        trimToSize(this.maxSize);
        return previous;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void trimToSize(int r7) {
        /*
        r6 = this;
    L_0x0000:
        monitor-enter(r6);
        r3 = r6.size;	 Catch:{ all -> 0x0032 }
        if (r3 < 0) goto L_0x0011;
    L_0x0005:
        r3 = r6.map;	 Catch:{ all -> 0x0032 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0032 }
        if (r3 == 0) goto L_0x0035;
    L_0x000d:
        r3 = r6.size;	 Catch:{ all -> 0x0032 }
        if (r3 == 0) goto L_0x0035;
    L_0x0011:
        r3 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0032 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0032 }
        r4.<init>();	 Catch:{ all -> 0x0032 }
        r5 = r6.getClass();	 Catch:{ all -> 0x0032 }
        r5 = r5.getName();	 Catch:{ all -> 0x0032 }
        r4 = r4.append(r5);	 Catch:{ all -> 0x0032 }
        r5 = ".sizeOf() is reporting inconsistent results!";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0032 }
        r4 = r4.toString();	 Catch:{ all -> 0x0032 }
        r3.<init>(r4);	 Catch:{ all -> 0x0032 }
        throw r3;	 Catch:{ all -> 0x0032 }
    L_0x0032:
        r3 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0032 }
        throw r3;
    L_0x0035:
        r3 = r6.size;	 Catch:{ all -> 0x0032 }
        if (r3 <= r7) goto L_0x0041;
    L_0x0039:
        r3 = r6.map;	 Catch:{ all -> 0x0032 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0032 }
        if (r3 == 0) goto L_0x0043;
    L_0x0041:
        monitor-exit(r6);	 Catch:{ all -> 0x0032 }
        return;
    L_0x0043:
        r3 = r6.map;	 Catch:{ all -> 0x0032 }
        r3 = r3.entrySet();	 Catch:{ all -> 0x0032 }
        r3 = r3.iterator();	 Catch:{ all -> 0x0032 }
        r1 = r3.next();	 Catch:{ all -> 0x0032 }
        r1 = (java.util.Map.Entry) r1;	 Catch:{ all -> 0x0032 }
        r0 = r1.getKey();	 Catch:{ all -> 0x0032 }
        r2 = r1.getValue();	 Catch:{ all -> 0x0032 }
        r3 = r6.map;	 Catch:{ all -> 0x0032 }
        r3.remove(r0);	 Catch:{ all -> 0x0032 }
        r3 = r6.size;	 Catch:{ all -> 0x0032 }
        r4 = r6.safeSizeOf(r0, r2);	 Catch:{ all -> 0x0032 }
        r3 = r3 - r4;
        r6.size = r3;	 Catch:{ all -> 0x0032 }
        r3 = r6.evictionCount;	 Catch:{ all -> 0x0032 }
        r3 = r3 + 1;
        r6.evictionCount = r3;	 Catch:{ all -> 0x0032 }
        monitor-exit(r6);	 Catch:{ all -> 0x0032 }
        r3 = 1;
        r4 = 0;
        r6.entryRemoved(r3, r0, r2, r4);
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.LruCache.trimToSize(int):void");
    }

    public final V remove(K key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        V previous;
        synchronized (this) {
            previous = this.map.remove(key);
            if (previous != null) {
                this.size -= safeSizeOf(key, previous);
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, null);
        }
        return previous;
    }

    protected void entryRemoved(boolean evicted, K k, V v, V v2) {
    }

    protected V create(K k) {
        return null;
    }

    private int safeSizeOf(K key, V value) {
        int result = sizeOf(key, value);
        if (result >= 0) {
            return result;
        }
        throw new IllegalStateException("Negative size: " + key + "=" + value);
    }

    protected int sizeOf(K k, V v) {
        return 1;
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() {
        String format;
        int hitPercent = 0;
        synchronized (this) {
            int accesses = this.hitCount + this.missCount;
            if (accesses != 0) {
                hitPercent = (this.hitCount * 100) / accesses;
            }
            format = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(hitPercent)});
        }
        return format;
    }
}
