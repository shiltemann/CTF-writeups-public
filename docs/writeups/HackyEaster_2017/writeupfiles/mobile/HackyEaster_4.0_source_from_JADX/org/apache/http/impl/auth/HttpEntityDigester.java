package org.apache.http.impl.auth;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

class HttpEntityDigester extends OutputStream {
    private boolean closed;
    private byte[] digest;
    private final MessageDigest digester;

    HttpEntityDigester(MessageDigest digester) {
        this.digester = digester;
        this.digester.reset();
    }

    public void write(int b) throws IOException {
        if (this.closed) {
            throw new IOException("Stream has been already closed");
        }
        this.digester.update((byte) b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        if (this.closed) {
            throw new IOException("Stream has been already closed");
        }
        this.digester.update(b, off, len);
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.digest = this.digester.digest();
            super.close();
        }
    }

    public byte[] getDigest() {
        return this.digest;
    }
}
