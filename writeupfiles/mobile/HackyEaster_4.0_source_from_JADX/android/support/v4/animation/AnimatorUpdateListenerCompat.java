package android.support.v4.animation;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

@RestrictTo({Scope.LIBRARY_GROUP})
public interface AnimatorUpdateListenerCompat {
    void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat);
}
