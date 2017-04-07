package android.support.v4.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat {
    private final GestureDetectorCompatImpl mImpl;

    interface GestureDetectorCompatImpl {
        boolean isLongpressEnabled();

        boolean onTouchEvent(MotionEvent motionEvent);

        void setIsLongpressEnabled(boolean z);

        void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener);
    }

    static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
        private static final int DOUBLE_TAP_TIMEOUT;
        private static final int LONGPRESS_TIMEOUT;
        private static final int LONG_PRESS = 2;
        private static final int SHOW_PRESS = 1;
        private static final int TAP = 3;
        private static final int TAP_TIMEOUT;
        private boolean mAlwaysInBiggerTapRegion;
        private boolean mAlwaysInTapRegion;
        MotionEvent mCurrentDownEvent;
        boolean mDeferConfirmSingleTap;
        OnDoubleTapListener mDoubleTapListener;
        private int mDoubleTapSlopSquare;
        private float mDownFocusX;
        private float mDownFocusY;
        private final Handler mHandler;
        private boolean mInLongPress;
        private boolean mIsDoubleTapping;
        private boolean mIsLongpressEnabled;
        private float mLastFocusX;
        private float mLastFocusY;
        final OnGestureListener mListener;
        private int mMaximumFlingVelocity;
        private int mMinimumFlingVelocity;
        private MotionEvent mPreviousUpEvent;
        boolean mStillDown;
        private int mTouchSlopSquare;
        private VelocityTracker mVelocityTracker;

        private class GestureHandler extends Handler {
            GestureHandler() {
            }

            GestureHandler(Handler handler) {
                super(handler.getLooper());
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GestureDetectorCompatImplBase.SHOW_PRESS /*1*/:
                        GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                    case GestureDetectorCompatImplBase.LONG_PRESS /*2*/:
                        GestureDetectorCompatImplBase.this.dispatchLongPress();
                    case GestureDetectorCompatImplBase.TAP /*3*/:
                        if (GestureDetectorCompatImplBase.this.mDoubleTapListener == null) {
                            return;
                        }
                        if (GestureDetectorCompatImplBase.this.mStillDown) {
                            GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
                        } else {
                            GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                        }
                    default:
                        throw new RuntimeException("Unknown message " + msg);
                }
            }
        }

        static {
            LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
            TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
            DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
        }

        public GestureDetectorCompatImplBase(Context context, OnGestureListener listener, Handler handler) {
            if (handler != null) {
                this.mHandler = new GestureHandler(handler);
            } else {
                this.mHandler = new GestureHandler();
            }
            this.mListener = listener;
            if (listener instanceof OnDoubleTapListener) {
                setOnDoubleTapListener((OnDoubleTapListener) listener);
            }
            init(context);
        }

        private void init(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            } else if (this.mListener == null) {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            } else {
                this.mIsLongpressEnabled = true;
                ViewConfiguration configuration = ViewConfiguration.get(context);
                int touchSlop = configuration.getScaledTouchSlop();
                int doubleTapSlop = configuration.getScaledDoubleTapSlop();
                this.mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
                this.mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
                this.mTouchSlopSquare = touchSlop * touchSlop;
                this.mDoubleTapSlopSquare = doubleTapSlop * doubleTapSlop;
            }
        }

        public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
            this.mDoubleTapListener = onDoubleTapListener;
        }

        public void setIsLongpressEnabled(boolean isLongpressEnabled) {
            this.mIsLongpressEnabled = isLongpressEnabled;
        }

        public boolean isLongpressEnabled() {
            return this.mIsLongpressEnabled;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTouchEvent(android.view.MotionEvent r43) {
            /*
            r42 = this;
            r6 = r43.getAction();
            r0 = r42;
            r0 = r0.mVelocityTracker;
            r36 = r0;
            if (r36 != 0) goto L_0x0016;
        L_0x000c:
            r36 = android.view.VelocityTracker.obtain();
            r0 = r36;
            r1 = r42;
            r1.mVelocityTracker = r0;
        L_0x0016:
            r0 = r42;
            r0 = r0.mVelocityTracker;
            r36 = r0;
            r0 = r36;
            r1 = r43;
            r0.addMovement(r1);
            r0 = r6 & 255;
            r36 = r0;
            r37 = 6;
            r0 = r36;
            r1 = r37;
            if (r0 != r1) goto L_0x004e;
        L_0x002f:
            r22 = 1;
        L_0x0031:
            if (r22 == 0) goto L_0x0051;
        L_0x0033:
            r25 = android.support.v4.view.MotionEventCompat.getActionIndex(r43);
        L_0x0037:
            r26 = 0;
            r27 = 0;
            r7 = r43.getPointerCount();
            r18 = 0;
        L_0x0041:
            r0 = r18;
            if (r0 >= r7) goto L_0x0069;
        L_0x0045:
            r0 = r25;
            r1 = r18;
            if (r0 != r1) goto L_0x0054;
        L_0x004b:
            r18 = r18 + 1;
            goto L_0x0041;
        L_0x004e:
            r22 = 0;
            goto L_0x0031;
        L_0x0051:
            r25 = -1;
            goto L_0x0037;
        L_0x0054:
            r0 = r43;
            r1 = r18;
            r36 = r0.getX(r1);
            r26 = r26 + r36;
            r0 = r43;
            r1 = r18;
            r36 = r0.getY(r1);
            r27 = r27 + r36;
            goto L_0x004b;
        L_0x0069:
            if (r22 == 0) goto L_0x0081;
        L_0x006b:
            r12 = r7 + -1;
        L_0x006d:
            r0 = (float) r12;
            r36 = r0;
            r14 = r26 / r36;
            r0 = (float) r12;
            r36 = r0;
            r15 = r27 / r36;
            r17 = 0;
            r0 = r6 & 255;
            r36 = r0;
            switch(r36) {
                case 0: goto L_0x012f;
                case 1: goto L_0x036e;
                case 2: goto L_0x0277;
                case 3: goto L_0x04b1;
                case 4: goto L_0x0080;
                case 5: goto L_0x0083;
                case 6: goto L_0x0097;
                default: goto L_0x0080;
            };
        L_0x0080:
            return r17;
        L_0x0081:
            r12 = r7;
            goto L_0x006d;
        L_0x0083:
            r0 = r42;
            r0.mLastFocusX = r14;
            r0 = r42;
            r0.mDownFocusX = r14;
            r0 = r42;
            r0.mLastFocusY = r15;
            r0 = r42;
            r0.mDownFocusY = r15;
            r42.cancelTaps();
            goto L_0x0080;
        L_0x0097:
            r0 = r42;
            r0.mLastFocusX = r14;
            r0 = r42;
            r0.mDownFocusX = r14;
            r0 = r42;
            r0.mLastFocusY = r15;
            r0 = r42;
            r0.mDownFocusY = r15;
            r0 = r42;
            r0 = r0.mVelocityTracker;
            r36 = r0;
            r37 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r0 = r42;
            r0 = r0.mMaximumFlingVelocity;
            r38 = r0;
            r0 = r38;
            r0 = (float) r0;
            r38 = r0;
            r36.computeCurrentVelocity(r37, r38);
            r28 = android.support.v4.view.MotionEventCompat.getActionIndex(r43);
            r0 = r43;
            r1 = r28;
            r19 = r0.getPointerId(r1);
            r0 = r42;
            r0 = r0.mVelocityTracker;
            r36 = r0;
            r0 = r36;
            r1 = r19;
            r33 = android.support.v4.view.VelocityTrackerCompat.getXVelocity(r0, r1);
            r0 = r42;
            r0 = r0.mVelocityTracker;
            r36 = r0;
            r0 = r36;
            r1 = r19;
            r35 = android.support.v4.view.VelocityTrackerCompat.getYVelocity(r0, r1);
            r18 = 0;
        L_0x00e7:
            r0 = r18;
            if (r0 >= r7) goto L_0x0080;
        L_0x00eb:
            r0 = r18;
            r1 = r28;
            if (r0 != r1) goto L_0x00f4;
        L_0x00f1:
            r18 = r18 + 1;
            goto L_0x00e7;
        L_0x00f4:
            r0 = r43;
            r1 = r18;
            r20 = r0.getPointerId(r1);
            r0 = r42;
            r0 = r0.mVelocityTracker;
            r36 = r0;
            r0 = r36;
            r1 = r20;
            r36 = android.support.v4.view.VelocityTrackerCompat.getXVelocity(r0, r1);
            r32 = r33 * r36;
            r0 = r42;
            r0 = r0.mVelocityTracker;
            r36 = r0;
            r0 = r36;
            r1 = r20;
            r36 = android.support.v4.view.VelocityTrackerCompat.getYVelocity(r0, r1);
            r34 = r35 * r36;
            r13 = r32 + r34;
            r36 = 0;
            r36 = (r13 > r36 ? 1 : (r13 == r36 ? 0 : -1));
            if (r36 >= 0) goto L_0x00f1;
        L_0x0124:
            r0 = r42;
            r0 = r0.mVelocityTracker;
            r36 = r0;
            r36.clear();
            goto L_0x0080;
        L_0x012f:
            r0 = r42;
            r0 = r0.mDoubleTapListener;
            r36 = r0;
            if (r36 == 0) goto L_0x01a6;
        L_0x0137:
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 3;
            r16 = r36.hasMessages(r37);
            if (r16 == 0) goto L_0x0150;
        L_0x0145:
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 3;
            r36.removeMessages(r37);
        L_0x0150:
            r0 = r42;
            r0 = r0.mCurrentDownEvent;
            r36 = r0;
            if (r36 == 0) goto L_0x0263;
        L_0x0158:
            r0 = r42;
            r0 = r0.mPreviousUpEvent;
            r36 = r0;
            if (r36 == 0) goto L_0x0263;
        L_0x0160:
            if (r16 == 0) goto L_0x0263;
        L_0x0162:
            r0 = r42;
            r0 = r0.mCurrentDownEvent;
            r36 = r0;
            r0 = r42;
            r0 = r0.mPreviousUpEvent;
            r37 = r0;
            r0 = r42;
            r1 = r36;
            r2 = r37;
            r3 = r43;
            r36 = r0.isConsideredDoubleTap(r1, r2, r3);
            if (r36 == 0) goto L_0x0263;
        L_0x017c:
            r36 = 1;
            r0 = r36;
            r1 = r42;
            r1.mIsDoubleTapping = r0;
            r0 = r42;
            r0 = r0.mDoubleTapListener;
            r36 = r0;
            r0 = r42;
            r0 = r0.mCurrentDownEvent;
            r37 = r0;
            r36 = r36.onDoubleTap(r37);
            r17 = r17 | r36;
            r0 = r42;
            r0 = r0.mDoubleTapListener;
            r36 = r0;
            r0 = r36;
            r1 = r43;
            r36 = r0.onDoubleTapEvent(r1);
            r17 = r17 | r36;
        L_0x01a6:
            r0 = r42;
            r0.mLastFocusX = r14;
            r0 = r42;
            r0.mDownFocusX = r14;
            r0 = r42;
            r0.mLastFocusY = r15;
            r0 = r42;
            r0.mDownFocusY = r15;
            r0 = r42;
            r0 = r0.mCurrentDownEvent;
            r36 = r0;
            if (r36 == 0) goto L_0x01c7;
        L_0x01be:
            r0 = r42;
            r0 = r0.mCurrentDownEvent;
            r36 = r0;
            r36.recycle();
        L_0x01c7:
            r36 = android.view.MotionEvent.obtain(r43);
            r0 = r36;
            r1 = r42;
            r1.mCurrentDownEvent = r0;
            r36 = 1;
            r0 = r36;
            r1 = r42;
            r1.mAlwaysInTapRegion = r0;
            r36 = 1;
            r0 = r36;
            r1 = r42;
            r1.mAlwaysInBiggerTapRegion = r0;
            r36 = 1;
            r0 = r36;
            r1 = r42;
            r1.mStillDown = r0;
            r36 = 0;
            r0 = r36;
            r1 = r42;
            r1.mInLongPress = r0;
            r36 = 0;
            r0 = r36;
            r1 = r42;
            r1.mDeferConfirmSingleTap = r0;
            r0 = r42;
            r0 = r0.mIsLongpressEnabled;
            r36 = r0;
            if (r36 == 0) goto L_0x0233;
        L_0x0201:
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 2;
            r36.removeMessages(r37);
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 2;
            r0 = r42;
            r0 = r0.mCurrentDownEvent;
            r38 = r0;
            r38 = r38.getDownTime();
            r40 = TAP_TIMEOUT;
            r0 = r40;
            r0 = (long) r0;
            r40 = r0;
            r38 = r38 + r40;
            r40 = LONGPRESS_TIMEOUT;
            r0 = r40;
            r0 = (long) r0;
            r40 = r0;
            r38 = r38 + r40;
            r36.sendEmptyMessageAtTime(r37, r38);
        L_0x0233:
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 1;
            r0 = r42;
            r0 = r0.mCurrentDownEvent;
            r38 = r0;
            r38 = r38.getDownTime();
            r40 = TAP_TIMEOUT;
            r0 = r40;
            r0 = (long) r0;
            r40 = r0;
            r38 = r38 + r40;
            r36.sendEmptyMessageAtTime(r37, r38);
            r0 = r42;
            r0 = r0.mListener;
            r36 = r0;
            r0 = r36;
            r1 = r43;
            r36 = r0.onDown(r1);
            r17 = r17 | r36;
            goto L_0x0080;
        L_0x0263:
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 3;
            r38 = DOUBLE_TAP_TIMEOUT;
            r0 = r38;
            r0 = (long) r0;
            r38 = r0;
            r36.sendEmptyMessageDelayed(r37, r38);
            goto L_0x01a6;
        L_0x0277:
            r0 = r42;
            r0 = r0.mInLongPress;
            r36 = r0;
            if (r36 != 0) goto L_0x0080;
        L_0x027f:
            r0 = r42;
            r0 = r0.mLastFocusX;
            r36 = r0;
            r23 = r36 - r14;
            r0 = r42;
            r0 = r0.mLastFocusY;
            r36 = r0;
            r24 = r36 - r15;
            r0 = r42;
            r0 = r0.mIsDoubleTapping;
            r36 = r0;
            if (r36 == 0) goto L_0x02a9;
        L_0x0297:
            r0 = r42;
            r0 = r0.mDoubleTapListener;
            r36 = r0;
            r0 = r36;
            r1 = r43;
            r36 = r0.onDoubleTapEvent(r1);
            r17 = r17 | r36;
            goto L_0x0080;
        L_0x02a9:
            r0 = r42;
            r0 = r0.mAlwaysInTapRegion;
            r36 = r0;
            if (r36 == 0) goto L_0x0336;
        L_0x02b1:
            r0 = r42;
            r0 = r0.mDownFocusX;
            r36 = r0;
            r36 = r14 - r36;
            r0 = r36;
            r9 = (int) r0;
            r0 = r42;
            r0 = r0.mDownFocusY;
            r36 = r0;
            r36 = r15 - r36;
            r0 = r36;
            r10 = (int) r0;
            r36 = r9 * r9;
            r37 = r10 * r10;
            r11 = r36 + r37;
            r0 = r42;
            r0 = r0.mTouchSlopSquare;
            r36 = r0;
            r0 = r36;
            if (r11 <= r0) goto L_0x0322;
        L_0x02d7:
            r0 = r42;
            r0 = r0.mListener;
            r36 = r0;
            r0 = r42;
            r0 = r0.mCurrentDownEvent;
            r37 = r0;
            r0 = r36;
            r1 = r37;
            r2 = r43;
            r3 = r23;
            r4 = r24;
            r17 = r0.onScroll(r1, r2, r3, r4);
            r0 = r42;
            r0.mLastFocusX = r14;
            r0 = r42;
            r0.mLastFocusY = r15;
            r36 = 0;
            r0 = r36;
            r1 = r42;
            r1.mAlwaysInTapRegion = r0;
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 3;
            r36.removeMessages(r37);
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 1;
            r36.removeMessages(r37);
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 2;
            r36.removeMessages(r37);
        L_0x0322:
            r0 = r42;
            r0 = r0.mTouchSlopSquare;
            r36 = r0;
            r0 = r36;
            if (r11 <= r0) goto L_0x0080;
        L_0x032c:
            r36 = 0;
            r0 = r36;
            r1 = r42;
            r1.mAlwaysInBiggerTapRegion = r0;
            goto L_0x0080;
        L_0x0336:
            r36 = java.lang.Math.abs(r23);
            r37 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r36 = (r36 > r37 ? 1 : (r36 == r37 ? 0 : -1));
            if (r36 >= 0) goto L_0x034a;
        L_0x0340:
            r36 = java.lang.Math.abs(r24);
            r37 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r36 = (r36 > r37 ? 1 : (r36 == r37 ? 0 : -1));
            if (r36 < 0) goto L_0x0080;
        L_0x034a:
            r0 = r42;
            r0 = r0.mListener;
            r36 = r0;
            r0 = r42;
            r0 = r0.mCurrentDownEvent;
            r37 = r0;
            r0 = r36;
            r1 = r37;
            r2 = r43;
            r3 = r23;
            r4 = r24;
            r17 = r0.onScroll(r1, r2, r3, r4);
            r0 = r42;
            r0.mLastFocusX = r14;
            r0 = r42;
            r0.mLastFocusY = r15;
            goto L_0x0080;
        L_0x036e:
            r36 = 0;
            r0 = r36;
            r1 = r42;
            r1.mStillDown = r0;
            r8 = android.view.MotionEvent.obtain(r43);
            r0 = r42;
            r0 = r0.mIsDoubleTapping;
            r36 = r0;
            if (r36 == 0) goto L_0x03e8;
        L_0x0382:
            r0 = r42;
            r0 = r0.mDoubleTapListener;
            r36 = r0;
            r0 = r36;
            r1 = r43;
            r36 = r0.onDoubleTapEvent(r1);
            r17 = r17 | r36;
        L_0x0392:
            r0 = r42;
            r0 = r0.mPreviousUpEvent;
            r36 = r0;
            if (r36 == 0) goto L_0x03a3;
        L_0x039a:
            r0 = r42;
            r0 = r0.mPreviousUpEvent;
            r36 = r0;
            r36.recycle();
        L_0x03a3:
            r0 = r42;
            r0.mPreviousUpEvent = r8;
            r0 = r42;
            r0 = r0.mVelocityTracker;
            r36 = r0;
            if (r36 == 0) goto L_0x03c0;
        L_0x03af:
            r0 = r42;
            r0 = r0.mVelocityTracker;
            r36 = r0;
            r36.recycle();
            r36 = 0;
            r0 = r36;
            r1 = r42;
            r1.mVelocityTracker = r0;
        L_0x03c0:
            r36 = 0;
            r0 = r36;
            r1 = r42;
            r1.mIsDoubleTapping = r0;
            r36 = 0;
            r0 = r36;
            r1 = r42;
            r1.mDeferConfirmSingleTap = r0;
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 1;
            r36.removeMessages(r37);
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 2;
            r36.removeMessages(r37);
            goto L_0x0080;
        L_0x03e8:
            r0 = r42;
            r0 = r0.mInLongPress;
            r36 = r0;
            if (r36 == 0) goto L_0x0404;
        L_0x03f0:
            r0 = r42;
            r0 = r0.mHandler;
            r36 = r0;
            r37 = 3;
            r36.removeMessages(r37);
            r36 = 0;
            r0 = r36;
            r1 = r42;
            r1.mInLongPress = r0;
            goto L_0x0392;
        L_0x0404:
            r0 = r42;
            r0 = r0.mAlwaysInTapRegion;
            r36 = r0;
            if (r36 == 0) goto L_0x0439;
        L_0x040c:
            r0 = r42;
            r0 = r0.mListener;
            r36 = r0;
            r0 = r36;
            r1 = r43;
            r17 = r0.onSingleTapUp(r1);
            r0 = r42;
            r0 = r0.mDeferConfirmSingleTap;
            r36 = r0;
            if (r36 == 0) goto L_0x0392;
        L_0x0422:
            r0 = r42;
            r0 = r0.mDoubleTapListener;
            r36 = r0;
            if (r36 == 0) goto L_0x0392;
        L_0x042a:
            r0 = r42;
            r0 = r0.mDoubleTapListener;
            r36 = r0;
            r0 = r36;
            r1 = r43;
            r0.onSingleTapConfirmed(r1);
            goto L_0x0392;
        L_0x0439:
            r0 = r42;
            r0 = r0.mVelocityTracker;
            r29 = r0;
            r36 = 0;
            r0 = r43;
            r1 = r36;
            r21 = r0.getPointerId(r1);
            r36 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r0 = r42;
            r0 = r0.mMaximumFlingVelocity;
            r37 = r0;
            r0 = r37;
            r0 = (float) r0;
            r37 = r0;
            r0 = r29;
            r1 = r36;
            r2 = r37;
            r0.computeCurrentVelocity(r1, r2);
            r0 = r29;
            r1 = r21;
            r31 = android.support.v4.view.VelocityTrackerCompat.getYVelocity(r0, r1);
            r0 = r29;
            r1 = r21;
            r30 = android.support.v4.view.VelocityTrackerCompat.getXVelocity(r0, r1);
            r36 = java.lang.Math.abs(r31);
            r0 = r42;
            r0 = r0.mMinimumFlingVelocity;
            r37 = r0;
            r0 = r37;
            r0 = (float) r0;
            r37 = r0;
            r36 = (r36 > r37 ? 1 : (r36 == r37 ? 0 : -1));
            if (r36 > 0) goto L_0x0495;
        L_0x0482:
            r36 = java.lang.Math.abs(r30);
            r0 = r42;
            r0 = r0.mMinimumFlingVelocity;
            r37 = r0;
            r0 = r37;
            r0 = (float) r0;
            r37 = r0;
            r36 = (r36 > r37 ? 1 : (r36 == r37 ? 0 : -1));
            if (r36 <= 0) goto L_0x0392;
        L_0x0495:
            r0 = r42;
            r0 = r0.mListener;
            r36 = r0;
            r0 = r42;
            r0 = r0.mCurrentDownEvent;
            r37 = r0;
            r0 = r36;
            r1 = r37;
            r2 = r43;
            r3 = r30;
            r4 = r31;
            r17 = r0.onFling(r1, r2, r3, r4);
            goto L_0x0392;
        L_0x04b1:
            r42.cancel();
            goto L_0x0080;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImplBase.onTouchEvent(android.view.MotionEvent):boolean");
        }

        private void cancel() {
            this.mHandler.removeMessages(SHOW_PRESS);
            this.mHandler.removeMessages(LONG_PRESS);
            this.mHandler.removeMessages(TAP);
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            this.mIsDoubleTapping = false;
            this.mStillDown = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private void cancelTaps() {
            this.mHandler.removeMessages(SHOW_PRESS);
            this.mHandler.removeMessages(LONG_PRESS);
            this.mHandler.removeMessages(TAP);
            this.mIsDoubleTapping = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private boolean isConsideredDoubleTap(MotionEvent firstDown, MotionEvent firstUp, MotionEvent secondDown) {
            if (!this.mAlwaysInBiggerTapRegion || secondDown.getEventTime() - firstUp.getEventTime() > ((long) DOUBLE_TAP_TIMEOUT)) {
                return false;
            }
            int deltaX = ((int) firstDown.getX()) - ((int) secondDown.getX());
            int deltaY = ((int) firstDown.getY()) - ((int) secondDown.getY());
            if ((deltaX * deltaX) + (deltaY * deltaY) < this.mDoubleTapSlopSquare) {
                return true;
            }
            return false;
        }

        void dispatchLongPress() {
            this.mHandler.removeMessages(TAP);
            this.mDeferConfirmSingleTap = false;
            this.mInLongPress = true;
            this.mListener.onLongPress(this.mCurrentDownEvent);
        }
    }

    static class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
        private final GestureDetector mDetector;

        public GestureDetectorCompatImplJellybeanMr2(Context context, OnGestureListener listener, Handler handler) {
            this.mDetector = new GestureDetector(context, listener, handler);
        }

        public boolean isLongpressEnabled() {
            return this.mDetector.isLongpressEnabled();
        }

        public boolean onTouchEvent(MotionEvent ev) {
            return this.mDetector.onTouchEvent(ev);
        }

        public void setIsLongpressEnabled(boolean enabled) {
            this.mDetector.setIsLongpressEnabled(enabled);
        }

        public void setOnDoubleTapListener(OnDoubleTapListener listener) {
            this.mDetector.setOnDoubleTapListener(listener);
        }
    }

    public GestureDetectorCompat(Context context, OnGestureListener listener) {
        this(context, listener, null);
    }

    public GestureDetectorCompat(Context context, OnGestureListener listener, Handler handler) {
        if (VERSION.SDK_INT > 17) {
            this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context, listener, handler);
        } else {
            this.mImpl = new GestureDetectorCompatImplBase(context, listener, handler);
        }
    }

    public boolean isLongpressEnabled() {
        return this.mImpl.isLongpressEnabled();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.mImpl.onTouchEvent(event);
    }

    public void setIsLongpressEnabled(boolean enabled) {
        this.mImpl.setIsLongpressEnabled(enabled);
    }

    public void setOnDoubleTapListener(OnDoubleTapListener listener) {
        this.mImpl.setOnDoubleTapListener(listener);
    }
}
