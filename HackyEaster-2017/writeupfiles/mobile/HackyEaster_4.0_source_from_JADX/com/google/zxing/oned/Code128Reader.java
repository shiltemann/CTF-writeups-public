package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Code128Reader extends OneDReader {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    static final int[][] CODE_PATTERNS;
    private static final int CODE_SHIFT = 98;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final int MAX_AVG_VARIANCE = 64;
    private static final int MAX_INDIVIDUAL_VARIANCE = 179;

    static {
        CODE_PATTERNS = new int[][]{new int[]{2, 1, 2, 2, 2, 2}, new int[]{2, 2, 2, 1, 2, 2}, new int[]{2, 2, 2, 2, 2, 1}, new int[]{1, 2, 1, 2, 2, 3}, new int[]{1, 2, 1, 3, 2, 2}, new int[]{1, 3, 1, 2, 2, 2}, new int[]{1, 2, 2, 2, 1, 3}, new int[]{1, 2, 2, 3, 1, 2}, new int[]{1, 3, 2, 2, 1, 2}, new int[]{2, 2, 1, 2, 1, 3}, new int[]{2, 2, 1, 3, 1, 2}, new int[]{2, 3, 1, 2, 1, 2}, new int[]{1, 1, 2, 2, 3, 2}, new int[]{1, 2, 2, 1, 3, 2}, new int[]{1, 2, 2, 2, 3, 1}, new int[]{1, 1, 3, 2, 2, 2}, new int[]{1, 2, 3, 1, 2, 2}, new int[]{1, 2, 3, 2, 2, 1}, new int[]{2, 2, 3, 2, 1, 1}, new int[]{2, 2, 1, 1, 3, 2}, new int[]{2, 2, 1, 2, 3, 1}, new int[]{2, 1, 3, 2, 1, 2}, new int[]{2, 2, 3, 1, 1, 2}, new int[]{3, 1, 2, 1, 3, 1}, new int[]{3, 1, 1, 2, 2, 2}, new int[]{3, 2, 1, 1, 2, 2}, new int[]{3, 2, 1, 2, 2, 1}, new int[]{3, 1, 2, 2, 1, 2}, new int[]{3, 2, 2, 1, 1, 2}, new int[]{3, 2, 2, 2, 1, 1}, new int[]{2, 1, 2, 1, 2, 3}, new int[]{2, 1, 2, 3, 2, 1}, new int[]{2, 3, 2, 1, 2, 1}, new int[]{1, 1, 1, 3, 2, 3}, new int[]{1, 3, 1, 1, 2, 3}, new int[]{1, 3, 1, 3, 2, 1}, new int[]{1, 1, 2, 3, 1, 3}, new int[]{1, 3, 2, 1, 1, 3}, new int[]{1, 3, 2, 3, 1, 1}, new int[]{2, 1, 1, 3, 1, 3}, new int[]{2, 3, 1, 1, 1, 3}, new int[]{2, 3, 1, 3, 1, 1}, new int[]{1, 1, 2, 1, 3, 3}, new int[]{1, 1, 2, 3, 3, 1}, new int[]{1, 3, 2, 1, 3, 1}, new int[]{1, 1, 3, 1, 2, 3}, new int[]{1, 1, 3, 3, 2, 1}, new int[]{1, 3, 3, 1, 2, 1}, new int[]{3, 1, 3, 1, 2, 1}, new int[]{2, 1, 1, 3, 3, 1}, new int[]{2, 3, 1, 1, 3, 1}, new int[]{2, 1, 3, 1, 1, 3}, new int[]{2, 1, 3, 3, 1, 1}, new int[]{2, 1, 3, 1, 3, 1}, new int[]{3, 1, 1, 1, 2, 3}, new int[]{3, 1, 1, 3, 2, 1}, new int[]{3, 3, 1, 1, 2, 1}, new int[]{3, 1, 2, 1, 1, 3}, new int[]{3, 1, 2, 3, 1, 1}, new int[]{3, 3, 2, 1, 1, 1}, new int[]{3, 1, 4, 1, 1, 1}, new int[]{2, 2, 1, 4, 1, 1}, new int[]{4, 3, 1, 1, 1, 1}, new int[]{1, 1, 1, 2, 2, 4}, new int[]{1, 1, 1, 4, 2, 2}, new int[]{1, 2, 1, 1, 2, 4}, new int[]{1, 2, 1, 4, 2, 1}, new int[]{1, 4, 1, 1, 2, 2}, new int[]{1, 4, 1, 2, 2, 1}, new int[]{1, 1, 2, 2, 1, 4}, new int[]{1, 1, 2, 4, 1, 2}, new int[]{1, 2, 2, 1, 1, 4}, new int[]{1, 2, 2, 4, 1, 1}, new int[]{1, 4, 2, 1, 1, 2}, new int[]{1, 4, 2, 2, 1, 1}, new int[]{2, 4, 1, 2, 1, 1}, new int[]{2, 2, 1, 1, 1, 4}, new int[]{4, 1, 3, 1, 1, 1}, new int[]{2, 4, 1, 1, 1, 2}, new int[]{1, 3, 4, 1, 1, 1}, new int[]{1, 1, 1, 2, 4, 2}, new int[]{1, 2, 1, 1, 4, 2}, new int[]{1, 2, 1, 2, 4, 1}, new int[]{1, 1, 4, 2, 1, 2}, new int[]{1, 2, 4, 1, 1, 2}, new int[]{1, 2, 4, 2, 1, 1}, new int[]{4, 1, 1, 2, 1, 2}, new int[]{4, 2, 1, 1, 1, 2}, new int[]{4, 2, 1, 2, 1, 1}, new int[]{2, 1, 2, 1, 4, 1}, new int[]{2, 1, 4, 1, 2, 1}, new int[]{4, 1, 2, 1, 2, 1}, new int[]{1, 1, 1, 1, 4, 3}, new int[]{1, 1, 1, 3, 4, 1}, new int[]{1, 3, 1, 1, 4, 1}, new int[]{1, 1, 4, 1, 1, 3}, new int[]{1, 1, 4, 3, 1, 1}, new int[]{4, 1, 1, 1, 1, 3}, new int[]{4, 1, 1, 3, 1, 1}, new int[]{1, 1, 3, 1, 4, 1}, new int[]{1, 1, 4, 1, 3, 1}, new int[]{3, 1, 1, 1, 4, 1}, new int[]{4, 1, 1, 1, 3, 1}, new int[]{2, 1, 1, 4, 1, 2}, new int[]{2, 1, 1, 2, 1, 4}, new int[]{2, 1, 1, 2, 3, 2}, new int[]{2, 3, 3, 1, 1, 1, 2}};
    }

    private static int[] findStartPattern(BitArray row) throws NotFoundException {
        int width = row.getSize();
        int rowOffset = row.getNextSet(0);
        int counterPosition = 0;
        int[] counters = new int[6];
        int patternStart = rowOffset;
        boolean isWhite = false;
        int patternLength = counters.length;
        int i = rowOffset;
        while (i < width) {
            if ((row.get(i) ^ isWhite) != 0) {
                counters[counterPosition] = counters[counterPosition] + 1;
            } else {
                if (counterPosition == patternLength - 1) {
                    int bestVariance = MAX_AVG_VARIANCE;
                    int bestMatch = -1;
                    for (int startCode = CODE_START_A; startCode <= CODE_START_C; startCode++) {
                        int variance = OneDReader.patternMatchVariance(counters, CODE_PATTERNS[startCode], MAX_INDIVIDUAL_VARIANCE);
                        if (variance < bestVariance) {
                            bestVariance = variance;
                            bestMatch = startCode;
                        }
                    }
                    if (bestMatch < 0 || !row.isRange(Math.max(0, patternStart - ((i - patternStart) / 2)), patternStart, false)) {
                        patternStart += counters[0] + counters[1];
                        System.arraycopy(counters, 2, counters, 0, patternLength - 2);
                        counters[patternLength - 2] = 0;
                        counters[patternLength - 1] = 0;
                        counterPosition--;
                    } else {
                        return new int[]{patternStart, i, bestMatch};
                    }
                }
                counterPosition++;
                counters[counterPosition] = 1;
                isWhite = !isWhite;
            }
            i++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int decodeCode(BitArray row, int[] counters, int rowOffset) throws NotFoundException {
        OneDReader.recordPattern(row, rowOffset, counters);
        int bestVariance = MAX_AVG_VARIANCE;
        int bestMatch = -1;
        for (int d = 0; d < CODE_PATTERNS.length; d++) {
            int variance = OneDReader.patternMatchVariance(counters, CODE_PATTERNS[d], MAX_INDIVIDUAL_VARIANCE);
            if (variance < bestVariance) {
                bestVariance = variance;
                bestMatch = d;
            }
        }
        if (bestMatch >= 0) {
            return bestMatch;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public Result decodeRow(int rowNumber, BitArray row, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException, ChecksumException {
        int codeSet;
        int[] startPatternInfo = findStartPattern(row);
        int startCode = startPatternInfo[2];
        switch (startCode) {
            case CODE_START_A /*103*/:
                codeSet = CODE_FNC_4_A;
                break;
            case CODE_START_B /*104*/:
                codeSet = CODE_FNC_4_B;
                break;
            case CODE_START_C /*105*/:
                codeSet = CODE_CODE_C;
                break;
            default:
                throw FormatException.getFormatInstance();
        }
        boolean done = false;
        boolean isNextShifted = false;
        StringBuilder stringBuilder = new StringBuilder(20);
        List<Byte> arrayList = new ArrayList(20);
        int lastStart = startPatternInfo[0];
        int nextStart = startPatternInfo[1];
        int[] counters = new int[6];
        int lastCode = 0;
        int code = 0;
        int checksumTotal = startCode;
        int multiplier = 0;
        boolean lastCharacterWasPrintable = true;
        while (!done) {
            boolean unshift = isNextShifted;
            isNextShifted = false;
            lastCode = code;
            code = decodeCode(row, counters, nextStart);
            arrayList.add(Byte.valueOf((byte) code));
            if (code != CODE_STOP) {
                lastCharacterWasPrintable = true;
            }
            if (code != CODE_STOP) {
                multiplier++;
                checksumTotal += multiplier * code;
            }
            lastStart = nextStart;
            for (int counter : counters) {
                nextStart += counter;
            }
            switch (code) {
                case CODE_START_A /*103*/:
                case CODE_START_B /*104*/:
                case CODE_START_C /*105*/:
                    throw FormatException.getFormatInstance();
                default:
                    switch (codeSet) {
                        case CODE_CODE_C /*99*/:
                            if (code >= CODE_FNC_4_B) {
                                if (code != CODE_STOP) {
                                    lastCharacterWasPrintable = false;
                                }
                                switch (code) {
                                    case CODE_FNC_4_B /*100*/:
                                        codeSet = CODE_FNC_4_B;
                                        break;
                                    case CODE_FNC_4_A /*101*/:
                                        codeSet = CODE_FNC_4_A;
                                        break;
                                    case CODE_FNC_1 /*102*/:
                                        break;
                                    case CODE_STOP /*106*/:
                                        done = true;
                                        break;
                                    default:
                                        break;
                                }
                            }
                            if (code < 10) {
                                stringBuilder.append('0');
                            }
                            stringBuilder.append(code);
                            break;
                        case CODE_FNC_4_B /*100*/:
                            if (code >= CODE_FNC_3) {
                                if (code != CODE_STOP) {
                                    lastCharacterWasPrintable = false;
                                }
                                switch (code) {
                                    case CODE_FNC_3 /*96*/:
                                    case CODE_FNC_2 /*97*/:
                                    case CODE_FNC_4_B /*100*/:
                                    case CODE_FNC_1 /*102*/:
                                        break;
                                    case CODE_SHIFT /*98*/:
                                        isNextShifted = true;
                                        codeSet = CODE_FNC_4_A;
                                        break;
                                    case CODE_CODE_C /*99*/:
                                        codeSet = CODE_CODE_C;
                                        break;
                                    case CODE_FNC_4_A /*101*/:
                                        codeSet = CODE_FNC_4_A;
                                        break;
                                    case CODE_STOP /*106*/:
                                        done = true;
                                        break;
                                    default:
                                        break;
                                }
                            }
                            stringBuilder.append((char) (code + 32));
                            break;
                        case CODE_FNC_4_A /*101*/:
                            if (code >= MAX_AVG_VARIANCE) {
                                if (code >= CODE_FNC_3) {
                                    if (code != CODE_STOP) {
                                        lastCharacterWasPrintable = false;
                                    }
                                    switch (code) {
                                        case CODE_FNC_3 /*96*/:
                                        case CODE_FNC_2 /*97*/:
                                        case CODE_FNC_4_A /*101*/:
                                        case CODE_FNC_1 /*102*/:
                                            break;
                                        case CODE_SHIFT /*98*/:
                                            isNextShifted = true;
                                            codeSet = CODE_FNC_4_B;
                                            break;
                                        case CODE_CODE_C /*99*/:
                                            codeSet = CODE_CODE_C;
                                            break;
                                        case CODE_FNC_4_B /*100*/:
                                            codeSet = CODE_FNC_4_B;
                                            break;
                                        case CODE_STOP /*106*/:
                                            done = true;
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                stringBuilder.append((char) (code - 64));
                                break;
                            }
                            stringBuilder.append((char) (code + 32));
                            break;
                            break;
                    }
                    if (unshift) {
                        if (codeSet == CODE_FNC_4_A) {
                            codeSet = CODE_FNC_4_B;
                        } else {
                            codeSet = CODE_FNC_4_A;
                        }
                    }
            }
        }
        nextStart = row.getNextUnset(nextStart);
        if (!row.isRange(nextStart, Math.min(row.getSize(), ((nextStart - lastStart) / 2) + nextStart), false)) {
            throw NotFoundException.getNotFoundInstance();
        } else if ((checksumTotal - (multiplier * lastCode)) % CODE_START_A != lastCode) {
            throw ChecksumException.getChecksumInstance();
        } else {
            int resultLength = stringBuilder.length();
            if (resultLength == 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (resultLength > 0 && lastCharacterWasPrintable) {
                if (codeSet == CODE_CODE_C) {
                    stringBuilder.delete(resultLength - 2, resultLength);
                } else {
                    stringBuilder.delete(resultLength - 1, resultLength);
                }
            }
            float left = ((float) (startPatternInfo[1] + startPatternInfo[0])) / 2.0f;
            float right = ((float) (nextStart + lastStart)) / 2.0f;
            int rawCodesSize = arrayList.size();
            byte[] rawBytes = new byte[rawCodesSize];
            for (int i = 0; i < rawCodesSize; i++) {
                rawBytes[i] = ((Byte) arrayList.get(i)).byteValue();
            }
            String stringBuilder2 = stringBuilder.toString();
            r33 = new ResultPoint[2];
            r33[0] = new ResultPoint(left, (float) rowNumber);
            r33[1] = new ResultPoint(right, (float) rowNumber);
            return new Result(stringBuilder2, rawBytes, r33, BarcodeFormat.CODE_128);
        }
    }
}
