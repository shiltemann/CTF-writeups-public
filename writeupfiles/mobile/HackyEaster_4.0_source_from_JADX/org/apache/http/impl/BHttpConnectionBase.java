package org.apache.http.impl;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.http.ConnectionClosedException;
import org.apache.http.Header;
import org.apache.http.HttpConnection;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpInetConnection;
import org.apache.http.HttpMessage;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.entity.LaxContentLengthStrategy;
import org.apache.http.impl.entity.StrictContentLengthStrategy;
import org.apache.http.impl.io.ChunkedInputStream;
import org.apache.http.impl.io.ChunkedOutputStream;
import org.apache.http.impl.io.ContentLengthInputStream;
import org.apache.http.impl.io.ContentLengthOutputStream;
import org.apache.http.impl.io.EmptyInputStream;
import org.apache.http.impl.io.HttpTransportMetricsImpl;
import org.apache.http.impl.io.IdentityInputStream;
import org.apache.http.impl.io.IdentityOutputStream;
import org.apache.http.impl.io.SessionInputBufferImpl;
import org.apache.http.impl.io.SessionOutputBufferImpl;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.NetUtils;

@NotThreadSafe
public class BHttpConnectionBase implements HttpConnection, HttpInetConnection {
    private final HttpConnectionMetricsImpl connMetrics;
    private final SessionInputBufferImpl inbuffer;
    private final ContentLengthStrategy incomingContentStrategy;
    private final MessageConstraints messageConstraints;
    private final SessionOutputBufferImpl outbuffer;
    private final ContentLengthStrategy outgoingContentStrategy;
    private final AtomicReference<Socket> socketHolder;

    protected BHttpConnectionBase(int buffersize, int fragmentSizeHint, CharsetDecoder chardecoder, CharsetEncoder charencoder, MessageConstraints messageConstraints, ContentLengthStrategy incomingContentStrategy, ContentLengthStrategy outgoingContentStrategy) {
        Args.positive(buffersize, "Buffer size");
        HttpTransportMetricsImpl inTransportMetrics = new HttpTransportMetricsImpl();
        HttpTransportMetricsImpl outTransportMetrics = new HttpTransportMetricsImpl();
        this.inbuffer = new SessionInputBufferImpl(inTransportMetrics, buffersize, -1, messageConstraints != null ? messageConstraints : MessageConstraints.DEFAULT, chardecoder);
        this.outbuffer = new SessionOutputBufferImpl(outTransportMetrics, buffersize, fragmentSizeHint, charencoder);
        this.messageConstraints = messageConstraints;
        this.connMetrics = new HttpConnectionMetricsImpl(inTransportMetrics, outTransportMetrics);
        if (incomingContentStrategy == null) {
            incomingContentStrategy = LaxContentLengthStrategy.INSTANCE;
        }
        this.incomingContentStrategy = incomingContentStrategy;
        if (outgoingContentStrategy == null) {
            outgoingContentStrategy = StrictContentLengthStrategy.INSTANCE;
        }
        this.outgoingContentStrategy = outgoingContentStrategy;
        this.socketHolder = new AtomicReference();
    }

    protected void ensureOpen() throws IOException {
        Socket socket = (Socket) this.socketHolder.get();
        if (socket == null) {
            throw new ConnectionClosedException("Connection is closed");
        }
        if (!this.inbuffer.isBound()) {
            this.inbuffer.bind(getSocketInputStream(socket));
        }
        if (!this.outbuffer.isBound()) {
            this.outbuffer.bind(getSocketOutputStream(socket));
        }
    }

    protected InputStream getSocketInputStream(Socket socket) throws IOException {
        return socket.getInputStream();
    }

    protected OutputStream getSocketOutputStream(Socket socket) throws IOException {
        return socket.getOutputStream();
    }

    protected void bind(Socket socket) throws IOException {
        Args.notNull(socket, "Socket");
        this.socketHolder.set(socket);
        this.inbuffer.bind(null);
        this.outbuffer.bind(null);
    }

    protected SessionInputBuffer getSessionInputBuffer() {
        return this.inbuffer;
    }

    protected SessionOutputBuffer getSessionOutputBuffer() {
        return this.outbuffer;
    }

    protected void doFlush() throws IOException {
        this.outbuffer.flush();
    }

    public boolean isOpen() {
        return this.socketHolder.get() != null;
    }

    protected Socket getSocket() {
        return (Socket) this.socketHolder.get();
    }

    protected OutputStream createOutputStream(long len, SessionOutputBuffer outbuffer) {
        if (len == -2) {
            return new ChunkedOutputStream((int) AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, outbuffer);
        }
        if (len == -1) {
            return new IdentityOutputStream(outbuffer);
        }
        return new ContentLengthOutputStream(outbuffer, len);
    }

    protected OutputStream prepareOutput(HttpMessage message) throws HttpException {
        return createOutputStream(this.outgoingContentStrategy.determineLength(message), this.outbuffer);
    }

    protected InputStream createInputStream(long len, SessionInputBuffer inbuffer) {
        if (len == -2) {
            return new ChunkedInputStream(inbuffer, this.messageConstraints);
        }
        if (len == -1) {
            return new IdentityInputStream(inbuffer);
        }
        if (len == 0) {
            return EmptyInputStream.INSTANCE;
        }
        return new ContentLengthInputStream(inbuffer, len);
    }

    protected HttpEntity prepareInput(HttpMessage message) throws HttpException {
        BasicHttpEntity entity = new BasicHttpEntity();
        long len = this.incomingContentStrategy.determineLength(message);
        InputStream instream = createInputStream(len, this.inbuffer);
        if (len == -2) {
            entity.setChunked(true);
            entity.setContentLength(-1);
            entity.setContent(instream);
        } else if (len == -1) {
            entity.setChunked(false);
            entity.setContentLength(-1);
            entity.setContent(instream);
        } else {
            entity.setChunked(false);
            entity.setContentLength(len);
            entity.setContent(instream);
        }
        Header contentTypeHeader = message.getFirstHeader(HTTP.CONTENT_TYPE);
        if (contentTypeHeader != null) {
            entity.setContentType(contentTypeHeader);
        }
        Header contentEncodingHeader = message.getFirstHeader(HTTP.CONTENT_ENCODING);
        if (contentEncodingHeader != null) {
            entity.setContentEncoding(contentEncodingHeader);
        }
        return entity;
    }

    public InetAddress getLocalAddress() {
        Socket socket = (Socket) this.socketHolder.get();
        return socket != null ? socket.getLocalAddress() : null;
    }

    public int getLocalPort() {
        Socket socket = (Socket) this.socketHolder.get();
        return socket != null ? socket.getLocalPort() : -1;
    }

    public InetAddress getRemoteAddress() {
        Socket socket = (Socket) this.socketHolder.get();
        return socket != null ? socket.getInetAddress() : null;
    }

    public int getRemotePort() {
        Socket socket = (Socket) this.socketHolder.get();
        return socket != null ? socket.getPort() : -1;
    }

    public void setSocketTimeout(int timeout) {
        Socket socket = (Socket) this.socketHolder.get();
        if (socket != null) {
            try {
                socket.setSoTimeout(timeout);
            } catch (SocketException e) {
            }
        }
    }

    public int getSocketTimeout() {
        int i = -1;
        Socket socket = (Socket) this.socketHolder.get();
        if (socket != null) {
            try {
                i = socket.getSoTimeout();
            } catch (SocketException e) {
            }
        }
        return i;
    }

    public void shutdown() throws IOException {
        Socket socket = (Socket) this.socketHolder.getAndSet(null);
        if (socket != null) {
            try {
                socket.setSoLinger(true, 0);
            } catch (IOException e) {
            } finally {
                socket.close();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() throws java.io.IOException {
        /*
        r3 = this;
        r1 = r3.socketHolder;
        r2 = 0;
        r0 = r1.getAndSet(r2);
        r0 = (java.net.Socket) r0;
        if (r0 == 0) goto L_0x001e;
    L_0x000b:
        r1 = r3.inbuffer;	 Catch:{ all -> 0x001f }
        r1.clear();	 Catch:{ all -> 0x001f }
        r1 = r3.outbuffer;	 Catch:{ all -> 0x001f }
        r1.flush();	 Catch:{ all -> 0x001f }
        r0.shutdownOutput();	 Catch:{ IOException -> 0x0024, UnsupportedOperationException -> 0x0028 }
    L_0x0018:
        r0.shutdownInput();	 Catch:{ IOException -> 0x0026, UnsupportedOperationException -> 0x0028 }
    L_0x001b:
        r0.close();
    L_0x001e:
        return;
    L_0x001f:
        r1 = move-exception;
        r0.close();
        throw r1;
    L_0x0024:
        r1 = move-exception;
        goto L_0x0018;
    L_0x0026:
        r1 = move-exception;
        goto L_0x001b;
    L_0x0028:
        r1 = move-exception;
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.BHttpConnectionBase.close():void");
    }

    private int fillInputBuffer(int timeout) throws IOException {
        Socket socket = (Socket) this.socketHolder.get();
        int oldtimeout = socket.getSoTimeout();
        try {
            socket.setSoTimeout(timeout);
            int fillBuffer = this.inbuffer.fillBuffer();
            return fillBuffer;
        } finally {
            socket.setSoTimeout(oldtimeout);
        }
    }

    protected boolean awaitInput(int timeout) throws IOException {
        if (this.inbuffer.hasBufferedData()) {
            return true;
        }
        fillInputBuffer(timeout);
        return this.inbuffer.hasBufferedData();
    }

    public boolean isStale() {
        if (!isOpen()) {
            return true;
        }
        try {
            if (fillInputBuffer(1) >= 0) {
                return false;
            }
            return true;
        } catch (SocketTimeoutException e) {
            return false;
        } catch (IOException e2) {
            return true;
        }
    }

    protected void incrementRequestCount() {
        this.connMetrics.incrementRequestCount();
    }

    protected void incrementResponseCount() {
        this.connMetrics.incrementResponseCount();
    }

    public HttpConnectionMetrics getMetrics() {
        return this.connMetrics;
    }

    public String toString() {
        Socket socket = (Socket) this.socketHolder.get();
        if (socket == null) {
            return "[Not bound]";
        }
        StringBuilder buffer = new StringBuilder();
        SocketAddress remoteAddress = socket.getRemoteSocketAddress();
        SocketAddress localAddress = socket.getLocalSocketAddress();
        if (!(remoteAddress == null || localAddress == null)) {
            NetUtils.formatAddress(buffer, localAddress);
            buffer.append("<->");
            NetUtils.formatAddress(buffer, remoteAddress);
        }
        return buffer.toString();
    }
}
