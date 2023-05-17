package org.apache.http.concurrent;

public interface FutureCallback<T> {
    void cancelled();

    void completed(T t);

    void failed(Exception exception);
}
