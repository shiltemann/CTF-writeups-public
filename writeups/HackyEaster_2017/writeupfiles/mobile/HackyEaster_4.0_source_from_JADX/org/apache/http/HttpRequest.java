package org.apache.http;

public interface HttpRequest extends HttpMessage {
    RequestLine getRequestLine();
}
