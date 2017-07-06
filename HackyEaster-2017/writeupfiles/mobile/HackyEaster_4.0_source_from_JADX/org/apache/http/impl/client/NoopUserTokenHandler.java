package org.apache.http.impl.client;

import org.apache.http.annotation.Immutable;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.protocol.HttpContext;

@Immutable
public class NoopUserTokenHandler implements UserTokenHandler {
    public static final NoopUserTokenHandler INSTANCE;

    static {
        INSTANCE = new NoopUserTokenHandler();
    }

    public Object getUserToken(HttpContext context) {
        return null;
    }
}
