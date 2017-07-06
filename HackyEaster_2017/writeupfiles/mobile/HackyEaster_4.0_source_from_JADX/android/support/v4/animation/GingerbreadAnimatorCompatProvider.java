package android.support.v4.animation;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

@TargetApi(9)
@RequiresApi(9)
class GingerbreadAnimatorCompatProvider implements AnimatorProvider {

    private static class GingerbreadFloatValueAnimator implements ValueAnimatorCompat {
        private long mDuration;
        private boolean mEnded;
        private float mFraction;
        List<AnimatorListenerCompat> mListeners;
        private Runnable mLoopRunnable;
        private long mStartTime;
        private boolean mStarted;
        View mTarget;
        List<AnimatorUpdateListenerCompat> mUpdateListeners;

        /* renamed from: android.support.v4.animation.GingerbreadAnimatorCompatProvider.GingerbreadFloatValueAnimator.1 */
        class C00001 implements Runnable {
            C00001() {
            }

            public void run() {
                float fraction = (((float) (GingerbreadFloatValueAnimator.this.getTime() - GingerbreadFloatValueAnimator.this.mStartTime)) * 1.0f) / ((float) GingerbreadFloatValueAnimator.this.mDuration);
                if (fraction > 1.0f || GingerbreadFloatValueAnimator.this.mTarget.getParent() == null) {
                    fraction = 1.0f;
                }
                GingerbreadFloatValueAnimator.this.mFraction = fraction;
                GingerbreadFloatValueAnimator.this.notifyUpdateListeners();
                if (GingerbreadFloatValueAnimator.this.mFraction >= 1.0f) {
                    GingerbreadFloatValueAnimator.this.dispatchEnd();
                } else {
                    GingerbreadFloatValueAnimator.this.mTarget.postDelayed(GingerbreadFloatValueAnimator.this.mLoopRunnable, 16);
                }
            }
        }

        public GingerbreadFloatValueAnimator() {
            this.mListeners = new ArrayList();
            this.mUpdateListeners = new ArrayList();
            this.mDuration = 200;
            this.mFraction = 0.0f;
            this.mStarted = false;
            this.mEnded = false;
            this.mLoopRunnable = new C00001();
        }

        private void notifyUpdateListeners() {
            for (int i = this.mUpdateListeners.size() - 1; i >= 0; i--) {
                ((AnimatorUpdateListenerCompat) this.mUpdateListeners.get(i)).onAnimationUpdate(this);
            }
        }

        public void setTarget(View view) {
            this.mTarget = view;
        }

        public void addListener(AnimatorListenerCompat listener) {
            this.mListeners.add(listener);
        }

        public void setDuration(long duration) {
            if (!this.mStarted) {
                this.mDuration = duration;
            }
        }

        public void start() {
            if (!this.mStarted) {
                this.mStarted = true;
                dispatchStart();
                this.mFraction = 0.0f;
                this.mStartTime = getTime();
                this.mTarget.postDelayed(this.mLoopRunnable, 16);
            }
        }

        private long getTime() {
            return this.mTarget.getDrawingTime();
        }

        private void dispatchStart() {
            for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                ((AnimatorListenerCompat) this.mListeners.get(i)).onAnimationStart(this);
            }
        }

        private void dispatchEnd() {
            for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                ((AnimatorListenerCompat) this.mListeners.get(i)).onAnimationEnd(this);
            }
        }

        private void dispatchCancel() {
            for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                ((AnimatorListenerCompat) this.mListeners.get(i)).onAnimationCancel(this);
            }
        }

        public void cancel() {
            if (!this.mEnded) {
                this.mEnded = true;
                if (this.mStarted) {
                    dispatchCancel();
                }
                dispatchEnd();
            }
        }

        public void addUpdateListener(AnimatorUpdateListenerCompat animatorUpdateListener) {
            this.mUpdateListeners.add(animatorUpdateListener);
        }

        public float getAnimatedFraction() {
            return this.mFraction;
        }
    }

    GingerbreadAnimatorCompatProvider() {
    }

    public ValueAnimatorCompat emptyValueAnimator() {
        return new GingerbreadFloatValueAnimator();
    }

    public void clearInterpolator(View view) {
    }
}
