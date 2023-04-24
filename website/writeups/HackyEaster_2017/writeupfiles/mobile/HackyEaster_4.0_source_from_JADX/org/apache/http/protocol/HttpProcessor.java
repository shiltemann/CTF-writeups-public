package org.apache.http.protocol;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;

public interface HttpProcessor extends HttpRequestInterceptor, HttpResponseInterceptor {
}
