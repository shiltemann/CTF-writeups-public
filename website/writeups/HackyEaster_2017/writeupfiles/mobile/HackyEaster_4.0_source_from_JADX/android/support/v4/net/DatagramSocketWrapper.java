package android.support.v4.net;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;

class DatagramSocketWrapper extends Socket {

    private static class DatagramSocketImplWrapper extends SocketImpl {
        public DatagramSocketImplWrapper(DatagramSocket socket, FileDescriptor fd) {
            this.localport = socket.getLocalPort();
            this.fd = fd;
        }

        protected void accept(SocketImpl newSocket) throws IOException {
            throw new UnsupportedOperationException();
        }

        protected int available() throws IOException {
            throw new UnsupportedOperationException();
        }

        protected void bind(InetAddress address, int port) throws IOException {
            throw new UnsupportedOperationException();
        }

        protected void close() throws IOException {
            throw new UnsupportedOperationException();
        }

        protected void connect(String host, int port) throws IOException {
            throw new UnsupportedOperationException();
        }

        protected void connect(InetAddress address, int port) throws IOException {
            throw new UnsupportedOperationException();
        }

        protected void create(boolean isStreaming) throws IOException {
            throw new UnsupportedOperationException();
        }

        protected InputStream getInputStream() throws IOException {
            throw new UnsupportedOperationException();
        }

        protected OutputStream getOutputStream() throws IOException {
            throw new UnsupportedOperationException();
        }

        protected void listen(int backlog) throws IOException {
            throw new UnsupportedOperationException();
        }

        protected void connect(SocketAddress remoteAddr, int timeout) throws IOException {
            throw new UnsupportedOperationException();
        }

        protected void sendUrgentData(int value) throws IOException {
            throw new UnsupportedOperationException();
        }

        public Object getOption(int optID) throws SocketException {
            throw new UnsupportedOperationException();
        }

        public void setOption(int optID, Object val) throws SocketException {
            throw new UnsupportedOperationException();
        }
    }

    public DatagramSocketWrapper(DatagramSocket socket, FileDescriptor fd) throws SocketException {
        super(new DatagramSocketImplWrapper(socket, fd));
    }
}
