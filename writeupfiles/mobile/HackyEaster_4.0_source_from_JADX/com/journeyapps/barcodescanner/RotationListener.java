package com.journeyapps.barcodescanner;

import android.content.Context;
import android.view.OrientationEventListener;
import android.view.WindowManager;

public class RotationListener {
    private RotationCallback callback;
    private int lastRotation;
    private OrientationEventListener orientationEventListener;
    private WindowManager windowManager;

    /* renamed from: com.journeyapps.barcodescanner.RotationListener.1 */
    class C00541 extends OrientationEventListener {
        C00541(Context x0, int x1) {
            super(x0, x1);
        }

        public void onOrientationChanged(int orientation) {
            WindowManager localWindowManager = RotationListener.this.windowManager;
            RotationCallback localCallback = RotationListener.this.callback;
            if (RotationListener.this.windowManager != null && localCallback != null) {
                int newRotation = localWindowManager.getDefaultDisplay().getRotation();
                if (newRotation != RotationListener.this.lastRotation) {
                    RotationListener.this.lastRotation = newRotation;
                    localCallback.onRotationChanged(newRotation);
                }
            }
        }
    }

    public void listen(Context context, RotationCallback callback) {
        stop();
        context = context.getApplicationContext();
        this.callback = callback;
        this.windowManager = (WindowManager) context.getSystemService("window");
        this.orientationEventListener = new C00541(context, 3);
        this.orientationEventListener.enable();
        this.lastRotation = this.windowManager.getDefaultDisplay().getRotation();
    }

    public void stop() {
        if (this.orientationEventListener != null) {
            this.orientationEventListener.disable();
        }
        this.orientationEventListener = null;
        this.windowManager = null;
        this.callback = null;
    }
}
