package com.google.zxing.maxicode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.maxicode.decoder.Decoder;
import java.util.Map;

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
