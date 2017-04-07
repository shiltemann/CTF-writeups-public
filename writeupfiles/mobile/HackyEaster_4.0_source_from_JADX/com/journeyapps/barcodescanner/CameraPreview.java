package com.journeyapps.barcodescanner;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.google.zxing.client.android.C0035R;
import com.journeyapps.barcodescanner.camera.CameraInstance;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import com.journeyapps.barcodescanner.camera.CameraSurface;
import com.journeyapps.barcodescanner.camera.CenterCropStrategy;
import com.journeyapps.barcodescanner.camera.DisplayConfiguration;
import com.journeyapps.barcodescanner.camera.FitCenterStrategy;
import com.journeyapps.barcodescanner.camera.FitXYStrategy;
import com.journeyapps.barcodescanner.camera.PreviewScalingStrategy;
import java.util.ArrayList;
import java.util.List;

public class CameraPreview extends ViewGroup {
    private static final int ROTATION_LISTENER_DELAY_MS = 250;
    private static final String TAG;
    private CameraInstance cameraInstance;
    private CameraSettings cameraSettings;
    private Size containerSize;
    private Size currentSurfaceSize;
    private DisplayConfiguration displayConfiguration;
    private final StateListener fireState;
    private Rect framingRect;
    private Size framingRectSize;
    private double marginFraction;
    private int openedOrientation;
    private boolean previewActive;
    private Rect previewFramingRect;
    private PreviewScalingStrategy previewScalingStrategy;
    private Size previewSize;
    private RotationCallback rotationCallback;
    private RotationListener rotationListener;
    private final Callback stateCallback;
    private Handler stateHandler;
    private List<StateListener> stateListeners;
    private final SurfaceHolder.Callback surfaceCallback;
    private Rect surfaceRect;
    private SurfaceView surfaceView;
    private TextureView textureView;
    private boolean torchOn;
    private boolean useTextureView;
    private WindowManager windowManager;

    /* renamed from: com.journeyapps.barcodescanner.CameraPreview.1 */
    class C00451 implements SurfaceTextureListener {
        C00451() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            onSurfaceTextureSizeChanged(surface, width, height);
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            CameraPreview.this.currentSurfaceSize = new Size(width, height);
            CameraPreview.this.startPreviewIfReady();
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.CameraPreview.2 */
    class C00462 implements SurfaceHolder.Callback {
        C00462() {
        }

        public void surfaceCreated(SurfaceHolder holder) {
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            CameraPreview.this.currentSurfaceSize = null;
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (holder == null) {
                Log.e(CameraPreview.TAG, "*** WARNING *** surfaceChanged() gave us a null surface!");
                return;
            }
            CameraPreview.this.currentSurfaceSize = new Size(width, height);
            CameraPreview.this.startPreviewIfReady();
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.CameraPreview.3 */
    class C00473 implements Callback {
        C00473() {
        }

        public boolean handleMessage(Message message) {
            if (message.what == C0035R.id.zxing_prewiew_size_ready) {
                CameraPreview.this.previewSized((Size) message.obj);
                return true;
            }
            if (message.what == C0035R.id.zxing_camera_error) {
                Exception error = message.obj;
                if (CameraPreview.this.isActive()) {
                    CameraPreview.this.pause();
                    CameraPreview.this.fireState.cameraError(error);
                }
            }
            return false;
        }
    }

    public interface StateListener {
        void cameraError(Exception exception);

        void previewSized();

        void previewStarted();

        void previewStopped();
    }

    /* renamed from: com.journeyapps.barcodescanner.CameraPreview.4 */
    class C01194 implements RotationCallback {

        /* renamed from: com.journeyapps.barcodescanner.CameraPreview.4.1 */
        class C00481 implements Runnable {
            C00481() {
            }

            public void run() {
                CameraPreview.this.rotationChanged();
            }
        }

        C01194() {
        }

        public void onRotationChanged(int rotation) {
            CameraPreview.this.stateHandler.postDelayed(new C00481(), 250);
        }
    }

    /* renamed from: com.journeyapps.barcodescanner.CameraPreview.5 */
    class C01205 implements StateListener {
        C01205() {
        }

        public void previewSized() {
            for (StateListener listener : CameraPreview.this.stateListeners) {
                listener.previewSized();
            }
        }

        public void previewStarted() {
            for (StateListener listener : CameraPreview.this.stateListeners) {
                listener.previewStarted();
            }
        }

        public void previewStopped() {
            for (StateListener listener : CameraPreview.this.stateListeners) {
                listener.previewStopped();
            }
        }

        public void cameraError(Exception error) {
            for (StateListener listener : CameraPreview.this.stateListeners) {
                listener.cameraError(error);
            }
        }
    }

    static {
        TAG = CameraPreview.class.getSimpleName();
    }

    @TargetApi(14)
    private SurfaceTextureListener surfaceTextureListener() {
        return new C00451();
    }

    public CameraPreview(Context context) {
        super(context);
        this.useTextureView = false;
        this.previewActive = false;
        this.openedOrientation = -1;
        this.stateListeners = new ArrayList();
        this.cameraSettings = new CameraSettings();
        this.framingRect = null;
        this.previewFramingRect = null;
        this.framingRectSize = null;
        this.marginFraction = 0.1d;
        this.previewScalingStrategy = null;
        this.torchOn = false;
        this.surfaceCallback = new C00462();
        this.stateCallback = new C00473();
        this.rotationCallback = new C01194();
        this.fireState = new C01205();
        initialize(context, null, 0, 0);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.useTextureView = false;
        this.previewActive = false;
        this.openedOrientation = -1;
        this.stateListeners = new ArrayList();
        this.cameraSettings = new CameraSettings();
        this.framingRect = null;
        this.previewFramingRect = null;
        this.framingRectSize = null;
        this.marginFraction = 0.1d;
        this.previewScalingStrategy = null;
        this.torchOn = false;
        this.surfaceCallback = new C00462();
        this.stateCallback = new C00473();
        this.rotationCallback = new C01194();
        this.fireState = new C01205();
        initialize(context, attrs, 0, 0);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.useTextureView = false;
        this.previewActive = false;
        this.openedOrientation = -1;
        this.stateListeners = new ArrayList();
        this.cameraSettings = new CameraSettings();
        this.framingRect = null;
        this.previewFramingRect = null;
        this.framingRectSize = null;
        this.marginFraction = 0.1d;
        this.previewScalingStrategy = null;
        this.torchOn = false;
        this.surfaceCallback = new C00462();
        this.stateCallback = new C00473();
        this.rotationCallback = new C01194();
        this.fireState = new C01205();
        initialize(context, attrs, defStyleAttr, 0);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (getBackground() == null) {
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
        initializeAttributes(attrs);
        this.windowManager = (WindowManager) context.getSystemService("window");
        this.stateHandler = new Handler(this.stateCallback);
        this.rotationListener = new RotationListener();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setupSurfaceView();
    }

    protected void initializeAttributes(AttributeSet attrs) {
        TypedArray styledAttributes = getContext().obtainStyledAttributes(attrs, C0035R.styleable.zxing_camera_preview);
        int framingRectWidth = (int) styledAttributes.getDimension(C0035R.styleable.zxing_camera_preview_zxing_framing_rect_width, -1.0f);
        int framingRectHeight = (int) styledAttributes.getDimension(C0035R.styleable.zxing_camera_preview_zxing_framing_rect_height, -1.0f);
        if (framingRectWidth > 0 && framingRectHeight > 0) {
            this.framingRectSize = new Size(framingRectWidth, framingRectHeight);
        }
        this.useTextureView = styledAttributes.getBoolean(C0035R.styleable.zxing_camera_preview_zxing_use_texture_view, true);
        int scalingStrategyNumber = styledAttributes.getInteger(C0035R.styleable.zxing_camera_preview_zxing_preview_scaling_strategy, -1);
        if (scalingStrategyNumber == 1) {
            this.previewScalingStrategy = new CenterCropStrategy();
        } else if (scalingStrategyNumber == 2) {
            this.previewScalingStrategy = new FitCenterStrategy();
        } else if (scalingStrategyNumber == 3) {
            this.previewScalingStrategy = new FitXYStrategy();
        }
        styledAttributes.recycle();
    }

    private void rotationChanged() {
        if (isActive() && getDisplayRotation() != this.openedOrientation) {
            pause();
            resume();
        }
    }

    private void setupSurfaceView() {
        if (!this.useTextureView || VERSION.SDK_INT < 14) {
            this.surfaceView = new SurfaceView(getContext());
            if (VERSION.SDK_INT < 11) {
                this.surfaceView.getHolder().setType(3);
            }
            this.surfaceView.getHolder().addCallback(this.surfaceCallback);
            addView(this.surfaceView);
            return;
        }
        this.textureView = new TextureView(getContext());
        this.textureView.setSurfaceTextureListener(surfaceTextureListener());
        addView(this.textureView);
    }

    public void addStateListener(StateListener listener) {
        this.stateListeners.add(listener);
    }

    private void calculateFrames() {
        if (this.containerSize == null || this.previewSize == null || this.displayConfiguration == null) {
            this.previewFramingRect = null;
            this.framingRect = null;
            this.surfaceRect = null;
            throw new IllegalStateException("containerSize or previewSize is not set yet");
        }
        int previewWidth = this.previewSize.width;
        int previewHeight = this.previewSize.height;
        int width = this.containerSize.width;
        int height = this.containerSize.height;
        this.surfaceRect = this.displayConfiguration.scalePreview(this.previewSize);
        this.framingRect = calculateFramingRect(new Rect(0, 0, width, height), this.surfaceRect);
        Rect frameInPreview = new Rect(this.framingRect);
        frameInPreview.offset(-this.surfaceRect.left, -this.surfaceRect.top);
        this.previewFramingRect = new Rect((frameInPreview.left * previewWidth) / this.surfaceRect.width(), (frameInPreview.top * previewHeight) / this.surfaceRect.height(), (frameInPreview.right * previewWidth) / this.surfaceRect.width(), (frameInPreview.bottom * previewHeight) / this.surfaceRect.height());
        if (this.previewFramingRect.width() <= 0 || this.previewFramingRect.height() <= 0) {
            this.previewFramingRect = null;
            this.framingRect = null;
            Log.w(TAG, "Preview frame is too small");
            return;
        }
        this.fireState.previewSized();
    }

    public void setTorch(boolean on) {
        this.torchOn = on;
        if (this.cameraInstance != null) {
            this.cameraInstance.setTorch(on);
        }
    }

    private void containerSized(Size containerSize) {
        this.containerSize = containerSize;
        if (this.cameraInstance != null && this.cameraInstance.getDisplayConfiguration() == null) {
            this.displayConfiguration = new DisplayConfiguration(getDisplayRotation(), containerSize);
            this.displayConfiguration.setPreviewScalingStrategy(getPreviewScalingStrategy());
            this.cameraInstance.setDisplayConfiguration(this.displayConfiguration);
            this.cameraInstance.configureCamera();
            if (this.torchOn) {
                this.cameraInstance.setTorch(this.torchOn);
            }
        }
    }

    public void setPreviewScalingStrategy(PreviewScalingStrategy previewScalingStrategy) {
        this.previewScalingStrategy = previewScalingStrategy;
    }

    public PreviewScalingStrategy getPreviewScalingStrategy() {
        if (this.previewScalingStrategy != null) {
            return this.previewScalingStrategy;
        }
        if (this.textureView != null) {
            return new CenterCropStrategy();
        }
        return new FitCenterStrategy();
    }

    private void previewSized(Size size) {
        this.previewSize = size;
        if (this.containerSize != null) {
            calculateFrames();
            requestLayout();
            startPreviewIfReady();
        }
    }

    protected Matrix calculateTextureTransform(Size textureSize, Size previewSize) {
        float scaleX;
        float scaleY;
        float ratioTexture = ((float) textureSize.width) / ((float) textureSize.height);
        float ratioPreview = ((float) previewSize.width) / ((float) previewSize.height);
        if (ratioTexture < ratioPreview) {
            scaleX = ratioPreview / ratioTexture;
            scaleY = 1.0f;
        } else {
            scaleX = 1.0f;
            scaleY = ratioTexture / ratioPreview;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);
        matrix.postTranslate((((float) textureSize.width) - (((float) textureSize.width) * scaleX)) / 2.0f, (((float) textureSize.height) - (((float) textureSize.height) * scaleY)) / 2.0f);
        return matrix;
    }

    private void startPreviewIfReady() {
        if (this.currentSurfaceSize != null && this.previewSize != null && this.surfaceRect != null) {
            if (this.surfaceView != null && this.currentSurfaceSize.equals(new Size(this.surfaceRect.width(), this.surfaceRect.height()))) {
                startCameraPreview(new CameraSurface(this.surfaceView.getHolder()));
            } else if (this.textureView != null && VERSION.SDK_INT >= 14 && this.textureView.getSurfaceTexture() != null) {
                if (this.previewSize != null) {
                    this.textureView.setTransform(calculateTextureTransform(new Size(this.textureView.getWidth(), this.textureView.getHeight()), this.previewSize));
                }
                startCameraPreview(new CameraSurface(this.textureView.getSurfaceTexture()));
            }
        }
    }

    @SuppressLint({"DrawAllocation"})
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        containerSized(new Size(r - l, b - t));
        if (this.surfaceView != null) {
            if (this.surfaceRect == null) {
                this.surfaceView.layout(0, 0, getWidth(), getHeight());
            } else {
                this.surfaceView.layout(this.surfaceRect.left, this.surfaceRect.top, this.surfaceRect.right, this.surfaceRect.bottom);
            }
        } else if (this.textureView != null && VERSION.SDK_INT >= 14) {
            this.textureView.layout(0, 0, getWidth(), getHeight());
        }
    }

    public Rect getFramingRect() {
        return this.framingRect;
    }

    public Rect getPreviewFramingRect() {
        return this.previewFramingRect;
    }

    public CameraSettings getCameraSettings() {
        return this.cameraSettings;
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        this.cameraSettings = cameraSettings;
    }

    public void resume() {
        Util.validateMainThread();
        Log.d(TAG, "resume()");
        initCamera();
        if (this.currentSurfaceSize != null) {
            startPreviewIfReady();
        } else if (this.surfaceView != null) {
            this.surfaceView.getHolder().addCallback(this.surfaceCallback);
        } else if (this.textureView != null && VERSION.SDK_INT >= 14) {
            this.textureView.setSurfaceTextureListener(surfaceTextureListener());
        }
        requestLayout();
        this.rotationListener.listen(getContext(), this.rotationCallback);
    }

    public void pause() {
        Util.validateMainThread();
        Log.d(TAG, "pause()");
        this.openedOrientation = -1;
        if (this.cameraInstance != null) {
            this.cameraInstance.close();
            this.cameraInstance = null;
            this.previewActive = false;
        }
        if (this.currentSurfaceSize == null && this.surfaceView != null) {
            this.surfaceView.getHolder().removeCallback(this.surfaceCallback);
        }
        if (this.currentSurfaceSize == null && this.textureView != null && VERSION.SDK_INT >= 14) {
            this.textureView.setSurfaceTextureListener(null);
        }
        this.containerSize = null;
        this.previewSize = null;
        this.previewFramingRect = null;
        this.rotationListener.stop();
        this.fireState.previewStopped();
    }

    public Size getFramingRectSize() {
        return this.framingRectSize;
    }

    public void setFramingRectSize(Size framingRectSize) {
        this.framingRectSize = framingRectSize;
    }

    public double getMarginFraction() {
        return this.marginFraction;
    }

    public void setMarginFraction(double marginFraction) {
        if (marginFraction >= 0.5d) {
            throw new IllegalArgumentException("The margin fraction must be less than 0.5");
        }
        this.marginFraction = marginFraction;
    }

    public boolean isUseTextureView() {
        return this.useTextureView;
    }

    public void setUseTextureView(boolean useTextureView) {
        this.useTextureView = useTextureView;
    }

    protected boolean isActive() {
        return this.cameraInstance != null;
    }

    private int getDisplayRotation() {
        return this.windowManager.getDefaultDisplay().getRotation();
    }

    private void initCamera() {
        if (this.cameraInstance != null) {
            Log.w(TAG, "initCamera called twice");
            return;
        }
        this.cameraInstance = new CameraInstance(getContext());
        this.cameraInstance.setCameraSettings(this.cameraSettings);
        this.cameraInstance.setReadyHandler(this.stateHandler);
        this.cameraInstance.open();
        this.openedOrientation = getDisplayRotation();
    }

    private void startCameraPreview(CameraSurface surface) {
        if (!this.previewActive && this.cameraInstance != null) {
            Log.i(TAG, "Starting preview");
            this.cameraInstance.setSurface(surface);
            this.cameraInstance.startPreview();
            this.previewActive = true;
            previewStarted();
            this.fireState.previewStarted();
        }
    }

    protected void previewStarted() {
    }

    public CameraInstance getCameraInstance() {
        return this.cameraInstance;
    }

    public boolean isPreviewActive() {
        return this.previewActive;
    }

    protected Rect calculateFramingRect(Rect container, Rect surface) {
        Rect intersection = new Rect(container);
        boolean intersects = intersection.intersect(surface);
        if (this.framingRectSize != null) {
            intersection.inset(Math.max(0, (intersection.width() - this.framingRectSize.width) / 2), Math.max(0, (intersection.height() - this.framingRectSize.height) / 2));
        } else {
            int margin = (int) Math.min(((double) intersection.width()) * this.marginFraction, ((double) intersection.height()) * this.marginFraction);
            intersection.inset(margin, margin);
            if (intersection.height() > intersection.width()) {
                intersection.inset(0, (intersection.height() - intersection.width()) / 2);
            }
        }
        return intersection;
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        Bundle myState = new Bundle();
        myState.putParcelable("super", superState);
        myState.putBoolean("torch", this.torchOn);
        return myState;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle myState = (Bundle) state;
            super.onRestoreInstanceState(myState.getParcelable("super"));
            setTorch(myState.getBoolean("torch"));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
