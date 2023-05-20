package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import android.util.Log;
import com.journeyapps.barcodescanner.Size;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LegacyPreviewScalingStrategy extends PreviewScalingStrategy {
    private static final String TAG;

    /* renamed from: com.journeyapps.barcodescanner.camera.LegacyPreviewScalingStrategy.1 */
    class C00641 implements Comparator<Size> {
        final /* synthetic */ Size val$desired;

        C00641(Size size) {
            this.val$desired = size;
        }

        public int compare(Size a, Size b) {
            int aScale = LegacyPreviewScalingStrategy.scale(a, this.val$desired).width - a.width;
            int bScale = LegacyPreviewScalingStrategy.scale(b, this.val$desired).width - b.width;
            if (aScale == 0 && bScale == 0) {
                return a.compareTo(b);
            }
            if (aScale == 0) {
                return -1;
            }
            if (bScale == 0) {
                return 1;
            }
            if (aScale < 0 && bScale < 0) {
                return a.compareTo(b);
            }
            if (aScale > 0 && bScale > 0) {
                return -a.compareTo(b);
            }
            if (aScale >= 0) {
                return 1;
            }
            return -1;
        }
    }

    static {
        TAG = LegacyPreviewScalingStrategy.class.getSimpleName();
    }

    public Size getBestPreviewSize(List<Size> sizes, Size desired) {
        if (desired == null) {
            return (Size) sizes.get(0);
        }
        Collections.sort(sizes, new C00641(desired));
        Log.i(TAG, "Viewfinder size: " + desired);
        Log.i(TAG, "Preview in order of preference: " + sizes);
        return (Size) sizes.get(0);
    }

    public static Size scale(Size from, Size to) {
        Size current = from;
        if (to.fitsIn(current)) {
            Size scaled66;
            while (true) {
                scaled66 = current.scale(2, 3);
                Size scaled50 = current.scale(1, 2);
                if (!to.fitsIn(scaled50)) {
                    break;
                }
                current = scaled50;
            }
            if (to.fitsIn(scaled66)) {
                return scaled66;
            }
            return current;
        }
        while (true) {
            Size scaled150 = current.scale(3, 2);
            Size scaled200 = current.scale(2, 1);
            if (to.fitsIn(scaled150)) {
                return scaled150;
            }
            if (to.fitsIn(scaled200)) {
                return scaled200;
            }
            current = scaled200;
        }
    }

    public Rect scalePreview(Size previewSize, Size viewfinderSize) {
        Size scaledPreview = scale(previewSize, viewfinderSize);
        Log.i(TAG, "Preview: " + previewSize + "; Scaled: " + scaledPreview + "; Want: " + viewfinderSize);
        int dx = (scaledPreview.width - viewfinderSize.width) / 2;
        int dy = (scaledPreview.height - viewfinderSize.height) / 2;
        return new Rect(-dx, -dy, scaledPreview.width - dx, scaledPreview.height - dy);
    }
}
