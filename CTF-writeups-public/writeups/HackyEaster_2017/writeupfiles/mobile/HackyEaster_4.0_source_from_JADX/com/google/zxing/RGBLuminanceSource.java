package com.google.zxing;

import android.support.v4.view.MotionEventCompat;

public final class RGBLuminanceSource extends LuminanceSource {
    private final int dataHeight;
    private final int dataWidth;
    private final int left;
    private final byte[] luminances;
    private final int top;

    public RGBLuminanceSource(int width, int height, int[] pixels) {
        super(width, height);
        this.dataWidth = width;
        this.dataHeight = height;
        this.left = 0;
        this.top = 0;
        this.luminances = new byte[(width * height)];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                int pixel = pixels[offset + x];
                int r = (pixel >> 16) & MotionEventCompat.ACTION_MASK;
                int g = (pixel >> 8) & MotionEventCompat.ACTION_MASK;
                int b = pixel & MotionEventCompat.ACTION_MASK;
                if (r == g && g == b) {
                    this.luminances[offset + x] = (byte) r;
                } else {
                    this.luminances[offset + x] = (byte) ((((r + g) + g) + b) >> 2);
                }
            }
        }
    }

    private RGBLuminanceSource(byte[] pixels, int dataWidth, int dataHeight, int left, int top, int width, int height) {
        super(width, height);
        if (left + width > dataWidth || top + height > dataHeight) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
        this.luminances = pixels;
        this.dataWidth = dataWidth;
        this.dataHeight = dataHeight;
        this.left = left;
        this.top = top;
    }

    public byte[] getRow(int y, byte[] row) {
        if (y < 0 || y >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + y);
        }
        int width = getWidth();
        if (row == null || row.length < width) {
            row = new byte[width];
        }
        System.arraycopy(this.luminances, ((this.top + y) * this.dataWidth) + this.left, row, 0, width);
        return row;
    }

    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        if (width == this.dataWidth && height == this.dataHeight) {
            return this.luminances;
        }
        int area = width * height;
        byte[] matrix = new byte[area];
        int inputOffset = (this.top * this.dataWidth) + this.left;
        if (width == this.dataWidth) {
            System.arraycopy(this.luminances, inputOffset, matrix, 0, area);
            return matrix;
        }
        byte[] rgb = this.luminances;
        for (int y = 0; y < height; y++) {
            System.arraycopy(rgb, inputOffset, matrix, y * width, width);
            inputOffset += this.dataWidth;
        }
        return matrix;
    }

    public boolean isCropSupported() {
        return true;
    }

    public LuminanceSource crop(int left, int top, int width, int height) {
        return new RGBLuminanceSource(this.luminances, this.dataWidth, this.dataHeight, this.left + left, this.top + top, width, height);
    }
}
