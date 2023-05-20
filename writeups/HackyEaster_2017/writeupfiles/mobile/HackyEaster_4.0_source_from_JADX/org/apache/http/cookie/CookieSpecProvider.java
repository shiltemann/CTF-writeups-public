package org.apache.http.cookie;

import org.apache.http.protocol.HttpContext;

public interface CookieSpecProvider {
    CookieSpec create(HttpContext httpContext);
}
