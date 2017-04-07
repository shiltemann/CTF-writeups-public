package org.apache.http.auth;

import org.apache.http.protocol.HttpContext;

public interface AuthSchemeProvider {
    AuthScheme create(HttpContext httpContext);
}
