package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import java.util.Arrays;
import java.util.Map;

public final class Detector {
    private static final int INTEGER_MATH_SHIFT = 8;
    private static final int MAX_AVG_VARIANCE = 107;
    private static final int MAX_INDIVIDUAL_VARIANCE = 204;
    private static final int PATTERN_MATCH_RESULT_SCALE_FACTOR = 256;
    private static final int SKEW_THRESHOLD = 3;
    private static final int[] START_PATTERN;
    private static final int[] START_PATTERN_REVERSE;
    private static final int[] STOP_PATTERN;
    private static final int[] STOP_PATTERN_REVERSE;
    private final BinaryBitmap image;

    static {
        START_PATTERN = new int[]{INTEGER_MATH_SHIFT, 1, 1, 1, 1, 1, 1, SKEW_THRESHOLD};
        START_PATTERN_REVERSE = new int[]{SKEW_THRESHOLD, 1, 1, 1, 1, 1, 1, INTEGER_MATH_SHIFT};
        STOP_PATTERN = new int[]{7, 1, 1, SKEW_THRESHOLD, 1, 1, 1, 2, 1};
        STOP_PATTERN_REVERSE = new int[]{1, 2, 1, 1, 1, SKEW_THRESHOLD, 1, 1, 7};
    }

    public Detector(BinaryBitmap image) {
        this.image = image;
    }

    public DetectorResult detect() throws NotFoundException {
        return detect(null);
    }

    public DetectorResult detect(Map<DecodeHintType, ?> hints) throws NotFoundException {
        BitMatrix matrix = this.image.getBlackMatrix();
        boolean tryHarder = hints != null && hints.containsKey(DecodeHintType.TRY_HARDER);
        ResultPoint[] vertices = findVertices(matrix, tryHarder);
        if (vertices == null) {
            vertices = findVertices180(matrix, tryHarder);
            if (vertices != null) {
                correctCodeWordVertices(vertices, true);
            }
        } else {
            correctCodeWordVertices(vertices, false);
        }
        if (vertices == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        float moduleWidth = computeModuleWidth(vertices);
        if (moduleWidth < 1.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int dimension = computeDimension(vertices[4], vertices[6], vertices[5], vertices[7], moduleWidth);
        if (dimension < 1) {
            throw NotFoundException.getNotFoundInstance();
        }
        int ydimension = computeYDimension(vertices[4], vertices[6], vertices[5], vertices[7], moduleWidth);
        if (ydimension <= dimension) {
            ydimension = dimension;
        }
        return new DetectorResult(sampleGrid(matrix, vertices[4], vertices[5], vertices[6], vertices[7], dimension, ydimension), new ResultPoint[]{vertices[5], vertices[4], vertices[6], vertices[7]});
    }

    private static ResultPoint[] findVertices(BitMatrix matrix, boolean tryHarder) {
        int i;
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        ResultPoint[] result = new ResultPoint[INTEGER_MATH_SHIFT];
        boolean found = false;
        int[] counters = new int[START_PATTERN.length];
        int rowStep = Math.max(1, height >> (tryHarder ? 9 : 7));
        for (i = 0; i < height; i += rowStep) {
            int[] loc = findGuardPattern(matrix, 0, i, width, false, START_PATTERN, counters);
            if (loc != null) {
                result[0] = new ResultPoint((float) loc[0], (float) i);
                result[4] = new ResultPoint((float) loc[1], (float) i);
                found = true;
                break;
            }
        }
        if (found) {
            found = false;
            for (i = height - 1; i > 0; i -= rowStep) {
                loc = findGuardPattern(matrix, 0, i, width, false, START_PATTERN, counters);
                if (loc != null) {
                    result[1] = new ResultPoint((float) loc[0], (float) i);
                    result[5] = new ResultPoint((float) loc[1], (float) i);
                    found = true;
                    break;
                }
            }
        }
        counters = new int[STOP_PATTERN.length];
        if (found) {
            found = false;
            for (i = 0; i < height; i += rowStep) {
                loc = findGuardPattern(matrix, 0, i, width, false, STOP_PATTERN, counters);
                if (loc != null) {
                    result[2] = new ResultPoint((float) loc[1], (float) i);
                    result[6] = new ResultPoint((float) loc[0], (float) i);
                    found = true;
                    break;
                }
            }
        }
        if (found) {
            found = false;
            for (i = height - 1; i > 0; i -= rowStep) {
                loc = findGuardPattern(matrix, 0, i, width, false, STOP_PATTERN, counters);
                if (loc != null) {
                    result[SKEW_THRESHOLD] = new ResultPoint((float) loc[1], (float) i);
                    result[7] = new ResultPoint((float) loc[0], (float) i);
                    found = true;
                    break;
                }
            }
        }
        return found ? result : null;
    }

    private static ResultPoint[] findVertices180(BitMatrix matrix, boolean tryHarder) {
        int i;
        int height = matrix.getHeight();
        int halfWidth = matrix.getWidth() >> 1;
        ResultPoint[] result = new ResultPoint[INTEGER_MATH_SHIFT];
        boolean found = false;
        int[] counters = new int[START_PATTERN_REVERSE.length];
        int rowStep = Math.max(1, height >> (tryHarder ? 9 : 7));
        for (i = height - 1; i > 0; i -= rowStep) {
            int[] loc = findGuardPattern(matrix, halfWidth, i, halfWidth, true, START_PATTERN_REVERSE, counters);
            if (loc != null) {
                result[0] = new ResultPoint((float) loc[1], (float) i);
                result[4] = new ResultPoint((float) loc[0], (float) i);
                found = true;
                break;
            }
        }
        if (found) {
            found = false;
            for (i = 0; i < height; i += rowStep) {
                loc = findGuardPattern(matrix, halfWidth, i, halfWidth, true, START_PATTERN_REVERSE, counters);
                if (loc != null) {
                    result[1] = new ResultPoint((float) loc[1], (float) i);
                    result[5] = new ResultPoint((float) loc[0], (float) i);
                    found = true;
                    break;
                }
            }
        }
        counters = new int[STOP_PATTERN_REVERSE.length];
        if (found) {
            found = false;
            for (i = height - 1; i > 0; i -= rowStep) {
                loc = findGuardPattern(matrix, 0, i, halfWidth, false, STOP_PATTERN_REVERSE, counters);
                if (loc != null) {
                    result[2] = new ResultPoint((float) loc[0], (float) i);
                    result[6] = new ResultPoint((float) loc[1], (float) i);
                    found = true;
                    break;
                }
            }
        }
        if (found) {
            found = false;
            for (i = 0; i < height; i += rowStep) {
                loc = findGuardPattern(matrix, 0, i, halfWidth, false, STOP_PATTERN_REVERSE, counters);
                if (loc != null) {
                    result[SKEW_THRESHOLD] = new ResultPoint((float) loc[0], (float) i);
                    result[7] = new ResultPoint((float) loc[1], (float) i);
                    found = true;
                    break;
                }
            }
        }
        return found ? result : null;
    }

    private static void correctCodeWordVertices(ResultPoint[] vertices, boolean upsideDown) {
        float deltax;
        float deltay;
        float correction;
        float v0x = vertices[0].getX();
        float v0y = vertices[0].getY();
        float v2x = vertices[2].getX();
        float v2y = vertices[2].getY();
        float v4x = vertices[4].getX();
        float v4y = vertices[4].getY();
        float v6x = vertices[6].getX();
        float v6y = vertices[6].getY();
        float skew = v4y - v6y;
        if (upsideDown) {
            skew = -skew;
        }
        if (skew > 3.0f) {
            deltax = v6x - v0x;
            deltay = v6y - v0y;
            correction = ((v4x - v0x) * deltax) / ((deltax * deltax) + (deltay * deltay));
            vertices[4] = new ResultPoint((correction * deltax) + v0x, (correction * deltay) + v0y);
        } else {
            if ((-skew) > 3.0f) {
                deltax = v2x - v4x;
                deltay = v2y - v4y;
                correction = ((v2x - v6x) * deltax) / ((deltax * deltax) + (deltay * deltay));
                vertices[6] = new ResultPoint(v2x - (correction * deltax), v2y - (correction * deltay));
            }
        }
        float v1x = vertices[1].getX();
        float v1y = vertices[1].getY();
        float v3x = vertices[SKEW_THRESHOLD].getX();
        float v3y = vertices[SKEW_THRESHOLD].getY();
        float v5x = vertices[5].getX();
        float v5y = vertices[5].getY();
        float v7x = vertices[7].getX();
        float v7y = vertices[7].getY();
        skew = v7y - v5y;
        if (upsideDown) {
            skew = -skew;
        }
        if (skew > 3.0f) {
            deltax = v7x - v1x;
            deltay = v7y - v1y;
            correction = ((v5x - v1x) * deltax) / ((deltax * deltax) + (deltay * deltay));
            vertices[5] = new ResultPoint((correction * deltax) + v1x, (correction * deltay) + v1y);
            return;
        }
        if ((-skew) > 3.0f) {
            deltax = v3x - v5x;
            deltay = v3y - v5y;
            correction = ((v3x - v7x) * deltax) / ((deltax * deltax) + (deltay * deltay));
            vertices[7] = new ResultPoint(v3x - (correction * deltax), v3y - (correction * deltay));
        }
    }

    private static float computeModuleWidth(ResultPoint[] vertices) {
        return (((ResultPoint.distance(vertices[0], vertices[4]) + ResultPoint.distance(vertices[1], vertices[5])) / 34.0f) + ((ResultPoint.distance(vertices[6], vertices[2]) + ResultPoint.distance(vertices[7], vertices[SKEW_THRESHOLD])) / 36.0f)) / 2.0f;
    }

    private static int computeDimension(ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomLeft, ResultPoint bottomRight, float moduleWidth) {
        return ((((MathUtils.round(ResultPoint.distance(topLeft, topRight) / moduleWidth) + MathUtils.round(ResultPoint.distance(bottomLeft, bottomRight) / moduleWidth)) >> 1) + INTEGER_MATH_SHIFT) / 17) * 17;
    }

    private static int computeYDimension(ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomLeft, ResultPoint bottomRight, float moduleWidth) {
        return (MathUtils.round(ResultPoint.distance(topLeft, bottomLeft) / moduleWidth) + MathUtils.round(ResultPoint.distance(topRight, bottomRight) / moduleWidth)) >> 1;
    }

    private static BitMatrix sampleGrid(BitMatrix matrix, ResultPoint topLeft, ResultPoint bottomLeft, ResultPoint topRight, ResultPoint bottomRight, int xdimension, int ydimension) throws NotFoundException {
        return GridSampler.getInstance().sampleGrid(matrix, xdimension, ydimension, 0.0f, 0.0f, (float) xdimension, 0.0f, (float) xdimension, (float) ydimension, 0.0f, (float) ydimension, topLeft.getX(), topLeft.getY(), topRight.getX(), topRight.getY(), bottomRight.getX(), bottomRight.getY(), bottomLeft.getX(), bottomLeft.getY());
    }

    private static int[] findGuardPattern(BitMatrix matrix, int column, int row, int width, boolean whiteFirst, int[] pattern, int[] counters) {
        Arrays.fill(counters, 0, counters.length, 0);
        int patternLength = pattern.length;
        boolean isWhite = whiteFirst;
        int counterPosition = 0;
        int patternStart = column;
        for (int x = column; x < column + width; x++) {
            if ((matrix.get(x, row) ^ isWhite) != 0) {
                counters[counterPosition] = counters[counterPosition] + 1;
            } else {
                if (counterPosition != patternLength - 1) {
                    counterPosition++;
                } else if (patternMatchVariance(counters, pattern, MAX_INDIVIDUAL_VARIANCE) < MAX_AVG_VARIANCE) {
                    return new int[]{patternStart, x};
                } else {
                    patternStart += counters[0] + counters[1];
                    System.arraycopy(counters, 2, counters, 0, patternLength - 2);
                    counters[patternLength - 2] = 0;
                    counters[patternLength - 1] = 0;
                    counterPosition--;
                }
                counters[counterPosition] = 1;
                isWhite = !isWhite;
            }
        }
        return null;
    }

    private static int patternMatchVariance(int[] counters, int[] pattern, int maxIndividualVariance) {
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
