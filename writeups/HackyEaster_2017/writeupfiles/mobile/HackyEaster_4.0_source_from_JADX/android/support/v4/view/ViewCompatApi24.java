package android.support.v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.PointerIcon;
import android.view.View;

@TargetApi(24)
@RequiresApi(24)
class ViewCompatApi24 {
    ViewCompatApi24() {
    }

    public static void setPointerIcon(View view, Object pointerIcon) {
        view.setPointerIcon((PointerIcon) pointerIcon);
    }
}
