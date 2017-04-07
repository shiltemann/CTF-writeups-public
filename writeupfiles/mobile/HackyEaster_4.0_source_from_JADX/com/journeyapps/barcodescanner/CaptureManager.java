package com.journeyapps.barcodescanner;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.C0035R;
import com.google.zxing.client.android.InactivityTimer;
import com.google.zxing.client.android.Intents.Scan;
import com.journeyapps.barcodescanner.CameraPreview.StateListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CaptureManager {
    private static final long DELAY_BEEP = 150;
    private static final String SAVED_ORIENTATION_LOCK = "SAVED_ORIENTATION_LOCK";
    private static final String TAG;
    private static int cameraPermissionReqCode;
    private Activity activity;
    private boolean askedPermission;
    private CompoundBarcodeView barcodeView;
    private BeepManager beepManager;
    private BarcodeCallback callback;
    private boolean destroyed;
    private Handler handler;
    private InactivityTimer inactivityTimer;
    private int orientationLock;
    private boolean returnBarcodeImagePath;
    private final StateListener stateListener;

    /* renamed from: com.journeyapps.barcodescanner.CaptureManager.3 */
    class C00503 implements Runnable {
        C00503() {
        }

        public void run() {
            Log.d(CaptureManager.TAG, "Finishing due to inactivity");
            CaptureManager.this.finish();
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.CaptureManager.4 */
    class C00514 implements OnClickListener {
        C00514() {
        }

        public void onClick(DialogInterface dialog, int which) {
            CaptureManager.this.finish();
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.CaptureManager.5 */
    class C00525 implements OnCancelListener {
        C00525() {
        }

        public void onCancel(DialogInterface dialog) {
            CaptureManager.this.finish();
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.CaptureManager.1 */
    class C01211 implements BarcodeCallback {

        /* renamed from: com.journeyapps.barcodescanner.CaptureManager.1.1 */
        class C00491 implements Runnable {
            final /* synthetic */ BarcodeResult val$result;

            C00491(BarcodeResult barcodeResult) {
                this.val$result = barcodeResult;
            }

            public void run() {
                CaptureManager.this.returnResult(this.val$result);
            }
        }

        C01211() {
        }

        public void barcodeResult(BarcodeResult result) {
            CaptureManager.this.barcodeView.pause();
            CaptureManager.this.beepManager.playBeepSoundAndVibrate();
            CaptureManager.this.handler.postDelayed(new C00491(result), CaptureManager.DELAY_BEEP);
        }

        public void possibleResultPoints(List<ResultPoint> list) {
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.CaptureManager.2 */
    class C01222 implements StateListener {
        C01222() {
        }

        public void previewSized() {
        }

        public void previewStarted() {
        }

        public void previewStopped() {
        }

        public void cameraError(Exception error) {
            CaptureManager.this.displayFrameworkBugMessageAndExit();
        }
    }

    static {
        TAG = CaptureManager.class.getSimpleName();
        cameraPermissionReqCode = 250;
    }

    public CaptureManager(Activity activity, CompoundBarcodeView barcodeView) {
        this.orientationLock = -1;
        this.returnBarcodeImagePath = false;
        this.destroyed = false;
        this.callback = new C01211();
        this.stateListener = new C01222();
        this.askedPermission = false;
        this.activity = activity;
        this.barcodeView = barcodeView;
        barcodeView.getBarcodeView().addStateListener(this.stateListener);
        this.handler = new Handler();
        this.inactivityTimer = new InactivityTimer(activity, new C00503());
        this.beepManager = new BeepManager(activity);
    }

    public void initializeFromIntent(Intent intent, Bundle savedInstanceState) {
        this.activity.getWindow().addFlags(AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
        if (savedInstanceState != null) {
            this.orientationLock = savedInstanceState.getInt(SAVED_ORIENTATION_LOCK, -1);
        }
        if (intent != null) {
            if (this.orientationLock == -1 && intent.getBooleanExtra(Scan.ORIENTATION_LOCKED, true)) {
                lockOrientation();
            }
            if (Scan.ACTION.equals(intent.getAction())) {
                this.barcodeView.initializeFromIntent(intent);
            }
            if (!intent.getBooleanExtra(Scan.BEEP_ENABLED, true)) {
                this.beepManager.setBeepEnabled(false);
                this.beepManager.updatePrefs();
            }
            if (intent.getBooleanExtra(Scan.BARCODE_IMAGE_ENABLED, false)) {
                this.returnBarcodeImagePath = true;
            }
        }
    }

    protected void lockOrientation() {
        if (this.orientationLock == -1) {
            int rotation = this.activity.getWindowManager().getDefaultDisplay().getRotation();
            int baseOrientation = this.activity.getResources().getConfiguration().orientation;
            int orientation = 0;
            if (baseOrientation == 2) {
                if (rotation == 0 || rotation == 1) {
                    orientation = 0;
                } else {
                    orientation = 8;
                }
            } else if (baseOrientation == 1) {
                if (rotation == 0 || rotation == 3) {
                    orientation = 1;
                } else {
                    orientation = 9;
                }
            }
            this.orientationLock = orientation;
        }
        this.activity.setRequestedOrientation(this.orientationLock);
    }

    public void decode() {
        this.barcodeView.decodeSingle(this.callback);
    }

    public void onResume() {
        if (VERSION.SDK_INT >= 23) {
            openCameraWithPermission();
        } else {
            this.barcodeView.resume();
        }
        this.beepManager.updatePrefs();
        this.inactivityTimer.start();
    }

    @TargetApi(23)
    private void openCameraWithPermission() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.CAMERA") == 0) {
            this.barcodeView.resume();
        } else if (!this.askedPermission) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.CAMERA"}, cameraPermissionReqCode);
            this.askedPermission = true;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != cameraPermissionReqCode) {
            return;
        }
        if (grantResults.length <= 0 || grantResults[0] != 0) {
            displayFrameworkBugMessageAndExit();
        } else {
            this.barcodeView.resume();
        }
    }

    public void onPause() {
        this.barcodeView.pause();
        this.inactivityTimer.cancel();
        this.beepManager.close();
    }

    public void onDestroy() {
        this.destroyed = true;
        this.inactivityTimer.cancel();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVED_ORIENTATION_LOCK, this.orientationLock);
    }

    public static Intent resultIntent(BarcodeResult rawResult, String barcodeImagePath) {
        Intent intent = new Intent(Scan.ACTION);
        intent.addFlags(AccessibilityNodeInfoCompat.ACTION_COLLAPSE);
        intent.putExtra(Scan.RESULT, rawResult.toString());
        intent.putExtra(Scan.RESULT_FORMAT, rawResult.getBarcodeFormat().toString());
        byte[] rawBytes = rawResult.getRawBytes();
        if (rawBytes != null && rawBytes.length > 0) {
            intent.putExtra(Scan.RESULT_BYTES, rawBytes);
        }
        Map<ResultMetadataType, ?> metadata = rawResult.getResultMetadata();
        if (metadata != null) {
            if (metadata.containsKey(ResultMetadataType.UPC_EAN_EXTENSION)) {
                intent.putExtra(Scan.RESULT_UPC_EAN_EXTENSION, metadata.get(ResultMetadataType.UPC_EAN_EXTENSION).toString());
            }
            Number orientation = (Number) metadata.get(ResultMetadataType.ORIENTATION);
            if (orientation != null) {
                intent.putExtra(Scan.RESULT_ORIENTATION, orientation.intValue());
            }
            String ecLevel = (String) metadata.get(ResultMetadataType.ERROR_CORRECTION_LEVEL);
            if (ecLevel != null) {
                intent.putExtra(Scan.RESULT_ERROR_CORRECTION_LEVEL, ecLevel);
            }
            Iterable<byte[]> byteSegments = (Iterable) metadata.get(ResultMetadataType.BYTE_SEGMENTS);
            if (byteSegments != null) {
                int i = 0;
                for (byte[] byteSegment : byteSegments) {
                    intent.putExtra(Scan.RESULT_BYTE_SEGMENTS_PREFIX + i, byteSegment);
                    i++;
                }
            }
        }
        if (barcodeImagePath != null) {
            intent.putExtra(Scan.RESULT_BARCODE_IMAGE_PATH, barcodeImagePath);
        }
        return intent;
    }

    private String getBarcodeImagePath(BarcodeResult rawResult) {
        String barcodeImagePath = null;
        if (this.returnBarcodeImagePath) {
            Bitmap bmp = rawResult.getBitmap();
            try {
                File bitmapFile = File.createTempFile("barcodeimage", ".jpg", this.activity.getCacheDir());
                FileOutputStream outputStream = new FileOutputStream(bitmapFile);
                bmp.compress(CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
                barcodeImagePath = bitmapFile.getAbsolutePath();
            } catch (IOException e) {
                Log.w(TAG, "Unable to create temporary file and store bitmap! " + e);
            }
        }
        return barcodeImagePath;
    }

    private void finish() {
        this.activity.finish();
    }

    protected void returnResult(BarcodeResult rawResult) {
        this.activity.setResult(-1, resultIntent(rawResult, getBarcodeImagePath(rawResult)));
        finish();
    }

    protected void displayFrameworkBugMessageAndExit() {
        if (!this.activity.isFinishing() && !this.destroyed) {
            Builder builder = new Builder(this.activity);
            builder.setTitle(this.activity.getString(C0035R.string.zxing_app_name));
            builder.setMessage(this.activity.getString(C0035R.string.zxing_msg_camera_framework_bug));
            builder.setPositiveButton(C0035R.string.zxing_button_ok, new C00514());
            builder.setOnCancelListener(new C00525());
            builder.show();
        }
    }

    public static int getCameraPermissionReqCode() {
        return cameraPermissionReqCode;
    }

    public static void setCameraPermissionReqCode(int cameraPermissionReqCode) {
        cameraPermissionReqCode = cameraPermissionReqCode;
    }
}
