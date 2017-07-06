package org.apache.http.io;

public interface BufferInfo {
    int available();

    int capacity();

    int length();
}
