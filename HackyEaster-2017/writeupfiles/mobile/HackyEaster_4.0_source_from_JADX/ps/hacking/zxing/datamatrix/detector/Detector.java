package ps.hacking.zxing.datamatrix.detector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import ps.hacking.zxing.NotFoundException;
import ps.hacking.zxing.ResultPoint;
import ps.hacking.zxing.common.BitMatrix;
import ps.hacking.zxing.common.DetectorResult;
import ps.hacking.zxing.common.GridSampler;
import ps.hacking.zxing.common.detector.MathUtils;
import ps.hacking.zxing.common.detector.WhiteRectangleDetector;

public final class Detector {
    private final BitMatrix image;
    private final WhiteRectangleDetector rectangleDetector;

    private static final class ResultPointsAndTransitions {
        private final ResultPoint from;
        private final ResultPoint to;
        private final int transitions;

        private ResultPointsAndTransitions(ResultPoint from, ResultPoint to, int transitions) {
            this.from = from;
            this.to = to;
            this.transitions = transitions;
        }

        ResultPoint getFrom() {
            return this.from;
        }

        ResultPoint getTo() {
            return this.to;
        }

        public int getTransitions() {
            return this.transitions;
        }

        public String toString() {
            return this.from + "/" + this.to + '/' + this.transitions;
        }
    }

    private static final class ResultPointsAndTransitionsComparator implements Comparator<ResultPointsAndTransitions>, Serializable {
        private ResultPointsAndTransitionsComparator() {
        }

        public int compare(ResultPointsAndTransitions o1, ResultPointsAndTransitions o2) {
            return o1.getTransitions() - o2.getTransitions();
        }
    }

    public Detector(BitMatrix image) throws NotFoundException {
        this.image = image;
        this.rectangleDetector = new WhiteRectangleDetector(image);
    }

    public DetectorResult detect() throws NotFoundException {
        ResultPoint[] cornerPoints = this.rectangleDetector.detect();
        ResultPoint pointA = cornerPoints[0];
        ResultPoint pointB = cornerPoints[1];
        ResultPoint pointC = cornerPoints[2];
        ResultPoint pointD = cornerPoints[3];
        List<ResultPointsAndTransitions> arrayList = new ArrayList(4);
        arrayList.add(transitionsBetween(pointA, pointB));
        arrayList.add(transitionsBetween(pointA, pointC));
        arrayList.add(transitionsBetween(pointB, pointD));
        arrayList.add(transitionsBetween(pointC, pointD));
        Collections.sort(arrayList, new ResultPointsAndTransitionsComparator());
        ResultPointsAndTransitions lSideOne = (ResultPointsAndTransitions) arrayList.get(0);
        ResultPointsAndTransitions lSideTwo = (ResultPointsAndTransitions) arrayList.get(1);
        Map<ResultPoint, Integer> pointCount = new HashMap();
        increment(pointCount, lSideOne.getFrom());
        increment(pointCount, lSideOne.getTo());
        increment(pointCount, lSideTwo.getFrom());
        increment(pointCount, lSideTwo.getTo());
        ResultPoint maybeTopLeft = null;
        ResultPoint bottomLeft = null;
        ResultPoint maybeBottomRight = null;
        for (Entry<ResultPoint, Integer> entry : pointCount.entrySet()) {
            ResultPoint point = (ResultPoint) entry.getKey();
            if (((Integer) entry.getValue()).intValue() == 2) {
                bottomLeft = point;
            } else if (maybeTopLeft == null) {
                maybeTopLeft = point;
            } else {
                maybeBottomRight = point;
            }
        }
        if (maybeTopLeft == null || bottomLeft == null || maybeBottomRight == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        ResultPoint topRight;
        ResultPoint correctedTopRight;
        BitMatrix bits;
        ResultPoint[] corners = new ResultPoint[]{maybeTopLeft, bottomLeft, maybeBottomRight};
        ResultPoint.orderBestPatterns(corners);
        ResultPoint bottomRight = corners[0];
        bottomLeft = corners[1];
        ResultPoint topLeft = corners[2];
        if (!pointCount.containsKey(pointA)) {
            topRight = pointA;
        } else if (!pointCount.containsKey(pointB)) {
            topRight = pointB;
        } else if (pointCount.containsKey(pointC)) {
            topRight = pointD;
        } else {
            topRight = pointC;
        }
        int dimensionTop = transitionsBetween(topLeft, topRight).getTransitions();
        int dimensionRight = transitionsBetween(bottomRight, topRight).getTransitions();
        if ((dimensionTop & 1) == 1) {
            dimensionTop++;
        }
        dimensionTop += 2;
        if ((dimensionRight & 1) == 1) {
            dimensionRight++;
        }
        dimensionRight += 2;
        if (dimensionTop * 4 >= dimensionRight * 7 || dimensionRight * 4 >= dimensionTop * 7) {
            correctedTopRight = correctTopRightRectangular(bottomLeft, bottomRight, topLeft, topRight, dimensionTop, dimensionRight);
            if (correctedTopRight == null) {
                correctedTopRight = topRight;
            }
            dimensionTop = transitionsBetween(topLeft, correctedTopRight).getTransitions();
            dimensionRight = transitionsBetween(bottomRight, correctedTopRight).getTransitions();
            if ((dimensionTop & 1) == 1) {
                dimensionTop++;
            }
            if ((dimensionRight & 1) == 1) {
                dimensionRight++;
            }
            bits = sampleGrid(this.image, topLeft, bottomLeft, bottomRight, correctedTopRight, dimensionTop, dimensionRight);
        } else {
            correctedTopRight = correctTopRight(bottomLeft, bottomRight, topLeft, topRight, Math.min(dimensionRight, dimensionTop));
            if (correctedTopRight == null) {
                correctedTopRight = topRight;
            }
            int dimensionCorrected = Math.max(transitionsBetween(topLeft, correctedTopRight).getTransitions(), transitionsBetween(bottomRight, correctedTopRight).getTransitions()) + 1;
            if ((dimensionCorrected & 1) == 1) {
                dimensionCorrected++;
            }
            bits = sampleGrid(this.image, topLeft, bottomLeft, bottomRight, correctedTopRight, dimensionCorrected, dimensionCorrected);
        }
        return new DetectorResult(bits, new ResultPoint[]{topLeft, bottomLeft, bottomRight, correctedTopRight});
    }

    private ResultPoint correctTopRightRectangular(ResultPoint bottomLeft, ResultPoint bottomRight, ResultPoint topLeft, ResultPoint topRight, int dimensionTop, int dimensionRight) {
        float corr = ((float) distance(bottomLeft, bottomRight)) / ((float) dimensionTop);
        int norm = distance(topLeft, topRight);
        ResultPoint c1 = new ResultPoint(topRight.getX() + (corr * ((topRight.getX() - topLeft.getX()) / ((float) norm))), topRight.getY() + (corr * ((topRight.getY() - topLeft.getY()) / ((float) norm))));
        corr = ((float) distance(bottomLeft, topLeft)) / ((float) dimensionRight);
        norm = distance(bottomRight, topRight);
        ResultPoint c2 = new ResultPoint(topRight.getX() + (corr * ((topRight.getX() - bottomRight.getX()) / ((float) norm))), topRight.getY() + (corr * ((topRight.getY() - bottomRight.getY()) / ((float) norm))));
        if (isValid(c1)) {
            if (!isValid(c2)) {
                return c1;
            }
            if (Math.abs(dimensionTop - transitionsBetween(topLeft, c1).getTransitions()) + Math.abs(dimensionRight - transitionsBetween(bottomRight, c1).getTransitions()) <= Math.abs(dimensionTop - transitionsBetween(topLeft, c2).getTransitions()) + Math.abs(dimensionRight - transitionsBetween(bottomRight, c2).getTransitions())) {
                return c1;
            }
            return c2;
        } else if (isValid(c2)) {
            return c2;
        } else {
            return null;
        }
    }

    private ResultPoint correctTopRight(ResultPoint bottomLeft, ResultPoint bottomRight, ResultPoint topLeft, ResultPoint topRight, int dimension) {
        float corr = ((float) distance(bottomLeft, bottomRight)) / ((float) dimension);
        int norm = distance(topLeft, topRight);
        ResultPoint c1 = new ResultPoint(topRight.getX() + (corr * ((topRight.getX() - topLeft.getX()) / ((float) norm))), topRight.getY() + (corr * ((topRight.getY() - topLeft.getY()) / ((float) norm))));
        corr = ((float) distance(bottomLeft, topLeft)) / ((float) dimension);
        norm = distance(bottomRight, topRight);
        ResultPoint c2 = new ResultPoint(topRight.getX() + (corr * ((topRight.getX() - bottomRight.getX()) / ((float) norm))), topRight.getY() + (corr * ((topRight.getY() - bottomRight.getY()) / ((float) norm))));
        if (isValid(c1)) {
            return (!isValid(c2) || Math.abs(transitionsBetween(topLeft, c1).getTransitions() - transitionsBetween(bottomRight, c1).getTransitions()) <= Math.abs(transitionsBetween(topLeft, c2).getTransitions() - transitionsBetween(bottomRight, c2).getTransitions())) ? c1 : c2;
        } else {
            if (isValid(c2)) {
                return c2;
            }
            return null;
        }
    }

    private boolean isValid(ResultPoint p) {
        return p.getX() >= 0.0f && p.getX() < ((float) this.image.getWidth()) && p.getY() > 0.0f && p.getY() < ((float) this.image.getHeight());
    }

    private static int distance(ResultPoint a, ResultPoint b) {
        return MathUtils.round(ResultPoint.distance(a, b));
    }

    private static void increment(Map<ResultPoint, Integer> table, ResultPoint key) {
        Integer value = (Integer) table.get(key);
        table.put(key, Integer.valueOf(value == null ? 1 : value.intValue() + 1));
    }

    private static BitMatrix sampleGrid(BitMatrix image, ResultPoint topLeft, ResultPoint bottomLeft, ResultPoint bottomRight, ResultPoint topRight, int dimensionX, int dimensionY) throws NotFoundException {
        return GridSampler.getInstance().sampleGrid(image, dimensionX, dimensionY, 0.5f, 0.5f, ((float) dimensionX) - 0.5f, 0.5f, ((float) dimensionX) - 0.5f, ((float) dimensionY) - 0.5f, 0.5f, ((float) dimensionY) - 0.5f, topLeft.getX(), topLeft.getY(), topRight.getX(), topRight.getY(), bottomRight.getX(), bottomRight.getY(), bottomLeft.getX(), bottomLeft.getY());
    }

    private ResultPointsAndTransitions transitionsBetween(ResultPoint from, ResultPoint to) {
        int i;
        int fromX = (int) from.getX();
        int fromY = (int) from.getY();
        int toX = (int) to.getX();
        int toY = (int) to.getY();
        boolean steep = Math.abs(toY - fromY) > Math.abs(toX - fromX);
        if (steep) {
            int temp = fromX;
            fromX = fromY;
            fromY = temp;
            temp = toX;
            toX = toY;
            toY = temp;
        }
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);
        int error = (-dx) >> 1;
        int ystep = fromY < toY ? 1 : -1;
        int xstep = fromX < toX ? 1 : -1;
        int transitions = 0;
        BitMatrix bitMatrix = this.image;
        if (steep) {
            i = fromY;
        } else {
            i = fromX;
        }
        boolean inBlack = bitMatrix.get(i, steep ? fromX : fromY);
        int y = fromY;
        for (int x = fromX; x != toX; x += xstep) {
            bitMatrix = this.image;
            if (steep) {
                i = y;
            } else {
                i = x;
            }
            boolean isBlack = bitMatrix.get(i, steep ? x : y);
            if (isBlack != inBlack) {
                transitions++;
                inBlack = isBlack;
            }
            error += dy;
            if (error > 0) {
                if (y == toY) {
                    break;
                }
                y += ystep;
                error -= dx;
            }
        }
        return new ResultPointsAndTransitions(to, transitions, null);
    }
}
