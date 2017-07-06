package org.apache.http;

import org.apache.http.util.CharArrayBuffer;

public interface FormattedHeader extends Header {
    CharArrayBuffer getBuffer();

    int getValuePos();
}
