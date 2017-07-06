package com.google.zxing.common.detector;

public final class MathUtils {
    private MathUtils() {
    }

    public static int round(float d) {
        return (int) (0.5f + d);
    }

    public static float distance(float aX, float aY, float bX, float bY) {
        float xDiff = aX - bX;
        float yDiff = aY - bY;
        return (float) Math.sqrt((double) ((xDiff * xDiff) + (yDiff * yDiff)));
    }

    public static float distance(int aX, int aY, int bX, int bY) {
        int xDiff = aX - bX;
        int yDiff = aY - bY;
        return (float) Math.sqrt((double) ((xDiff * xDiff) + (yDiff * yDiff)));
    }
}
