package com.journeyapps.barcodescanner;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.NotificationCompat.MessagingStyle;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.C0035R;
import com.google.zxing.client.android.DecodeFormatManager;
import com.google.zxing.client.android.DecodeHintManager;
import com.google.zxing.client.android.Intents.Scan;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompoundBarcodeView extends FrameLayout {
    private BarcodeView barcodeView;
    private TextView statusView;
    private TorchListener torchListener;
    private ViewfinderView viewFinder;

    public interface TorchListener {
        void onTorchOff();

        void onTorchOn();
    }

    private class WrappedCallback implements BarcodeCallback {
        private BarcodeCallback delegate;

        public WrappedCallback(BarcodeCallback delegate) {
            this.delegate = delegate;
        }

        public void barcodeResult(BarcodeResult result) {
            this.delegate.barcodeResult(result);
        }

        public void possibleResultPoints(List<ResultPoint> resultPoints) {
            for (ResultPoint point : resultPoints) {
                CompoundBarcodeView.this.viewFinder.addPossibleResultPoint(point);
            }
            this.delegate.possibleResultPoints(resultPoints);
        }
    }

    public CompoundBarcodeView(Context context) {
        super(context);
        initialize();
    }

    public CompoundBarcodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public CompoundBarcodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, C0035R.styleable.zxing_view);
        int scannerLayout = attributes.getResourceId(C0035R.styleable.zxing_view_zxing_scanner_layout, C0035R.layout.zxing_barcode_scanner);
        attributes.recycle();
        inflate(getContext(), scannerLayout, this);
        this.barcodeView = (BarcodeView) findViewById(C0035R.id.zxing_barcode_surface);
        if (this.barcodeView == null) {
            throw new IllegalArgumentException("There is no a com.journeyapps.barcodescanner.BarcodeView on provided layout with the id \"zxing_barcode_surface\".");
        }
        this.barcodeView.initializeAttributes(attrs);
        this.viewFinder = (ViewfinderView) findViewById(C0035R.id.zxing_viewfinder_view);
        if (this.viewFinder == null) {
            throw new IllegalArgumentException("There is no a com.journeyapps.barcodescanner.ViewfinderView on provided layout with the id \"zxing_viewfinder_view\".");
        }
        this.viewFinder.setCameraPreview(this.barcodeView);
        this.statusView = (TextView) findViewById(C0035R.id.zxing_status_view);
    }

    private void initialize() {
        initialize(null);
    }

    public void initializeFromIntent(Intent intent) {
        Set<BarcodeFormat> decodeFormats = DecodeFormatManager.parseDecodeFormats(intent);
        Map<DecodeHintType, Object> decodeHints = DecodeHintManager.parseDecodeHints(intent);
        CameraSettings settings = new CameraSettings();
        if (intent.hasExtra(Scan.CAMERA_ID)) {
            int cameraId = intent.getIntExtra(Scan.CAMERA_ID, -1);
            if (cameraId >= 0) {
                settings.setRequestedCameraId(cameraId);
            }
        }
        String customPromptMessage = intent.getStringExtra(Scan.PROMPT_MESSAGE);
        if (customPromptMessage != null) {
            setStatusText(customPromptMessage);
        }
        String characterSet = intent.getStringExtra(Scan.CHARACTER_SET);
        new MultiFormatReader().setHints(decodeHints);
        this.barcodeView.setCameraSettings(settings);
        this.barcodeView.setDecoderFactory(new DefaultDecoderFactory(decodeFormats, decodeHints, characterSet));
    }

    public void setStatusText(String text) {
        if (this.statusView != null) {
            this.statusView.setText(text);
        }
    }

    public void pause() {
        this.barcodeView.pause();
    }

    public void resume() {
        this.barcodeView.resume();
    }

    public BarcodeView getBarcodeView() {
        return (BarcodeView) findViewById(C0035R.id.zxing_barcode_surface);
    }

    public ViewfinderView getViewFinder() {
        return this.viewFinder;
    }

    public TextView getStatusView() {
        return this.statusView;
    }

    public void decodeSingle(BarcodeCallback callback) {
        this.barcodeView.decodeSingle(new WrappedCallback(callback));
    }

    public void decodeContinuous(BarcodeCallback callback) {
        this.barcodeView.decodeContinuous(new WrappedCallback(callback));
    }

    public void setTorchOn() {
        this.barcodeView.setTorch(true);
        if (this.torchListener != null) {
            this.torchListener.onTorchOn();
        }
    }

    public void setTorchOff() {
        this.barcodeView.setTorch(false);
        if (this.torchListener != null) {
            this.torchListener.onTorchOff();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case MotionEventCompat.AXIS_DISTANCE /*24*/:
                setTorchOn();
                return true;
            case MessagingStyle.MAXIMUM_RETAINED_MESSAGES /*25*/:
                setTorchOff();
                return true;
            case MotionEventCompat.AXIS_RELATIVE_X /*27*/:
            case 80:
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    public void setTorchListener(TorchListener listener) {
        this.torchListener = listener;
    }
}
