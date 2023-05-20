package com.google.zxing.qrcode.detector;

import android.support.v4.app.NotificationCompat.WearableExtender;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Map;
import org.apache.http.conn.params.ConnPerRouteBean;

public class Detector {
    private final BitMatrix image;
    private ResultPointCallback resultPointCallback;

    public Detector(BitMatrix image) {
        this.image = image;
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final ResultPointCallback getResultPointCallback() {
        return this.resultPointCallback;
    }

    public DetectorResult detect() throws NotFoundException, FormatException {
        return detect(null);
    }

    public final DetectorResult detect(Map<DecodeHintType, ?> hints) throws NotFoundException, FormatException {
        this.resultPointCallback = hints == null ? null : (ResultPointCallback) hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        return processFinderPatternInfo(new FinderPatternFinder(this.image, this.resultPointCallback).find(hints));
    }

    protected final DetectorResult processFinderPatternInfo(FinderPatternInfo info) throws NotFoundException, FormatException {
        ResultPoint topLeft = info.getTopLeft();
        ResultPoint topRight = info.getTopRight();
        FinderPattern bottomLeft = info.getBottomLeft();
        float moduleSize = calculateModuleSize(topLeft, topRight, bottomLeft);
        if (moduleSize < 1.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int dimension = computeDimension(topLeft, topRight, bottomLeft, moduleSize);
        Version provisionalVersion = Version.getProvisionalVersionForDimension(dimension);
        int modulesBetweenFPCenters = provisionalVersion.getDimensionForVersion() - 7;
        AlignmentPattern alignmentPattern = null;
        if (provisionalVersion.getAlignmentPatternCenters().length > 0) {
            float correctionToTopLeft = 1.0f - (3.0f / ((float) modulesBetweenFPCenters));
            int estAlignmentX = (int) (topLeft.getX() + ((((topRight.getX() - topLeft.getX()) + bottomLeft.getX()) - topLeft.getX()) * correctionToTopLeft));
            int estAlignmentY = (int) (topLeft.getY() + ((((topRight.getY() - topLeft.getY()) + bottomLeft.getY()) - topLeft.getY()) * correctionToTopLeft));
            int i = 4;
            while (i <= 16) {
                try {
                    alignmentPattern = findAlignmentInRegion(moduleSize, estAlignmentX, estAlignmentY, (float) i);
                    break;
                } catch (NotFoundException e) {
                    i <<= 1;
                }
            }
        }
        return new DetectorResult(sampleGrid(this.image, createTransform(topLeft, topRight, bottomLeft, alignmentPattern, dimension), dimension), alignmentPattern == null ? new ResultPoint[]{bottomLeft, topLeft, topRight} : new ResultPoint[]{bottomLeft, topLeft, topRight, alignmentPattern});
    }

    private static PerspectiveTransform createTransform(ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomLeft, ResultPoint alignmentPattern, int dimension) {
        float bottomRightX;
        float bottomRightY;
        float sourceBottomRightX;
        float sourceBottomRightY;
        float dimMinusThree = ((float) dimension) - 3.5f;
        if (alignmentPattern != null) {
            bottomRightX = alignmentPattern.getX();
            bottomRightY = alignmentPattern.getY();
            sourceBottomRightX = dimMinusThree - 3.0f;
            sourceBottomRightY = sourceBottomRightX;
        } else {
            bottomRightX = (topRight.getX() - topLeft.getX()) + bottomLeft.getX();
            bottomRightY = (topRight.getY() - topLeft.getY()) + bottomLeft.getY();
            sourceBottomRightX = dimMinusThree;
            sourceBottomRightY = dimMinusThree;
        }
        return PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, dimMinusThree, 3.5f, sourceBottomRightX, sourceBottomRightY, 3.5f, dimMinusThree, topLeft.getX(), topLeft.getY(), topRight.getX(), topRight.getY(), bottomRightX, bottomRightY, bottomLeft.getX(), bottomLeft.getY());
    }

    private static BitMatrix sampleGrid(BitMatrix image, PerspectiveTransform transform, int dimension) throws NotFoundException {
        return GridSampler.getInstance().sampleGrid(image, dimension, dimension, transform);
    }

    private static int computeDimension(ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomLeft, float moduleSize) throws NotFoundException {
        int dimension = ((MathUtils.round(ResultPoint.distance(topLeft, topRight) / moduleSize) + MathUtils.round(ResultPoint.distance(topLeft, bottomLeft) / moduleSize)) >> 1) + 7;
        switch (dimension & 3) {
            case WearableExtender.SIZE_DEFAULT /*0*/:
                return dimension + 1;
            case ConnPerRouteBean.DEFAULT_MAX_CONNECTIONS_PER_ROUTE /*2*/:
                return dimension - 1;
            case WearableExtender.SIZE_MEDIUM /*3*/:
                throw NotFoundException.getNotFoundInstance();
            default:
                return dimension;
        }
    }

    protected final float calculateModuleSize(ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomLeft) {
        return (calculateModuleSizeOneWay(topLeft, topRight) + calculateModuleSizeOneWay(topLeft, bottomLeft)) / 2.0f;
    }

    private float calculateModuleSizeOneWay(ResultPoint pattern, ResultPoint otherPattern) {
        float moduleSizeEst1 = sizeOfBlackWhiteBlackRunBothWays((int) pattern.getX(), (int) pattern.getY(), (int) otherPattern.getX(), (int) otherPattern.getY());
        float moduleSizeEst2 = sizeOfBlackWhiteBlackRunBothWays((int) otherPattern.getX(), (int) otherPattern.getY(), (int) pattern.getX(), (int) pattern.getY());
        if (Float.isNaN(moduleSizeEst1)) {
            return moduleSizeEst2 / 7.0f;
        }
        if (Float.isNaN(moduleSizeEst2)) {
            return moduleSizeEst1 / 7.0f;
        }
        return (moduleSizeEst1 + moduleSizeEst2) / 14.0f;
    }

    private float sizeOfBlackWhiteBlackRunBothWays(int fromX, int fromY, int toX, int toY) {
        float result = sizeOfBlackWhiteBlackRun(fromX, fromY, toX, toY);
        float scale = 1.0f;
        int otherToX = fromX - (toX - fromX);
        if (otherToX < 0) {
            scale = ((float) fromX) / ((float) (fromX - otherToX));
            otherToX = 0;
        } else if (otherToX >= this.image.getWidth()) {
            scale = ((float) ((this.image.getWidth() - 1) - fromX)) / ((float) (otherToX - fromX));
            otherToX = this.image.getWidth() - 1;
        }
        int otherToY = (int) (((float) fromY) - (((float) (toY - fromY)) * scale));
        scale = 1.0f;
        if (otherToY < 0) {
            scale = ((float) fromY) / ((float) (fromY - otherToY));
            otherToY = 0;
        } else if (otherToY >= this.image.getHeight()) {
            scale = ((float) ((this.image.getHeight() - 1) - fromY)) / ((float) (otherToY - fromY));
            otherToY = this.image.getHeight() - 1;
        }
        return (result + sizeOfBlackWhiteBlackRun(fromX, fromY, (int) (((float) fromX) + (((float) (otherToX - fromX)) * scale)), otherToY)) - 1.0f;
    }

    private float sizeOfBlackWhiteBlackRun(int fromX, int fromY, int toX, int toY) {
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
        int xstep = fromX < toX ? 1 : -1;
        int ystep = fromY < toY ? 1 : -1;
        int state = 0;
        int xLimit = toX + xstep;
        int y = fromY;
        for (int x = fromX; x != xLimit; x += xstep) {
            int realX;
            int realY;
            if (steep) {
                realX = y;
            } else {
                realX = x;
            }
            if (steep) {
                realY = x;
            } else {
                realY = y;
            }
            if ((state == 1) == this.image.get(realX, realY)) {
                if (state == 2) {
                    return MathUtils.distance(x, y, fromX, fromY);
                }
                state++;
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
        if (state != 2) {
            return Float.NaN;
        }
        return MathUtils.distance(toX + xstep, toY, fromX, fromY);
    }

    protected final AlignmentPattern findAlignmentInRegion(float overallEstModuleSize, int estAlignmentX, int estAlignmentY, float allowanceFactor) throws NotFoundException {
        int allowance = (int) (allowanceFactor * overallEstModuleSize);
        int alignmentAreaLeftX = Math.max(0, estAlignmentX - allowance);
        int alignmentAreaRightX = Math.min(this.image.getWidth() - 1, estAlignmentX + allowance);
        if (((float) (alignmentAreaRightX - alignmentAreaLeftX)) < overallEstModuleSize * 3.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int alignmentAreaTopY = Math.max(0, estAlignmentY - allowance);
        int alignmentAreaBottomY = Math.min(this.image.getHeight() - 1, estAlignmentY + allowance);
        if (((float) (alignmentAreaBottomY - alignmentAreaTopY)) < overallEstModuleSize * 3.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        return new AlignmentPatternFinder(this.image, alignmentAreaLeftX, alignmentAreaTopY, alignmentAreaRightX - alignmentAreaLeftX, alignmentAreaBottomY - alignmentAreaTopY, overallEstModuleSize, this.resultPointCallback).find();
    }
}
