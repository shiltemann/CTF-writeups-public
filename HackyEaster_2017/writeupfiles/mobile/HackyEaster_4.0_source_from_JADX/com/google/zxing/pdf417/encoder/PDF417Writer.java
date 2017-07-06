package com.google.zxing.pdf417.encoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.lang.reflect.Array;
import java.util.EnumMap;
import java.util.Map;

public final class PDF417Writer implements Writer {
    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        if (format != BarcodeFormat.PDF_417) {
            throw new IllegalArgumentException("Can only encode PDF_417, but got " + format);
        }
        PDF417 encoder = new PDF417();
        if (hints != null) {
            if (hints.containsKey(EncodeHintType.PDF417_COMPACT)) {
                encoder.setCompact(((Boolean) hints.get(EncodeHintType.PDF417_COMPACT)).booleanValue());
            }
            if (hints.containsKey(EncodeHintType.PDF417_COMPACTION)) {
                encoder.setCompaction((Compaction) hints.get(EncodeHintType.PDF417_COMPACTION));
            }
            if (hints.containsKey(EncodeHintType.PDF417_DIMENSIONS)) {
                Dimensions dimensions = (Dimensions) hints.get(EncodeHintType.PDF417_DIMENSIONS);
                encoder.setDimensions(dimensions.getMaxCols(), dimensions.getMinCols(), dimensions.getMaxRows(), dimensions.getMinRows());
            }
        }
        return bitMatrixFromEncoder(encoder, contents, width, height);
    }

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        return encode(contents, format, width, height, null);
    }

    @Deprecated
    public BitMatrix encode(String contents, BarcodeFormat format, boolean compact, int width, int height, int minCols, int maxCols, int minRows, int maxRows, Compaction compaction) throws WriterException {
        Map<EncodeHintType, Object> hints = new EnumMap(EncodeHintType.class);
        hints.put(EncodeHintType.PDF417_COMPACT, Boolean.valueOf(compact));
        hints.put(EncodeHintType.PDF417_COMPACTION, compaction);
        hints.put(EncodeHintType.PDF417_DIMENSIONS, new Dimensions(minCols, maxCols, minRows, maxRows));
        return encode(contents, format, width, height, hints);
    }

    private static BitMatrix bitMatrixFromEncoder(PDF417 encoder, String contents, int width, int height) throws WriterException {
        int i;
        int scale;
        encoder.generateBarcodeLogic(contents, 2);
        byte[][] originalScale = encoder.getBarcodeMatrix().getScaledMatrix(2, 8);
        boolean rotated = false;
        int i2 = height > width ? 1 : 0;
        if (originalScale[0].length < originalScale.length) {
            i = 1;
        } else {
            i = 0;
        }
        if ((i2 ^ i) != 0) {
            originalScale = rotateArray(originalScale);
            rotated = true;
        }
        int scaleX = width / originalScale[0].length;
        int scaleY = height / originalScale.length;
        if (scaleX < scaleY) {
            scale = scaleX;
        } else {
            scale = scaleY;
        }
        if (scale <= 1) {
            return bitMatrixFrombitArray(originalScale);
        }
        byte[][] scaledMatrix = encoder.getBarcodeMatrix().getScaledMatrix(scale * 2, (scale * 4) * 2);
        if (rotated) {
            scaledMatrix = rotateArray(scaledMatrix);
        }
        return bitMatrixFrombitArray(scaledMatrix);
    }

    private static BitMatrix bitMatrixFrombitArray(byte[][] input) {
        BitMatrix output = new BitMatrix(input[0].length + 60, input.length + 60);
        output.clear();
        int y = 0;
        int yOutput = output.getHeight() - 30;
        while (y < input.length) {
            for (int x = 0; x < input[0].length; x++) {
                if (input[y][x] == 1) {
                    output.set(x + 30, yOutput);
                }
            }
            y++;
            yOutput--;
        }
        return output;
    }

    private static byte[][] rotateArray(byte[][] bitarray) {
        byte[][] temp = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{bitarray[0].length, bitarray.length});
        for (int ii = 0; ii < bitarray.length; ii++) {
            int inverseii = (bitarray.length - ii) - 1;
            for (int jj = 0; jj < bitarray[0].length; jj++) {
                temp[jj][inverseii] = bitarray[ii][jj];
            }
        }
        return temp;
    }
}
