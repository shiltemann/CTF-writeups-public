package org.apache.http.util;

import java.io.Serializable;
import org.apache.http.annotation.NotThreadSafe;

@NotThreadSafe
public final class ByteArrayBuffer implements Serializable {
    private static final long serialVersionUID = 4359112959524048036L;
    private byte[] buffer;
    private int len;

    public ByteArrayBuffer(int capacity) {
        Args.notNegative(capacity, "Buffer capacity");
        this.buffer = new byte[capacity];
    }

    private void expand(int newlen) {
        byte[] newbuffer = new byte[Math.max(this.buffer.length << 1, newlen)];
        System.arraycopy(this.buffer, 0, newbuffer, 0, this.len);
        this.buffer = newbuffer;
    }

    public void append(byte[] b, int off, int len) {
        if (b != null) {
            if (off < 0 || off > b.length || len < 0 || off + len < 0 || off + len > b.length) {
                throw new IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + b.length);
            } else if (len != 0) {
                int newlen = this.len + len;
                if (newlen > this.buffer.length) {
                    expand(newlen);
                }
                System.arraycopy(b, off, this.buffer, this.len, len);
                this.len = newlen;
            }
        }
    }

    public void append(int b) {
        int newlen = this.len + 1;
        if (newlen > this.buffer.length) {
            expand(newlen);
        }
        this.buffer[this.len] = (byte) b;
        this.len = newlen;
    }

    public void append(char[] b, int off, int len) {
        if (b != null) {
            if (off < 0 || off > b.length || len < 0 || off + len < 0 || off + len > b.length) {
                throw new IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + b.length);
            } else if (len != 0) {
                int oldlen = this.len;
                int newlen = oldlen + len;
                if (newlen > this.buffer.length) {
                    expand(newlen);
                }
                int i1 = off;
                for (int i2 = oldlen; i2 < newlen; i2++) {
                    this.buffer[i2] = (byte) b[i1];
                    i1++;
                }
                this.len = newlen;
            }
        }
    }

    public void append(CharArrayBuffer b, int off, int len) {
        if (b != null) {
            append(b.buffer(), off, len);
        }
    }

    public void clear() {
        this.len = 0;
    }

    public byte[] toByteArray() {
        byte[] b = new byte[this.len];
        if (this.len > 0) {
            System.arraycopy(this.buffer, 0, b, 0, this.len);
        }
        return b;
    }

    public int byteAt(int i) {
        return this.buffer[i];
    }

    public int capacity() {
        return this.buffer.length;
    }

    public int length() {
        return this.len;
    }

    public void ensureCapacity(int required) {
        if (required > 0 && required > this.buffer.length - this.len) {
            expand(this.len + required);
        }
    }

    public byte[] buffer() {
        return this.buffer;
    }

    public void setLength(int len) {
        if (len < 0 || len > this.buffer.length) {
            throw new IndexOutOfBoundsException("len: " + len + " < 0 or > buffer len: " + this.buffer.length);
        }
        this.len = len;
    }

    public boolean isEmpty() {
        return this.len == 0;
    }

    public boolean isFull() {
        return this.len == this.buffer.length;
    }

    public int indexOf(byte b, int from, int to) {
        int beginIndex = from;
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        int endIndex = to;
        if (endIndex > this.len) {
            endIndex = this.len;
        }
        if (beginIndex > endIndex) {
            return -1;
        }
        for (int i = beginIndex; i < endIndex; i++) {
            if (this.buffer[i] == b) {
                return i;
            }
        }
        return -1;
    }

    public int indexOf(byte b) {
        return indexOf(b, 0, this.len);
    }
}
