package com.journeyapps.barcodescanner.camera;

import android.os.Handler;
import android.os.HandlerThread;

class CameraThread {
    private static final String TAG;
    private static CameraThread instance;
    private final Object LOCK;
    private Handler handler;
    private int openCount;
    private HandlerThread thread;

    static {
        TAG = CameraThread.class.getSimpleName();
    }

    public static CameraThread getInstance() {
        if (instance == null) {
            instance = new CameraThread();
        }
        return instance;
    }

    private CameraThread() {
        this.openCount = 0;
        this.LOCK = new Object();
    }

    protected void enqueue(Runnable runnable) {
        synchronized (this.LOCK) {
            checkRunning();
            this.handler.post(runnable);
        }
    }

    protected void enqueueDelayed(Runnable runnable, long delayMillis) {
        synchronized (this.LOCK) {
            checkRunning();
            this.handler.postDelayed(runnable, delayMillis);
        }
    }

    private void checkRunning() {
        synchronized (this.LOCK) {
            if (this.handler == null) {
                if (this.openCount <= 0) {
                    throw new IllegalStateException("CameraThread is not open");
                }
                this.thread = new HandlerThread("CameraThread");
                this.thread.start();
                this.handler = new Handler(this.thread.getLooper());
            }
        }
    }

    private void quit() {
        synchronized (this.LOCK) {
            this.thread.quit();
            this.thread = null;
            this.handler = null;
        }
    }

    protected void decrementInstances() {
        synchronized (this.LOCK) {
            this.openCount--;
            if (this.openCount == 0) {
                quit();
            }
        }
    }

    protected void incrementAndEnqueue(Runnable runner) {
        synchronized (this.LOCK) {
            this.openCount++;
            enqueue(runner);
        }
    }
}
