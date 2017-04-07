package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import android.util.Log;
import com.journeyapps.barcodescanner.Size;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class PreviewScalingStrategy {
    private static final String TAG;

    /* renamed from: com.journeyapps.barcodescanner.camera.PreviewScalingStrategy.1 */
    class C00651 implements Comparator<Size> {
        final /* synthetic */ Size val$desired;

        C00651(Size size) {
            this.val$desired = size;
        }

        public int compare(Size a, Size b) {
            return Float.compare(PreviewScalingStrategy.this.getScore(b, this.val$desired), PreviewScalingStrategy.this.getScore(a, this.val$desired));
        }
    }

    public abstract Rect scalePreview(Size size, Size size2);

    static {
        TAG = PreviewScalingStrategy.class.getSimpleName();
    }

    public Size getBestPreviewSize(List<Size> sizes, Size desired) {
        List<Size> ordered = getBestPreviewOrder(sizes, desired);
        Log.i(TAG, "Viewfinder size: " + desired);
        Log.i(TAG, "Preview in order of preference: " + ordered);
        return (Size) ordered.get(0);
    }

    public List<Size> getBestPreviewOrder(List<Size> sizes, Size desired) {
        if (desired != null) {
            Collections.sort(sizes, new C00651(desired));
        }
        return sizes;
    }

    protected float getScore(Size size, Size desired) {
        return 0.5f;
    }
}
