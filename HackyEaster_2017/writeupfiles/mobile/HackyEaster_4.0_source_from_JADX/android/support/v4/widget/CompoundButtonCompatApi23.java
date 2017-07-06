package android.support.v4.widget;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.widget.CompoundButton;

@TargetApi(23)
@RequiresApi(23)
class CompoundButtonCompatApi23 {
    CompoundButtonCompatApi23() {
    }

    static Drawable getButtonDrawable(CompoundButton button) {
        return button.getButtonDrawable();
    }
}
