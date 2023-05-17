package com.google.zxing.common.detector;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

public final class MonochromeRectangleDetector {
    private static final int MAX_MODULES = 32;
    private final BitMatrix image;

    public MonochromeRectangleDetector(BitMatrix image) {
        this.image = image;
    }

    public ResultPoint[] detect() throws NotFoundException {
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int halfHeight = height >> 1;
        int halfWidth = width >> 1;
        int deltaY = Math.max(1, height / AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
        int deltaX = Math.max(1, width / AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
        int bottom = height;
        int right = width;
        int top = ((int) findCornerFromCenter(halfWidth, 0, 0, right, halfHeight, -deltaY, 0, bottom, halfWidth >> 1).getY()) - 1;
        int left = ((int) findCornerFromCenter(halfWidth, -deltaX, 0, right, halfHeight, 0, top, bottom, halfHeight >> 1).getX()) - 1;
        right = ((int) findCornerFromCenter(halfWidth, deltaX, left, right, halfHeight, 0, top, bottom, halfHeight >> 1).getX()) + 1;
        ResultPoint pointD = findCornerFromCenter(halfWidth, 0, left, right, halfHeight, deltaY, top, bottom, halfWidth >> 1);
        ResultPoint pointA = findCornerFromCenter(halfWidth, 0, left, right, halfHeight, -deltaY, top, ((int) pointD.getY()) + 1, halfWidth >> 2);
        return new ResultPoint[]{pointA, pointB, pointC, pointD};
    }

    private ResultPoint findCornerFromCenter(int centerX, int deltaX, int left, int right, int centerY, int deltaY, int top, int bottom, int maxWhiteRun) throws NotFoundException {
        int[] lastRange = null;
        int y = centerY;
        int x = centerX;
        while (y < bottom && y >= top && x < right && x >= left) {
            int[] range;
            if (deltaX == 0) {
                range = blackWhiteRange(y, maxWhiteRun, left, right, true);
            } else {
                range = blackWhiteRange(x, maxWhiteRun, top, bottom, false);
            }
            if (range != null) {
                lastRange = range;
                y += deltaY;
                x += deltaX;
            } else if (lastRange == null) {
                throw NotFoundException.getNotFoundInstance();
            } else if (deltaX == 0) {
                int lastY = y - deltaY;
                if (lastRange[0] >= centerX) {
                    return new ResultPoint((float) lastRange[1], (float) lastY);
                }
                if (lastRange[1] <= centerX) {
                    return new ResultPoint((float) lastRange[0], (float) lastY);
                }
                float f;
                if (deltaY > 0) {
                    f = (float) lastRange[0];
                } else {
                    f = (float) lastRange[1];
                }
                return new ResultPoint(f, (float) lastY);
            } else {
                int lastX = x - deltaX;
                if (lastRange[0] >= centerY) {
                    return new ResultPoint((float) lastX, (float) lastRange[1]);
                }
                if (lastRange[1] <= centerY) {
                    return new ResultPoint((float) lastX, (float) lastRange[0]);
                }
                return new ResultPoint((float) lastX, deltaX < 0 ? (float) lastRange[0] : (float) lastRange[1]);
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int[] blackWhiteRange(int r8, int r9, int r10, int r11, boolean r12) {
        /*
        r7 = this;
        r5 = r10 + r11;
        r0 = r5 >> 1;
        r2 = r0;
    L_0x0005:
        if (r2 < r10) goto L_0x0032;
    L_0x0007:
        if (r12 == 0) goto L_0x0014;
    L_0x0009:
        r5 = r7.image;
        r5 = r5.get(r2, r8);
        if (r5 == 0) goto L_0x001c;
    L_0x0011:
        r2 = r2 + -1;
        goto L_0x0005;
    L_0x0014:
        r5 = r7.image;
        r5 = r5.get(r8, r2);
        if (r5 != 0) goto L_0x0011;
    L_0x001c:
        r4 = r2;
    L_0x001d:
        r2 = r2 + -1;
        if (r2 < r10) goto L_0x002b;
    L_0x0021:
        if (r12 == 0) goto L_0x0044;
    L_0x0023:
        r5 = r7.image;
        r5 = r5.get(r2, r8);
        if (r5 == 0) goto L_0x001d;
    L_0x002b:
        r3 = r4 - r2;
        if (r2 < r10) goto L_0x0031;
    L_0x002f:
        if (r3 <= r9) goto L_0x0005;
    L_0x0031:
        r2 = r4;
    L_0x0032:
        r2 = r2 + 1;
        r1 = r0;
    L_0x0035:
        if (r1 >= r11) goto L_0x006b;
    L_0x0037:
        if (r12 == 0) goto L_0x004d;
    L_0x0039:
        r5 = r7.image;
        r5 = r5.get(r1, r8);
        if (r5 == 0) goto L_0x0055;
    L_0x0041:
        r1 = r1 + 1;
        goto L_0x0035;
    L_0x0044:
        r5 = r7.image;
        r5 = r5.get(r8, r2);
        if (r5 == 0) goto L_0x001d;
    L_0x004c:
        goto L_0x002b;
    L_0x004d:
        r5 = r7.image;
        r5 = r5.get(r8, r1);
        if (r5 != 0) goto L_0x0041;
    L_0x0055:
        r4 = r1;
    L_0x0056:
        r1 = r1 + 1;
        if (r1 >= r11) goto L_0x0064;
    L_0x005a:
        if (r12 == 0) goto L_0x0079;
    L_0x005c:
        r5 = r7.image;
        r5 = r5.get(r1, r8);
        if (r5 == 0) goto L_0x0056;
    L_0x0064:
        r3 = r1 - r4;
        if (r1 >= r11) goto L_0x006a;
    L_0x0068:
        if (r3 <= r9) goto L_0x0035;
    L_0x006a:
        r1 = r4;
    L_0x006b:
        r1 = r1 + -1;
        if (r1 <= r2) goto L_0x0082;
    L_0x006f:
        r5 = 2;
        r5 = new int[r5];
        r6 = 0;
        r5[r6] = r2;
        r6 = 1;
        r5[r6] = r1;
    L_0x0078:
        return r5;
    L_0x0079:
        r5 = r7.image;
        r5 = r5.get(r8, r1);
        if (r5 == 0) goto L_0x0056;
    L_0x0081:
        goto L_0x0064;
    L_0x0082:
        r5 = 0;
        goto L_0x0078;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.common.detector.MonochromeRectangleDetector.blackWhiteRange(int, int, int, int, boolean):int[]");
    }
}
