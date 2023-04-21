package android.support.v4.widget;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

@TargetApi(17)
@RequiresApi(17)
class TextViewCompatJbMr1 {
    TextViewCompatJbMr1() {
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        Drawable drawable;
        boolean rtl = true;
        if (textView.getLayoutDirection() != 1) {
            rtl = false;
        }
        if (rtl) {
            drawable = end;
        } else {
            drawable = start;
        }
        if (!rtl) {
            start = end;
        }
        textView.setCompoundDrawables(drawable, top, start, bottom);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        Drawable drawable;
        boolean rtl = true;
        if (textView.getLayoutDirection() != 1) {
            rtl = false;
        }
        if (rtl) {
            drawable = end;
        } else {
            drawable = start;
        }
        if (!rtl) {
            start = end;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, top, start, bottom);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, int start, int top, int end, int bottom) {
        int i;
        boolean rtl = true;
        if (textView.getLayoutDirection() != 1) {
            rtl = false;
        }
        if (rtl) {
            i = end;
        } else {
            i = start;
        }
        if (!rtl) {
            start = end;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(i, top, start, bottom);
    }

    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        boolean rtl = true;
        if (textView.getLayoutDirection() != 1) {
            rtl = false;
        }
        Drawable[] compounds = textView.getCompoundDrawables();
        if (rtl) {
            Drawable start = compounds[2];
            Drawable end = compounds[0];
            compounds[0] = start;
            compounds[2] = end;
        }
        return compounds;
    }
}
