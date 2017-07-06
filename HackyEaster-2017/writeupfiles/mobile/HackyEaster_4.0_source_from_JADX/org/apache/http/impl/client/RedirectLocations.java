package org.apache.http.impl.client;

import java.net.URI;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.http.annotation.NotThreadSafe;

@NotThreadSafe
public class RedirectLocations extends AbstractList<Object> {
    private final List<URI> all;
    private final Set<URI> unique;

    public RedirectLocations() {
        this.unique = new HashSet();
        this.all = new ArrayList();
    }

    public boolean contains(URI uri) {
        return this.unique.contains(uri);
    }

    public void add(URI uri) {
        this.unique.add(uri);
        this.all.add(uri);
    }

    public boolean remove(URI uri) {
        boolean removed = this.unique.remove(uri);
        if (removed) {
            Iterator<URI> it = this.all.iterator();
            while (it.hasNext()) {
                if (((URI) it.next()).equals(uri)) {
                    it.remove();
                }
            }
        }
        return removed;
    }

    public List<URI> getAll() {
        return new ArrayList(this.all);
    }

    public URI get(int index) {
        return (URI) this.all.get(index);
    }

    public int size() {
        return this.all.size();
    }

    public Object set(int index, Object element) {
        URI removed = (URI) this.all.set(index, (URI) element);
        this.unique.remove(removed);
        this.unique.add((URI) element);
        if (this.all.size() != this.unique.size()) {
            this.unique.addAll(this.all);
        }
        return removed;
    }

    public void add(int index, Object element) {
        this.all.add(index, (URI) element);
        this.unique.add((URI) element);
    }

    public URI remove(int index) {
        URI removed = (URI) this.all.remove(index);
        this.unique.remove(removed);
        if (this.all.size() != this.unique.size()) {
            this.unique.addAll(this.all);
        }
        return removed;
    }

    public boolean contains(Object o) {
        return this.unique.contains(o);
    }
}
