package android.support.v4.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

public final class ScrollerCompat {
    private final boolean mIsIcsOrNewer;
    OverScroller mScroller;

    public static ScrollerCompat create(Context context) {
        return create(context, null);
    }

    public static ScrollerCompat create(Context context, Interpolator interpolator) {
        return new ScrollerCompat(VERSION.SDK_INT >= 14, context, interpolator);
    }

    ScrollerCompat(boolean isIcsOrNewer, Context context, Interpolator interpolator) {
        this.mIsIcsOrNewer = isIcsOrNewer;
        this.mScroller = interpolator != null ? new OverScroller(context, interpolator) : new OverScroller(context);
    }

    public boolean isFinished() {
        return this.mScroller.isFinished();
    }

    public int getCurrX() {
        return this.mScroller.getCurrX();
    }

    public int getCurrY() {
        return this.mScroller.getCurrY();
    }

    public int getFinalX() {
        return this.mScroller.getFinalX();
    }

    public int getFinalY() {
        return this.mScroller.getFinalY();
    }

    public float getCurrVelocity() {
        return this.mIsIcsOrNewer ? ScrollerCompatIcs.getCurrVelocity(this.mScroller) : 0.0f;
    }

    public boolean computeScrollOffset() {
        return this.mScroller.computeScrollOffset();
    }

    public void startScroll(int startX, int startY, int dx, int dy) {
        this.mScroller.startScroll(startX, startY, dx, dy);
    }

    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        this.mScroller.startScroll(startX, startY, dx, dy, duration);
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY);
    }

    public boolean springBack(int startX, int startY, int minX, int maxX, int minY, int maxY) {
        return this.mScroller.springBack(startX, startY, minX, maxX, minY, maxY);
    }

    public void abortAnimation() {
        this.mScroller.abortAnimation();
    }

    public void notifyHorizontalEdgeReached(int startX, int finalX, int overX) {
        this.mScroller.notifyHorizontalEdgeReached(startX, finalX, overX);
    }

    public void notifyVerticalEdgeReached(int startY, int finalY, int overY) {
        this.mScroller.notifyVerticalEdgeReached(startY, finalY, overY);
    }

    public boolean isOverScrolled() {
        return this.mScroller.isOverScrolled();
    }
}
