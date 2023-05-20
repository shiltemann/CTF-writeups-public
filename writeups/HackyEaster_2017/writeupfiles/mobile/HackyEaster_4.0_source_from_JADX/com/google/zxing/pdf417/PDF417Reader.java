package com.google.zxing.pdf417;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.pdf417.decoder.Decoder;
import com.google.zxing.pdf417.detector.Detector;
import java.util.Map;

public final class PDF417Reader implements Reader {
    private static final ResultPoint[] NO_POINTS;
    private final Decoder decoder;

    public PDF417Reader() {
        this.decoder = new Decoder();
    }

    static {
        NO_POINTS = new ResultPoint[0];
    }

    public Result decode(BinaryBitmap image) throws NotFoundException, FormatException, ChecksumException {
        return decode(image, null);
    }

    public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, FormatException, ChecksumException {
        DecoderResult decoderResult;
        ResultPoint[] points;
        if (hints == null || !hints.containsKey(DecodeHintType.PURE_BARCODE)) {
            DetectorResult detectorResult = new Detector(image).detect();
            decoderResult = this.decoder.decode(detectorResult.getBits());
            points = detectorResult.getPoints();
        } else {
            decoderResult = this.decoder.decode(extractPureBits(image.getBlackMatrix()));
            points = NO_POINTS;
        }
        return new Result(decoderResult.getText(), decoderResult.getRawBytes(), points, BarcodeFormat.PDF_417);
    }

    public void reset() {
    }

    private static BitMatrix extractPureBits(BitMatrix image) throws NotFoundException {
        int[] leftTopBlack = image.getTopLeftOnBit();
        int[] rightBottomBlack = image.getBottomRightOnBit();
        if (leftTopBlack == null || rightBottomBlack == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        int moduleSize = moduleSize(leftTopBlack, image);
        int top = leftTopBlack[1];
        int bottom = rightBottomBlack[1];
        int left = findPatternStart(leftTopBlack[0], top, image);
        int matrixWidth = ((findPatternEnd(leftTopBlack[0], top, image) - left) + 1) / moduleSize;
        int matrixHeight = ((bottom - top) + 1) / moduleSize;
        if (matrixWidth <= 0 || matrixHeight <= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        int nudge = moduleSize >> 1;
        top += nudge;
        left += nudge;
        BitMatrix bits = new BitMatrix(matrixWidth, matrixHeight);
        for (int y = 0; y < matrixHeight; y++) {
            int iOffset = top + (y * moduleSize);
            for (int x = 0; x < matrixWidth; x++) {
                if (image.get((x * moduleSize) + left, iOffset)) {
                    bits.set(x, y);
                }
            }
        }
        return bits;
    }

    private static int moduleSize(int[] leftTopBlack, BitMatrix image) throws NotFoundException {
        int x = leftTopBlack[0];
        int y = leftTopBlack[1];
        int width = image.getWidth();
        while (x < width && image.get(x, y)) {
            x++;
        }
        if (x == width) {
            throw NotFoundException.getNotFoundInstance();
        }
        int moduleSize = (x - leftTopBlack[0]) >>> 3;
        if (moduleSize != 0) {
            return moduleSize;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int findPatternStart(int x, int y, BitMatrix image) throws NotFoundException {
        int width = image.getWidth();
        int start = x;
        int transitions = 0;
        boolean black = true;
        while (start < width - 1 && transitions < 8) {
            start++;
            boolean newBlack = image.get(start, y);
            if (black != newBlack) {
                transitions++;
            }
            black = newBlack;
        }
        if (start != width - 1) {
            return start;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int findPatternEnd(int x, int y, BitMatrix image) throws NotFoundException {
        int end = image.getWidth() - 1;
        while (end > x && !image.get(end, y)) {
            end--;
        }
        int transitions = 0;
        boolean black = true;
        while (end > x && transitions < 9) {
            end--;
            boolean newBlack = image.get(end, y);
            if (black != newBlack) {
                transitions++;
            }
            black = newBlack;
        }
        if (end != x) {
            return end;
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
