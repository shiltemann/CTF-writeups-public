package com.journeyapps.barcodescanner.camera;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import com.google.zxing.client.android.C0035R;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.Util;

public class CameraInstance {
    private static final String TAG;
    private CameraManager cameraManager;
    private CameraSettings cameraSettings;
    private CameraThread cameraThread;
    private Runnable closer;
    private Runnable configure;
    private DisplayConfiguration displayConfiguration;
    private boolean open;
    private Runnable opener;
    private Runnable previewStarter;
    private Handler readyHandler;
    private CameraSurface surface;

    /* renamed from: com.journeyapps.barcodescanner.camera.CameraInstance.1 */
    class C00581 implements Runnable {
        final /* synthetic */ boolean val$on;

        C00581(boolean z) {
            this.val$on = z;
        }

        public void run() {
            CameraInstance.this.cameraManager.setTorch(this.val$on);
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.camera.CameraInstance.2 */
    class C00592 implements Runnable {
        final /* synthetic */ PreviewCallback val$callback;

        C00592(PreviewCallback previewCallback) {
            this.val$callback = previewCallback;
        }

        public void run() {
            CameraInstance.this.cameraManager.requestPreviewFrame(this.val$callback);
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.camera.CameraInstance.3 */
    class C00603 implements Runnable {
        C00603() {
        }

        public void run() {
            try {
                Log.d(CameraInstance.TAG, "Opening camera");
                CameraInstance.this.cameraManager.open();
            } catch (Exception e) {
                CameraInstance.this.notifyError(e);
                Log.e(CameraInstance.TAG, "Failed to open camera", e);
            }
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.camera.CameraInstance.4 */
    class C00614 implements Runnable {
        C00614() {
        }

        public void run() {
            try {
                Log.d(CameraInstance.TAG, "Configuring camera");
                CameraInstance.this.cameraManager.configure();
                if (CameraInstance.this.readyHandler != null) {
                    CameraInstance.this.readyHandler.obtainMessage(C0035R.id.zxing_prewiew_size_ready, CameraInstance.this.getPreviewSize()).sendToTarget();
                }
            } catch (Exception e) {
                CameraInstance.this.notifyError(e);
                Log.e(CameraInstance.TAG, "Failed to configure camera", e);
            }
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.camera.CameraInstance.5 */
    class C00625 implements Runnable {
        C00625() {
        }

        public void run() {
            try {
                Log.d(CameraInstance.TAG, "Starting preview");
                CameraInstance.this.cameraManager.setPreviewDisplay(CameraInstance.this.surface);
                CameraInstance.this.cameraManager.startPreview();
            } catch (Exception e) {
                CameraInstance.this.notifyError(e);
                Log.e(CameraInstance.TAG, "Failed to start preview", e);
            }
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.camera.CameraInstance.6 */
    class C00636 implements Runnable {
        C00636() {
        }

        public void run() {
            try {
                Log.d(CameraInstance.TAG, "Closing camera");
                CameraInstance.this.cameraManager.stopPreview();
                CameraInstance.this.cameraManager.close();
            } catch (Exception e) {
                Log.e(CameraInstance.TAG, "Failed to close camera", e);
            }
            CameraInstance.this.cameraThread.decrementInstances();
        }
    }

    static {
        TAG = CameraInstance.class.getSimpleName();
    }

    public CameraInstance(Context context) {
        this.open = false;
        this.cameraSettings = new CameraSettings();
        this.opener = new C00603();
        this.configure = new C00614();
        this.previewStarter = new C00625();
        this.closer = new C00636();
        Util.validateMainThread();
        this.cameraThread = CameraThread.getInstance();
        this.cameraManager = new CameraManager(context);
        this.cameraManager.setCameraSettings(this.cameraSettings);
    }

    public void setDisplayConfiguration(DisplayConfiguration configuration) {
        this.displayConfiguration = configuration;
        this.cameraManager.setDisplayConfiguration(configuration);
    }

    public DisplayConfiguration getDisplayConfiguration() {
        return this.displayConfiguration;
    }

    public void setReadyHandler(Handler readyHandler) {
        this.readyHandler = readyHandler;
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        setSurface(new CameraSurface(surfaceHolder));
    }

    public void setSurface(CameraSurface surface) {
        this.surface = surface;
    }

    public CameraSettings getCameraSettings() {
        return this.cameraSettings;
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        if (!this.open) {
            this.cameraSettings = cameraSettings;
            this.cameraManager.setCameraSettings(cameraSettings);
        }
    }

    private Size getPreviewSize() {
        return this.cameraManager.getPreviewSize();
    }

    public int getCameraRotation() {
        return this.cameraManager.getCameraRotation();
    }

    public void open() {
        Util.validateMainThread();
        this.open = true;
        this.cameraThread.incrementAndEnqueue(this.opener);
    }

    public void configureCamera() {
        Util.validateMainThread();
        validateOpen();
        this.cameraThread.enqueue(this.configure);
    }

    public void startPreview() {
        Util.validateMainThread();
        validateOpen();
        this.cameraThread.enqueue(this.previewStarter);
    }

    public void setTorch(boolean on) {
        Util.validateMainThread();
        if (this.open) {
            this.cameraThread.enqueue(new C00581(on));
        }
    }

    public void close() {
        Util.validateMainThread();
        if (this.open) {
            this.cameraThread.enqueue(this.closer);
        }
        this.open = false;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void requestPreview(PreviewCallback callback) {
        validateOpen();
        this.cameraThread.enqueue(new C00592(callback));
    }

    private void validateOpen() {
        if (!this.open) {
            throw new IllegalStateException("CameraInstance is not open");
        }
    }

    private void notifyError(Exception error) {
        if (this.readyHandler != null) {
            this.readyHandler.obtainMessage(C0035R.id.zxing_camera_error, error).sendToTarget();
        }
    }
}
