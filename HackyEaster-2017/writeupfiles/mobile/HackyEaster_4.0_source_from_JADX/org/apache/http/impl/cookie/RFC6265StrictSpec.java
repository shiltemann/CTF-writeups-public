package org.apache.http.impl.cookie;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.protocol.HttpDateGenerator;

@ThreadSafe
public class RFC6265StrictSpec extends RFC6265CookieSpecBase {
    static final String[] DATE_PATTERNS;

    static {
        DATE_PATTERNS = new String[]{HttpDateGenerator.PATTERN_RFC1123, DateUtils.PATTERN_RFC1036, DateUtils.PATTERN_ASCTIME};
    }

    public RFC6265StrictSpec() {
        super(new BasicPathHandler(), new BasicDomainHandler(), new BasicMaxAgeHandler(), new BasicSecureHandler(), new BasicExpiresHandler(DATE_PATTERNS));
    }

    RFC6265StrictSpec(CommonCookieAttributeHandler... handlers) {
        super(handlers);
    }

    public String toString() {
        return "rfc6265-strict";
    }
}
