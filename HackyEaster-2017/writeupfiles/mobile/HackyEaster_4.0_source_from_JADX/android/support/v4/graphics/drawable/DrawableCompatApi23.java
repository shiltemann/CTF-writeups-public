package android.support.v4.graphics.drawable;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
class DrawableCompatApi23 {
    DrawableCompatApi23() {
    }

    public static boolean setLayoutDirection(Drawable drawable, int layoutDirection) {
        return drawable.setLayoutDirection(layoutDirection);
    }

    public static int getLayoutDirection(Drawable drawable) {
        return drawable.getLayoutDirection();
    }
}
