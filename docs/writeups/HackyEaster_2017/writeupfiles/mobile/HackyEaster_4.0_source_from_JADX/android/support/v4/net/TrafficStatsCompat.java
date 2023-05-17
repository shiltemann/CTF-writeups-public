package android.support.v4.net;

import android.os.Build.VERSION;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public final class TrafficStatsCompat {
    private static final TrafficStatsCompatImpl IMPL;

    interface TrafficStatsCompatImpl {
        void clearThreadStatsTag();

        int getThreadStatsTag();

        void incrementOperationCount(int i);

        void incrementOperationCount(int i, int i2);

        void setThreadStatsTag(int i);

        void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException;

        void tagSocket(Socket socket) throws SocketException;

        void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException;

        void untagSocket(Socket socket) throws SocketException;
    }

    static class BaseTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
        private ThreadLocal<SocketTags> mThreadSocketTags;

        /* renamed from: android.support.v4.net.TrafficStatsCompat.BaseTrafficStatsCompatImpl.1 */
        class C00081 extends ThreadLocal<SocketTags> {
            C00081() {
            }

            protected SocketTags initialValue() {
                return new SocketTags();
            }
        }

        private static class SocketTags {
            public int statsTag;

            SocketTags() {
                this.statsTag = -1;
            }
        }

        BaseTrafficStatsCompatImpl() {
            this.mThreadSocketTags = new C00081();
        }

        public void clearThreadStatsTag() {
            ((SocketTags) this.mThreadSocketTags.get()).statsTag = -1;
        }

        public int getThreadStatsTag() {
            return ((SocketTags) this.mThreadSocketTags.get()).statsTag;
        }

        public void incrementOperationCount(int operationCount) {
        }

        public void incrementOperationCount(int tag, int operationCount) {
        }

        public void setThreadStatsTag(int tag) {
            ((SocketTags) this.mThreadSocketTags.get()).statsTag = tag;
        }

        public void tagSocket(Socket socket) {
        }

        public void untagSocket(Socket socket) {
        }

        public void tagDatagramSocket(DatagramSocket socket) {
        }

        public void untagDatagramSocket(DatagramSocket socket) {
        }
    }

    static class IcsTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
        IcsTrafficStatsCompatImpl() {
        }

        public void clearThreadStatsTag() {
            TrafficStatsCompatIcs.clearThreadStatsTag();
        }

        public int getThreadStatsTag() {
            return TrafficStatsCompatIcs.getThreadStatsTag();
        }

        public void incrementOperationCount(int operationCount) {
            TrafficStatsCompatIcs.incrementOperationCount(operationCount);
        }

        public void incrementOperationCount(int tag, int operationCount) {
            TrafficStatsCompatIcs.incrementOperationCount(tag, operationCount);
        }

        public void setThreadStatsTag(int tag) {
            TrafficStatsCompatIcs.setThreadStatsTag(tag);
        }

        public void tagSocket(Socket socket) throws SocketException {
            TrafficStatsCompatIcs.tagSocket(socket);
        }

        public void untagSocket(Socket socket) throws SocketException {
            TrafficStatsCompatIcs.untagSocket(socket);
        }

        public void tagDatagramSocket(DatagramSocket socket) throws SocketException {
            TrafficStatsCompatIcs.tagDatagramSocket(socket);
        }

        public void untagDatagramSocket(DatagramSocket socket) throws SocketException {
            TrafficStatsCompatIcs.untagDatagramSocket(socket);
        }
    }

    static class Api24TrafficStatsCompatImpl extends IcsTrafficStatsCompatImpl {
        Api24TrafficStatsCompatImpl() {
        }

        public void tagDatagramSocket(DatagramSocket socket) throws SocketException {
            TrafficStatsCompatApi24.tagDatagramSocket(socket);
        }

        public void untagDatagramSocket(DatagramSocket socket) throws SocketException {
            TrafficStatsCompatApi24.untagDatagramSocket(socket);
        }
    }

    static {
        if ("N".equals(VERSION.CODENAME)) {
            IMPL = new Api24TrafficStatsCompatImpl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new IcsTrafficStatsCompatImpl();
        } else {
            IMPL = new BaseTrafficStatsCompatImpl();
        }
    }

    public static void clearThreadStatsTag() {
        IMPL.clearThreadStatsTag();
    }

    public static int getThreadStatsTag() {
        return IMPL.getThreadStatsTag();
    }

    public static void incrementOperationCount(int operationCount) {
        IMPL.incrementOperationCount(operationCount);
    }

    public static void incrementOperationCount(int tag, int operationCount) {
        IMPL.incrementOperationCount(tag, operationCount);
    }

    public static void setThreadStatsTag(int tag) {
        IMPL.setThreadStatsTag(tag);
    }

    public static void tagSocket(Socket socket) throws SocketException {
        IMPL.tagSocket(socket);
    }

    public static void untagSocket(Socket socket) throws SocketException {
        IMPL.untagSocket(socket);
    }

    public static void tagDatagramSocket(DatagramSocket socket) throws SocketException {
        IMPL.tagDatagramSocket(socket);
    }

    public static void untagDatagramSocket(DatagramSocket socket) throws SocketException {
        IMPL.untagDatagramSocket(socket);
    }

    private TrafficStatsCompat() {
    }
}
