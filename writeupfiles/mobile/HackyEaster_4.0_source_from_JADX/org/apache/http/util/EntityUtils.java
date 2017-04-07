package org.apache.http.util;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;

public final class EntityUtils {
    private EntityUtils() {
    }

    public static void consumeQuietly(HttpEntity entity) {
        try {
            consume(entity);
        } catch (IOException e) {
        }
    }

    public static void consume(HttpEntity entity) throws IOException {
        if (entity != null && entity.isStreaming()) {
            InputStream instream = entity.getContent();
            if (instream != null) {
                instream.close();
            }
        }
    }

    public static void updateEntity(HttpResponse response, HttpEntity entity) throws IOException {
        Args.notNull(response, "Response");
        consume(response.getEntity());
        response.setEntity(entity);
    }

    public static byte[] toByteArray(HttpEntity entity) throws IOException {
        boolean z = false;
        Args.notNull(entity, "Entity");
        InputStream instream = entity.getContent();
        if (instream == null) {
            return null;
        }
        try {
            if (entity.getContentLength() <= 2147483647L) {
                z = true;
            }
            Args.check(z, "HTTP entity too large to be buffered in memory");
            int i = (int) entity.getContentLength();
            if (i < 0) {
                i = AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD;
            }
            ByteArrayBuffer buffer = new ByteArrayBuffer(i);
            byte[] tmp = new byte[AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD];
            while (true) {
                int l = instream.read(tmp);
                if (l == -1) {
                    break;
                }
                buffer.append(tmp, 0, l);
            }
            byte[] toByteArray = buffer.toByteArray();
            return toByteArray;
        } finally {
            instream.close();
        }
    }

    @Deprecated
    public static String getContentCharSet(HttpEntity entity) throws ParseException {
        Args.notNull(entity, "Entity");
        if (entity.getContentType() == null) {
            return null;
        }
        HeaderElement[] values = entity.getContentType().getElements();
        if (values.length <= 0) {
            return null;
        }
        NameValuePair param = values[0].getParameterByName("charset");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    @Deprecated
    public static String getContentMimeType(HttpEntity entity) throws ParseException {
        Args.notNull(entity, "Entity");
        if (entity.getContentType() == null) {
            return null;
        }
        HeaderElement[] values = entity.getContentType().getElements();
        if (values.length > 0) {
            return values[0].getName();
        }
        return null;
    }

    public static String toString(HttpEntity entity, Charset defaultCharset) throws IOException, ParseException {
        boolean z = false;
        Args.notNull(entity, "Entity");
        InputStream instream = entity.getContent();
        if (instream == null) {
            return null;
        }
        int i;
        Charset charset;
        try {
            if (entity.getContentLength() <= 2147483647L) {
                z = true;
            }
            Args.check(z, "HTTP entity too large to be buffered in memory");
            i = (int) entity.getContentLength();
            if (i < 0) {
                i = AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD;
            }
            charset = null;
            ContentType contentType = ContentType.get(entity);
            if (contentType != null) {
                charset = contentType.getCharset();
            }
        } catch (UnsupportedCharsetException ex) {
            if (defaultCharset == null) {
                throw new UnsupportedEncodingException(ex.getMessage());
            }
        } catch (Throwable th) {
            instream.close();
        }
        if (charset == null) {
            charset = defaultCharset;
        }
        if (charset == null) {
            charset = HTTP.DEF_CONTENT_CHARSET;
        }
        Reader reader = new InputStreamReader(instream, charset);
        CharArrayBuffer buffer = new CharArrayBuffer(i);
        char[] tmp = new char[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            int l = reader.read(tmp);
            if (l != -1) {
                buffer.append(tmp, 0, l);
            } else {
                String charArrayBuffer = buffer.toString();
                instream.close();
                return charArrayBuffer;
            }
        }
    }

    public static String toString(HttpEntity entity, String defaultCharset) throws IOException, ParseException {
        return toString(entity, defaultCharset != null ? Charset.forName(defaultCharset) : null);
    }

    public static String toString(HttpEntity entity) throws IOException, ParseException {
        return toString(entity, (Charset) null);
    }
}
