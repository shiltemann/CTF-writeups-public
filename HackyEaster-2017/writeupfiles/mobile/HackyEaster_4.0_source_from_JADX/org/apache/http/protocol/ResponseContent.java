package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.annotation.Immutable;
import org.apache.http.util.Args;

@Immutable
public class ResponseContent implements HttpResponseInterceptor {
    private final boolean overwrite;

    public ResponseContent() {
        this(false);
    }

    public ResponseContent(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
        Args.notNull(response, "HTTP response");
        if (this.overwrite) {
            response.removeHeaders(HTTP.TRANSFER_ENCODING);
            response.removeHeaders(HTTP.CONTENT_LEN);
        } else if (response.containsHeader(HTTP.TRANSFER_ENCODING)) {
            throw new ProtocolException("Transfer-encoding header already present");
        } else if (response.containsHeader(HTTP.CONTENT_LEN)) {
            throw new ProtocolException("Content-Length header already present");
        }
        ProtocolVersion ver = response.getStatusLine().getProtocolVersion();
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            long len = entity.getContentLength();
            if (entity.isChunked() && !ver.lessEquals(HttpVersion.HTTP_1_0)) {
                response.addHeader(HTTP.TRANSFER_ENCODING, HTTP.CHUNK_CODING);
            } else if (len >= 0) {
                response.addHeader(HTTP.CONTENT_LEN, Long.toString(entity.getContentLength()));
            }
            if (!(entity.getContentType() == null || response.containsHeader(HTTP.CONTENT_TYPE))) {
                response.addHeader(entity.getContentType());
            }
            if (entity.getContentEncoding() != null && !response.containsHeader(HTTP.CONTENT_ENCODING)) {
                response.addHeader(entity.getContentEncoding());
                return;
            }
            return;
        }
        int status = response.getStatusLine().getStatusCode();
        if (status != HttpStatus.SC_NO_CONTENT && status != HttpStatus.SC_NOT_MODIFIED && status != HttpStatus.SC_RESET_CONTENT) {
            response.addHeader(HTTP.CONTENT_LEN, "0");
        }
    }
}
