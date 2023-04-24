package ps.hacking.zxing.maxicode;

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
import ps.hacking.zxing.maxicode.decoder.Decoder;

public final class MaxiCodeReader implements Reader {
    private static final int MATRIX_HEIGHT = 33;
    private static final int MATRIX_WIDTH = 30;
    private static final ResultPoint[] NO_POINTS;
    private final Decoder decoder;

    public MaxiCodeReader() {
        this.decoder = new Decoder();
    }

    static {
        NO_POINTS = new ResultPoint[0];
    }

    Decoder getDecoder() {
        return this.decoder;
    }

    public Result decode(BinaryBitmap image) throws NotFoundException, ChecksumException, FormatException {
        return decode(image, null);
    }

    public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, ChecksumException, FormatException {
        if (hints == null || !hints.containsKey(DecodeHintType.PURE_BARCODE)) {
            throw NotFoundException.getNotFoundInstance();
        }
        DecoderResult decoderResult = this.decoder.decode(extractPureBits(image.getBlackMatrix()), hints);
        Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), NO_POINTS, BarcodeFormat.MAXICODE);
        String ecLevel = decoderResult.getECLevel();
        if (ecLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
        }
        return result;
    }

    public void reset() {
    }

    private static BitMatrix extractPureBits(BitMatrix image) throws NotFoundException {
        int[] enclosingRectangle = image.getEnclosingRectangle();
        if (enclosingRectangle == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        int left = enclosingRectangle[0];
        int top = enclosingRectangle[1];
        int width = enclosingRectangle[2];
        int height = enclosingRectangle[3];
        BitMatrix bits = new BitMatrix(MATRIX_WIDTH, MATRIX_HEIGHT);
        for (int y = 0; y < MATRIX_HEIGHT; y++) {
            int iy = top + (((y * height) + (height / 2)) / MATRIX_HEIGHT);
            for (int x = 0; x < MATRIX_WIDTH; x++) {
                if (image.get(left + ((((x * width) + (width / 2)) + (((y & 1) * width) / 2)) / MATRIX_WIDTH), iy)) {
                    bits.set(x, y);
                }
            }
        }
        return bits;
    }
}
