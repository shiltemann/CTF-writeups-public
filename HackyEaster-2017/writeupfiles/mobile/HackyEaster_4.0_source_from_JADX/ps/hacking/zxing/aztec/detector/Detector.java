package ps.hacking.zxing.aztec.detector;

import ps.hacking.zxing.NotFoundException;
import ps.hacking.zxing.ResultPoint;
import ps.hacking.zxing.aztec.AztecDetectorResult;
import ps.hacking.zxing.common.BitMatrix;
import ps.hacking.zxing.common.GridSampler;
import ps.hacking.zxing.common.detector.MathUtils;
import ps.hacking.zxing.common.detector.WhiteRectangleDetector;
import ps.hacking.zxing.common.reedsolomon.GenericGF;
import ps.hacking.zxing.common.reedsolomon.ReedSolomonDecoder;
import ps.hacking.zxing.common.reedsolomon.ReedSolomonException;

public final class Detector {
    private boolean compact;
    private final BitMatrix image;
    private int nbCenterLayers;
    private int nbDataBlocks;
    private int nbLayers;
    private int shift;

    private static final class Point {
        public final int f19x;
        public final int f20y;

        public ResultPoint toResultPoint() {
            return new ResultPoint((float) this.f19x, (float) this.f20y);
        }

        private Point(int x, int y) {
            this.f19x = x;
            this.f20y = y;
        }
    }

    public Detector(BitMatrix image) {
        this.image = image;
    }

    public AztecDetectorResult detect() throws NotFoundException {
        Point[] bullEyeCornerPoints = getBullEyeCornerPoints(getMatrixCenter());
        extractParameters(bullEyeCornerPoints);
        ResultPoint[] corners = getMatrixCornerPoints(bullEyeCornerPoints);
        return new AztecDetectorResult(sampleGrid(this.image, corners[this.shift % 4], corners[(this.shift + 3) % 4], corners[(this.shift + 2) % 4], corners[(this.shift + 1) % 4]), corners, this.compact, this.nbDataBlocks, this.nbLayers);
    }

    private void extractParameters(Point[] bullEyeCornerPoints) throws NotFoundException {
        boolean[] parameterData;
        boolean[] resab = sampleLine(bullEyeCornerPoints[0], bullEyeCornerPoints[1], (this.nbCenterLayers * 2) + 1);
        boolean[] resbc = sampleLine(bullEyeCornerPoints[1], bullEyeCornerPoints[2], (this.nbCenterLayers * 2) + 1);
        boolean[] rescd = sampleLine(bullEyeCornerPoints[2], bullEyeCornerPoints[3], (this.nbCenterLayers * 2) + 1);
        boolean[] resda = sampleLine(bullEyeCornerPoints[3], bullEyeCornerPoints[0], (this.nbCenterLayers * 2) + 1);
        if (resab[0] && resab[this.nbCenterLayers * 2]) {
            this.shift = 0;
        } else if (resbc[0] && resbc[this.nbCenterLayers * 2]) {
            this.shift = 1;
        } else if (rescd[0] && rescd[this.nbCenterLayers * 2]) {
            this.shift = 2;
        } else if (resda[0] && resda[this.nbCenterLayers * 2]) {
            this.shift = 3;
        } else {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean[] shiftedParameterData;
        int i;
        if (this.compact) {
            shiftedParameterData = new boolean[28];
            for (i = 0; i < 7; i++) {
                shiftedParameterData[i] = resab[i + 2];
                shiftedParameterData[i + 7] = resbc[i + 2];
                shiftedParameterData[i + 14] = rescd[i + 2];
                shiftedParameterData[i + 21] = resda[i + 2];
            }
            parameterData = new boolean[28];
            for (i = 0; i < 28; i++) {
                parameterData[i] = shiftedParameterData[((this.shift * 7) + i) % 28];
            }
        } else {
            shiftedParameterData = new boolean[40];
            for (i = 0; i < 11; i++) {
                if (i < 5) {
                    shiftedParameterData[i] = resab[i + 2];
                    shiftedParameterData[i + 10] = resbc[i + 2];
                    shiftedParameterData[i + 20] = rescd[i + 2];
                    shiftedParameterData[i + 30] = resda[i + 2];
                }
                if (i > 5) {
                    shiftedParameterData[i - 1] = resab[i + 2];
                    shiftedParameterData[(i + 10) - 1] = resbc[i + 2];
                    shiftedParameterData[(i + 20) - 1] = rescd[i + 2];
                    shiftedParameterData[(i + 30) - 1] = resda[i + 2];
                }
            }
            parameterData = new boolean[40];
            for (i = 0; i < 40; i++) {
                parameterData[i] = shiftedParameterData[((this.shift * 10) + i) % 40];
            }
        }
        correctParameterData(parameterData, this.compact);
        getParameters(parameterData);
    }

    private ResultPoint[] getMatrixCornerPoints(Point[] bullEyeCornerPoints) throws NotFoundException {
        float ratio = ((float) (((this.nbLayers > 4 ? 1 : 0) + (this.nbLayers * 2)) + ((this.nbLayers - 4) / 8))) / (2.0f * ((float) this.nbCenterLayers));
        int dx = bullEyeCornerPoints[0].f19x - bullEyeCornerPoints[2].f19x;
        dx += dx > 0 ? 1 : -1;
        int dy = bullEyeCornerPoints[0].f20y - bullEyeCornerPoints[2].f20y;
        dy += dy > 0 ? 1 : -1;
        int targetcx = MathUtils.round(((float) bullEyeCornerPoints[2].f19x) - (((float) dx) * ratio));
        int targetcy = MathUtils.round(((float) bullEyeCornerPoints[2].f20y) - (((float) dy) * ratio));
        int targetax = MathUtils.round(((float) bullEyeCornerPoints[0].f19x) + (((float) dx) * ratio));
        int targetay = MathUtils.round(((float) bullEyeCornerPoints[0].f20y) + (((float) dy) * ratio));
        dx = bullEyeCornerPoints[1].f19x - bullEyeCornerPoints[3].f19x;
        dx += dx > 0 ? 1 : -1;
        dy = bullEyeCornerPoints[1].f20y - bullEyeCornerPoints[3].f20y;
        dy += dy > 0 ? 1 : -1;
        int targetdx = MathUtils.round(((float) bullEyeCornerPoints[3].f19x) - (((float) dx) * ratio));
        int targetdy = MathUtils.round(((float) bullEyeCornerPoints[3].f20y) - (((float) dy) * ratio));
        int targetbx = MathUtils.round(((float) bullEyeCornerPoints[1].f19x) + (((float) dx) * ratio));
        int targetby = MathUtils.round(((float) bullEyeCornerPoints[1].f20y) + (((float) dy) * ratio));
        if (isValid(targetax, targetay) && isValid(targetbx, targetby) && isValid(targetcx, targetcy) && isValid(targetdx, targetdy)) {
            r12 = new ResultPoint[4];
            r12[0] = new ResultPoint((float) targetax, (float) targetay);
            r12[1] = new ResultPoint((float) targetbx, (float) targetby);
            r12[2] = new ResultPoint((float) targetcx, (float) targetcy);
            r12[3] = new ResultPoint((float) targetdx, (float) targetdy);
            return r12;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static void correctParameterData(boolean[] parameterData, boolean compact) throws NotFoundException {
        int numCodewords;
        int numDataCodewords;
        int i;
        int j;
        if (compact) {
            numCodewords = 7;
            numDataCodewords = 2;
        } else {
            numCodewords = 10;
            numDataCodewords = 4;
        }
        int numECCodewords = numCodewords - numDataCodewords;
        int[] parameterWords = new int[numCodewords];
        for (i = 0; i < numCodewords; i++) {
            int flag = 1;
            for (j = 1; j <= 4; j++) {
                if (parameterData[((4 * i) + 4) - j]) {
                    parameterWords[i] = parameterWords[i] + flag;
                }
                flag <<= 1;
            }
        }
        try {
            new ReedSolomonDecoder(GenericGF.AZTEC_PARAM).decode(parameterWords, numECCodewords);
            for (i = 0; i < numDataCodewords; i++) {
                flag = 1;
                for (j = 1; j <= 4; j++) {
                    parameterData[((i * 4) + 4) - j] = (parameterWords[i] & flag) == flag;
                    flag <<= 1;
                }
            }
        } catch (ReedSolomonException e) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    private Point[] getBullEyeCornerPoints(Point pCenter) throws NotFoundException {
        int i;
        Point pina = pCenter;
        Point pinb = pCenter;
        Point pinc = pCenter;
        Point pind = pCenter;
        boolean color = true;
        this.nbCenterLayers = 1;
        while (true) {
            i = this.nbCenterLayers;
            if (r0 >= 9) {
                break;
            }
            Point pouta = getFirstDifferent(pina, color, 1, -1);
            Point poutb = getFirstDifferent(pinb, color, 1, 1);
            Point poutc = getFirstDifferent(pinc, color, -1, 1);
            Point poutd = getFirstDifferent(pind, color, -1, -1);
            i = this.nbCenterLayers;
            if (r0 > 2) {
                float q = (distance(poutd, pouta) * ((float) this.nbCenterLayers)) / (distance(pind, pina) * ((float) (this.nbCenterLayers + 2)));
                if (((double) q) < 0.75d) {
                    break;
                }
                if (((double) q) <= 1.25d) {
                    if (!isWhiteOrBlackRectangle(pouta, poutb, poutc, poutd)) {
                        break;
                    }
                }
                break;
            }
            pina = pouta;
            pinb = poutb;
            pinc = poutc;
            pind = poutd;
            if (color) {
                color = false;
            } else {
                color = true;
            }
            this.nbCenterLayers++;
        }
        i = this.nbCenterLayers;
        if (r0 != 5) {
            i = this.nbCenterLayers;
            if (r0 != 7) {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        i = this.nbCenterLayers;
        this.compact = r0 == 5;
        float ratio = 1.5f / ((float) ((this.nbCenterLayers * 2) - 3));
        int dx = pina.f19x - pinc.f19x;
        int dy = pina.f20y - pinc.f20y;
        int targetcx = MathUtils.round(((float) pinc.f19x) - (((float) dx) * ratio));
        int targetcy = MathUtils.round(((float) pinc.f20y) - (((float) dy) * ratio));
        int targetax = MathUtils.round(((float) pina.f19x) + (((float) dx) * ratio));
        int targetay = MathUtils.round(((float) pina.f20y) + (((float) dy) * ratio));
        dx = pinb.f19x - pind.f19x;
        dy = pinb.f20y - pind.f20y;
        int targetdx = MathUtils.round(((float) pind.f19x) - (((float) dx) * ratio));
        int targetdy = MathUtils.round(((float) pind.f20y) - (((float) dy) * ratio));
        int targetbx = MathUtils.round(((float) pinb.f19x) + (((float) dx) * ratio));
        int targetby = MathUtils.round(((float) pinb.f20y) + (((float) dy) * ratio));
        if (isValid(targetax, targetay) && isValid(targetbx, targetby) && isValid(targetcx, targetcy) && isValid(targetdx, targetdy)) {
            Point pa = new Point(targetay, null);
            Point pb = new Point(targetby, null);
            Point pc = new Point(targetcy, null);
            Point pd = new Point(targetdy, null);
            return new Point[]{pa, pb, pc, pd};
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private Point getMatrixCenter() {
        ResultPoint pointA;
        ResultPoint pointB;
        ResultPoint pointC;
        ResultPoint pointD;
        int cx;
        int cy;
        try {
            ResultPoint[] cornerPoints = new WhiteRectangleDetector(this.image).detect();
            pointA = cornerPoints[0];
            pointB = cornerPoints[1];
            pointC = cornerPoints[2];
            pointD = cornerPoints[3];
        } catch (NotFoundException e) {
            cx = this.image.getWidth() / 2;
            cy = this.image.getHeight() / 2;
            pointA = getFirstDifferent(new Point(cy - 7, null), false, 1, -1).toResultPoint();
            pointB = getFirstDifferent(new Point(cy + 7, null), false, 1, 1).toResultPoint();
            pointC = getFirstDifferent(new Point(cy + 7, null), false, -1, 1).toResultPoint();
            pointD = getFirstDifferent(new Point(cy - 7, null), false, -1, -1).toResultPoint();
        }
        cx = MathUtils.round((((pointA.getX() + pointD.getX()) + pointB.getX()) + pointC.getX()) / 4.0f);
        cy = MathUtils.round((((pointA.getY() + pointD.getY()) + pointB.getY()) + pointC.getY()) / 4.0f);
        try {
            cornerPoints = new WhiteRectangleDetector(this.image, 15, cx, cy).detect();
            pointA = cornerPoints[0];
            pointB = cornerPoints[1];
            pointC = cornerPoints[2];
            pointD = cornerPoints[3];
        } catch (NotFoundException e2) {
            pointA = getFirstDifferent(new Point(cy - 7, null), false, 1, -1).toResultPoint();
            pointB = getFirstDifferent(new Point(cy + 7, null), false, 1, 1).toResultPoint();
            pointC = getFirstDifferent(new Point(cy + 7, null), false, -1, 1).toResultPoint();
            pointD = getFirstDifferent(new Point(cy - 7, null), false, -1, -1).toResultPoint();
        }
        return new Point(MathUtils.round((((pointA.getY() + pointD.getY()) + pointB.getY()) + pointC.getY()) / 4.0f), null);
    }

    private BitMatrix sampleGrid(BitMatrix image, ResultPoint topLeft, ResultPoint bottomLeft, ResultPoint bottomRight, ResultPoint topRight) throws NotFoundException {
        int dimension;
        if (this.compact) {
            dimension = (this.nbLayers * 4) + 11;
        } else if (this.nbLayers <= 4) {
            dimension = (this.nbLayers * 4) + 15;
        } else {
            dimension = ((this.nbLayers * 4) + ((((this.nbLayers - 4) / 8) + 1) * 2)) + 15;
        }
        return GridSampler.getInstance().sampleGrid(image, dimension, dimension, 0.5f, 0.5f, ((float) dimension) - 0.5f, 0.5f, ((float) dimension) - 0.5f, ((float) dimension) - 0.5f, 0.5f, ((float) dimension) - 0.5f, topLeft.getX(), topLeft.getY(), topRight.getX(), topRight.getY(), bottomRight.getX(), bottomRight.getY(), bottomLeft.getX(), bottomLeft.getY());
    }

    private void getParameters(boolean[] parameterData) {
        int nbBitsForNbLayers;
        int nbBitsForNbDatablocks;
        int i;
        if (this.compact) {
            nbBitsForNbLayers = 2;
            nbBitsForNbDatablocks = 6;
        } else {
            nbBitsForNbLayers = 5;
            nbBitsForNbDatablocks = 11;
        }
        for (i = 0; i < nbBitsForNbLayers; i++) {
            this.nbLayers <<= 1;
            if (parameterData[i]) {
                this.nbLayers++;
            }
        }
        for (i = nbBitsForNbLayers; i < nbBitsForNbLayers + nbBitsForNbDatablocks; i++) {
            this.nbDataBlocks <<= 1;
            if (parameterData[i]) {
                this.nbDataBlocks++;
            }
        }
        this.nbLayers++;
        this.nbDataBlocks++;
    }

    private boolean[] sampleLine(Point p1, Point p2, int size) {
        boolean[] res = new boolean[size];
        float d = distance(p1, p2);
        float moduleSize = d / ((float) (size - 1));
        float dx = (((float) (p2.f19x - p1.f19x)) * moduleSize) / d;
        float dy = (((float) (p2.f20y - p1.f20y)) * moduleSize) / d;
        float px = (float) p1.f19x;
        float py = (float) p1.f20y;
        for (int i = 0; i < size; i++) {
            res[i] = this.image.get(MathUtils.round(px), MathUtils.round(py));
            px += dx;
            py += dy;
        }
        return res;
    }

    private boolean isWhiteOrBlackRectangle(Point p1, Point p2, Point p3, Point p4) {
        Point p12 = new Point(p1.f20y + 3, null);
        Point p22 = new Point(p2.f20y - 3, null);
        Point p32 = new Point(p3.f20y - 3, null);
        Point p42 = new Point(p4.f20y + 3, null);
        int cInit = getColor(p42, p12);
        if (cInit != 0 && getColor(p12, p22) == cInit && getColor(p22, p32) == cInit && getColor(p32, p42) == cInit) {
            return true;
        }
        return false;
    }

    private int getColor(Point p1, Point p2) {
        float d = distance(p1, p2);
        float dx = ((float) (p2.f19x - p1.f19x)) / d;
        float dy = ((float) (p2.f20y - p1.f20y)) / d;
        int error = 0;
        float px = (float) p1.f19x;
        float py = (float) p1.f20y;
        boolean colorModel = this.image.get(p1.f19x, p1.f20y);
        for (int i = 0; ((float) i) < d; i++) {
            px += dx;
            py += dy;
            if (this.image.get(MathUtils.round(px), MathUtils.round(py)) != colorModel) {
                error++;
            }
        }
        float errRatio = ((float) error) / d;
        if (((double) errRatio) <= 0.1d || ((double) errRatio) >= 0.9d) {
            return ((double) errRatio) <= 0.1d ? colorModel ? 1 : -1 : colorModel ? -1 : 1;
        } else {
            return 0;
        }
    }

    private Point getFirstDifferent(Point init, boolean color, int dx, int dy) {
        int x = init.f19x + dx;
        int y = init.f20y + dy;
        while (isValid(x, y) && this.image.get(x, y) == color) {
            x += dx;
            y += dy;
        }
        x -= dx;
        y -= dy;
        while (isValid(x, y) && this.image.get(x, y) == color) {
            x += dx;
        }
        x -= dx;
        while (isValid(x, y) && this.image.get(x, y) == color) {
            y += dy;
        }
        return new Point(y - dy, null);
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < this.image.getWidth() && y > 0 && y < this.image.getHeight();
    }

    private static float distance(Point a, Point b) {
        return MathUtils.distance(a.f19x, a.f20y, b.f19x, b.f20y);
    }
}
