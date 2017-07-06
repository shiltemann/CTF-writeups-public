package ps.hacking.zxing.datamatrix;

import java.util.List;
import java.util.Map;
import ps.hacking.zxing.BarcodeFormat;
import ps.hacking.zxing.BinaryBitmap;
import ps.hacking.zxing.ChecksumException;
import ps.hacking.zxing.DecodeHintType;
import ps.hacking.zxing.FormatException;
import ps.hacking.zxing.NotFoundException;
import ps.hacking.zxing.Reader;
import ps.hacking.zxing.Result;
import ps.hacking.zxing.ResultMetadataType;
import ps.hacking.zxing.ResultPoint;
import ps.hacking.zxing.common.BitMatrix;
import ps.hacking.zxing.common.DecoderResult;
import ps.hacking.zxing.common.DetectorResult;
import ps.hacking.zxing.datamatrix.decoder.Decoder;
import ps.hacking.zxing.datamatrix.detector.Detector;

public final class DataMatrixReader implements Reader {
    private static final ResultPoint[] NO_POINTS;
    private final Decoder decoder;

    public DataMatrixReader() {
        this.decoder = new Decoder();
    }

    static {
        NO_POINTS = new ResultPoint[0];
    }

    public Result decode(BinaryBitmap image) throws NotFoundException, ChecksumException, FormatException {
        return decode(image, null);
    }

    public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, ChecksumException, FormatException {
        DecoderResult decoderResult;
        ResultPoint[] points;
        if (hints == null || !hints.containsKey(DecodeHintType.PURE_BARCODE)) {
            DetectorResult detectorResult = new Detector(image.getBlackMatrix()).detect();
            decoderResult = this.decoder.decode(detectorResult.getBits());
            points = detectorResult.getPoints();
        } else {
            decoderResult = this.decoder.decode(extractPureBits(image.getBlackMatrix()));
            points = NO_POINTS;
        }
        Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), points, BarcodeFormat.DATA_MATRIX);
        List<byte[]> byteSegments = decoderResult.getByteSegments();
        if (byteSegments != null) {
            result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
        }
        String ecLevel = decoderResult.getECLevel();
        if (ecLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
        }
        return result;
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
        int left = leftTopBlack[0];
        int matrixWidth = ((rightBottomBlack[0] - left) + 1) / moduleSize;
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
        int width = image.getWidth();
        int x = leftTopBlack[0];
        int y = leftTopBlack[1];
        while (x < width && image.get(x, y)) {
            x++;
        }
        if (x == width) {
            throw NotFoundException.getNotFoundInstance();
        }
        int moduleSize = x - leftTopBlack[0];
        if (moduleSize != 0) {
            return moduleSize;
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
