package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import android.util.Log;
import com.journeyapps.barcodescanner.Size;

public class CenterCropStrategy extends PreviewScalingStrategy {
    private static final String TAG;

    static {
        TAG = CenterCropStrategy.class.getSimpleName();
    }

    protected float getScore(Size size, Size desired) {
        if (size.width <= 0 || size.height <= 0) {
            return 0.0f;
        }
        float scaleScore;
        Size scaled = size.scaleCrop(desired);
        float scaleRatio = (((float) scaled.width) * 1.0f) / ((float) size.width);
        if (scaleRatio > 1.0f) {
            scaleScore = (float) Math.pow((double) (1.0f / scaleRatio), 1.1d);
        } else {
            scaleScore = scaleRatio;
        }
        float cropRatio = ((((float) scaled.width) * 1.0f) / ((float) desired.width)) + ((((float) scaled.height) * 1.0f) / ((float) desired.height));
        return scaleScore * ((1.0f / cropRatio) / cropRatio);
    }

    public Rect scalePreview(Size previewSize, Size viewfinderSize) {
        Size scaledPreview = previewSize.scaleCrop(viewfinderSize);
        Log.i(TAG, "Preview: " + previewSize + "; Scaled: " + scaledPreview + "; Want: " + viewfinderSize);
        int dx = (scaledPreview.width - viewfinderSize.width) / 2;
        int dy = (scaledPreview.height - viewfinderSize.height) / 2;
        return new Rect(-dx, -dy, scaledPreview.width - dx, scaledPreview.height - dy);
    }
}
