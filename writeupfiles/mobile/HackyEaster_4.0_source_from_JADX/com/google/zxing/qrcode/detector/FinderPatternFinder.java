package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FinderPatternFinder {
    private static final int CENTER_QUORUM = 2;
    private static final int INTEGER_MATH_SHIFT = 8;
    protected static final int MAX_MODULES = 57;
    protected static final int MIN_SKIP = 3;
    private final int[] crossCheckStateCount;
    private boolean hasSkipped;
    private final BitMatrix image;
    private final List<FinderPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;

    private static final class CenterComparator implements Comparator<FinderPattern>, Serializable {
        private final float average;

        private CenterComparator(float f) {
            this.average = f;
        }

        public int compare(FinderPattern center1, FinderPattern center2) {
            if (center2.getCount() != center1.getCount()) {
                return center2.getCount() - center1.getCount();
            }
            float dA = Math.abs(center2.getEstimatedModuleSize() - this.average);
            float dB = Math.abs(center1.getEstimatedModuleSize() - this.average);
            if (dA < dB) {
                return 1;
            }
            return dA == dB ? 0 : -1;
        }
    }

    private static final class FurthestFromAverageComparator implements Comparator<FinderPattern>, Serializable {
        private final float average;

        private FurthestFromAverageComparator(float f) {
            this.average = f;
        }

        public int compare(FinderPattern center1, FinderPattern center2) {
            float dA = Math.abs(center2.getEstimatedModuleSize() - this.average);
            float dB = Math.abs(center1.getEstimatedModuleSize() - this.average);
            if (dA < dB) {
                return -1;
            }
            return dA == dB ? 0 : 1;
        }
    }

    public FinderPatternFinder(BitMatrix image) {
        this(image, null);
    }

    public FinderPatternFinder(BitMatrix image, ResultPointCallback resultPointCallback) {
        this.image = image;
        this.possibleCenters = new ArrayList();
        this.crossCheckStateCount = new int[5];
        this.resultPointCallback = resultPointCallback;
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final List<FinderPattern> getPossibleCenters() {
        return this.possibleCenters;
    }

    final FinderPatternInfo find(Map<DecodeHintType, ?> hints) throws NotFoundException {
        boolean tryHarder = hints != null && hints.containsKey(DecodeHintType.TRY_HARDER);
        int maxI = this.image.getHeight();
        int maxJ = this.image.getWidth();
        int iSkip = (maxI * MIN_SKIP) / 228;
        if (iSkip < MIN_SKIP || tryHarder) {
            iSkip = MIN_SKIP;
        }
        boolean done = false;
        int[] stateCount = new int[5];
        int i = iSkip - 1;
        while (i < maxI && !done) {
            stateCount[0] = 0;
            stateCount[1] = 0;
            stateCount[CENTER_QUORUM] = 0;
            stateCount[MIN_SKIP] = 0;
            stateCount[4] = 0;
            int currentState = 0;
            int j = 0;
            while (j < maxJ) {
                if (this.image.get(j, i)) {
                    if ((currentState & 1) == 1) {
                        currentState++;
                    }
                    stateCount[currentState] = stateCount[currentState] + 1;
                } else if ((currentState & 1) != 0) {
                    stateCount[currentState] = stateCount[currentState] + 1;
                } else if (currentState != 4) {
                    currentState++;
                    stateCount[currentState] = stateCount[currentState] + 1;
                } else if (!foundPatternCross(stateCount)) {
                    stateCount[0] = stateCount[CENTER_QUORUM];
                    stateCount[1] = stateCount[MIN_SKIP];
                    stateCount[CENTER_QUORUM] = stateCount[4];
                    stateCount[MIN_SKIP] = 1;
                    stateCount[4] = 0;
                    currentState = MIN_SKIP;
                } else if (handlePossibleCenter(stateCount, i, j)) {
                    iSkip = CENTER_QUORUM;
                    if (this.hasSkipped) {
                        done = haveMultiplyConfirmedCenters();
                    } else {
                        int rowSkip = findRowSkip();
                        if (rowSkip > stateCount[CENTER_QUORUM]) {
                            i += (rowSkip - stateCount[CENTER_QUORUM]) - CENTER_QUORUM;
                            j = maxJ - 1;
                        }
                    }
                    currentState = 0;
                    stateCount[0] = 0;
                    stateCount[1] = 0;
                    stateCount[CENTER_QUORUM] = 0;
                    stateCount[MIN_SKIP] = 0;
                    stateCount[4] = 0;
                } else {
                    stateCount[0] = stateCount[CENTER_QUORUM];
                    stateCount[1] = stateCount[MIN_SKIP];
                    stateCount[CENTER_QUORUM] = stateCount[4];
                    stateCount[MIN_SKIP] = 1;
                    stateCount[4] = 0;
                    currentState = MIN_SKIP;
                }
                j++;
            }
            if (foundPatternCross(stateCount) && handlePossibleCenter(stateCount, i, maxJ)) {
                iSkip = stateCount[0];
                if (this.hasSkipped) {
                    done = haveMultiplyConfirmedCenters();
                }
            }
            i += iSkip;
        }
        FinderPattern[] patternInfo = selectBestPatterns();
        ResultPoint.orderBestPatterns(patternInfo);
        return new FinderPatternInfo(patternInfo);
    }

    private static float centerFromEnd(int[] stateCount, int end) {
        return ((float) ((end - stateCount[4]) - stateCount[MIN_SKIP])) - (((float) stateCount[CENTER_QUORUM]) / 2.0f);
    }

    protected static boolean foundPatternCross(int[] stateCount) {
        boolean z = true;
        int totalModuleSize = 0;
        for (int i = 0; i < 5; i++) {
            int count = stateCount[i];
            if (count == 0) {
                return false;
            }
            totalModuleSize += count;
        }
        if (totalModuleSize < 7) {
            return false;
        }
        int moduleSize = (totalModuleSize << INTEGER_MATH_SHIFT) / 7;
        int maxVariance = moduleSize / CENTER_QUORUM;
        if (Math.abs(moduleSize - (stateCount[0] << INTEGER_MATH_SHIFT)) >= maxVariance || Math.abs(moduleSize - (stateCount[1] << INTEGER_MATH_SHIFT)) >= maxVariance || Math.abs((moduleSize * MIN_SKIP) - (stateCount[CENTER_QUORUM] << INTEGER_MATH_SHIFT)) >= maxVariance * MIN_SKIP || Math.abs(moduleSize - (stateCount[MIN_SKIP] << INTEGER_MATH_SHIFT)) >= maxVariance || Math.abs(moduleSize - (stateCount[4] << INTEGER_MATH_SHIFT)) >= maxVariance) {
            z = false;
        }
        return z;
    }

    private int[] getCrossCheckStateCount() {
        this.crossCheckStateCount[0] = 0;
        this.crossCheckStateCount[1] = 0;
        this.crossCheckStateCount[CENTER_QUORUM] = 0;
        this.crossCheckStateCount[MIN_SKIP] = 0;
        this.crossCheckStateCount[4] = 0;
        return this.crossCheckStateCount;
    }

    private float crossCheckVertical(int startI, int centerJ, int maxCount, int originalStateCountTotal) {
        BitMatrix image = this.image;
        int maxI = image.getHeight();
        int[] stateCount = getCrossCheckStateCount();
        int i = startI;
        while (i >= 0 && image.get(centerJ, i)) {
            stateCount[CENTER_QUORUM] = stateCount[CENTER_QUORUM] + 1;
            i--;
        }
        if (i < 0) {
            return Float.NaN;
        }
        while (i >= 0 && !image.get(centerJ, i) && stateCount[1] <= maxCount) {
            stateCount[1] = stateCount[1] + 1;
            i--;
        }
        if (i < 0 || stateCount[1] > maxCount) {
            return Float.NaN;
        }
        while (i >= 0 && image.get(centerJ, i) && stateCount[0] <= maxCount) {
            stateCount[0] = stateCount[0] + 1;
            i--;
        }
        if (stateCount[0] > maxCount) {
            return Float.NaN;
        }
        i = startI + 1;
        while (i < maxI && image.get(centerJ, i)) {
            stateCount[CENTER_QUORUM] = stateCount[CENTER_QUORUM] + 1;
            i++;
        }
        if (i == maxI) {
            return Float.NaN;
        }
        while (i < maxI && !image.get(centerJ, i) && stateCount[MIN_SKIP] < maxCount) {
            stateCount[MIN_SKIP] = stateCount[MIN_SKIP] + 1;
            i++;
        }
        if (i == maxI || stateCount[MIN_SKIP] >= maxCount) {
            return Float.NaN;
        }
        while (i < maxI && image.get(centerJ, i) && stateCount[4] < maxCount) {
            stateCount[4] = stateCount[4] + 1;
            i++;
        }
        if (stateCount[4] >= maxCount) {
            return Float.NaN;
        }
        if (Math.abs(((((stateCount[0] + stateCount[1]) + stateCount[CENTER_QUORUM]) + stateCount[MIN_SKIP]) + stateCount[4]) - originalStateCountTotal) * 5 >= originalStateCountTotal * CENTER_QUORUM) {
            return Float.NaN;
        }
        return foundPatternCross(stateCount) ? centerFromEnd(stateCount, i) : Float.NaN;
    }

    private float crossCheckHorizontal(int startJ, int centerI, int maxCount, int originalStateCountTotal) {
        BitMatrix image = this.image;
        int maxJ = image.getWidth();
        int[] stateCount = getCrossCheckStateCount();
        int j = startJ;
        while (j >= 0 && image.get(j, centerI)) {
            stateCount[CENTER_QUORUM] = stateCount[CENTER_QUORUM] + 1;
            j--;
        }
        if (j < 0) {
            return Float.NaN;
        }
        while (j >= 0 && !image.get(j, centerI) && stateCount[1] <= maxCount) {
            stateCount[1] = stateCount[1] + 1;
            j--;
        }
        if (j < 0 || stateCount[1] > maxCount) {
            return Float.NaN;
        }
        while (j >= 0 && image.get(j, centerI) && stateCount[0] <= maxCount) {
            stateCount[0] = stateCount[0] + 1;
            j--;
        }
        if (stateCount[0] > maxCount) {
            return Float.NaN;
        }
        j = startJ + 1;
        while (j < maxJ && image.get(j, centerI)) {
            stateCount[CENTER_QUORUM] = stateCount[CENTER_QUORUM] + 1;
            j++;
        }
        if (j == maxJ) {
            return Float.NaN;
        }
        while (j < maxJ && !image.get(j, centerI) && stateCount[MIN_SKIP] < maxCount) {
            stateCount[MIN_SKIP] = stateCount[MIN_SKIP] + 1;
            j++;
        }
        if (j == maxJ || stateCount[MIN_SKIP] >= maxCount) {
            return Float.NaN;
        }
        while (j < maxJ && image.get(j, centerI) && stateCount[4] < maxCount) {
            stateCount[4] = stateCount[4] + 1;
            j++;
        }
        if (stateCount[4] >= maxCount) {
            return Float.NaN;
        }
        if (Math.abs(((((stateCount[0] + stateCount[1]) + stateCount[CENTER_QUORUM]) + stateCount[MIN_SKIP]) + stateCount[4]) - originalStateCountTotal) * 5 >= originalStateCountTotal) {
            return Float.NaN;
        }
        return foundPatternCross(stateCount) ? centerFromEnd(stateCount, j) : Float.NaN;
    }

    protected final boolean handlePossibleCenter(int[] stateCount, int i, int j) {
        int stateCountTotal = (((stateCount[0] + stateCount[1]) + stateCount[CENTER_QUORUM]) + stateCount[MIN_SKIP]) + stateCount[4];
        float centerJ = centerFromEnd(stateCount, j);
        float centerI = crossCheckVertical(i, (int) centerJ, stateCount[CENTER_QUORUM], stateCountTotal);
        if (!Float.isNaN(centerI)) {
            centerJ = crossCheckHorizontal((int) centerJ, (int) centerI, stateCount[CENTER_QUORUM], stateCountTotal);
            if (!Float.isNaN(centerJ)) {
                float estimatedModuleSize = ((float) stateCountTotal) / 7.0f;
                boolean found = false;
                for (int index = 0; index < this.possibleCenters.size(); index++) {
                    FinderPattern center = (FinderPattern) this.possibleCenters.get(index);
                    if (center.aboutEquals(estimatedModuleSize, centerI, centerJ)) {
                        this.possibleCenters.set(index, center.combineEstimate(centerI, centerJ, estimatedModuleSize));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    FinderPattern point = new FinderPattern(centerJ, centerI, estimatedModuleSize);
                    this.possibleCenters.add(point);
                    if (this.resultPointCallback != null) {
                        this.resultPointCallback.foundPossibleResultPoint(point);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private int findRowSkip() {
        if (this.possibleCenters.size() <= 1) {
            return 0;
        }
        FinderPattern firstConfirmedCenter = null;
        for (FinderPattern center : this.possibleCenters) {
            if (center.getCount() >= CENTER_QUORUM) {
                if (firstConfirmedCenter == null) {
                    firstConfirmedCenter = center;
                } else {
                    this.hasSkipped = true;
                    return ((int) (Math.abs(firstConfirmedCenter.getX() - center.getX()) - Math.abs(firstConfirmedCenter.getY() - center.getY()))) / CENTER_QUORUM;
                }
            }
        }
        return 0;
    }

    private boolean haveMultiplyConfirmedCenters() {
        int confirmedCount = 0;
        float totalModuleSize = 0.0f;
        int max = this.possibleCenters.size();
        for (FinderPattern pattern : this.possibleCenters) {
            if (pattern.getCount() >= CENTER_QUORUM) {
                confirmedCount++;
                totalModuleSize += pattern.getEstimatedModuleSize();
            }
        }
        if (confirmedCount < MIN_SKIP) {
            return false;
        }
        float average = totalModuleSize / ((float) max);
        float totalDeviation = 0.0f;
        for (FinderPattern pattern2 : this.possibleCenters) {
            totalDeviation += Math.abs(pattern2.getEstimatedModuleSize() - average);
        }
        if (totalDeviation <= 0.05f * totalModuleSize) {
            return true;
        }
        return false;
    }

    private FinderPattern[] selectBestPatterns() throws NotFoundException {
        int startSize = this.possibleCenters.size();
        if (startSize < MIN_SKIP) {
            throw NotFoundException.getNotFoundInstance();
        }
        float totalModuleSize;
        if (startSize > MIN_SKIP) {
            totalModuleSize = 0.0f;
            float square = 0.0f;
            for (FinderPattern center : this.possibleCenters) {
                float size = center.getEstimatedModuleSize();
                totalModuleSize += size;
                square += size * size;
            }
            float average = totalModuleSize / ((float) startSize);
            float stdDev = (float) Math.sqrt((double) ((square / ((float) startSize)) - (average * average)));
            Collections.sort(this.possibleCenters, new FurthestFromAverageComparator(null));
            float limit = Math.max(0.2f * average, stdDev);
            int i = 0;
            while (i < this.possibleCenters.size() && this.possibleCenters.size() > MIN_SKIP) {
                if (Math.abs(((FinderPattern) this.possibleCenters.get(i)).getEstimatedModuleSize() - average) > limit) {
                    this.possibleCenters.remove(i);
                    i--;
                }
                i++;
            }
        }
        if (this.possibleCenters.size() > MIN_SKIP) {
            totalModuleSize = 0.0f;
            for (FinderPattern possibleCenter : this.possibleCenters) {
                totalModuleSize += possibleCenter.getEstimatedModuleSize();
            }
            Collections.sort(this.possibleCenters, new CenterComparator(null));
            this.possibleCenters.subList(MIN_SKIP, this.possibleCenters.size()).clear();
        }
        FinderPattern[] finderPatternArr = new FinderPattern[MIN_SKIP];
        finderPatternArr[0] = (FinderPattern) this.possibleCenters.get(0);
        finderPatternArr[1] = (FinderPattern) this.possibleCenters.get(1);
        finderPatternArr[CENTER_QUORUM] = (FinderPattern) this.possibleCenters.get(CENTER_QUORUM);
        return finderPatternArr;
    }
}
