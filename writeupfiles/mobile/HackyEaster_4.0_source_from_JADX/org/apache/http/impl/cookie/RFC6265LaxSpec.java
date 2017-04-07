package org.apache.http.impl.cookie;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.cookie.CommonCookieAttributeHandler;

@ThreadSafe
public class RFC6265LaxSpec extends RFC6265CookieSpecBase {
    public RFC6265LaxSpec() {
        super(new BasicPathHandler(), new BasicDomainHandler(), new LaxMaxAgeHandler(), new BasicSecureHandler(), new LaxExpiresHandler());
    }

    RFC6265LaxSpec(CommonCookieAttributeHandler... handlers) {
        super(handlers);
    }

    public String toString() {
        return "rfc6265-lax";
    }
}
