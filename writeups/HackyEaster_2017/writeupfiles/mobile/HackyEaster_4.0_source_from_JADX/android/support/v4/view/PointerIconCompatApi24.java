package android.support.v4.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.RequiresApi;
import android.view.PointerIcon;

@TargetApi(24)
@RequiresApi(24)
class PointerIconCompatApi24 {
    PointerIconCompatApi24() {
    }

    public static Object getSystemIcon(Context context, int style) {
        return PointerIcon.getSystemIcon(context, style);
    }

    public static Object create(Bitmap bitmap, float hotSpotX, float hotSpotY) {
        return PointerIcon.create(bitmap, hotSpotX, hotSpotY);
    }

    public static Object load(Resources resources, int resourceId) {
        return PointerIcon.load(resources, resourceId);
    }
}
