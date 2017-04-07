package com.google.zxing.oned;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public abstract class OneDReader implements Reader {
    protected static final int INTEGER_MATH_SHIFT = 8;
    protected static final int PATTERN_MATCH_RESULT_SCALE_FACTOR = 256;

    public abstract Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException;

    public Result decode(BinaryBitmap image) throws NotFoundException, FormatException {
        return decode(image, null);
    }

    public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, FormatException {
        Result doDecode;
        try {
            doDecode = doDecode(image, hints);
        } catch (NotFoundException nfe) {
            boolean tryHarder = hints != null && hints.containsKey(DecodeHintType.TRY_HARDER);
            if (tryHarder && image.isRotateSupported()) {
                BinaryBitmap rotatedImage = image.rotateCounterClockwise();
                doDecode = doDecode(rotatedImage, hints);
                Map<ResultMetadataType, ?> metadata = doDecode.getResultMetadata();
                int orientation = 270;
                if (metadata != null && metadata.containsKey(ResultMetadataType.ORIENTATION)) {
                    orientation = (((Integer) metadata.get(ResultMetadataType.ORIENTATION)).intValue() + 270) % 360;
                }
                doDecode.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(orientation));
                ResultPoint[] points = doDecode.getResultPoints();
                if (points != null) {
                    int height = rotatedImage.getHeight();
                    for (int i = 0; i < points.length; i++) {
                        points[i] = new ResultPoint((((float) height) - points[i].getY()) - 1.0f, points[i].getX());
                    }
                }
            } else {
                throw nfe;
            }
        }
        return doDecode;
    }

    public void reset() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.zxing.Result doDecode(com.google.zxing.BinaryBitmap r23, java.util.Map<com.google.zxing.DecodeHintType, ?> r24) throws com.google.zxing.NotFoundException {
        /*
        r22 = this;
        r16 = r23.getWidth();
        r3 = r23.getHeight();
        r11 = new com.google.zxing.common.BitArray;
        r0 = r16;
        r11.<init>(r0);
        r6 = r3 >> 1;
        if (r24 == 0) goto L_0x0051;
    L_0x0013:
        r18 = com.google.zxing.DecodeHintType.TRY_HARDER;
        r0 = r24;
        r1 = r18;
        r18 = r0.containsKey(r1);
        if (r18 == 0) goto L_0x0051;
    L_0x001f:
        r15 = 1;
    L_0x0020:
        r19 = 1;
        if (r15 == 0) goto L_0x0053;
    L_0x0024:
        r18 = 8;
    L_0x0026:
        r18 = r3 >> r18;
        r0 = r19;
        r1 = r18;
        r13 = java.lang.Math.max(r0, r1);
        if (r15 == 0) goto L_0x0056;
    L_0x0032:
        r5 = r3;
    L_0x0033:
        r17 = 0;
    L_0x0035:
        r0 = r17;
        if (r0 >= r5) goto L_0x004c;
    L_0x0039:
        r18 = r17 + 1;
        r14 = r18 >> 1;
        r18 = r17 & 1;
        if (r18 != 0) goto L_0x0059;
    L_0x0041:
        r4 = 1;
    L_0x0042:
        if (r4 == 0) goto L_0x005b;
    L_0x0044:
        r18 = r13 * r14;
        r12 = r6 + r18;
        if (r12 < 0) goto L_0x004c;
    L_0x004a:
        if (r12 < r3) goto L_0x005d;
    L_0x004c:
        r18 = com.google.zxing.NotFoundException.getNotFoundInstance();
        throw r18;
    L_0x0051:
        r15 = 0;
        goto L_0x0020;
    L_0x0053:
        r18 = 5;
        goto L_0x0026;
    L_0x0056:
        r5 = 15;
        goto L_0x0033;
    L_0x0059:
        r4 = 0;
        goto L_0x0042;
    L_0x005b:
        r14 = -r14;
        goto L_0x0044;
    L_0x005d:
        r0 = r23;
        r11 = r0.getBlackRow(r12, r11);	 Catch:{ NotFoundException -> 0x0104 }
        r2 = 0;
    L_0x0064:
        r18 = 2;
        r0 = r18;
        if (r2 >= r0) goto L_0x0105;
    L_0x006a:
        r18 = 1;
        r0 = r18;
        if (r2 != r0) goto L_0x0098;
    L_0x0070:
        r11.reverse();
        if (r24 == 0) goto L_0x0098;
    L_0x0075:
        r18 = com.google.zxing.DecodeHintType.NEED_RESULT_POINT_CALLBACK;
        r0 = r24;
        r1 = r18;
        r18 = r0.containsKey(r1);
        if (r18 == 0) goto L_0x0098;
    L_0x0081:
        r7 = new java.util.EnumMap;
        r18 = com.google.zxing.DecodeHintType.class;
        r0 = r18;
        r7.<init>(r0);
        r0 = r24;
        r7.putAll(r0);
        r18 = com.google.zxing.DecodeHintType.NEED_RESULT_POINT_CALLBACK;
        r0 = r18;
        r7.remove(r0);
        r24 = r7;
    L_0x0098:
        r0 = r22;
        r1 = r24;
        r10 = r0.decodeRow(r12, r11, r1);	 Catch:{ ReaderException -> 0x0109 }
        r18 = 1;
        r0 = r18;
        if (r2 != r0) goto L_0x0103;
    L_0x00a6:
        r18 = com.google.zxing.ResultMetadataType.ORIENTATION;	 Catch:{ ReaderException -> 0x0109 }
        r19 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ ReaderException -> 0x0109 }
        r0 = r18;
        r1 = r19;
        r10.putMetadata(r0, r1);	 Catch:{ ReaderException -> 0x0109 }
        r9 = r10.getResultPoints();	 Catch:{ ReaderException -> 0x0109 }
        if (r9 == 0) goto L_0x0103;
    L_0x00bb:
        r18 = 0;
        r19 = new com.google.zxing.ResultPoint;	 Catch:{ ReaderException -> 0x0109 }
        r0 = r16;
        r0 = (float) r0;	 Catch:{ ReaderException -> 0x0109 }
        r20 = r0;
        r21 = 0;
        r21 = r9[r21];	 Catch:{ ReaderException -> 0x0109 }
        r21 = r21.getX();	 Catch:{ ReaderException -> 0x0109 }
        r20 = r20 - r21;
        r21 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r20 = r20 - r21;
        r21 = 0;
        r21 = r9[r21];	 Catch:{ ReaderException -> 0x0109 }
        r21 = r21.getY();	 Catch:{ ReaderException -> 0x0109 }
        r19.<init>(r20, r21);	 Catch:{ ReaderException -> 0x0109 }
        r9[r18] = r19;	 Catch:{ ReaderException -> 0x0109 }
        r18 = 1;
        r19 = new com.google.zxing.ResultPoint;	 Catch:{ ReaderException -> 0x0109 }
        r0 = r16;
        r0 = (float) r0;	 Catch:{ ReaderException -> 0x0109 }
        r20 = r0;
        r21 = 1;
        r21 = r9[r21];	 Catch:{ ReaderException -> 0x0109 }
        r21 = r21.getX();	 Catch:{ ReaderException -> 0x0109 }
        r20 = r20 - r21;
        r21 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r20 = r20 - r21;
        r21 = 1;
        r21 = r9[r21];	 Catch:{ ReaderException -> 0x0109 }
        r21 = r21.getY();	 Catch:{ ReaderException -> 0x0109 }
        r19.<init>(r20, r21);	 Catch:{ ReaderException -> 0x0109 }
        r9[r18] = r19;	 Catch:{ ReaderException -> 0x0109 }
    L_0x0103:
        return r10;
    L_0x0104:
        r8 = move-exception;
    L_0x0105:
        r17 = r17 + 1;
        goto L_0x0035;
    L_0x0109:
        r18 = move-exception;
        r2 = r2 + 1;
        goto L_0x0064;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.OneDReader.doDecode(com.google.zxing.BinaryBitmap, java.util.Map):com.google.zxing.Result");
    }

    protected static void recordPattern(BitArray row, int start, int[] counters) throws NotFoundException {
        int numCounters = counters.length;
        Arrays.fill(counters, 0, numCounters, 0);
        int end = row.getSize();
        if (start >= end) {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean isWhite;
        if (row.get(start)) {
            isWhite = false;
        } else {
            isWhite = true;
        }
        int counterPosition = 0;
        int i = start;
        while (i < end) {
            if ((row.get(i) ^ isWhite) != 0) {
                counters[counterPosition] = counters[counterPosition] + 1;
            } else {
                counterPosition++;
                if (counterPosition == numCounters) {
                    break;
                }
                counters[counterPosition] = 1;
                isWhite = !isWhite;
            }
            i++;
        }
        if (counterPosition == numCounters) {
            return;
        }
        if (counterPosition != numCounters - 1 || i != end) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    protected static void recordPatternInReverse(BitArray row, int start, int[] counters) throws NotFoundException {
        int numTransitionsLeft = counters.length;
        boolean last = row.get(start);
        while (start > 0 && numTransitionsLeft >= 0) {
            start--;
            if (row.get(start) != last) {
                numTransitionsLeft--;
                last = !last;
            }
        }
        if (numTransitionsLeft >= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        recordPattern(row, start + 1, counters);
    }

    protected static int patternMatchVariance(int[] counters, int[] pattern, int maxIndividualVariance) {
        int numCounters = counters.length;
        int total = 0;
        int patternLength = 0;
        for (int i = 0; i < numCounters; i++) {
            total += counters[i];
            patternLength += pattern[i];
        }
        if (total < patternLength) {
            return Integer.MAX_VALUE;
        }
        int unitBarWidth = (total << INTEGER_MATH_SHIFT) / patternLength;
        maxIndividualVariance = (maxIndividualVariance * unitBarWidth) >> INTEGER_MATH_SHIFT;
        int totalVariance = 0;
        for (int x = 0; x < numCounters; x++) {
            int counter = counters[x] << INTEGER_MATH_SHIFT;
            int scaledPattern = pattern[x] * unitBarWidth;
            int variance = counter > scaledPattern ? counter - scaledPattern : scaledPattern - counter;
            if (variance > maxIndividualVariance) {
                return Integer.MAX_VALUE;
            }
            totalVariance += variance;
        }
        return totalVariance / total;
    }
}
