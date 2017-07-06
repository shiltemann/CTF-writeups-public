package org.apache.http.cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.config.Lookup;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.Args;

@Deprecated
@ThreadSafe
public final class CookieSpecRegistry implements Lookup<CookieSpecProvider> {
    private final ConcurrentHashMap<String, CookieSpecFactory> registeredSpecs;

    /* renamed from: org.apache.http.cookie.CookieSpecRegistry.1 */
    class C01461 implements CookieSpecProvider {
        final /* synthetic */ String val$name;

        C01461(String str) {
            this.val$name = str;
        }

        public CookieSpec create(HttpContext context) {
            return CookieSpecRegistry.this.getCookieSpec(this.val$name, ((HttpRequest) context.getAttribute(HttpCoreContext.HTTP_REQUEST)).getParams());
        }
    }

    public CookieSpecRegistry() {
        this.registeredSpecs = new ConcurrentHashMap();
    }

    public void register(String name, CookieSpecFactory factory) {
        Args.notNull(name, "Name");
        Args.notNull(factory, "Cookie spec factory");
        this.registeredSpecs.put(name.toLowerCase(Locale.ENGLISH), factory);
    }

    public void unregister(String id) {
        Args.notNull(id, "Id");
        this.registeredSpecs.remove(id.toLowerCase(Locale.ENGLISH));
    }

    public CookieSpec getCookieSpec(String name, HttpParams params) throws IllegalStateException {
        Args.notNull(name, "Name");
        CookieSpecFactory factory = (CookieSpecFactory) this.registeredSpecs.get(name.toLowerCase(Locale.ENGLISH));
        if (factory != null) {
            return factory.newInstance(params);
        }
        throw new IllegalStateException("Unsupported cookie spec: " + name);
    }

    public CookieSpec getCookieSpec(String name) throws IllegalStateException {
        return getCookieSpec(name, null);
    }

    public List<String> getSpecNames() {
        return new ArrayList(this.registeredSpecs.keySet());
    }

    public void setItems(Map<String, CookieSpecFactory> map) {
        if (map != null) {
            this.registeredSpecs.clear();
            this.registeredSpecs.putAll(map);
        }
    }

    public CookieSpecProvider lookup(String name) {
        return new C01461(name);
    }
}
