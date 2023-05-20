package com.google.zxing.oned.rss.expanded;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpStatus;

public final class RSSExpandedReader extends AbstractRSSReader {
    private static final int[] EVEN_TOTAL_SUBSET;
    private static final int[][] FINDER_PATTERNS;
    private static final int[][] FINDER_PATTERN_SEQUENCES;
    private static final int FINDER_PAT_A = 0;
    private static final int FINDER_PAT_B = 1;
    private static final int FINDER_PAT_C = 2;
    private static final int FINDER_PAT_D = 3;
    private static final int FINDER_PAT_E = 4;
    private static final int FINDER_PAT_F = 5;
    private static final int[] GSUM;
    private static final int LONGEST_SEQUENCE_SIZE;
    private static final int MAX_PAIRS = 11;
    private static final int[] SYMBOL_WIDEST;
    private static final int[][] WEIGHTS;
    private final int[] currentSequence;
    private final List<ExpandedPair> pairs;
    private final int[] startEnd;

    public RSSExpandedReader() {
        this.pairs = new ArrayList(MAX_PAIRS);
        this.startEnd = new int[FINDER_PAT_C];
        this.currentSequence = new int[LONGEST_SEQUENCE_SIZE];
    }

    static {
        SYMBOL_WIDEST = new int[]{7, FINDER_PAT_F, FINDER_PAT_E, FINDER_PAT_D, FINDER_PAT_B};
        EVEN_TOTAL_SUBSET = new int[]{FINDER_PAT_E, 20, 52, 104, HttpStatus.SC_NO_CONTENT};
        GSUM = new int[]{LONGEST_SEQUENCE_SIZE, 348, 1388, 2948, 3988};
        FINDER_PATTERNS = new int[][]{new int[]{FINDER_PAT_B, 8, FINDER_PAT_E, FINDER_PAT_B}, new int[]{FINDER_PAT_D, 6, FINDER_PAT_E, FINDER_PAT_B}, new int[]{FINDER_PAT_D, FINDER_PAT_E, 6, FINDER_PAT_B}, new int[]{FINDER_PAT_D, FINDER_PAT_C, 8, FINDER_PAT_B}, new int[]{FINDER_PAT_C, 6, FINDER_PAT_F, FINDER_PAT_B}, new int[]{FINDER_PAT_C, FINDER_PAT_C, 9, FINDER_PAT_B}};
        WEIGHTS = new int[][]{new int[]{FINDER_PAT_B, FINDER_PAT_D, 9, 27, 81, 32, 96, 77}, new int[]{20, 60, 180, 118, 143, 7, 21, 63}, new int[]{189, 145, 13, 39, 117, 140, 209, HttpStatus.SC_RESET_CONTENT}, new int[]{193, 157, 49, 147, 19, 57, 171, 91}, new int[]{62, 186, 136, 197, 169, 85, 44, 132}, new int[]{185, 133, 188, 142, FINDER_PAT_E, 12, 36, 108}, new int[]{113, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS, 173, 97, 80, 29, 87, 50}, new int[]{150, 28, 84, 41, 123, 158, 52, 156}, new int[]{46, 138, HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION, 187, 139, HttpStatus.SC_PARTIAL_CONTENT, 196, 166}, new int[]{76, 17, 51, 153, 37, 111, 122, 155}, new int[]{43, 129, 176, 106, 107, 110, 119, 146}, new int[]{16, 48, 144, 10, 30, 90, 59, 177}, new int[]{109, 116, 137, HttpStatus.SC_OK, 178, 112, 125, 164}, new int[]{70, 210, 208, HttpStatus.SC_ACCEPTED, 184, 130, 179, 115}, new int[]{134, 191, 151, 31, 93, 68, HttpStatus.SC_NO_CONTENT, 190}, new int[]{148, 22, 66, 198, 172, 94, 71, FINDER_PAT_C}, new int[]{6, 18, 54, 162, 64, 192, 154, 40}, new int[]{120, 149, 25, 75, 14, 42, 126, 167}, new int[]{79, 26, 78, 23, 69, HttpStatus.SC_MULTI_STATUS, 199, 175}, new int[]{103, 98, 83, 38, 114, 131, 182, 124}, new int[]{161, 61, 183, 127, 170, 88, 53, 159}, new int[]{55, 165, 73, 8, 24, 72, FINDER_PAT_F, 15}, new int[]{45, 135, 194, 160, 58, 174, 100, 89}};
        FINDER_PATTERN_SEQUENCES = new int[][]{new int[]{LONGEST_SEQUENCE_SIZE, LONGEST_SEQUENCE_SIZE}, new int[]{LONGEST_SEQUENCE_SIZE, FINDER_PAT_B, FINDER_PAT_B}, new int[]{LONGEST_SEQUENCE_SIZE, FINDER_PAT_C, FINDER_PAT_B, FINDER_PAT_D}, new int[]{LONGEST_SEQUENCE_SIZE, FINDER_PAT_E, FINDER_PAT_B, FINDER_PAT_D, FINDER_PAT_C}, new int[]{LONGEST_SEQUENCE_SIZE, FINDER_PAT_E, FINDER_PAT_B, FINDER_PAT_D, FINDER_PAT_D, FINDER_PAT_F}, new int[]{LONGEST_SEQUENCE_SIZE, FINDER_PAT_E, FINDER_PAT_B, FINDER_PAT_D, FINDER_PAT_E, FINDER_PAT_F, FINDER_PAT_F}, new int[]{LONGEST_SEQUENCE_SIZE, LONGEST_SEQUENCE_SIZE, FINDER_PAT_B, FINDER_PAT_B, FINDER_PAT_C, FINDER_PAT_C, FINDER_PAT_D, FINDER_PAT_D}, new int[]{LONGEST_SEQUENCE_SIZE, LONGEST_SEQUENCE_SIZE, FINDER_PAT_B, FINDER_PAT_B, FINDER_PAT_C, FINDER_PAT_C, FINDER_PAT_D, FINDER_PAT_E, FINDER_PAT_E}, new int[]{LONGEST_SEQUENCE_SIZE, LONGEST_SEQUENCE_SIZE, FINDER_PAT_B, FINDER_PAT_B, FINDER_PAT_C, FINDER_PAT_C, FINDER_PAT_D, FINDER_PAT_E, FINDER_PAT_F, FINDER_PAT_F}, new int[]{LONGEST_SEQUENCE_SIZE, LONGEST_SEQUENCE_SIZE, FINDER_PAT_B, FINDER_PAT_B, FINDER_PAT_C, FINDER_PAT_D, FINDER_PAT_D, FINDER_PAT_E, FINDER_PAT_E, FINDER_PAT_F, FINDER_PAT_F}};
        LONGEST_SEQUENCE_SIZE = FINDER_PATTERN_SEQUENCES[FINDER_PATTERN_SEQUENCES.length - 1].length;
    }

    public Result decodeRow(int rowNumber, BitArray row, Map<DecodeHintType, ?> map) throws NotFoundException {
        reset();
        decodeRow2pairs(rowNumber, row);
        return constructResult(this.pairs);
    }

    public void reset() {
        this.pairs.clear();
    }

    List<ExpandedPair> decodeRow2pairs(int rowNumber, BitArray row) throws NotFoundException {
        while (true) {
            ExpandedPair nextPair = retrieveNextPair(row, this.pairs, rowNumber);
            this.pairs.add(nextPair);
            if (nextPair.mayBeLast()) {
                if (checkChecksum()) {
                    return this.pairs;
                }
                if (nextPair.mustBeLast()) {
                    break;
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static Result constructResult(List<ExpandedPair> pairs) throws NotFoundException {
        String resultingString = AbstractExpandedDecoder.createDecoder(BitArrayBuilder.buildBitArray(pairs)).parseInformation();
        ResultPoint[] firstPoints = ((ExpandedPair) pairs.get(LONGEST_SEQUENCE_SIZE)).getFinderPattern().getResultPoints();
        ResultPoint[] lastPoints = ((ExpandedPair) pairs.get(pairs.size() - 1)).getFinderPattern().getResultPoints();
        ResultPoint[] resultPointArr = new ResultPoint[FINDER_PAT_E];
        resultPointArr[LONGEST_SEQUENCE_SIZE] = firstPoints[LONGEST_SEQUENCE_SIZE];
        resultPointArr[FINDER_PAT_B] = firstPoints[FINDER_PAT_B];
        resultPointArr[FINDER_PAT_C] = lastPoints[LONGEST_SEQUENCE_SIZE];
        resultPointArr[FINDER_PAT_D] = lastPoints[FINDER_PAT_B];
        return new Result(resultingString, null, resultPointArr, BarcodeFormat.RSS_EXPANDED);
    }

    private boolean checkChecksum() {
        ExpandedPair firstPair = (ExpandedPair) this.pairs.get(LONGEST_SEQUENCE_SIZE);
        DataCharacter checkCharacter = firstPair.getLeftChar();
        int checksum = firstPair.getRightChar().getChecksumPortion();
        int s = FINDER_PAT_C;
        for (int i = FINDER_PAT_B; i < this.pairs.size(); i += FINDER_PAT_B) {
            ExpandedPair currentPair = (ExpandedPair) this.pairs.get(i);
            checksum += currentPair.getLeftChar().getChecksumPortion();
            s += FINDER_PAT_B;
            DataCharacter currentRightChar = currentPair.getRightChar();
            if (currentRightChar != null) {
                checksum += currentRightChar.getChecksumPortion();
                s += FINDER_PAT_B;
            }
        }
        if (((s - 4) * 211) + (checksum % 211) == checkCharacter.getValue()) {
            return true;
        }
        return false;
    }

    private static int getNextSecondBar(BitArray row, int initialPos) {
        if (row.get(initialPos)) {
            return row.getNextSet(row.getNextUnset(initialPos));
        }
        return row.getNextUnset(row.getNextSet(initialPos));
    }

    ExpandedPair retrieveNextPair(BitArray row, List<ExpandedPair> previousPairs, int rowNumber) throws NotFoundException {
        boolean isOddPattern;
        FinderPattern pattern;
        DataCharacter rightChar;
        if (previousPairs.size() % FINDER_PAT_C == 0) {
            isOddPattern = true;
        } else {
            isOddPattern = false;
        }
        boolean keepFinding = true;
        int forcedOffset = -1;
        do {
            findNextPair(row, previousPairs, forcedOffset);
            pattern = parseFoundFinderPattern(row, rowNumber, isOddPattern);
            if (pattern == null) {
                forcedOffset = getNextSecondBar(row, this.startEnd[LONGEST_SEQUENCE_SIZE]);
                continue;
            } else {
                keepFinding = false;
                continue;
            }
        } while (keepFinding);
        boolean mayBeLast = checkPairSequence(previousPairs, pattern);
        DataCharacter leftChar = decodeDataCharacter(row, pattern, isOddPattern, true);
        try {
            rightChar = decodeDataCharacter(row, pattern, isOddPattern, false);
        } catch (NotFoundException nfe) {
            if (mayBeLast) {
                rightChar = null;
            } else {
                throw nfe;
            }
        }
        return new ExpandedPair(leftChar, rightChar, pattern, mayBeLast);
    }

    private boolean checkPairSequence(List<ExpandedPair> previousPairs, FinderPattern pattern) throws NotFoundException {
        int currentSequenceLength = previousPairs.size() + FINDER_PAT_B;
        if (currentSequenceLength > this.currentSequence.length) {
            throw NotFoundException.getNotFoundInstance();
        }
        int pos;
        for (pos = LONGEST_SEQUENCE_SIZE; pos < previousPairs.size(); pos += FINDER_PAT_B) {
            this.currentSequence[pos] = ((ExpandedPair) previousPairs.get(pos)).getFinderPattern().getValue();
        }
        this.currentSequence[currentSequenceLength - 1] = pattern.getValue();
        int[][] arr$ = FINDER_PATTERN_SEQUENCES;
        int len$ = arr$.length;
        for (int i$ = LONGEST_SEQUENCE_SIZE; i$ < len$; i$ += FINDER_PAT_B) {
            int[] validSequence = arr$[i$];
            if (validSequence.length >= currentSequenceLength) {
                boolean valid = true;
                for (pos = LONGEST_SEQUENCE_SIZE; pos < currentSequenceLength; pos += FINDER_PAT_B) {
                    if (this.currentSequence[pos] != validSequence[pos]) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    if (currentSequenceLength == validSequence.length) {
                        return true;
                    }
                    return false;
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void findNextPair(BitArray row, List<ExpandedPair> previousPairs, int forcedOffset) throws NotFoundException {
        int rowOffset;
        int[] counters = getDecodeFinderCounters();
        counters[LONGEST_SEQUENCE_SIZE] = LONGEST_SEQUENCE_SIZE;
        counters[FINDER_PAT_B] = LONGEST_SEQUENCE_SIZE;
        counters[FINDER_PAT_C] = LONGEST_SEQUENCE_SIZE;
        counters[FINDER_PAT_D] = LONGEST_SEQUENCE_SIZE;
        int width = row.getSize();
        if (forcedOffset >= 0) {
            rowOffset = forcedOffset;
        } else if (previousPairs.isEmpty()) {
            rowOffset = LONGEST_SEQUENCE_SIZE;
        } else {
            rowOffset = ((ExpandedPair) previousPairs.get(previousPairs.size() - 1)).getFinderPattern().getStartEnd()[FINDER_PAT_B];
        }
        boolean searchingEvenPair = previousPairs.size() % FINDER_PAT_C != 0;
        boolean isWhite = false;
        while (rowOffset < width) {
            isWhite = !row.get(rowOffset);
            if (!isWhite) {
                break;
            }
            rowOffset += FINDER_PAT_B;
        }
        int counterPosition = LONGEST_SEQUENCE_SIZE;
        int patternStart = rowOffset;
        for (int x = rowOffset; x < width; x += FINDER_PAT_B) {
            if ((row.get(x) ^ isWhite) != 0) {
                counters[counterPosition] = counters[counterPosition] + FINDER_PAT_B;
            } else {
                if (counterPosition == FINDER_PAT_D) {
                    if (searchingEvenPair) {
                        reverseCounters(counters);
                    }
                    if (AbstractRSSReader.isFinderPattern(counters)) {
                        this.startEnd[LONGEST_SEQUENCE_SIZE] = patternStart;
                        this.startEnd[FINDER_PAT_B] = x;
                        return;
                    }
                    if (searchingEvenPair) {
                        reverseCounters(counters);
                    }
                    patternStart += counters[LONGEST_SEQUENCE_SIZE] + counters[FINDER_PAT_B];
                    counters[LONGEST_SEQUENCE_SIZE] = counters[FINDER_PAT_C];
                    counters[FINDER_PAT_B] = counters[FINDER_PAT_D];
                    counters[FINDER_PAT_C] = LONGEST_SEQUENCE_SIZE;
                    counters[FINDER_PAT_D] = LONGEST_SEQUENCE_SIZE;
                    counterPosition--;
                } else {
                    counterPosition += FINDER_PAT_B;
                }
                counters[counterPosition] = FINDER_PAT_B;
                isWhite = !isWhite;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static void reverseCounters(int[] counters) {
        int length = counters.length;
        for (int i = LONGEST_SEQUENCE_SIZE; i < length / FINDER_PAT_C; i += FINDER_PAT_B) {
            int tmp = counters[i];
            counters[i] = counters[(length - i) - 1];
            counters[(length - i) - 1] = tmp;
        }
    }

    private FinderPattern parseFoundFinderPattern(BitArray row, int rowNumber, boolean oddPattern) {
        int firstCounter;
        int start;
        int end;
        if (oddPattern) {
            int firstElementStart = this.startEnd[LONGEST_SEQUENCE_SIZE] - 1;
            while (firstElementStart >= 0 && !row.get(firstElementStart)) {
                firstElementStart--;
            }
            firstElementStart += FINDER_PAT_B;
            firstCounter = this.startEnd[LONGEST_SEQUENCE_SIZE] - firstElementStart;
            start = firstElementStart;
            end = this.startEnd[FINDER_PAT_B];
        } else {
            start = this.startEnd[LONGEST_SEQUENCE_SIZE];
            end = row.getNextUnset(this.startEnd[FINDER_PAT_B] + FINDER_PAT_B);
            firstCounter = end - this.startEnd[FINDER_PAT_B];
        }
        int[] counters = getDecodeFinderCounters();
        System.arraycopy(counters, LONGEST_SEQUENCE_SIZE, counters, FINDER_PAT_B, counters.length - 1);
        counters[LONGEST_SEQUENCE_SIZE] = firstCounter;
        try {
            int value = AbstractRSSReader.parseFinderValue(counters, FINDER_PATTERNS);
            int[] iArr = new int[FINDER_PAT_C];
            iArr[LONGEST_SEQUENCE_SIZE] = start;
            iArr[FINDER_PAT_B] = end;
            return new FinderPattern(value, iArr, start, end, rowNumber);
        } catch (NotFoundException e) {
            return null;
        }
    }

    DataCharacter decodeDataCharacter(BitArray row, FinderPattern pattern, boolean isOddPattern, boolean leftChar) throws NotFoundException {
        int i;
        int length;
        int[] counters = getDataCharacterCounters();
        counters[LONGEST_SEQUENCE_SIZE] = LONGEST_SEQUENCE_SIZE;
        counters[FINDER_PAT_B] = LONGEST_SEQUENCE_SIZE;
        counters[FINDER_PAT_C] = LONGEST_SEQUENCE_SIZE;
        counters[FINDER_PAT_D] = LONGEST_SEQUENCE_SIZE;
        counters[FINDER_PAT_E] = LONGEST_SEQUENCE_SIZE;
        counters[FINDER_PAT_F] = LONGEST_SEQUENCE_SIZE;
        counters[6] = LONGEST_SEQUENCE_SIZE;
        counters[7] = LONGEST_SEQUENCE_SIZE;
        if (leftChar) {
            OneDReader.recordPatternInReverse(row, pattern.getStartEnd()[LONGEST_SEQUENCE_SIZE], counters);
        } else {
            OneDReader.recordPattern(row, pattern.getStartEnd()[FINDER_PAT_B] + FINDER_PAT_B, counters);
            i = LONGEST_SEQUENCE_SIZE;
            for (int j = counters.length - 1; i < j; j--) {
                int temp = counters[i];
                counters[i] = counters[j];
                counters[j] = temp;
                i += FINDER_PAT_B;
            }
        }
        float elementWidth = ((float) AbstractRSSReader.count(counters)) / ((float) 17);
        int[] oddCounts = getOddCounts();
        int[] evenCounts = getEvenCounts();
        float[] oddRoundingErrors = getOddRoundingErrors();
        float[] evenRoundingErrors = getEvenRoundingErrors();
        i = LONGEST_SEQUENCE_SIZE;
        while (true) {
            length = counters.length;
            if (i >= r0) {
                break;
            }
            float value = (1.0f * ((float) counters[i])) / elementWidth;
            int count = (int) (0.5f + value);
            if (count < FINDER_PAT_B) {
                count = FINDER_PAT_B;
            } else if (count > 8) {
                count = 8;
            }
            int offset = i >> FINDER_PAT_B;
            if ((i & FINDER_PAT_B) == 0) {
                oddCounts[offset] = count;
                oddRoundingErrors[offset] = value - ((float) count);
            } else {
                evenCounts[offset] = count;
                evenRoundingErrors[offset] = value - ((float) count);
            }
            i += FINDER_PAT_B;
        }
        adjustOddEvenCounts(17);
        int value2 = (pattern.getValue() * FINDER_PAT_E) + (isOddPattern ? LONGEST_SEQUENCE_SIZE : FINDER_PAT_C);
        if (leftChar) {
            length = LONGEST_SEQUENCE_SIZE;
        } else {
            length = FINDER_PAT_B;
        }
        int weightRowNumber = (length + value2) - 1;
        int oddSum = LONGEST_SEQUENCE_SIZE;
        int oddChecksumPortion = LONGEST_SEQUENCE_SIZE;
        for (i = oddCounts.length - 1; i >= 0; i--) {
            if (isNotA1left(pattern, isOddPattern, leftChar)) {
                oddChecksumPortion += oddCounts[i] * WEIGHTS[weightRowNumber][i * FINDER_PAT_C];
            }
            oddSum += oddCounts[i];
        }
        int evenChecksumPortion = LONGEST_SEQUENCE_SIZE;
        int evenSum = LONGEST_SEQUENCE_SIZE;
        for (i = evenCounts.length - 1; i >= 0; i--) {
            if (isNotA1left(pattern, isOddPattern, leftChar)) {
                evenChecksumPortion += evenCounts[i] * WEIGHTS[weightRowNumber][(i * FINDER_PAT_C) + FINDER_PAT_B];
            }
            evenSum += evenCounts[i];
        }
        int checksumPortion = oddChecksumPortion + evenChecksumPortion;
        if ((oddSum & FINDER_PAT_B) != 0 || oddSum > 13 || oddSum < FINDER_PAT_E) {
            throw NotFoundException.getNotFoundInstance();
        }
        int group = (13 - oddSum) / FINDER_PAT_C;
        int oddWidest = SYMBOL_WIDEST[group];
        int evenWidest = 9 - oddWidest;
        int vOdd = RSSUtils.getRSSvalue(oddCounts, oddWidest, true);
        return new DataCharacter(((vOdd * EVEN_TOTAL_SUBSET[group]) + RSSUtils.getRSSvalue(evenCounts, evenWidest, false)) + GSUM[group], checksumPortion);
    }

    private static boolean isNotA1left(FinderPattern pattern, boolean isOddPattern, boolean leftChar) {
        return (pattern.getValue() == 0 && isOddPattern && leftChar) ? false : true;
    }

    private void adjustOddEvenCounts(int numModules) throws NotFoundException {
        boolean oddParityBad;
        boolean evenParityBad = false;
        int oddSum = AbstractRSSReader.count(getOddCounts());
        int evenSum = AbstractRSSReader.count(getEvenCounts());
        int mismatch = (oddSum + evenSum) - numModules;
        if ((oddSum & FINDER_PAT_B) == FINDER_PAT_B) {
            oddParityBad = true;
        } else {
            oddParityBad = false;
        }
        if ((evenSum & FINDER_PAT_B) == 0) {
            evenParityBad = true;
        }
        boolean incrementOdd = false;
        boolean decrementOdd = false;
        if (oddSum > 13) {
            decrementOdd = true;
        } else if (oddSum < FINDER_PAT_E) {
            incrementOdd = true;
        }
        boolean incrementEven = false;
        boolean decrementEven = false;
        if (evenSum > 13) {
            decrementEven = true;
        } else if (evenSum < FINDER_PAT_E) {
            incrementEven = true;
        }
        if (mismatch == FINDER_PAT_B) {
            if (oddParityBad) {
                if (evenParityBad) {
                    throw NotFoundException.getNotFoundInstance();
                }
                decrementOdd = true;
            } else if (evenParityBad) {
                decrementEven = true;
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        } else if (mismatch == -1) {
            if (oddParityBad) {
                if (evenParityBad) {
                    throw NotFoundException.getNotFoundInstance();
                }
                incrementOdd = true;
            } else if (evenParityBad) {
                incrementEven = true;
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        } else if (mismatch != 0) {
            throw NotFoundException.getNotFoundInstance();
        } else if (oddParityBad) {
            if (!evenParityBad) {
                throw NotFoundException.getNotFoundInstance();
            } else if (oddSum < evenSum) {
                incrementOdd = true;
                decrementEven = true;
            } else {
                decrementOdd = true;
                incrementEven = true;
            }
        } else if (evenParityBad) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (incrementOdd) {
            if (decrementOdd) {
                throw NotFoundException.getNotFoundInstance();
            }
            AbstractRSSReader.increment(getOddCounts(), getOddRoundingErrors());
        }
        if (decrementOdd) {
            AbstractRSSReader.decrement(getOddCounts(), getOddRoundingErrors());
        }
        if (incrementEven) {
            if (decrementEven) {
                throw NotFoundException.getNotFoundInstance();
            }
            AbstractRSSReader.increment(getEvenCounts(), getOddRoundingErrors());
        }
        if (decrementEven) {
            AbstractRSSReader.decrement(getEvenCounts(), getEvenRoundingErrors());
        }
    }
}
