package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

public final class UPCEReader extends UPCEANReader {
    private static final int[] MIDDLE_END_PATTERN;
    private static final int[][] NUMSYS_AND_CHECK_DIGIT_PATTERNS;
    private final int[] decodeMiddleCounters;

    static {
        MIDDLE_END_PATTERN = new int[]{1, 1, 1, 1, 1, 1};
        NUMSYS_AND_CHECK_DIGIT_PATTERNS = new int[][]{new int[]{56, 52, 50, 49, 44, 38, 35, 42, 41, 37}, new int[]{7, 11, 13, 14, 19, 25, 28, 21, 22, 26}};
    }

    public UPCEReader() {
        this.decodeMiddleCounters = new int[4];
    }

    protected int decodeMiddle(BitArray row, int[] startRange, StringBuilder result) throws NotFoundException {
        int[] counters = this.decodeMiddleCounters;
        counters[0] = 0;
        counters[1] = 0;
        counters[2] = 0;
        counters[3] = 0;
        int end = row.getSize();
        int rowOffset = startRange[1];
        int lgPatternFound = 0;
        for (int x = 0; x < 6 && rowOffset < end; x++) {
            int bestMatch = UPCEANReader.decodeDigit(row, counters, rowOffset, L_AND_G_PATTERNS);
            result.append((char) ((bestMatch % 10) + 48));
            for (int counter : counters) {
                rowOffset += counter;
            }
            if (bestMatch >= 10) {
                lgPatternFound |= 1 << (5 - x);
            }
        }
        determineNumSysAndCheckDigit(result, lgPatternFound);
        return rowOffset;
    }

    protected int[] decodeEnd(BitArray row, int endStart) throws NotFoundException {
        return UPCEANReader.findGuardPattern(row, endStart, true, MIDDLE_END_PATTERN);
    }

    protected boolean checkChecksum(String s) throws FormatException, ChecksumException {
        return super.checkChecksum(convertUPCEtoUPCA(s));
    }

    private static void determineNumSysAndCheckDigit(StringBuilder resultString, int lgPatternFound) throws NotFoundException {
        for (int numSys = 0; numSys <= 1; numSys++) {
            for (int d = 0; d < 10; d++) {
                if (lgPatternFound == NUMSYS_AND_CHECK_DIGIT_PATTERNS[numSys][d]) {
                    resultString.insert(0, (char) (numSys + 48));
                    resultString.append((char) (d + 48));
                    return;
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.UPC_E;
    }

    public static String convertUPCEtoUPCA(String upce) {
        char[] upceChars = new char[6];
        upce.getChars(1, 7, upceChars, 0);
        StringBuilder result = new StringBuilder(12);
        result.append(upce.charAt(0));
        char lastChar = upceChars[5];
        switch (lastChar) {
            case '0':
            case '1':
            case '2':
                result.append(upceChars, 0, 2);
                result.append(lastChar);
                result.append("0000");
                result.append(upceChars, 2, 3);
                break;
            case '3':
                result.append(upceChars, 0, 3);
                result.append("00000");
                result.append(upceChars, 3, 2);
                break;
            case '4':
                result.append(upceChars, 0, 4);
                result.append("00000");
                result.append(upceChars[4]);
                break;
            default:
                result.append(upceChars, 0, 5);
                result.append("0000");
                result.append(lastChar);
                break;
        }
        result.append(upce.charAt(7));
        return result.toString();
    }
}
