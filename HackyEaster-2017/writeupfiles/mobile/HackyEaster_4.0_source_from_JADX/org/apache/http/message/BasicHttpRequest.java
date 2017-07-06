package org.apache.http.message;

import org.apache.http.HttpRequest;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.util.Args;

@NotThreadSafe
public class BasicHttpRequest extends AbstractHttpMessage implements HttpRequest {
    private final String method;
    private RequestLine requestline;
    private final String uri;

    public BasicHttpRequest(String method, String uri) {
        this.method = (String) Args.notNull(method, "Method name");
        this.uri = (String) Args.notNull(uri, "Request URI");
        this.requestline = null;
    }

    public BasicHttpRequest(String method, String uri, ProtocolVersion ver) {
        this(new BasicRequestLine(method, uri, ver));
    }

    public BasicHttpRequest(RequestLine requestline) {
        this.requestline = (RequestLine) Args.notNull(requestline, "Request line");
        this.method = requestline.getMethod();
        this.uri = requestline.getUri();
    }

    public ProtocolVersion getProtocolVersion() {
        return getRequestLine().getProtocolVersion();
    }

    public RequestLine getRequestLine() {
        if (this.requestline == null) {
            this.requestline = new BasicRequestLine(this.method, this.uri, HttpVersion.HTTP_1_1);
        }
        return this.requestline;
    }

    public String toString() {
        return this.method + TokenParser.SP + this.uri + TokenParser.SP + this.headergroup;
    }
}
