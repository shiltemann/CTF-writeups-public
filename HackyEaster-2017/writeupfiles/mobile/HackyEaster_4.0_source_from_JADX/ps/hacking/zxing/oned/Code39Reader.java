package ps.hacking.zxing.oned;

import android.support.v4.view.MotionEventCompat;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.apache.http.util.LangUtils;
import ps.hacking.zxing.BarcodeFormat;
import ps.hacking.zxing.ChecksumException;
import ps.hacking.zxing.DecodeHintType;
import ps.hacking.zxing.FormatException;
import ps.hacking.zxing.NotFoundException;
import ps.hacking.zxing.Result;
import ps.hacking.zxing.ResultPoint;
import ps.hacking.zxing.common.BitArray;

public final class Code39Reader extends OneDReader {
    private static final char[] ALPHABET;
    static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%";
    private static final int ASTERISK_ENCODING;
    static final int[] CHARACTER_ENCODINGS;
    private final boolean extendedMode;
    private final boolean usingCheckDigit;

    static {
        ALPHABET = ALPHABET_STRING.toCharArray();
        CHARACTER_ENCODINGS = new int[]{52, 289, 97, 352, 49, HttpStatus.SC_NOT_MODIFIED, 112, 37, 292, 100, 265, 73, 328, 25, 280, 88, 13, 268, 76, 28, 259, 67, 322, 19, 274, 82, 7, 262, 70, 22, 385, 193, 448, 145, HttpStatus.SC_BAD_REQUEST, 208, 133, 388, 196, 148, 168, 162, 138, 42};
        ASTERISK_ENCODING = CHARACTER_ENCODINGS[39];
    }

    public Code39Reader() {
        this.usingCheckDigit = false;
        this.extendedMode = false;
    }

    public Code39Reader(boolean usingCheckDigit) {
        this.usingCheckDigit = usingCheckDigit;
        this.extendedMode = false;
    }

    public Code39Reader(boolean usingCheckDigit, boolean extendedMode) {
        this.usingCheckDigit = usingCheckDigit;
        this.extendedMode = extendedMode;
    }

    public Result decodeRow(int rowNumber, BitArray row, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int lastStart;
        int[] counters = new int[9];
        int[] start = findAsteriskPattern(row, counters);
        int nextStart = row.getNextSet(start[1]);
        int end = row.getSize();
        StringBuilder stringBuilder = new StringBuilder(20);
        char decodedChar;
        do {
            OneDReader.recordPattern(row, nextStart, counters);
            int pattern = toNarrowWidePattern(counters);
            if (pattern < 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            decodedChar = patternToChar(pattern);
            stringBuilder.append(decodedChar);
            lastStart = nextStart;
            for (int counter : counters) {
                nextStart += counter;
            }
            nextStart = row.getNextSet(nextStart);
        } while (decodedChar != '*');
        stringBuilder.setLength(stringBuilder.length() - 1);
        int lastPatternSize = 0;
        for (int counter2 : counters) {
            lastPatternSize += counter2;
        }
        int whiteSpaceAfterEnd = (nextStart - lastStart) - lastPatternSize;
        if (nextStart == end || (whiteSpaceAfterEnd >> 1) >= lastPatternSize) {
            if (this.usingCheckDigit) {
                int max = stringBuilder.length() - 1;
                int total = 0;
                for (int i = 0; i < max; i++) {
                    total += ALPHABET_STRING.indexOf(stringBuilder.charAt(i));
                }
                if (stringBuilder.charAt(max) != ALPHABET[total % 43]) {
                    throw ChecksumException.getChecksumInstance();
                }
                stringBuilder.setLength(max);
            }
            if (stringBuilder.length() == 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            String resultString;
            if (this.extendedMode) {
                resultString = decodeExtended(stringBuilder);
            } else {
                resultString = stringBuilder.toString();
            }
            float right = ((float) (nextStart + lastStart)) / 2.0f;
            r27 = new ResultPoint[2];
            r27[0] = new ResultPoint(((float) (start[1] + start[0])) / 2.0f, (float) rowNumber);
            r27[1] = new ResultPoint(right, (float) rowNumber);
            return new Result(resultString, null, r27, BarcodeFormat.CODE_39);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int[] findAsteriskPattern(BitArray row, int[] counters) throws NotFoundException {
        int width = row.getSize();
        int rowOffset = row.getNextSet(0);
        int counterPosition = 0;
        int patternStart = rowOffset;
        boolean isWhite = false;
        int patternLength = counters.length;
        int i = rowOffset;
        while (i < width) {
            if ((row.get(i) ^ isWhite) != 0) {
                counters[counterPosition] = counters[counterPosition] + 1;
            } else {
                if (counterPosition != patternLength - 1) {
                    counterPosition++;
                } else if (toNarrowWidePattern(counters) == ASTERISK_ENCODING && row.isRange(Math.max(0, patternStart - ((i - patternStart) >> 1)), patternStart, false)) {
                    return new int[]{patternStart, i};
                } else {
                    patternStart += counters[0] + counters[1];
                    System.arraycopy(counters, 2, counters, 0, patternLength - 2);
                    counters[patternLength - 2] = 0;
                    counters[patternLength - 1] = 0;
                    counterPosition--;
                }
                counters[counterPosition] = 1;
                if (isWhite) {
                    isWhite = false;
                } else {
                    isWhite = true;
                }
            }
            i++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int toNarrowWidePattern(int[] counters) {
        int numCounters = counters.length;
        int maxNarrowCounter = 0;
        int wideCounters;
        do {
            int counter;
            int i;
            int minCounter = Integer.MAX_VALUE;
            for (int counter2 : counters) {
                if (counter2 < minCounter && counter2 > maxNarrowCounter) {
                    minCounter = counter2;
                }
            }
            maxNarrowCounter = minCounter;
            wideCounters = 0;
            int totalWideCountersWidth = 0;
            int pattern = 0;
            for (i = 0; i < numCounters; i++) {
                counter2 = counters[i];
                if (counters[i] > maxNarrowCounter) {
                    pattern |= 1 << ((numCounters - 1) - i);
                    wideCounters++;
                    totalWideCountersWidth += counter2;
                }
            }
            if (wideCounters == 3) {
                for (i = 0; i < numCounters && wideCounters > 0; i++) {
                    counter2 = counters[i];
                    if (counters[i] > maxNarrowCounter) {
                        wideCounters--;
                        if ((counter2 << 1) >= totalWideCountersWidth) {
                            return -1;
                        }
                    }
                }
                return pattern;
            }
        } while (wideCounters > 3);
        return -1;
    }

    private static char patternToChar(int pattern) throws NotFoundException {
        for (int i = 0; i < CHARACTER_ENCODINGS.length; i++) {
            if (CHARACTER_ENCODINGS[i] == pattern) {
                return ALPHABET[i];
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static String decodeExtended(CharSequence encoded) throws FormatException {
        int length = encoded.length();
        StringBuilder decoded = new StringBuilder(length);
        int i = 0;
        while (i < length) {
            char c = encoded.charAt(i);
            if (c == '+' || c == '$' || c == '%' || c == '/') {
                char next = encoded.charAt(i + 1);
                char decodedChar = '\u0000';
                switch (c) {
                    case MotionEventCompat.AXIS_GENERIC_5 /*36*/:
                        if (next >= 'A' && next <= 'Z') {
                            decodedChar = (char) (next - 64);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                        break;
                    case LangUtils.HASH_OFFSET /*37*/:
                        if (next < 'A' || next > 'E') {
                            if (next >= 'F' && next <= 'W') {
                                decodedChar = (char) (next - 11);
                                break;
                            }
                            throw FormatException.getFormatInstance();
                        }
                        decodedChar = (char) (next - 38);
                        break;
                        break;
                    case MotionEventCompat.AXIS_GENERIC_12 /*43*/:
                        if (next >= 'A' && next <= 'Z') {
                            decodedChar = (char) (next + 32);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                        break;
                    case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
                        if (next >= 'A' && next <= 'O') {
                            decodedChar = (char) (next - 32);
                            break;
                        } else if (next == 'Z') {
                            decodedChar = ':';
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                }
                decoded.append(decodedChar);
                i++;
            } else {
                decoded.append(c);
            }
            i++;
        }
        return decoded.toString();
    }
}
