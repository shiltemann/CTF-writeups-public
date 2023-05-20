package org.apache.http.impl.io;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.IOException;
import java.net.Socket;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

@NotThreadSafe
@Deprecated
public class SocketOutputBuffer extends AbstractSessionOutputBuffer {
    public SocketOutputBuffer(Socket socket, int buffersize, HttpParams params) throws IOException {
        Args.notNull(socket, "Socket");
        int n = buffersize;
        if (n < 0) {
            n = socket.getSendBufferSize();
        }
        if (n < AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT) {
            n = AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT;
        }
        init(socket.getOutputStream(), n, params);
    }
}
