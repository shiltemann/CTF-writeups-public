package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class EAN8Writer extends UPCEANWriter {
    private static final int CODE_WIDTH = 67;

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        if (format == BarcodeFormat.EAN_8) {
            return super.encode(contents, format, width, height, hints);
        }
        throw new IllegalArgumentException("Can only encode EAN_8, but got " + format);
    }

    public boolean[] encode(String contents) {
        if (contents.length() != 8) {
            throw new IllegalArgumentException("Requested contents should be 8 digits long, but got " + contents.length());
        }
        int i;
        boolean[] result = new boolean[CODE_WIDTH];
        int pos = 0 + OneDimensionalCodeWriter.appendPattern(result, 0, UPCEANReader.START_END_PATTERN, true);
        for (i = 0; i <= 3; i++) {
            pos += OneDimensionalCodeWriter.appendPattern(result, pos, UPCEANReader.L_PATTERNS[Integer.parseInt(contents.substring(i, i + 1))], false);
        }
        pos += OneDimensionalCodeWriter.appendPattern(result, pos, UPCEANReader.MIDDLE_PATTERN, false);
        for (i = 4; i <= 7; i++) {
            pos += OneDimensionalCodeWriter.appendPattern(result, pos, UPCEANReader.L_PATTERNS[Integer.parseInt(contents.substring(i, i + 1))], true);
        }
        pos += OneDimensionalCodeWriter.appendPattern(result, pos, UPCEANReader.START_END_PATTERN, true);
        return result;
    }
}
