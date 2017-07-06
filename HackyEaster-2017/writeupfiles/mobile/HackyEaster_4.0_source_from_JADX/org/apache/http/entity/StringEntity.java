package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

@NotThreadSafe
public class StringEntity extends AbstractHttpEntity implements Cloneable {
    protected final byte[] content;

    public StringEntity(String string, ContentType contentType) throws UnsupportedCharsetException {
        Args.notNull(string, "Source string");
        Charset charset = contentType != null ? contentType.getCharset() : null;
        if (charset == null) {
            charset = HTTP.DEF_CONTENT_CHARSET;
        }
        this.content = string.getBytes(charset);
        if (contentType != null) {
            setContentType(contentType.toString());
        }
    }

    @Deprecated
    public StringEntity(String string, String mimeType, String charset) throws UnsupportedEncodingException {
        Args.notNull(string, "Source string");
        String mt = mimeType != null ? mimeType : HTTP.PLAIN_TEXT_TYPE;
        String cs = charset != null ? charset : HTTP.ISO_8859_1;
        this.content = string.getBytes(cs);
        setContentType(mt + HTTP.CHARSET_PARAM + cs);
    }

    public StringEntity(String string, String charset) throws UnsupportedCharsetException {
        this(string, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), charset));
    }

    public StringEntity(String string, Charset charset) {
        this(string, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), charset));
    }

    public StringEntity(String string) throws UnsupportedEncodingException {
        this(string, ContentType.DEFAULT_TEXT);
    }

    public boolean isRepeatable() {
        return true;
    }

    public long getContentLength() {
        return (long) this.content.length;
    }

    public InputStream getContent() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    public void writeTo(OutputStream outstream) throws IOException {
        Args.notNull(outstream, "Output stream");
        outstream.write(this.content);
        outstream.flush();
    }

    public boolean isStreaming() {
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
