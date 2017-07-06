package org.apache.http.protocol;

@Deprecated
public interface HttpRequestHandlerResolver {
    HttpRequestHandler lookup(String str);
}
