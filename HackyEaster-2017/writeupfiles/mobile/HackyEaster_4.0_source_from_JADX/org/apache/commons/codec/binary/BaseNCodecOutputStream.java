package org.apache.commons.codec.binary;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BaseNCodecOutputStream extends FilterOutputStream {
    private final BaseNCodec baseNCodec;
    private final Context context;
    private final boolean doEncode;
    private final byte[] singleByte;

    public BaseNCodecOutputStream(OutputStream out, BaseNCodec basedCodec, boolean doEncode) {
        super(out);
        this.singleByte = new byte[1];
        this.context = new Context();
        this.baseNCodec = basedCodec;
        this.doEncode = doEncode;
    }

    public void write(int i) throws IOException {
        this.singleByte[0] = (byte) i;
        write(this.singleByte, 0, 1);
    }

    public void write(byte[] b, int offset, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (offset < 0 || len < 0) {
            throw new IndexOutOfBoundsException();
        } else if (offset > b.length || offset + len > b.length) {
            throw new IndexOutOfBoundsException();
        } else if (len > 0) {
            if (this.doEncode) {
                this.baseNCodec.encode(b, offset, len, this.context);
            } else {
                this.baseNCodec.decode(b, offset, len, this.context);
            }
            flush(false);
        }
    }

    private void flush(boolean propagate) throws IOException {
        int avail = this.baseNCodec.available(this.context);
        if (avail > 0) {
            byte[] buf = new byte[avail];
            int c = this.baseNCodec.readResults(buf, 0, avail, this.context);
            if (c > 0) {
                this.out.write(buf, 0, c);
            }
        }
        if (propagate) {
            this.out.flush();
        }
    }

    public void flush() throws IOException {
        flush(true);
    }

    public void close() throws IOException {
        if (this.doEncode) {
            this.baseNCodec.encode(this.singleByte, 0, -1, this.context);
        } else {
            this.baseNCodec.decode(this.singleByte, 0, -1, this.context);
        }
        flush();
        this.out.close();
    }
}
