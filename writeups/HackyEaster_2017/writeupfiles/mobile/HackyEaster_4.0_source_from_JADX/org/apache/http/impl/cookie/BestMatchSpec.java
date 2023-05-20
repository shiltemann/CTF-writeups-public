package org.apache.http.impl.cookie;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.params.CookiePolicy;

@Deprecated
@ThreadSafe
public class BestMatchSpec extends DefaultCookieSpec {
    public BestMatchSpec(String[] datepatterns, boolean oneHeader) {
        super(datepatterns, oneHeader);
    }

    public BestMatchSpec() {
        this(null, false);
    }

    public String toString() {
        return CookiePolicy.BEST_MATCH;
    }
}
