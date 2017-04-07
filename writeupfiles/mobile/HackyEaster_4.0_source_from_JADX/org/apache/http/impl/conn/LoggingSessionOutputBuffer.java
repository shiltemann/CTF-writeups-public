package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.Consts;
import org.apache.http.annotation.Immutable;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.util.CharArrayBuffer;

@Immutable
@Deprecated
public class LoggingSessionOutputBuffer implements SessionOutputBuffer {
    private final String charset;
    private final SessionOutputBuffer out;
    private final Wire wire;

    public LoggingSessionOutputBuffer(SessionOutputBuffer out, Wire wire, String charset) {
        this.out = out;
        this.wire = wire;
        if (charset == null) {
            charset = Consts.ASCII.name();
        }
        this.charset = charset;
    }

    public LoggingSessionOutputBuffer(SessionOutputBuffer out, Wire wire) {
        this(out, wire, null);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        this.out.write(b, off, len);
        if (this.wire.enabled()) {
            this.wire.output(b, off, len);
        }
    }

    public void write(int b) throws IOException {
        this.out.write(b);
        if (this.wire.enabled()) {
            this.wire.output(b);
        }
    }

    public void write(byte[] b) throws IOException {
        this.out.write(b);
        if (this.wire.enabled()) {
            this.wire.output(b);
        }
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public void writeLine(CharArrayBuffer buffer) throws IOException {
        this.out.writeLine(buffer);
        if (this.wire.enabled()) {
            this.wire.output((new String(buffer.buffer(), 0, buffer.length()) + "\r\n").getBytes(this.charset));
        }
    }

    public void writeLine(String s) throws IOException {
        this.out.writeLine(s);
        if (this.wire.enabled()) {
            this.wire.output((s + "\r\n").getBytes(this.charset));
        }
    }

    public HttpTransportMetrics getMetrics() {
        return this.out.getMetrics();
    }
}
