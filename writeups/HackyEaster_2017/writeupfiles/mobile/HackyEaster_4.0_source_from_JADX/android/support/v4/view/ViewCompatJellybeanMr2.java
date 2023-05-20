package android.support.v4.view;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.View;

@TargetApi(18)
@RequiresApi(18)
class ViewCompatJellybeanMr2 {
    ViewCompatJellybeanMr2() {
    }

    public static Rect getClipBounds(View view) {
        return view.getClipBounds();
    }

    public static void setClipBounds(View view, Rect clipBounds) {
        view.setClipBounds(clipBounds);
    }

    public static boolean isInLayout(View view) {
        return view.isInLayout();
    }
}
