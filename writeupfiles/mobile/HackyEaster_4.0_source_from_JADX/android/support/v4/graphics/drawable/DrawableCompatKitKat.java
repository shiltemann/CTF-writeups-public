package android.support.v4.graphics.drawable;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;

@TargetApi(19)
@RequiresApi(19)
class DrawableCompatKitKat {
    DrawableCompatKitKat() {
    }

    public static void setAutoMirrored(Drawable drawable, boolean mirrored) {
        drawable.setAutoMirrored(mirrored);
    }

    public static boolean isAutoMirrored(Drawable drawable) {
        return drawable.isAutoMirrored();
    }

    public static Drawable wrapForTinting(Drawable drawable) {
        if (drawable instanceof TintAwareDrawable) {
            return drawable;
        }
        return new DrawableWrapperKitKat(drawable);
    }

    public static int getAlpha(Drawable drawable) {
        return drawable.getAlpha();
    }
}
