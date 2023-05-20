package org.apache.http.impl.conn.tsccm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.Args;

@Deprecated
public class BasicPoolEntryRef extends WeakReference<BasicPoolEntry> {
    private final HttpRoute route;

    public BasicPoolEntryRef(BasicPoolEntry entry, ReferenceQueue<Object> queue) {
        super(entry, queue);
        Args.notNull(entry, "Pool entry");
        this.route = entry.getPlannedRoute();
    }

    public final HttpRoute getRoute() {
        return this.route;
    }
}
