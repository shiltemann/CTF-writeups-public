package org.apache.http.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.http.util.Args;

public class BasicFuture<T> implements Future<T>, Cancellable {
    private final FutureCallback<T> callback;
    private volatile boolean cancelled;
    private volatile boolean completed;
    private volatile Exception ex;
    private volatile T result;

    public BasicFuture(FutureCallback<T> callback) {
        this.callback = callback;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public boolean isDone() {
        return this.completed;
    }

    private T getResult() throws ExecutionException {
        if (this.ex == null) {
            return this.result;
        }
        throw new ExecutionException(this.ex);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized T get() throws java.lang.InterruptedException, java.util.concurrent.ExecutionException {
        /*
        r1 = this;
        monitor-enter(r1);
    L_0x0001:
        r0 = r1.completed;	 Catch:{ all -> 0x0009 }
        if (r0 != 0) goto L_0x000c;
    L_0x0005:
        r1.wait();	 Catch:{ all -> 0x0009 }
        goto L_0x0001;
    L_0x0009:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
    L_0x000c:
        r0 = r1.getResult();	 Catch:{ all -> 0x0009 }
        monitor-exit(r1);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.concurrent.BasicFuture.get():T");
    }

    public synchronized T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        T result;
        Args.notNull(unit, "Time unit");
        long msecs = unit.toMillis(timeout);
        long startTime = msecs <= 0 ? 0 : System.currentTimeMillis();
        long waitTime = msecs;
        if (this.completed) {
            result = getResult();
        } else if (waitTime <= 0) {
            throw new TimeoutException();
        } else {
            do {
                wait(waitTime);
                if (this.completed) {
                    result = getResult();
                } else {
                    waitTime = msecs - (System.currentTimeMillis() - startTime);
                }
            } while (waitTime > 0);
            throw new TimeoutException();
        }
        return result;
    }

    public boolean completed(T result) {
        boolean z = true;
        synchronized (this) {
            if (this.completed) {
                z = false;
            } else {
                this.completed = true;
                this.result = result;
                notifyAll();
                if (this.callback != null) {
                    this.callback.completed(result);
                }
            }
        }
        return z;
    }

    public boolean failed(Exception exception) {
        boolean z = true;
        synchronized (this) {
            if (this.completed) {
                z = false;
            } else {
                this.completed = true;
                this.ex = exception;
                notifyAll();
                if (this.callback != null) {
                    this.callback.failed(exception);
                }
            }
        }
        return z;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        boolean z = true;
        synchronized (this) {
            if (this.completed) {
                z = false;
            } else {
                this.completed = true;
                this.cancelled = true;
                notifyAll();
                if (this.callback != null) {
                    this.callback.cancelled();
                }
            }
        }
        return z;
    }

    public boolean cancel() {
        return cancel(true);
    }
}
