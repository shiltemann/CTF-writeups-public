package org.apache.http.impl.client;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.impl.cookie.IgnoreSpecProvider;
import org.apache.http.impl.cookie.NetscapeDraftSpecProvider;
import org.apache.http.impl.cookie.RFC6265CookieSpecProvider;
import org.apache.http.impl.cookie.RFC6265CookieSpecProvider.CompatibilityLevel;

public final class CookieSpecRegistries {
    public static RegistryBuilder<CookieSpecProvider> createDefaultBuilder(PublicSuffixMatcher publicSuffixMatcher) {
        CookieSpecProvider defaultProvider = new DefaultCookieSpecProvider(publicSuffixMatcher);
        CookieSpecProvider laxStandardProvider = new RFC6265CookieSpecProvider(CompatibilityLevel.RELAXED, publicSuffixMatcher);
        return RegistryBuilder.create().register(CookieSpecs.DEFAULT, defaultProvider).register(CookiePolicy.BEST_MATCH, defaultProvider).register(CookiePolicy.BROWSER_COMPATIBILITY, defaultProvider).register(CookieSpecs.STANDARD, laxStandardProvider).register(CookieSpecs.STANDARD_STRICT, new RFC6265CookieSpecProvider(CompatibilityLevel.STRICT, publicSuffixMatcher)).register(CookiePolicy.NETSCAPE, new NetscapeDraftSpecProvider()).register(CookiePolicy.IGNORE_COOKIES, new IgnoreSpecProvider());
    }

    public static RegistryBuilder<CookieSpecProvider> createDefaultBuilder() {
        return createDefaultBuilder(PublicSuffixMatcherLoader.getDefault());
    }

    public static Lookup<CookieSpecProvider> createDefault() {
        return createDefault(PublicSuffixMatcherLoader.getDefault());
    }

    public static Lookup<CookieSpecProvider> createDefault(PublicSuffixMatcher publicSuffixMatcher) {
        return createDefaultBuilder(publicSuffixMatcher).build();
    }

    private CookieSpecRegistries() {
    }
}
