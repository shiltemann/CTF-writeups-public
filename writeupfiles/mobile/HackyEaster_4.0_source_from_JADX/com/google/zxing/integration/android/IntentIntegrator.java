package com.google.zxing.integration.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.zxing.client.android.Intents.Scan;
import com.journeyapps.barcodescanner.CaptureActivity;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class IntentIntegrator {
    public static final Collection<String> ALL_CODE_TYPES;
    public static final Collection<String> DATA_MATRIX_TYPES;
    public static final Collection<String> ONE_D_CODE_TYPES;
    public static final Collection<String> PRODUCT_CODE_TYPES;
    public static final Collection<String> QR_CODE_TYPES;
    public static final int REQUEST_CODE = 49374;
    private static final String TAG;
    private final Activity activity;
    private Class<?> captureActivity;
    private Collection<String> desiredBarcodeFormats;
    private Fragment fragment;
    private final Map<String, Object> moreExtras;
    private android.support.v4.app.Fragment supportFragment;

    static {
        TAG = IntentIntegrator.class.getSimpleName();
        PRODUCT_CODE_TYPES = list("UPC_A", "UPC_E", "EAN_8", "EAN_13", "RSS_14");
        ONE_D_CODE_TYPES = list("UPC_A", "UPC_E", "EAN_8", "EAN_13", "CODE_39", "CODE_93", "CODE_128", "ITF", "RSS_14", "RSS_EXPANDED");
        QR_CODE_TYPES = Collections.singleton("QR_CODE");
        DATA_MATRIX_TYPES = Collections.singleton("DATA_MATRIX");
        ALL_CODE_TYPES = null;
    }

    protected Class<?> getDefaultCaptureActivity() {
        return CaptureActivity.class;
    }

    public IntentIntegrator(Activity activity) {
        this.moreExtras = new HashMap(3);
        this.activity = activity;
    }

    public Class<?> getCaptureActivity() {
        if (this.captureActivity == null) {
            this.captureActivity = getDefaultCaptureActivity();
        }
        return this.captureActivity;
    }

    public IntentIntegrator setCaptureActivity(Class<?> captureActivity) {
        this.captureActivity = captureActivity;
        return this;
    }

    public static IntentIntegrator forSupportFragment(android.support.v4.app.Fragment fragment) {
        IntentIntegrator integrator = new IntentIntegrator(fragment.getActivity());
        integrator.supportFragment = fragment;
        return integrator;
    }

    @TargetApi(11)
    public static IntentIntegrator forFragment(Fragment fragment) {
        IntentIntegrator integrator = new IntentIntegrator(fragment.getActivity());
        integrator.fragment = fragment;
        return integrator;
    }

    public Map<String, ?> getMoreExtras() {
        return this.moreExtras;
    }

    public final IntentIntegrator addExtra(String key, Object value) {
        this.moreExtras.put(key, value);
        return this;
    }

    public final IntentIntegrator setPrompt(String prompt) {
        if (prompt != null) {
            addExtra(Scan.PROMPT_MESSAGE, prompt);
        }
        return this;
    }

    public IntentIntegrator setOrientationLocked(boolean locked) {
        addExtra(Scan.ORIENTATION_LOCKED, Boolean.valueOf(locked));
        return this;
    }

    public IntentIntegrator setCameraId(int cameraId) {
        if (cameraId >= 0) {
            addExtra(Scan.CAMERA_ID, Integer.valueOf(cameraId));
        }
        return this;
    }

    public IntentIntegrator setBeepEnabled(boolean enabled) {
        addExtra(Scan.BEEP_ENABLED, Boolean.valueOf(enabled));
        return this;
    }

    public IntentIntegrator setBarcodeImageEnabled(boolean enabled) {
        addExtra(Scan.BARCODE_IMAGE_ENABLED, Boolean.valueOf(enabled));
        return this;
    }

    public IntentIntegrator setDesiredBarcodeFormats(Collection<String> desiredBarcodeFormats) {
        this.desiredBarcodeFormats = desiredBarcodeFormats;
        return this;
    }

    public final void initiateScan() {
        startActivityForResult(createScanIntent(), REQUEST_CODE);
    }

    public Intent createScanIntent() {
        Intent intentScan = new Intent(this.activity, getCaptureActivity());
        intentScan.setAction(Scan.ACTION);
        if (this.desiredBarcodeFormats != null) {
            StringBuilder joinedByComma = new StringBuilder();
            for (String format : this.desiredBarcodeFormats) {
                if (joinedByComma.length() > 0) {
                    joinedByComma.append(',');
                }
                joinedByComma.append(format);
            }
            intentScan.putExtra(Scan.FORMATS, joinedByComma.toString());
        }
        intentScan.addFlags(67108864);
        intentScan.addFlags(AccessibilityNodeInfoCompat.ACTION_COLLAPSE);
        attachMoreExtras(intentScan);
        return intentScan;
    }

    public final void initiateScan(Collection<String> desiredBarcodeFormats) {
        setDesiredBarcodeFormats(desiredBarcodeFormats);
        initiateScan();
    }

    protected void startActivityForResult(Intent intent, int code) {
        if (this.fragment != null) {
            if (VERSION.SDK_INT >= 11) {
                this.fragment.startActivityForResult(intent, code);
            }
        } else if (this.supportFragment != null) {
            this.supportFragment.startActivityForResult(intent, code);
        } else {
            this.activity.startActivityForResult(intent, code);
        }
    }

    protected void startActivity(Intent intent) {
        if (this.fragment != null) {
            if (VERSION.SDK_INT >= 11) {
                this.fragment.startActivity(intent);
            }
        } else if (this.supportFragment != null) {
            this.supportFragment.startActivity(intent);
        } else {
            this.activity.startActivity(intent);
        }
    }

    public static IntentResult parseActivityResult(int requestCode, int resultCode, Intent intent) {
        Integer orientation = null;
        if (requestCode != REQUEST_CODE) {
            return null;
        }
        if (resultCode != -1) {
            return new IntentResult();
        }
        String contents = intent.getStringExtra(Scan.RESULT);
        String formatName = intent.getStringExtra(Scan.RESULT_FORMAT);
        byte[] rawBytes = intent.getByteArrayExtra(Scan.RESULT_BYTES);
        int intentOrientation = intent.getIntExtra(Scan.RESULT_ORIENTATION, Integer.MIN_VALUE);
        if (intentOrientation != Integer.MIN_VALUE) {
            orientation = Integer.valueOf(intentOrientation);
        }
        return new IntentResult(contents, formatName, rawBytes, orientation, intent.getStringExtra(Scan.RESULT_ERROR_CORRECTION_LEVEL), intent.getStringExtra(Scan.RESULT_BARCODE_IMAGE_PATH));
    }

    private static List<String> list(String... values) {
        return Collections.unmodifiableList(Arrays.asList(values));
    }

    private void attachMoreExtras(Intent intent) {
        for (Entry<String, Object> entry : this.moreExtras.entrySet()) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Integer) {
                intent.putExtra(key, (Integer) value);
            } else if (value instanceof Long) {
                intent.putExtra(key, (Long) value);
            } else if (value instanceof Boolean) {
                intent.putExtra(key, (Boolean) value);
            } else if (value instanceof Double) {
                intent.putExtra(key, (Double) value);
            } else if (value instanceof Float) {
                intent.putExtra(key, (Float) value);
            } else if (value instanceof Bundle) {
                intent.putExtra(key, (Bundle) value);
            } else {
                intent.putExtra(key, value.toString());
            }
        }
    }
}
