package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

@Deprecated
class SocketFactoryAdaptor implements SocketFactory {
    private final SchemeSocketFactory factory;

    SocketFactoryAdaptor(SchemeSocketFactory factory) {
        this.factory = factory;
    }

    public Socket createSocket() throws IOException {
        return this.factory.createSocket(new BasicHttpParams());
    }

    public Socket connectSocket(Socket socket, String host, int port, InetAddress localAddress, int localPort, HttpParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
        InetSocketAddress local = null;
        if (localAddress != null || localPort > 0) {
            if (localPort <= 0) {
                localPort = 0;
            }
            local = new InetSocketAddress(localAddress, localPort);
        }
        return this.factory.connectSocket(socket, new InetSocketAddress(InetAddress.getByName(host), port), local, params);
    }

    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        return this.factory.isSecure(socket);
    }

    public SchemeSocketFactory getFactory() {
        return this.factory;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof SocketFactoryAdaptor) {
            return this.factory.equals(((SocketFactoryAdaptor) obj).factory);
        }
        return this.factory.equals(obj);
    }

    public int hashCode() {
        return this.factory.hashCode();
    }
}
