package org.apache.http.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Immutable
@Deprecated
public final class MultihomePlainSocketFactory implements SocketFactory {
    private static final MultihomePlainSocketFactory DEFAULT_FACTORY;

    static {
        DEFAULT_FACTORY = new MultihomePlainSocketFactory();
    }

    public static MultihomePlainSocketFactory getSocketFactory() {
        return DEFAULT_FACTORY;
    }

    private MultihomePlainSocketFactory() {
    }

    public Socket createSocket() {
        return new Socket();
    }

    public Socket connectSocket(Socket socket, String host, int port, InetAddress localAddress, int localPort, HttpParams params) throws IOException {
        Args.notNull(host, "Target host");
        Args.notNull(params, "HTTP parameters");
        Socket sock = socket;
        if (sock == null) {
            sock = createSocket();
        }
        if (localAddress != null || localPort > 0) {
            if (localPort <= 0) {
                localPort = 0;
            }
            sock.bind(new InetSocketAddress(localAddress, localPort));
        }
        int timeout = HttpConnectionParams.getConnectionTimeout(params);
        InetAddress[] inetadrs = InetAddress.getAllByName(host);
        List<InetAddress> addresses = new ArrayList(inetadrs.length);
        addresses.addAll(Arrays.asList(inetadrs));
        Collections.shuffle(addresses);
        IOException lastEx = null;
        for (InetAddress remoteAddress : addresses) {
            try {
                sock.connect(new InetSocketAddress(remoteAddress, port), timeout);
                break;
            } catch (SocketTimeoutException e) {
                throw new ConnectTimeoutException("Connect to " + remoteAddress + " timed out");
            } catch (IOException ex) {
                sock = new Socket();
                lastEx = ex;
            }
        }
        if (lastEx == null) {
            return sock;
        }
        throw lastEx;
    }

    public final boolean isSecure(Socket sock) throws IllegalArgumentException {
        boolean z;
        Args.notNull(sock, "Socket");
        if (sock.isClosed()) {
            z = false;
        } else {
            z = true;
        }
        Asserts.check(z, "Socket is closed");
        return false;
    }
}
