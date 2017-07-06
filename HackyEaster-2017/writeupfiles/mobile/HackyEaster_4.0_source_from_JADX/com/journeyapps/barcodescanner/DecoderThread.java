package com.journeyapps.barcodescanner;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.android.C0035R;
import com.journeyapps.barcodescanner.camera.CameraInstance;
import com.journeyapps.barcodescanner.camera.PreviewCallback;

public class DecoderThread {
    private static final String TAG;
    private final Object LOCK;
    private final Callback callback;
    private CameraInstance cameraInstance;
    private Rect cropRect;
    private Decoder decoder;
    private Handler handler;
    private final PreviewCallback previewCallback;
    private Handler resultHandler;
    private boolean running;
    private HandlerThread thread;

    /* renamed from: com.journeyapps.barcodescanner.DecoderThread.1 */
    class C00531 implements Callback {
        C00531() {
        }

        public boolean handleMessage(Message message) {
            if (message.what == C0035R.id.zxing_decode) {
                DecoderThread.this.decode((SourceData) message.obj);
            }
            return true;
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.DecoderThread.2 */
    class C01232 implements PreviewCallback {
        C01232() {
        }

        public void onPreview(SourceData sourceData) {
            synchronized (DecoderThread.this.LOCK) {
                if (DecoderThread.this.running) {
                    DecoderThread.this.handler.obtainMessage(C0035R.id.zxing_decode, sourceData).sendToTarget();
                }
            }
        }
    }

    static {
        TAG = DecoderThread.class.getSimpleName();
    }

    public DecoderThread(CameraInstance cameraInstance, Decoder decoder, Handler resultHandler) {
        this.running = false;
        this.LOCK = new Object();
        this.callback = new C00531();
        this.previewCallback = new C01232();
        Util.validateMainThread();
        this.cameraInstance = cameraInstance;
        this.decoder = decoder;
        this.resultHandler = resultHandler;
    }

    public Decoder getDecoder() {
        return this.decoder;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public Rect getCropRect() {
        return this.cropRect;
    }

    public void setCropRect(Rect cropRect) {
        this.cropRect = cropRect;
    }

    public void start() {
        Util.validateMainThread();
        this.thread = new HandlerThread(TAG);
        this.thread.start();
        this.handler = new Handler(this.thread.getLooper(), this.callback);
        this.running = true;
        requestNextPreview();
    }

    public void stop() {
        Util.validateMainThread();
        synchronized (this.LOCK) {
            this.running = false;
            this.handler.removeCallbacksAndMessages(null);
            this.thread.quit();
        }
    }

    private void requestNextPreview() {
        if (this.cameraInstance.isOpen()) {
            this.cameraInstance.requestPreview(this.previewCallback);
        }
    }

    protected LuminanceSource createSource(SourceData sourceData) {
        if (this.cropRect == null) {
            return null;
        }
        return sourceData.createSource();
    }

    private void decode(SourceData sourceData) {
        long start = System.currentTimeMillis();
        Result rawResult = null;
        sourceData.setCropRect(this.cropRect);
        LuminanceSource source = createSource(sourceData);
        if (source != null) {
            rawResult = this.decoder.decode(source);
        }
        if (rawResult != null) {
            Log.d(TAG, "Found barcode in " + (System.currentTimeMillis() - start) + " ms");
            if (this.resultHandler != null) {
                Message message = Message.obtain(this.resultHandler, C0035R.id.zxing_decode_succeeded, new BarcodeResult(rawResult, sourceData));
                message.setData(new Bundle());
                message.sendToTarget();
            }
        } else if (this.resultHandler != null) {
            Message.obtain(this.resultHandler, C0035R.id.zxing_decode_failed).sendToTarget();
        }
        if (this.resultHandler != null) {
            Message.obtain(this.resultHandler, C0035R.id.zxing_possible_result_points, this.decoder.getPossibleResultPoints()).sendToTarget();
        }
        requestNextPreview();
    }
}
