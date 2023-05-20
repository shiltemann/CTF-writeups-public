package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import com.journeyapps.barcodescanner.Size;

public class FitXYStrategy extends PreviewScalingStrategy {
    private static final String TAG;

    static {
        TAG = FitXYStrategy.class.getSimpleName();
    }

    private static float absRatio(float ratio) {
        if (ratio < 1.0f) {
            return 1.0f / ratio;
        }
        return ratio;
    }

    protected float getScore(Size size, Size desired) {
        if (size.width <= 0 || size.height <= 0) {
            return 0.0f;
        }
        float scaleX = absRatio((((float) size.width) * 1.0f) / ((float) desired.width));
        float scaleScore = (1.0f / scaleX) / absRatio((((float) size.height) * 1.0f) / ((float) desired.height));
        float distortion = absRatio(((((float) size.width) * 1.0f) / ((float) size.height)) / ((((float) desired.width) * 1.0f) / ((float) desired.height)));
        return scaleScore * (((1.0f / distortion) / distortion) / distortion);
    }

    public Rect scalePreview(Size previewSize, Size viewfinderSize) {
        return new Rect(0, 0, viewfinderSize.width, viewfinderSize.height);
    }
}
