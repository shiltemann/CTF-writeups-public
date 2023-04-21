package org.apache.http.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.util.Args;

@NotThreadSafe
public final class RegistryBuilder<I> {
    private final Map<String, I> items;

    public static <I> RegistryBuilder<I> create() {
        return new RegistryBuilder();
    }

    RegistryBuilder() {
        this.items = new HashMap();
    }

    public RegistryBuilder<I> register(String id, I item) {
        Args.notEmpty((CharSequence) id, "ID");
        Args.notNull(item, "Item");
        this.items.put(id.toLowerCase(Locale.ROOT), item);
        return this;
    }

    public Registry<I> build() {
        return new Registry(this.items);
    }

    public String toString() {
        return this.items.toString();
    }
}
