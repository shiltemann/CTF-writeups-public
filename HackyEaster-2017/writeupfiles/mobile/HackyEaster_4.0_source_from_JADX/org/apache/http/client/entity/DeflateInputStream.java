package org.apache.http.client.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class DeflateInputStream extends InputStream {
    private InputStream sourceStream;

    static class DeflateStream extends InflaterInputStream {
        private boolean closed;

        public DeflateStream(InputStream in, Inflater inflater) {
            super(in, inflater);
            this.closed = false;
        }

        public void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                this.inf.end();
                super.close();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DeflateInputStream(java.io.InputStream r11) throws java.io.IOException {
        /*
        r10 = this;
        r9 = 1;
        r8 = -1;
        r10.<init>();
        r7 = 6;
        r5 = new byte[r7];
        r6 = new java.io.PushbackInputStream;
        r7 = r5.length;
        r6.<init>(r11, r7);
        r2 = r6.read(r5);
        if (r2 != r8) goto L_0x001c;
    L_0x0014:
        r7 = new java.io.IOException;
        r8 = "Unable to read the response";
        r7.<init>(r8);
        throw r7;
    L_0x001c:
        r0 = new byte[r9];
        r3 = new java.util.zip.Inflater;
        r3.<init>();
    L_0x0023:
        r4 = r3.inflate(r0);	 Catch:{ DataFormatException -> 0x0037 }
        if (r4 != 0) goto L_0x0053;
    L_0x0029:
        r7 = r3.finished();	 Catch:{ DataFormatException -> 0x0037 }
        if (r7 == 0) goto L_0x004d;
    L_0x002f:
        r7 = new java.io.IOException;	 Catch:{ DataFormatException -> 0x0037 }
        r8 = "Unable to read the response";
        r7.<init>(r8);	 Catch:{ DataFormatException -> 0x0037 }
        throw r7;	 Catch:{ DataFormatException -> 0x0037 }
    L_0x0037:
        r1 = move-exception;
        r7 = 0;
        r6.unread(r5, r7, r2);	 Catch:{ all -> 0x005d }
        r7 = new org.apache.http.client.entity.DeflateInputStream$DeflateStream;	 Catch:{ all -> 0x005d }
        r8 = new java.util.zip.Inflater;	 Catch:{ all -> 0x005d }
        r9 = 1;
        r8.<init>(r9);	 Catch:{ all -> 0x005d }
        r7.<init>(r6, r8);	 Catch:{ all -> 0x005d }
        r10.sourceStream = r7;	 Catch:{ all -> 0x005d }
        r3.end();
    L_0x004c:
        return;
    L_0x004d:
        r7 = r3.needsDictionary();	 Catch:{ DataFormatException -> 0x0037 }
        if (r7 == 0) goto L_0x0062;
    L_0x0053:
        if (r4 != r8) goto L_0x006c;
    L_0x0055:
        r7 = new java.io.IOException;	 Catch:{ DataFormatException -> 0x0037 }
        r8 = "Unable to read the response";
        r7.<init>(r8);	 Catch:{ DataFormatException -> 0x0037 }
        throw r7;	 Catch:{ DataFormatException -> 0x0037 }
    L_0x005d:
        r7 = move-exception;
        r3.end();
        throw r7;
    L_0x0062:
        r7 = r3.needsInput();	 Catch:{ DataFormatException -> 0x0037 }
        if (r7 == 0) goto L_0x0023;
    L_0x0068:
        r3.setInput(r5);	 Catch:{ DataFormatException -> 0x0037 }
        goto L_0x0023;
    L_0x006c:
        r7 = 0;
        r6.unread(r5, r7, r2);	 Catch:{ DataFormatException -> 0x0037 }
        r7 = new org.apache.http.client.entity.DeflateInputStream$DeflateStream;	 Catch:{ DataFormatException -> 0x0037 }
        r8 = new java.util.zip.Inflater;	 Catch:{ DataFormatException -> 0x0037 }
        r8.<init>();	 Catch:{ DataFormatException -> 0x0037 }
        r7.<init>(r6, r8);	 Catch:{ DataFormatException -> 0x0037 }
        r10.sourceStream = r7;	 Catch:{ DataFormatException -> 0x0037 }
        r3.end();
        goto L_0x004c;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.client.entity.DeflateInputStream.<init>(java.io.InputStream):void");
    }

    public int read() throws IOException {
        return this.sourceStream.read();
    }

    public int read(byte[] b) throws IOException {
        return this.sourceStream.read(b);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        return this.sourceStream.read(b, off, len);
    }

    public long skip(long n) throws IOException {
        return this.sourceStream.skip(n);
    }

    public int available() throws IOException {
        return this.sourceStream.available();
    }

    public void mark(int readLimit) {
        this.sourceStream.mark(readLimit);
    }

    public void reset() throws IOException {
        this.sourceStream.reset();
    }

    public boolean markSupported() {
        return this.sourceStream.markSupported();
    }

    public void close() throws IOException {
        this.sourceStream.close();
    }
}
