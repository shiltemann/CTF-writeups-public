package org.apache.http.impl.io;

import java.io.InputStream;

public final class EmptyInputStream extends InputStream {
    public static final EmptyInputStream INSTANCE;

    static {
        INSTANCE = new EmptyInputStream();
    }

    private EmptyInputStream() {
    }

    public int available() {
        return 0;
    }

    public void close() {
    }

    public void mark(int readLimit) {
    }

    public boolean markSupported() {
        return true;
    }

    public int read() {
        return -1;
    }

    public int read(byte[] buf) {
        return -1;
    }

    public int read(byte[] buf, int off, int len) {
        return -1;
    }

    public void reset() {
    }

    public long skip(long n) {
        return 0;
    }
}
