package org.apache.http.client;

import org.apache.http.protocol.HttpContext;

public interface UserTokenHandler {
    Object getUserToken(HttpContext httpContext);
}
