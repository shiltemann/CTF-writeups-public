package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import com.journeyapps.barcodescanner.Size;
import java.util.List;

public class DisplayConfiguration {
    private static final String TAG;
    private boolean center;
    private PreviewScalingStrategy previewScalingStrategy;
    private int rotation;
    private Size viewfinderSize;

    static {
        TAG = DisplayConfiguration.class.getSimpleName();
    }

    public DisplayConfiguration(int rotation) {
        this.center = false;
        this.previewScalingStrategy = new FitCenterStrategy();
        this.rotation = rotation;
    }

    public DisplayConfiguration(int rotation, Size viewfinderSize) {
        this.center = false;
        this.previewScalingStrategy = new FitCenterStrategy();
        this.rotation = rotation;
        this.viewfinderSize = viewfinderSize;
    }

    public int getRotation() {
        return this.rotation;
    }

    public Size getViewfinderSize() {
        return this.viewfinderSize;
    }

    public PreviewScalingStrategy getPreviewScalingStrategy() {
        return this.previewScalingStrategy;
    }

    public void setPreviewScalingStrategy(PreviewScalingStrategy previewScalingStrategy) {
        this.previewScalingStrategy = previewScalingStrategy;
    }

    public Size getDesiredPreviewSize(boolean rotate) {
        if (this.viewfinderSize == null) {
            return null;
        }
        if (rotate) {
            return this.viewfinderSize.rotate();
        }
        return this.viewfinderSize;
    }

    public Size getBestPreviewSize(List<Size> sizes, boolean isRotated) {
        return this.previewScalingStrategy.getBestPreviewSize(sizes, getDesiredPreviewSize(isRotated));
    }

    public Rect scalePreview(Size previewSize) {
        return this.previewScalingStrategy.scalePreview(previewSize, this.viewfinderSize);
    }
}
