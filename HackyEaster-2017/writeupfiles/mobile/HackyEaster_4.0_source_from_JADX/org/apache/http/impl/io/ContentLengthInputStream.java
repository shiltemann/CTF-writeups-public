package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.ConnectionClosedException;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.Args;

@NotThreadSafe
public class ContentLengthInputStream extends InputStream {
    private static final int BUFFER_SIZE = 2048;
    private boolean closed;
    private final long contentLength;
    private SessionInputBuffer in;
    private long pos;

    public ContentLengthInputStream(SessionInputBuffer in, long contentLength) {
        this.pos = 0;
        this.closed = false;
        this.in = null;
        this.in = (SessionInputBuffer) Args.notNull(in, "Session input buffer");
        this.contentLength = Args.notNegative(contentLength, "Content length");
    }

    public void close() throws IOException {
        if (!this.closed) {
            try {
                if (this.pos < this.contentLength) {
                    do {
                    } while (read(new byte[BUFFER_SIZE]) >= 0);
                }
                this.closed = true;
            } catch (Throwable th) {
                this.closed = true;
            }
        }
    }

    public int available() throws IOException {
        if (this.in instanceof BufferInfo) {
            return Math.min(((BufferInfo) this.in).length(), (int) (this.contentLength - this.pos));
        }
        return 0;
    }

    public int read() throws IOException {
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        } else if (this.pos >= this.contentLength) {
            return -1;
        } else {
            int b = this.in.read();
            if (b != -1) {
                this.pos++;
                return b;
            } else if (this.pos >= this.contentLength) {
                return b;
            } else {
                throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: " + this.contentLength + "; received: " + this.pos);
            }
        }
    }

    public int read(byte[] b, int off, int len) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        } else if (this.pos >= this.contentLength) {
            return -1;
        } else {
            int chunk = len;
            if (this.pos + ((long) len) > this.contentLength) {
                chunk = (int) (this.contentLength - this.pos);
            }
            int count = this.in.read(b, off, chunk);
            if (count == -1 && this.pos < this.contentLength) {
                throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: " + this.contentLength + "; received: " + this.pos);
            } else if (count <= 0) {
                return count;
            } else {
                this.pos += (long) count;
                return count;
            }
        }
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public long skip(long n) throws IOException {
        if (n <= 0) {
            return 0;
        }
        byte[] buffer = new byte[BUFFER_SIZE];
        long remaining = Math.min(n, this.contentLength - this.pos);
        long count = 0;
        while (remaining > 0) {
            int l = read(buffer, 0, (int) Math.min(2048, remaining));
            if (l == -1) {
                return count;
            }
            count += (long) l;
            remaining -= (long) l;
        }
        return count;
    }
}
