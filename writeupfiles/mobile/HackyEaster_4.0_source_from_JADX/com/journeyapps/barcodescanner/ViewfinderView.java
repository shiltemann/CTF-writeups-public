package com.journeyapps.barcodescanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.view.View;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.C0035R;
import com.journeyapps.barcodescanner.CameraPreview.StateListener;
import java.util.ArrayList;
import java.util.List;

public class ViewfinderView extends View {
    protected static final long ANIMATION_DELAY = 80;
    protected static final int CURRENT_POINT_OPACITY = 160;
    protected static final int MAX_RESULT_POINTS = 20;
    protected static final int POINT_SIZE = 6;
    protected static final int[] SCANNER_ALPHA;
    protected static final String TAG;
    protected CameraPreview cameraPreview;
    protected Rect framingRect;
    protected final int laserColor;
    protected List<ResultPoint> lastPossibleResultPoints;
    protected final int maskColor;
    protected final Paint paint;
    protected List<ResultPoint> possibleResultPoints;
    protected Rect previewFramingRect;
    protected Bitmap resultBitmap;
    protected final int resultColor;
    protected final int resultPointColor;
    protected int scannerAlpha;

    /* renamed from: com.journeyapps.barcodescanner.ViewfinderView.1 */
    class C01241 implements StateListener {
        C01241() {
        }

        public void previewSized() {
            ViewfinderView.this.refreshSizes();
            ViewfinderView.this.invalidate();
        }

        public void previewStarted() {
        }

        public void previewStopped() {
        }

        public void cameraError(Exception error) {
        }
    }

    static {
        TAG = ViewfinderView.class.getSimpleName();
        SCANNER_ALPHA = new int[]{0, 64, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS, 192, MotionEventCompat.ACTION_MASK, 192, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS, 64};
    }

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint(1);
        Resources resources = getResources();
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, C0035R.styleable.zxing_finder);
        this.maskColor = attributes.getColor(C0035R.styleable.zxing_finder_zxing_viewfinder_mask, resources.getColor(C0035R.color.zxing_viewfinder_mask));
        this.resultColor = attributes.getColor(C0035R.styleable.zxing_finder_zxing_result_view, resources.getColor(C0035R.color.zxing_result_view));
        this.laserColor = attributes.getColor(C0035R.styleable.zxing_finder_zxing_viewfinder_laser, resources.getColor(C0035R.color.zxing_viewfinder_laser));
        this.resultPointColor = attributes.getColor(C0035R.styleable.zxing_finder_zxing_possible_result_points, resources.getColor(C0035R.color.zxing_possible_result_points));
        attributes.recycle();
        this.scannerAlpha = 0;
        this.possibleResultPoints = new ArrayList(5);
        this.lastPossibleResultPoints = null;
    }

    public void setCameraPreview(CameraPreview view) {
        this.cameraPreview = view;
        view.addStateListener(new C01241());
    }

    protected void refreshSizes() {
        if (this.cameraPreview != null) {
            Rect framingRect = this.cameraPreview.getFramingRect();
            Rect previewFramingRect = this.cameraPreview.getPreviewFramingRect();
            if (framingRect != null && previewFramingRect != null) {
                this.framingRect = framingRect;
                this.previewFramingRect = previewFramingRect;
            }
        }
    }

    @SuppressLint({"DrawAllocation"})
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (this.framingRect != null && this.previewFramingRect != null) {
            int i;
            Rect frame = this.framingRect;
            Rect previewFrame = this.previewFramingRect;
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            Paint paint = this.paint;
            if (this.resultBitmap != null) {
                i = this.resultColor;
            } else {
                i = this.maskColor;
            }
            paint.setColor(i);
            canvas.drawRect(0.0f, 0.0f, (float) width, (float) frame.top, this.paint);
            canvas.drawRect(0.0f, (float) frame.top, (float) frame.left, (float) (frame.bottom + 1), this.paint);
            canvas.drawRect((float) (frame.right + 1), (float) frame.top, (float) width, (float) (frame.bottom + 1), this.paint);
            canvas.drawRect(0.0f, (float) (frame.bottom + 1), (float) width, (float) height, this.paint);
            if (this.resultBitmap != null) {
                this.paint.setAlpha(CURRENT_POINT_OPACITY);
                canvas.drawBitmap(this.resultBitmap, null, frame, this.paint);
                return;
            }
            this.paint.setColor(this.laserColor);
            this.paint.setAlpha(SCANNER_ALPHA[this.scannerAlpha]);
            this.scannerAlpha = (this.scannerAlpha + 1) % SCANNER_ALPHA.length;
            int middle = (frame.height() / 2) + frame.top;
            canvas.drawRect((float) (frame.left + 2), (float) (middle - 1), (float) (frame.right - 1), (float) (middle + 2), this.paint);
            float scaleX = ((float) frame.width()) / ((float) previewFrame.width());
            float scaleY = ((float) frame.height()) / ((float) previewFrame.height());
            List<ResultPoint> currentPossible = this.possibleResultPoints;
            List<ResultPoint> currentLast = this.lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                this.lastPossibleResultPoints = null;
            } else {
                this.possibleResultPoints = new ArrayList(5);
                this.lastPossibleResultPoints = currentPossible;
                this.paint.setAlpha(CURRENT_POINT_OPACITY);
                this.paint.setColor(this.resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle((float) (((int) (point.getX() * scaleX)) + frameLeft), (float) (((int) (point.getY() * scaleY)) + frameTop), 6.0f, this.paint);
                }
            }
            if (currentLast != null) {
                this.paint.setAlpha(80);
                this.paint.setColor(this.resultPointColor);
                for (ResultPoint point2 : currentLast) {
                    canvas.drawCircle((float) (((int) (point2.getX() * scaleX)) + frameLeft), (float) (((int) (point2.getY() * scaleY)) + frameTop), 3.0f, this.paint);
                }
            }
            postInvalidateDelayed(ANIMATION_DELAY, frame.left - 6, frame.top - 6, frame.right + POINT_SIZE, frame.bottom + POINT_SIZE);
        }
    }

    public void drawViewfinder() {
        Bitmap resultBitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (resultBitmap != null) {
            resultBitmap.recycle();
        }
        invalidate();
    }

    public void drawResultBitmap(Bitmap result) {
        this.resultBitmap = result;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        List<ResultPoint> points = this.possibleResultPoints;
        points.add(point);
        int size = points.size();
        if (size > MAX_RESULT_POINTS) {
            points.subList(0, size - 10).clear();
        }
    }
}
