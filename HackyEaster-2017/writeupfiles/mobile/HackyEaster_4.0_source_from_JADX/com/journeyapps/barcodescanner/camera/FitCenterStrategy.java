package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import android.util.Log;
import com.journeyapps.barcodescanner.Size;

public class FitCenterStrategy extends PreviewScalingStrategy {
    private static final String TAG;

    static {
        TAG = FitCenterStrategy.class.getSimpleName();
    }

    protected float getScore(Size size, Size desired) {
        if (size.width <= 0 || size.height <= 0) {
            return 0.0f;
        }
        float scaleScore;
        Size scaled = size.scaleFit(desired);
        float scaleRatio = (((float) scaled.width) * 1.0f) / ((float) size.width);
        if (scaleRatio > 1.0f) {
            scaleScore = (float) Math.pow((double) (1.0f / scaleRatio), 1.1d);
        } else {
            scaleScore = scaleRatio;
        }
        float cropRatio = ((((float) desired.width) * 1.0f) / ((float) scaled.width)) * ((((float) desired.height) * 1.0f) / ((float) scaled.height));
        return scaleScore * (((1.0f / cropRatio) / cropRatio) / cropRatio);
    }

    public Rect scalePreview(Size previewSize, Size viewfinderSize) {
        Size scaledPreview = previewSize.scaleFit(viewfinderSize);
        Log.i(TAG, "Preview: " + previewSize + "; Scaled: " + scaledPreview + "; Want: " + viewfinderSize);
        int dx = (scaledPreview.width - viewfinderSize.width) / 2;
        int dy = (scaledPreview.height - viewfinderSize.height) / 2;
        return new Rect(-dx, -dy, scaledPreview.width - dx, scaledPreview.height - dy);
    }
}
