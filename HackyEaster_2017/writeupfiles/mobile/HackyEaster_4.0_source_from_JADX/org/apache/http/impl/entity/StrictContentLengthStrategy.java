package org.apache.http.impl.entity;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.Immutable;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

@Immutable
public class StrictContentLengthStrategy implements ContentLengthStrategy {
    public static final StrictContentLengthStrategy INSTANCE;
    private final int implicitLen;

    static {
        INSTANCE = new StrictContentLengthStrategy();
    }

    public StrictContentLengthStrategy(int implicitLen) {
        this.implicitLen = implicitLen;
    }

    public StrictContentLengthStrategy() {
        this(-1);
    }

    public long determineLength(HttpMessage message) throws HttpException {
        Args.notNull(message, "HTTP message");
        Header transferEncodingHeader = message.getFirstHeader(HTTP.TRANSFER_ENCODING);
        String s;
        if (transferEncodingHeader != null) {
            s = transferEncodingHeader.getValue();
            if (HTTP.CHUNK_CODING.equalsIgnoreCase(s)) {
                if (!message.getProtocolVersion().lessEquals(HttpVersion.HTTP_1_0)) {
                    return -2;
                }
                throw new ProtocolException("Chunked transfer encoding not allowed for " + message.getProtocolVersion());
            } else if (HTTP.IDENTITY_CODING.equalsIgnoreCase(s)) {
                return -1;
            } else {
                throw new ProtocolException("Unsupported transfer encoding: " + s);
            }
        }
        Header contentLengthHeader = message.getFirstHeader(HTTP.CONTENT_LEN);
        if (contentLengthHeader == null) {
            return (long) this.implicitLen;
        }
        s = contentLengthHeader.getValue();
        try {
            long len = Long.parseLong(s);
            if (len >= 0) {
                return len;
            }
            throw new ProtocolException("Negative content length: " + s);
        } catch (NumberFormatException e) {
            throw new ProtocolException("Invalid content length: " + s);
        }
    }
}
