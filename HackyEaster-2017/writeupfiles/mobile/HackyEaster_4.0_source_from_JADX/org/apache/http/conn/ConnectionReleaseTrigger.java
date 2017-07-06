package org.apache.http.conn;

import java.io.IOException;

public interface ConnectionReleaseTrigger {
    void abortConnection() throws IOException;

    void releaseConnection() throws IOException;
}
