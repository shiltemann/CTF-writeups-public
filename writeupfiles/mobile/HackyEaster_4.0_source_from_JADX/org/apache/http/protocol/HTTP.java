package org.apache.http.protocol;

import java.nio.charset.Charset;
import org.apache.http.Consts;
import org.apache.http.message.TokenParser;

public final class HTTP {
    @Deprecated
    public static final String ASCII = "ASCII";
    @Deprecated
    public static final String CHARSET_PARAM = "; charset=";
    public static final String CHUNK_CODING = "chunked";
    public static final String CONN_CLOSE = "Close";
    public static final String CONN_DIRECTIVE = "Connection";
    public static final String CONN_KEEP_ALIVE = "Keep-Alive";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_LEN = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final int CR = 13;
    public static final String DATE_HEADER = "Date";
    @Deprecated
    public static final String DEFAULT_CONTENT_CHARSET = "ISO-8859-1";
    @Deprecated
    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    @Deprecated
    public static final String DEFAULT_PROTOCOL_CHARSET = "US-ASCII";
    public static final Charset DEF_CONTENT_CHARSET;
    public static final Charset DEF_PROTOCOL_CHARSET;
    public static final String EXPECT_CONTINUE = "100-continue";
    public static final String EXPECT_DIRECTIVE = "Expect";
    public static final int HT = 9;
    public static final String IDENTITY_CODING = "identity";
    @Deprecated
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final int LF = 10;
    @Deprecated
    public static final String OCTET_STREAM_TYPE = "application/octet-stream";
    @Deprecated
    public static final String PLAIN_TEXT_TYPE = "text/plain";
    public static final String SERVER_HEADER = "Server";
    public static final int SP = 32;
    public static final String TARGET_HOST = "Host";
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";
    public static final String USER_AGENT = "User-Agent";
    @Deprecated
    public static final String US_ASCII = "US-ASCII";
    @Deprecated
    public static final String UTF_16 = "UTF-16";
    @Deprecated
    public static final String UTF_8 = "UTF-8";

    static {
        DEF_CONTENT_CHARSET = Consts.ISO_8859_1;
        DEF_PROTOCOL_CHARSET = Consts.ASCII;
    }

    public static boolean isWhitespace(char ch) {
        return ch == TokenParser.SP || ch == '\t' || ch == TokenParser.CR || ch == '\n';
    }

    private HTTP() {
    }
}
