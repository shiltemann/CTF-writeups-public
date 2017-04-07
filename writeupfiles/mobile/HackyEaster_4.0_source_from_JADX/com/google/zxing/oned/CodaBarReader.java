package com.google.zxing.oned;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Map;

public final class CodaBarReader extends OneDReader {
    static final char[] ALPHABET;
    private static final String ALPHABET_STRING = "0123456789-$:/.+ABCD";
    static final int[] CHARACTER_ENCODINGS;
    private static final int MAX_ACCEPTABLE = 512;
    private static final int MIN_CHARACTER_LENGTH = 3;
    private static final int PADDING = 384;
    private static final char[] STARTEND_ENCODING;
    private int counterLength;
    private int[] counters;
    private final StringBuilder decodeRowResult;

    static {
        ALPHABET = ALPHABET_STRING.toCharArray();
        CHARACTER_ENCODINGS = new int[]{MIN_CHARACTER_LENGTH, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};
        STARTEND_ENCODING = new char[]{'A', 'B', 'C', 'D'};
    }

    public CodaBarReader() {
        this.decodeRowResult = new StringBuilder(20);
        this.counters = new int[80];
        this.counterLength = 0;
    }

    public Result decodeRow(int rowNumber, BitArray row, Map<DecodeHintType, ?> map) throws NotFoundException {
        int i;
        setCounters(row);
        int startOffset = findStartPattern();
        int nextStart = startOffset;
        this.decodeRowResult.setLength(0);
        do {
            int charOffset = toNarrowWidePattern(nextStart);
            if (charOffset != -1) {
                this.decodeRowResult.append((char) charOffset);
                nextStart += 8;
                if (this.decodeRowResult.length() > 1 && arrayContains(STARTEND_ENCODING, ALPHABET[charOffset])) {
                    break;
                }
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        } while (nextStart < this.counterLength);
        int trailingWhitespace = this.counters[nextStart - 1];
        int lastPatternSize = 0;
        for (i = -8; i < -1; i++) {
            lastPatternSize += this.counters[nextStart + i];
        }
        if (nextStart >= this.counterLength || trailingWhitespace >= lastPatternSize / 2) {
            validatePattern(startOffset);
            for (i = 0; i < this.decodeRowResult.length(); i++) {
                this.decodeRowResult.setCharAt(i, ALPHABET[this.decodeRowResult.charAt(i)]);
            }
            if (arrayContains(STARTEND_ENCODING, this.decodeRowResult.charAt(0))) {
                if (!arrayContains(STARTEND_ENCODING, this.decodeRowResult.charAt(this.decodeRowResult.length() - 1))) {
                    throw NotFoundException.getNotFoundInstance();
                } else if (this.decodeRowResult.length() <= MIN_CHARACTER_LENGTH) {
                    throw NotFoundException.getNotFoundInstance();
                } else {
                    this.decodeRowResult.deleteCharAt(this.decodeRowResult.length() - 1);
                    this.decodeRowResult.deleteCharAt(0);
                    int runningCount = 0;
                    for (i = 0; i < startOffset; i++) {
                        runningCount += this.counters[i];
                    }
                    float left = (float) runningCount;
                    for (i = startOffset; i < nextStart - 1; i++) {
                        runningCount += this.counters[i];
                    }
                    float right = (float) runningCount;
                    String stringBuilder = this.decodeRowResult.toString();
                    r16 = new ResultPoint[2];
                    r16[0] = new ResultPoint(left, (float) rowNumber);
                    r16[1] = new ResultPoint(right, (float) rowNumber);
                    return new Result(stringBuilder, null, r16, BarcodeFormat.CODABAR);
                }
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void validatePattern(int r16) throws com.google.zxing.NotFoundException {
        /*
        r15 = this;
        r11 = 4;
        r10 = new int[r11];
        r10 = {0, 0, 0, 0};
        r11 = 4;
        r1 = new int[r11];
        r1 = {0, 0, 0, 0};
        r11 = r15.decodeRowResult;
        r11 = r11.length();
        r2 = r11 + -1;
        r8 = r16;
        r3 = 0;
    L_0x0017:
        r11 = CHARACTER_ENCODINGS;
        r12 = r15.decodeRowResult;
        r12 = r12.charAt(r3);
        r7 = r11[r12];
        r4 = 6;
    L_0x0022:
        if (r4 < 0) goto L_0x0042;
    L_0x0024:
        r11 = r4 & 1;
        r12 = r7 & 1;
        r12 = r12 * 2;
        r0 = r11 + r12;
        r11 = r10[r0];
        r12 = r15.counters;
        r13 = r8 + r4;
        r12 = r12[r13];
        r11 = r11 + r12;
        r10[r0] = r11;
        r11 = r1[r0];
        r11 = r11 + 1;
        r1[r0] = r11;
        r7 = r7 >> 1;
        r4 = r4 + -1;
        goto L_0x0022;
    L_0x0042:
        if (r3 < r2) goto L_0x0084;
    L_0x0044:
        r11 = 4;
        r5 = new int[r11];
        r11 = 4;
        r6 = new int[r11];
        r3 = 0;
    L_0x004b:
        r11 = 2;
        if (r3 >= r11) goto L_0x0089;
    L_0x004e:
        r11 = 0;
        r6[r3] = r11;
        r11 = r3 + 2;
        r12 = r10[r3];
        r12 = r12 << 8;
        r13 = r1[r3];
        r12 = r12 / r13;
        r13 = r3 + 2;
        r13 = r10[r13];
        r13 = r13 << 8;
        r14 = r3 + 2;
        r14 = r1[r14];
        r13 = r13 / r14;
        r12 = r12 + r13;
        r12 = r12 >> 1;
        r6[r11] = r12;
        r11 = r3 + 2;
        r11 = r6[r11];
        r5[r3] = r11;
        r11 = r3 + 2;
        r12 = r3 + 2;
        r12 = r10[r12];
        r12 = r12 * 512;
        r12 = r12 + 384;
        r13 = r3 + 2;
        r13 = r1[r13];
        r12 = r12 / r13;
        r5[r11] = r12;
        r3 = r3 + 1;
        goto L_0x004b;
    L_0x0084:
        r8 = r8 + 8;
        r3 = r3 + 1;
        goto L_0x0017;
    L_0x0089:
        r8 = r16;
        r3 = 0;
    L_0x008c:
        r11 = CHARACTER_ENCODINGS;
        r12 = r15.decodeRowResult;
        r12 = r12.charAt(r3);
        r7 = r11[r12];
        r4 = 6;
    L_0x0097:
        if (r4 < 0) goto L_0x00bb;
    L_0x0099:
        r11 = r4 & 1;
        r12 = r7 & 1;
        r12 = r12 * 2;
        r0 = r11 + r12;
        r11 = r15.counters;
        r12 = r8 + r4;
        r11 = r11[r12];
        r9 = r11 << 8;
        r11 = r6[r0];
        if (r9 < r11) goto L_0x00b1;
    L_0x00ad:
        r11 = r5[r0];
        if (r9 <= r11) goto L_0x00b6;
    L_0x00b1:
        r11 = com.google.zxing.NotFoundException.getNotFoundInstance();
        throw r11;
    L_0x00b6:
        r7 = r7 >> 1;
        r4 = r4 + -1;
        goto L_0x0097;
    L_0x00bb:
        if (r3 < r2) goto L_0x00be;
    L_0x00bd:
        return;
    L_0x00be:
        r8 = r8 + 8;
        r3 = r3 + 1;
        goto L_0x008c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.CodaBarReader.validatePattern(int):void");
    }

    private void setCounters(BitArray row) throws NotFoundException {
        this.counterLength = 0;
        int i = row.getNextUnset(0);
        int end = row.getSize();
        if (i >= end) {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean isWhite = true;
        int count = 0;
        while (i < end) {
            if ((row.get(i) ^ isWhite) != 0) {
                count++;
            } else {
                counterAppend(count);
                count = 1;
                isWhite = !isWhite;
            }
            i++;
        }
        counterAppend(count);
    }

    private void counterAppend(int e) {
        this.counters[this.counterLength] = e;
        this.counterLength++;
        if (this.counterLength >= this.counters.length) {
            int[] temp = new int[(this.counterLength * 2)];
            System.arraycopy(this.counters, 0, temp, 0, this.counterLength);
            this.counters = temp;
        }
    }

    private int findStartPattern() throws NotFoundException {
        int i = 1;
        while (i < this.counterLength) {
            int charOffset = toNarrowWidePattern(i);
            if (charOffset != -1 && arrayContains(STARTEND_ENCODING, ALPHABET[charOffset])) {
                int patternSize = 0;
                for (int j = i; j < i + 7; j++) {
                    patternSize += this.counters[j];
                }
                if (i == 1 || this.counters[i - 1] >= patternSize / 2) {
                    return i;
                }
            }
            i += 2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    static boolean arrayContains(char[] array, char key) {
        if (array != null) {
            for (char c : array) {
                if (c == key) {
                    return true;
                }
            }
        }
        return false;
    }

    private int toNarrowWidePattern(int position) {
        int end = position + 7;
        if (end >= this.counterLength) {
            return -1;
        }
        int i;
        int[] maxes = new int[]{0, 0};
        int[] mins = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        int[] thresholds = new int[]{0, 0};
        for (i = 0; i < 2; i++) {
            for (int j = position + i; j < end; j += 2) {
                if (this.counters[j] < mins[i]) {
                    mins[i] = this.counters[j];
                }
                if (this.counters[j] > maxes[i]) {
                    maxes[i] = this.counters[j];
                }
            }
            thresholds[i] = (mins[i] + maxes[i]) / 2;
        }
        int bitmask = AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS;
        int pattern = 0;
        for (i = 0; i < 7; i++) {
            bitmask >>= 1;
            if (this.counters[position + i] > thresholds[i & 1]) {
                pattern |= bitmask;
            }
        }
        for (i = 0; i < CHARACTER_ENCODINGS.length; i++) {
            if (CHARACTER_ENCODINGS[i] == pattern) {
                return i;
            }
        }
        return -1;
    }
}
