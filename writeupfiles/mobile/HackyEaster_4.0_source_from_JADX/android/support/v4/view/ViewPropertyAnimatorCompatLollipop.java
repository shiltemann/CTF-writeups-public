package android.support.v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;

@TargetApi(21)
@RequiresApi(21)
class ViewPropertyAnimatorCompatLollipop {
    ViewPropertyAnimatorCompatLollipop() {
    }

    public static void translationZ(View view, float value) {
        view.animate().translationZ(value);
    }

    public static void translationZBy(View view, float value) {
        view.animate().translationZBy(value);
    }

    public static void m8z(View view, float value) {
        view.animate().z(value);
    }

    public static void zBy(View view, float value) {
        view.animate().zBy(value);
    }
}
