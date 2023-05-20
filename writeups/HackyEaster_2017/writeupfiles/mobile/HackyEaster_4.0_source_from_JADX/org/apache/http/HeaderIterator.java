package org.apache.http;

import java.util.Iterator;

public interface HeaderIterator extends Iterator<Object> {
    boolean hasNext();

    Header nextHeader();
}
