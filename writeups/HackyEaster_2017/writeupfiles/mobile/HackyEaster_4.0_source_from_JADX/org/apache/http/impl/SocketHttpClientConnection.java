package org.apache.http.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import org.apache.http.HttpInetConnection;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.impl.io.SocketInputBuffer;
import org.apache.http.impl.io.SocketOutputBuffer;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@NotThreadSafe
@Deprecated
public class SocketHttpClientConnection extends AbstractHttpClientConnection implements HttpInetConnection {
    private volatile boolean open;
    private volatile Socket socket;

    public SocketHttpClientConnection() {
        this.socket = null;
    }

    protected void assertNotOpen() {
        Asserts.check(!this.open, "Connection is already open");
    }

    protected void assertOpen() {
        Asserts.check(this.open, "Connection is not open");
    }

    protected SessionInputBuffer createSessionInputBuffer(Socket socket, int buffersize, HttpParams params) throws IOException {
        return new SocketInputBuffer(socket, buffersize, params);
    }

    protected SessionOutputBuffer createSessionOutputBuffer(Socket socket, int buffersize, HttpParams params) throws IOException {
        return new SocketOutputBuffer(socket, buffersize, params);
    }

    protected void bind(Socket socket, HttpParams params) throws IOException {
        Args.notNull(socket, "Socket");
        Args.notNull(params, "HTTP parameters");
        this.socket = socket;
        int buffersize = params.getIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, -1);
        init(createSessionInputBuffer(socket, buffersize, params), createSessionOutputBuffer(socket, buffersize, params), params);
        this.open = true;
    }

    public boolean isOpen() {
        return this.open;
    }

    protected Socket getSocket() {
        return this.socket;
    }

    public InetAddress getLocalAddress() {
        if (this.socket != null) {
            return this.socket.getLocalAddress();
        }
        return null;
    }

    public int getLocalPort() {
        if (this.socket != null) {
            return this.socket.getLocalPort();
        }
        return -1;
    }

    public InetAddress getRemoteAddress() {
        if (this.socket != null) {
            return this.socket.getInetAddress();
        }
        return null;
    }

    public int getRemotePort() {
        if (this.socket != null) {
            return this.socket.getPort();
        }
        return -1;
    }

    public void setSocketTimeout(int timeout) {
        assertOpen();
        if (this.socket != null) {
            try {
                this.socket.setSoTimeout(timeout);
            } catch (SocketException e) {
            }
        }
    }

    public int getSocketTimeout() {
        int i = -1;
        if (this.socket != null) {
            try {
                i = this.socket.getSoTimeout();
            } catch (SocketException e) {
            }
        }
        return i;
    }

    public void shutdown() throws IOException {
        this.open = false;
        Socket tmpsocket = this.socket;
        if (tmpsocket != null) {
            tmpsocket.close();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() throws java.io.IOException {
        /*
        r2 = this;
        r1 = r2.open;
        if (r1 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r1 = 0;
        r2.open = r1;
        r0 = r2.socket;
        r2.doFlush();	 Catch:{ all -> 0x0017 }
        r0.shutdownOutput();	 Catch:{ IOException -> 0x001c, UnsupportedOperationException -> 0x0020 }
    L_0x0010:
        r0.shutdownInput();	 Catch:{ IOException -> 0x001e, UnsupportedOperationException -> 0x0020 }
    L_0x0013:
        r0.close();
        goto L_0x0004;
    L_0x0017:
        r1 = move-exception;
        r0.close();
        throw r1;
    L_0x001c:
        r1 = move-exception;
        goto L_0x0010;
    L_0x001e:
        r1 = move-exception;
        goto L_0x0013;
    L_0x0020:
        r1 = move-exception;
        goto L_0x0013;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.SocketHttpClientConnection.close():void");
    }

    private static void formatAddress(StringBuilder buffer, SocketAddress socketAddress) {
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress addr = (InetSocketAddress) socketAddress;
            buffer.append(addr.getAddress() != null ? addr.getAddress().getHostAddress() : addr.getAddress()).append(':').append(addr.getPort());
            return;
        }
        buffer.append(socketAddress);
    }

    public String toString() {
        if (this.socket == null) {
            return super.toString();
        }
        StringBuilder buffer = new StringBuilder();
        SocketAddress remoteAddress = this.socket.getRemoteSocketAddress();
        SocketAddress localAddress = this.socket.getLocalSocketAddress();
        if (!(remoteAddress == null || localAddress == null)) {
            formatAddress(buffer, localAddress);
            buffer.append("<->");
            formatAddress(buffer, remoteAddress);
        }
        return buffer.toString();
    }
}
