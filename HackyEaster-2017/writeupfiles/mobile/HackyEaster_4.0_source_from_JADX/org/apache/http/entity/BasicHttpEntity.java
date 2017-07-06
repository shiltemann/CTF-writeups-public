package org.apache.http.entity;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.impl.io.EmptyInputStream;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@NotThreadSafe
public class BasicHttpEntity extends AbstractHttpEntity {
    private InputStream content;
    private long length;

    public BasicHttpEntity() {
        this.length = -1;
    }

    public long getContentLength() {
        return this.length;
    }

    public InputStream getContent() throws IllegalStateException {
        Asserts.check(this.content != null, "Content has not been provided");
        return this.content;
    }

    public boolean isRepeatable() {
        return false;
    }

    public void setContentLength(long len) {
        this.length = len;
    }

    public void setContent(InputStream instream) {
        this.content = instream;
    }

    public void writeTo(OutputStream outstream) throws IOException {
        Args.notNull(outstream, "Output stream");
        InputStream instream = getContent();
        try {
            byte[] tmp = new byte[AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD];
            while (true) {
                int l = instream.read(tmp);
                if (l == -1) {
                    break;
                }
                outstream.write(tmp, 0, l);
            }
        } finally {
            instream.close();
        }
    }

    public boolean isStreaming() {
        return (this.content == null || this.content == EmptyInputStream.INSTANCE) ? false : true;
    }
}
