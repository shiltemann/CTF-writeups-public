package android.support.v4.view;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.WindowInsets;

@TargetApi(21)
@RequiresApi(21)
class WindowInsetsCompatApi21 {
    WindowInsetsCompatApi21() {
    }

    public static Object consumeStableInsets(Object insets) {
        return ((WindowInsets) insets).consumeStableInsets();
    }

    public static int getStableInsetBottom(Object insets) {
        return ((WindowInsets) insets).getStableInsetBottom();
    }

    public static int getStableInsetLeft(Object insets) {
        return ((WindowInsets) insets).getStableInsetLeft();
    }

    public static int getStableInsetRight(Object insets) {
        return ((WindowInsets) insets).getStableInsetRight();
    }

    public static int getStableInsetTop(Object insets) {
        return ((WindowInsets) insets).getStableInsetTop();
    }

    public static boolean hasStableInsets(Object insets) {
        return ((WindowInsets) insets).hasStableInsets();
    }

    public static boolean isConsumed(Object insets) {
        return ((WindowInsets) insets).isConsumed();
    }

    public static Object replaceSystemWindowInsets(Object insets, Rect systemWindowInsets) {
        return ((WindowInsets) insets).replaceSystemWindowInsets(systemWindowInsets);
    }
}
