package org.apache.commons.codec;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.protocol.HTTP;

public class Charsets {
    public static final Charset ISO_8859_1;
    public static final Charset US_ASCII;
    public static final Charset UTF_16;
    public static final Charset UTF_16BE;
    public static final Charset UTF_16LE;
    public static final Charset UTF_8;

    public static Charset toCharset(Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    public static Charset toCharset(String charset) {
        return charset == null ? Charset.defaultCharset() : Charset.forName(charset);
    }

    static {
        ISO_8859_1 = Charset.forName(HTTP.ISO_8859_1);
        US_ASCII = Charset.forName(HTTP.US_ASCII);
        UTF_16 = Charset.forName(HTTP.UTF_16);
        UTF_16BE = Charset.forName(CharEncoding.UTF_16BE);
        UTF_16LE = Charset.forName(CharEncoding.UTF_16LE);
        UTF_8 = Charset.forName(Hex.DEFAULT_CHARSET_NAME);
    }
}
