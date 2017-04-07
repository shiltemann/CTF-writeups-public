package android.support.v4.view;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.Gravity;

@TargetApi(17)
@RequiresApi(17)
class GravityCompatJellybeanMr1 {
    GravityCompatJellybeanMr1() {
    }

    public static int getAbsoluteGravity(int gravity, int layoutDirection) {
        return Gravity.getAbsoluteGravity(gravity, layoutDirection);
    }

    public static void apply(int gravity, int w, int h, Rect container, Rect outRect, int layoutDirection) {
        Gravity.apply(gravity, w, h, container, outRect, layoutDirection);
    }

    public static void apply(int gravity, int w, int h, Rect container, int xAdj, int yAdj, Rect outRect, int layoutDirection) {
        Gravity.apply(gravity, w, h, container, xAdj, yAdj, outRect, layoutDirection);
    }

    public static void applyDisplay(int gravity, Rect display, Rect inoutObj, int layoutDirection) {
        Gravity.applyDisplay(gravity, display, inoutObj, layoutDirection);
    }
}
