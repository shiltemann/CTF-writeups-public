package com.google.zxing.common;

import android.support.v4.view.MotionEventCompat;
import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;

public class GlobalHistogramBinarizer extends Binarizer {
    private static final byte[] EMPTY;
    private static final int LUMINANCE_BITS = 5;
    private static final int LUMINANCE_BUCKETS = 32;
    private static final int LUMINANCE_SHIFT = 3;
    private final int[] buckets;
    private byte[] luminances;

    static {
        EMPTY = new byte[0];
    }

    public GlobalHistogramBinarizer(LuminanceSource source) {
        super(source);
        this.luminances = EMPTY;
        this.buckets = new int[LUMINANCE_BUCKETS];
    }

    public BitArray getBlackRow(int y, BitArray row) throws NotFoundException {
        int x;
        LuminanceSource source = getLuminanceSource();
        int width = source.getWidth();
        if (row == null || row.getSize() < width) {
            row = new BitArray(width);
        } else {
            row.clear();
        }
        initArrays(width);
        byte[] localLuminances = source.getRow(y, this.luminances);
        int[] localBuckets = this.buckets;
        for (x = 0; x < width; x++) {
            int i = (localLuminances[x] & MotionEventCompat.ACTION_MASK) >> LUMINANCE_SHIFT;
            localBuckets[i] = localBuckets[i] + 1;
        }
        int blackPoint = estimateBlackPoint(localBuckets);
        int left = localLuminances[0] & MotionEventCompat.ACTION_MASK;
        int center = localLuminances[1] & MotionEventCompat.ACTION_MASK;
        for (x = 1; x < width - 1; x++) {
            int right = localLuminances[x + 1] & MotionEventCompat.ACTION_MASK;
            if (((((center << 2) - left) - right) >> 1) < blackPoint) {
                row.set(x);
            }
            left = center;
            center = right;
        }
        return row;
    }

    public BitMatrix getBlackMatrix() throws NotFoundException {
        int y;
        byte[] localLuminances;
        LuminanceSource source = getLuminanceSource();
        int width = source.getWidth();
        int height = source.getHeight();
        BitMatrix matrix = new BitMatrix(width, height);
        initArrays(width);
        int[] localBuckets = this.buckets;
        for (y = 1; y < LUMINANCE_BITS; y++) {
            int x;
            localLuminances = source.getRow((height * y) / LUMINANCE_BITS, this.luminances);
            int right = (width << 2) / LUMINANCE_BITS;
            for (x = width / LUMINANCE_BITS; x < right; x++) {
                int i = (localLuminances[x] & MotionEventCompat.ACTION_MASK) >> LUMINANCE_SHIFT;
                localBuckets[i] = localBuckets[i] + 1;
            }
        }
        int blackPoint = estimateBlackPoint(localBuckets);
        localLuminances = source.getMatrix();
        for (y = 0; y < height; y++) {
            int offset = y * width;
            for (x = 0; x < width; x++) {
                if ((localLuminances[offset + x] & MotionEventCompat.ACTION_MASK) < blackPoint) {
                    matrix.set(x, y);
                }
            }
        }
        return matrix;
    }

    public Binarizer createBinarizer(LuminanceSource source) {
        return new GlobalHistogramBinarizer(source);
    }

    private void initArrays(int luminanceSize) {
        if (this.luminances.length < luminanceSize) {
            this.luminances = new byte[luminanceSize];
        }
        for (int x = 0; x < LUMINANCE_BUCKETS; x++) {
            this.buckets[x] = 0;
        }
    }

    private static int estimateBlackPoint(int[] buckets) throws NotFoundException {
        int x;
        int numBuckets = buckets.length;
        int maxBucketCount = 0;
        int firstPeak = 0;
        int firstPeakSize = 0;
        for (x = 0; x < numBuckets; x++) {
            if (buckets[x] > firstPeakSize) {
                firstPeak = x;
                firstPeakSize = buckets[x];
            }
            if (buckets[x] > maxBucketCount) {
                maxBucketCount = buckets[x];
            }
        }
        int secondPeak = 0;
        int secondPeakScore = 0;
        for (x = 0; x < numBuckets; x++) {
            int distanceToBiggest = x - firstPeak;
            int score = (buckets[x] * distanceToBiggest) * distanceToBiggest;
            if (score > secondPeakScore) {
                secondPeak = x;
                secondPeakScore = score;
            }
        }
        if (firstPeak > secondPeak) {
            int temp = firstPeak;
            firstPeak = secondPeak;
            secondPeak = temp;
        }
        if (secondPeak - firstPeak <= (numBuckets >> 4)) {
            throw NotFoundException.getNotFoundInstance();
        }
        int bestValley = secondPeak - 1;
        int bestValleyScore = -1;
        for (x = secondPeak - 1; x > firstPeak; x--) {
            int fromFirst = x - firstPeak;
            score = ((fromFirst * fromFirst) * (secondPeak - x)) * (maxBucketCount - buckets[x]);
            if (score > bestValleyScore) {
                bestValley = x;
                bestValleyScore = score;
            }
        }
        return bestValley << LUMINANCE_SHIFT;
    }
}
