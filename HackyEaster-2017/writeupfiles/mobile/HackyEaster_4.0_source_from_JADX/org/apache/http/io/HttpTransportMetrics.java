package org.apache.http.io;

public interface HttpTransportMetrics {
    long getBytesTransferred();

    void reset();
}
