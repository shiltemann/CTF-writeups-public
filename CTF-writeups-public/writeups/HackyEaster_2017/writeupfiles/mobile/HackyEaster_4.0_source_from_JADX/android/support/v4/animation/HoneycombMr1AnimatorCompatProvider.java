package android.support.v4.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;

@TargetApi(12)
@RequiresApi(12)
class HoneycombMr1AnimatorCompatProvider implements AnimatorProvider {
    private TimeInterpolator mDefaultInterpolator;

    static class AnimatorListenerCompatWrapper implements AnimatorListener {
        final ValueAnimatorCompat mValueAnimatorCompat;
        final AnimatorListenerCompat mWrapped;

        public AnimatorListenerCompatWrapper(AnimatorListenerCompat wrapped, ValueAnimatorCompat valueAnimatorCompat) {
            this.mWrapped = wrapped;
            this.mValueAnimatorCompat = valueAnimatorCompat;
        }

        public void onAnimationStart(Animator animation) {
            this.mWrapped.onAnimationStart(this.mValueAnimatorCompat);
        }

        public void onAnimationEnd(Animator animation) {
            this.mWrapped.onAnimationEnd(this.mValueAnimatorCompat);
        }

        public void onAnimationCancel(Animator animation) {
            this.mWrapped.onAnimationCancel(this.mValueAnimatorCompat);
        }

        public void onAnimationRepeat(Animator animation) {
            this.mWrapped.onAnimationRepeat(this.mValueAnimatorCompat);
        }
    }

    static class HoneycombValueAnimatorCompat implements ValueAnimatorCompat {
        final Animator mWrapped;

        /* renamed from: android.support.v4.animation.HoneycombMr1AnimatorCompatProvider.HoneycombValueAnimatorCompat.1 */
        class C00011 implements AnimatorUpdateListener {
            final /* synthetic */ AnimatorUpdateListenerCompat val$animatorUpdateListener;

            C00011(AnimatorUpdateListenerCompat animatorUpdateListenerCompat) {
                this.val$animatorUpdateListener = animatorUpdateListenerCompat;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.val$animatorUpdateListener.onAnimationUpdate(HoneycombValueAnimatorCompat.this);
            }
        }

        public HoneycombValueAnimatorCompat(Animator wrapped) {
            this.mWrapped = wrapped;
        }

        public void setTarget(View view) {
            this.mWrapped.setTarget(view);
        }

        public void addListener(AnimatorListenerCompat listener) {
            this.mWrapped.addListener(new AnimatorListenerCompatWrapper(listener, this));
        }

        public void setDuration(long duration) {
            this.mWrapped.setDuration(duration);
        }

        public void start() {
            this.mWrapped.start();
        }

        public void cancel() {
            this.mWrapped.cancel();
        }

        public void addUpdateListener(AnimatorUpdateListenerCompat animatorUpdateListener) {
            if (this.mWrapped instanceof ValueAnimator) {
                ((ValueAnimator) this.mWrapped).addUpdateListener(new C00011(animatorUpdateListener));
            }
        }

        public float getAnimatedFraction() {
            return ((ValueAnimator) this.mWrapped).getAnimatedFraction();
        }
    }

    HoneycombMr1AnimatorCompatProvider() {
    }

    public ValueAnimatorCompat emptyValueAnimator() {
        return new HoneycombValueAnimatorCompat(ValueAnimator.ofFloat(new float[]{0.0f, 1.0f}));
    }

    public void clearInterpolator(View view) {
        if (this.mDefaultInterpolator == null) {
            this.mDefaultInterpolator = new ValueAnimator().getInterpolator();
        }
        view.animate().setInterpolator(this.mDefaultInterpolator);
    }
}
