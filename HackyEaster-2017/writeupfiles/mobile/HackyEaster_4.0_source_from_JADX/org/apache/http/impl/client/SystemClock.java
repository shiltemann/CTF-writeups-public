package org.apache.http.impl.client;

class SystemClock implements Clock {
    SystemClock() {
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
