package com.google.zxing.common;

import com.google.zxing.NotFoundException;

public abstract class GridSampler {
    private static GridSampler gridSampler;

    public abstract BitMatrix sampleGrid(BitMatrix bitMatrix, int i, int i2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16) throws NotFoundException;

    public abstract BitMatrix sampleGrid(BitMatrix bitMatrix, int i, int i2, PerspectiveTransform perspectiveTransform) throws NotFoundException;

    static {
        gridSampler = new DefaultGridSampler();
    }

    public static void setGridSampler(GridSampler newGridSampler) {
        gridSampler = newGridSampler;
    }

    public static GridSampler getInstance() {
        return gridSampler;
    }

    protected static void checkAndNudgePoints(BitMatrix image, float[] points) throws NotFoundException {
        int offset;
        int width = image.getWidth();
        int height = image.getHeight();
        boolean nudged = true;
        for (offset = 0; offset < points.length && nudged; offset += 2) {
            int x = (int) points[offset];
            int y = (int) points[offset + 1];
            if (x < -1 || x > width || y < -1 || y > height) {
                throw NotFoundException.getNotFoundInstance();
            }
            nudged = false;
            if (x == -1) {
                points[offset] = 0.0f;
                nudged = true;
            } else if (x == width) {
                points[offset] = (float) (width - 1);
                nudged = true;
            }
            if (y == -1) {
                points[offset + 1] = 0.0f;
                nudged = true;
            } else if (y == height) {
                points[offset + 1] = (float) (height - 1);
                nudged = true;
            }
        }
        nudged = true;
        for (offset = points.length - 2; offset >= 0 && nudged; offset -= 2) {
            x = (int) points[offset];
            y = (int) points[offset + 1];
            if (x < -1 || x > width || y < -1 || y > height) {
                throw NotFoundException.getNotFoundInstance();
            }
            nudged = false;
            if (x == -1) {
                points[offset] = 0.0f;
                nudged = true;
            } else if (x == width) {
                points[offset] = (float) (width - 1);
                nudged = true;
            }
            if (y == -1) {
                points[offset + 1] = 0.0f;
                nudged = true;
            } else if (y == height) {
                points[offset + 1] = (float) (height - 1);
                nudged = true;
            }
        }
    }
}
