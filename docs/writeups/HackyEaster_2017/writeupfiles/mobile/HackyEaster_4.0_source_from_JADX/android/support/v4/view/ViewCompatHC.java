package android.support.v4.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewParent;

@TargetApi(11)
@RequiresApi(11)
class ViewCompatHC {
    ViewCompatHC() {
    }

    static long getFrameTime() {
        return ValueAnimator.getFrameDelay();
    }

    public static float getAlpha(View view) {
        return view.getAlpha();
    }

    public static void setLayerType(View view, int layerType, Paint paint) {
        view.setLayerType(layerType, paint);
    }

    public static int getLayerType(View view) {
        return view.getLayerType();
    }

    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        return View.resolveSizeAndState(size, measureSpec, childMeasuredState);
    }

    public static int getMeasuredWidthAndState(View view) {
        return view.getMeasuredWidthAndState();
    }

    public static int getMeasuredHeightAndState(View view) {
        return view.getMeasuredHeightAndState();
    }

    public static int getMeasuredState(View view) {
        return view.getMeasuredState();
    }

    public static float getTranslationX(View view) {
        return view.getTranslationX();
    }

    public static float getTranslationY(View view) {
        return view.getTranslationY();
    }

    public static float getX(View view) {
        return view.getX();
    }

    public static float getY(View view) {
        return view.getY();
    }

    public static float getRotation(View view) {
        return view.getRotation();
    }

    public static float getRotationX(View view) {
        return view.getRotationX();
    }

    public static float getRotationY(View view) {
        return view.getRotationY();
    }

    public static float getScaleX(View view) {
        return view.getScaleX();
    }

    public static float getScaleY(View view) {
        return view.getScaleY();
    }

    public static void setTranslationX(View view, float value) {
        view.setTranslationX(value);
    }

    public static void setTranslationY(View view, float value) {
        view.setTranslationY(value);
    }

    public static Matrix getMatrix(View view) {
        return view.getMatrix();
    }

    public static void setAlpha(View view, float value) {
        view.setAlpha(value);
    }

    public static void setX(View view, float value) {
        view.setX(value);
    }

    public static void setY(View view, float value) {
        view.setY(value);
    }

    public static void setRotation(View view, float value) {
        view.setRotation(value);
    }

    public static void setRotationX(View view, float value) {
        view.setRotationX(value);
    }

    public static void setRotationY(View view, float value) {
        view.setRotationY(value);
    }

    public static void setScaleX(View view, float value) {
        view.setScaleX(value);
    }

    public static void setScaleY(View view, float value) {
        view.setScaleY(value);
    }

    public static void setPivotX(View view, float value) {
        view.setPivotX(value);
    }

    public static void setPivotY(View view, float value) {
        view.setPivotY(value);
    }

    public static float getPivotX(View view) {
        return view.getPivotX();
    }

    public static float getPivotY(View view) {
        return view.getPivotY();
    }

    public static void jumpDrawablesToCurrentState(View view) {
        view.jumpDrawablesToCurrentState();
    }

    public static void setSaveFromParentEnabled(View view, boolean enabled) {
        view.setSaveFromParentEnabled(enabled);
    }

    public static void setActivated(View view, boolean activated) {
        view.setActivated(activated);
    }

    public static int combineMeasuredStates(int curState, int newState) {
        return View.combineMeasuredStates(curState, newState);
    }

    static void offsetTopAndBottom(View view, int offset) {
        view.offsetTopAndBottom(offset);
        if (view.getVisibility() == 0) {
            tickleInvalidationFlag(view);
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                tickleInvalidationFlag((View) parent);
            }
        }
    }

    static void offsetLeftAndRight(View view, int offset) {
        view.offsetLeftAndRight(offset);
        if (view.getVisibility() == 0) {
            tickleInvalidationFlag(view);
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                tickleInvalidationFlag((View) parent);
            }
        }
    }

    private static void tickleInvalidationFlag(View view) {
        float y = view.getTranslationY();
        view.setTranslationY(1.0f + y);
        view.setTranslationY(y);
    }
}
