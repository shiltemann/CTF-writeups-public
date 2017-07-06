package com.google.zxing.common;

public final class PerspectiveTransform {
    private final float a11;
    private final float a12;
    private final float a13;
    private final float a21;
    private final float a22;
    private final float a23;
    private final float a31;
    private final float a32;
    private final float a33;

    private PerspectiveTransform(float a11, float a21, float a31, float a12, float a22, float a32, float a13, float a23, float a33) {
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
    }

    public static PerspectiveTransform quadrilateralToQuadrilateral(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3, float x0p, float y0p, float x1p, float y1p, float x2p, float y2p, float x3p, float y3p) {
        return squareToQuadrilateral(x0p, y0p, x1p, y1p, x2p, y2p, x3p, y3p).times(quadrilateralToSquare(x0, y0, x1, y1, x2, y2, x3, y3));
    }

    public void transformPoints(float[] points) {
        int max = points.length;
        float a11 = this.a11;
        float a12 = this.a12;
        float a13 = this.a13;
        float a21 = this.a21;
        float a22 = this.a22;
        float a23 = this.a23;
        float a31 = this.a31;
        float a32 = this.a32;
        float a33 = this.a33;
        for (int i = 0; i < max; i += 2) {
            float x = points[i];
            float y = points[i + 1];
            float denominator = ((a13 * x) + (a23 * y)) + a33;
            points[i] = (((a11 * x) + (a21 * y)) + a31) / denominator;
            points[i + 1] = (((a12 * x) + (a22 * y)) + a32) / denominator;
        }
    }

    public void transformPoints(float[] xValues, float[] yValues) {
        int n = xValues.length;
        for (int i = 0; i < n; i++) {
            float x = xValues[i];
            float y = yValues[i];
            float denominator = ((this.a13 * x) + (this.a23 * y)) + this.a33;
            xValues[i] = (((this.a11 * x) + (this.a21 * y)) + this.a31) / denominator;
            yValues[i] = (((this.a12 * x) + (this.a22 * y)) + this.a32) / denominator;
        }
    }

    public static PerspectiveTransform squareToQuadrilateral(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3) {
        float dx3 = ((x0 - x1) + x2) - x3;
        float dy3 = ((y0 - y1) + y2) - y3;
        if (dx3 == 0.0f && dy3 == 0.0f) {
            return new PerspectiveTransform(x1 - x0, x2 - x1, x0, y1 - y0, y2 - y1, y0, 0.0f, 0.0f, 1.0f);
        }
        float dx1 = x1 - x2;
        float dx2 = x3 - x2;
        float dy1 = y1 - y2;
        float dy2 = y3 - y2;
        float denominator = (dx1 * dy2) - (dx2 * dy1);
        float a13 = ((dx3 * dy2) - (dx2 * dy3)) / denominator;
        float a23 = ((dx1 * dy3) - (dx3 * dy1)) / denominator;
        return new PerspectiveTransform((x1 - x0) + (a13 * x1), (x3 - x0) + (a23 * x3), x0, (a13 * y1) + (y1 - y0), (a23 * y3) + (y3 - y0), y0, a13, a23, 1.0f);
    }

    public static PerspectiveTransform quadrilateralToSquare(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3) {
        return squareToQuadrilateral(x0, y0, x1, y1, x2, y2, x3, y3).buildAdjoint();
    }

    PerspectiveTransform buildAdjoint() {
        return new PerspectiveTransform((this.a22 * this.a33) - (this.a23 * this.a32), (this.a23 * this.a31) - (this.a21 * this.a33), (this.a21 * this.a32) - (this.a22 * this.a31), (this.a13 * this.a32) - (this.a12 * this.a33), (this.a11 * this.a33) - (this.a13 * this.a31), (this.a12 * this.a31) - (this.a11 * this.a32), (this.a12 * this.a23) - (this.a13 * this.a22), (this.a13 * this.a21) - (this.a11 * this.a23), (this.a11 * this.a22) - (this.a12 * this.a21));
    }

    PerspectiveTransform times(PerspectiveTransform other) {
        return new PerspectiveTransform(((this.a11 * other.a11) + (this.a21 * other.a12)) + (this.a31 * other.a13), ((this.a11 * other.a21) + (this.a21 * other.a22)) + (this.a31 * other.a23), ((this.a11 * other.a31) + (this.a21 * other.a32)) + (this.a31 * other.a33), ((this.a12 * other.a11) + (this.a22 * other.a12)) + (this.a32 * other.a13), ((this.a12 * other.a21) + (this.a22 * other.a22)) + (this.a32 * other.a23), ((this.a12 * other.a31) + (this.a22 * other.a32)) + (this.a32 * other.a33), ((this.a13 * other.a11) + (this.a23 * other.a12)) + (this.a33 * other.a13), ((this.a13 * other.a21) + (this.a23 * other.a22)) + (this.a33 * other.a23), ((this.a13 * other.a31) + (this.a23 * other.a32)) + (this.a33 * other.a33));
    }
}
