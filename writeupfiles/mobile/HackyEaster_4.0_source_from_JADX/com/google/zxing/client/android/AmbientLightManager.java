package com.google.zxing.client.android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import com.journeyapps.barcodescanner.camera.CameraManager;
import com.journeyapps.barcodescanner.camera.CameraSettings;

public final class AmbientLightManager implements SensorEventListener {
    private static final float BRIGHT_ENOUGH_LUX = 450.0f;
    private static final float TOO_DARK_LUX = 45.0f;
    private CameraManager cameraManager;
    private CameraSettings cameraSettings;
    private Context context;
    private Handler handler;
    private Sensor lightSensor;

    /* renamed from: com.google.zxing.client.android.AmbientLightManager.1 */
    class C00321 implements Runnable {
        final /* synthetic */ boolean val$on;

        C00321(boolean z) {
            this.val$on = z;
        }

        public void run() {
            AmbientLightManager.this.cameraManager.setTorch(this.val$on);
        }
    }

    public AmbientLightManager(Context context, CameraManager cameraManager, CameraSettings settings) {
        this.context = context;
        this.cameraManager = cameraManager;
        this.cameraSettings = settings;
        this.handler = new Handler();
    }

    public void start() {
        if (this.cameraSettings.isAutoTorchEnabled()) {
            SensorManager sensorManager = (SensorManager) this.context.getSystemService("sensor");
            this.lightSensor = sensorManager.getDefaultSensor(5);
            if (this.lightSensor != null) {
                sensorManager.registerListener(this, this.lightSensor, 3);
            }
        }
    }

    public void stop() {
        if (this.lightSensor != null) {
            ((SensorManager) this.context.getSystemService("sensor")).unregisterListener(this);
            this.lightSensor = null;
        }
    }

    private void setTorch(boolean on) {
        this.handler.post(new C00321(on));
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        float ambientLightLux = sensorEvent.values[0];
        if (this.cameraManager == null) {
            return;
        }
        if (ambientLightLux <= TOO_DARK_LUX) {
            setTorch(true);
        } else if (ambientLightLux >= BRIGHT_ENOUGH_LUX) {
            setTorch(false);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
