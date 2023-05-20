package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

final class MultiFinderPatternFinder extends FinderPatternFinder {
    private static final float DIFF_MODSIZE_CUTOFF = 0.5f;
    private static final float DIFF_MODSIZE_CUTOFF_PERCENT = 0.05f;
    private static final FinderPatternInfo[] EMPTY_RESULT_ARRAY;
    private static final float MAX_MODULE_COUNT_PER_EDGE = 180.0f;
    private static final float MIN_MODULE_COUNT_PER_EDGE = 9.0f;

    private static final class ModuleSizeComparator implements Comparator<FinderPattern>, Serializable {
        private ModuleSizeComparator() {
        }

        public int compare(FinderPattern center1, FinderPattern center2) {
            float value = center2.getEstimatedModuleSize() - center1.getEstimatedModuleSize();
            if (((double) value) < 0.0d) {
                return -1;
            }
            return ((double) value) > 0.0d ? 1 : 0;
        }
    }

    static {
        EMPTY_RESULT_ARRAY = new FinderPatternInfo[0];
    }

    MultiFinderPatternFinder(BitMatrix image) {
        super(image);
    }

    MultiFinderPatternFinder(BitMatrix image, ResultPointCallback resultPointCallback) {
        super(image, resultPointCallback);
    }

    private FinderPattern[][] selectMutipleBestPatterns() throws NotFoundException {
        List<FinderPattern> possibleCenters = getPossibleCenters();
        int size = possibleCenters.size();
        if (size < 3) {
            throw NotFoundException.getNotFoundInstance();
        } else if (size == 3) {
            FinderPattern[][] finderPatternArr = new FinderPattern[1][];
            finderPatternArr[0] = new FinderPattern[]{(FinderPattern) possibleCenters.get(0), (FinderPattern) possibleCenters.get(1), (FinderPattern) possibleCenters.get(2)};
            return finderPatternArr;
        } else {
            Collections.sort(possibleCenters, new ModuleSizeComparator());
            List<FinderPattern[]> results = new ArrayList();
            for (int i1 = 0; i1 < size - 2; i1++) {
                FinderPattern p1 = (FinderPattern) possibleCenters.get(i1);
                if (p1 != null) {
                    for (int i2 = i1 + 1; i2 < size - 1; i2++) {
                        FinderPattern p2 = (FinderPattern) possibleCenters.get(i2);
                        if (p2 != null) {
                            float vModSize12 = (p1.getEstimatedModuleSize() - p2.getEstimatedModuleSize()) / Math.min(p1.getEstimatedModuleSize(), p2.getEstimatedModuleSize());
                            if (Math.abs(p1.getEstimatedModuleSize() - p2.getEstimatedModuleSize()) > DIFF_MODSIZE_CUTOFF && vModSize12 >= DIFF_MODSIZE_CUTOFF_PERCENT) {
                                break;
                            }
                            for (int i3 = i2 + 1; i3 < size; i3++) {
                                FinderPattern p3 = (FinderPattern) possibleCenters.get(i3);
                                if (p3 != null) {
                                    float vModSize23 = (p2.getEstimatedModuleSize() - p3.getEstimatedModuleSize()) / Math.min(p2.getEstimatedModuleSize(), p3.getEstimatedModuleSize());
                                    if (Math.abs(p2.getEstimatedModuleSize() - p3.getEstimatedModuleSize()) > DIFF_MODSIZE_CUTOFF && vModSize23 >= DIFF_MODSIZE_CUTOFF_PERCENT) {
                                        break;
                                    }
                                    Object test = new FinderPattern[]{p1, p2, p3};
                                    ResultPoint.orderBestPatterns(test);
                                    FinderPatternInfo info = new FinderPatternInfo(test);
                                    float dA = ResultPoint.distance(info.getTopLeft(), info.getBottomLeft());
                                    float dC = ResultPoint.distance(info.getTopRight(), info.getBottomLeft());
                                    float dB = ResultPoint.distance(info.getTopLeft(), info.getTopRight());
                                    float estimatedModuleCount = (dA + dB) / (p1.getEstimatedModuleSize() * 2.0f);
                                    if (estimatedModuleCount <= MAX_MODULE_COUNT_PER_EDGE && estimatedModuleCount >= MIN_MODULE_COUNT_PER_EDGE && Math.abs((dA - dB) / Math.min(dA, dB)) < 0.1f) {
                                        float dCpy = (float) Math.sqrt((double) ((dA * dA) + (dB * dB)));
                                        if (Math.abs((dC - dCpy) / Math.min(dC, dCpy)) < 0.1f) {
                                            results.add(test);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (results.isEmpty()) {
                throw NotFoundException.getNotFoundInstance();
            }
            return (FinderPattern[][]) results.toArray(new FinderPattern[results.size()][]);
        }
    }

    public FinderPatternInfo[] findMulti(Map<DecodeHintType, ?> hints) throws NotFoundException {
        boolean tryHarder;
        BitMatrix image;
        int maxI;
        int maxJ;
        int iSkip;
        int[] stateCount;
        int i;
        int currentState;
        int j;
        FinderPattern[][] patternInfo;
        List<FinderPatternInfo> result;
        int i$;
        if (hints != null) {
            if (hints.containsKey(DecodeHintType.TRY_HARDER)) {
                tryHarder = true;
                image = getImage();
                maxI = image.getHeight();
                maxJ = image.getWidth();
                iSkip = (int) ((((float) maxI) / 228.0f) * 3.0f);
                if (iSkip < 3 || tryHarder) {
                    iSkip = 3;
                }
                stateCount = new int[5];
                for (i = iSkip - 1; i < maxI; i += iSkip) {
                    stateCount[0] = 0;
                    stateCount[1] = 0;
                    stateCount[2] = 0;
                    stateCount[3] = 0;
                    stateCount[4] = 0;
                    currentState = 0;
                    j = 0;
                    while (j < maxJ) {
                        if (image.get(j, i)) {
                            if ((currentState & 1) == 1) {
                                currentState++;
                            }
                            stateCount[currentState] = stateCount[currentState] + 1;
                        } else if ((currentState & 1) == 0) {
                            stateCount[currentState] = stateCount[currentState] + 1;
                        } else if (currentState == 4) {
                            currentState++;
                            stateCount[currentState] = stateCount[currentState] + 1;
                        } else if (FinderPatternFinder.foundPatternCross(stateCount)) {
                            stateCount[0] = stateCount[2];
                            stateCount[1] = stateCount[3];
                            stateCount[2] = stateCount[4];
                            stateCount[3] = 1;
                            stateCount[4] = 0;
                            currentState = 3;
                        } else {
                            if (!handlePossibleCenter(stateCount, i, j)) {
                                do {
                                    j++;
                                    if (j < maxJ) {
                                        break;
                                    }
                                } while (!image.get(j, i));
                                j--;
                            }
                            currentState = 0;
                            stateCount[0] = 0;
                            stateCount[1] = 0;
                            stateCount[2] = 0;
                            stateCount[3] = 0;
                            stateCount[4] = 0;
                        }
                        j++;
                    }
                    if (FinderPatternFinder.foundPatternCross(stateCount)) {
                        handlePossibleCenter(stateCount, i, maxJ);
                    }
                }
                patternInfo = selectMutipleBestPatterns();
                result = new ArrayList();
                for (FinderPattern[] pattern : patternInfo) {
                    ResultPoint.orderBestPatterns(pattern);
                    result.add(new FinderPatternInfo(pattern));
                }
                if (result.isEmpty()) {
                    return EMPTY_RESULT_ARRAY;
                }
                return (FinderPatternInfo[]) result.toArray(new FinderPatternInfo[result.size()]);
            }
        }
        tryHarder = false;
        image = getImage();
        maxI = image.getHeight();
        maxJ = image.getWidth();
        iSkip = (int) ((((float) maxI) / 228.0f) * 3.0f);
        iSkip = 3;
        stateCount = new int[5];
        for (i = iSkip - 1; i < maxI; i += iSkip) {
            stateCount[0] = 0;
            stateCount[1] = 0;
            stateCount[2] = 0;
            stateCount[3] = 0;
            stateCount[4] = 0;
            currentState = 0;
            j = 0;
            while (j < maxJ) {
                if (image.get(j, i)) {
                    if ((currentState & 1) == 1) {
                        currentState++;
                    }
                    stateCount[currentState] = stateCount[currentState] + 1;
                } else if ((currentState & 1) == 0) {
                    stateCount[currentState] = stateCount[currentState] + 1;
                } else if (currentState == 4) {
                    currentState++;
                    stateCount[currentState] = stateCount[currentState] + 1;
                } else if (FinderPatternFinder.foundPatternCross(stateCount)) {
                    stateCount[0] = stateCount[2];
                    stateCount[1] = stateCount[3];
                    stateCount[2] = stateCount[4];
                    stateCount[3] = 1;
                    stateCount[4] = 0;
                    currentState = 3;
                } else {
                    if (handlePossibleCenter(stateCount, i, j)) {
                        do {
                            j++;
                            if (j < maxJ) {
                                break;
                            }
                            j--;
                        } while (!image.get(j, i));
                        j--;
                    }
                    currentState = 0;
                    stateCount[0] = 0;
                    stateCount[1] = 0;
                    stateCount[2] = 0;
                    stateCount[3] = 0;
                    stateCount[4] = 0;
                }
                j++;
            }
            if (FinderPatternFinder.foundPatternCross(stateCount)) {
                handlePossibleCenter(stateCount, i, maxJ);
            }
        }
        patternInfo = selectMutipleBestPatterns();
        result = new ArrayList();
        for (i$ = 0; i$ < len$; i$++) {
            ResultPoint.orderBestPatterns(pattern);
            result.add(new FinderPatternInfo(pattern));
        }
        if (result.isEmpty()) {
            return EMPTY_RESULT_ARRAY;
        }
        return (FinderPatternInfo[]) result.toArray(new FinderPatternInfo[result.size()]);
    }
}
