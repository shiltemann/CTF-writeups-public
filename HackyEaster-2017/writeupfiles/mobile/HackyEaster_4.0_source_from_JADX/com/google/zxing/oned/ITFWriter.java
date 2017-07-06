package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class ITFWriter extends OneDimensionalCodeWriter {
    private static final int[] END_PATTERN;
    private static final int[] START_PATTERN;

    static {
        START_PATTERN = new int[]{1, 1, 1, 1};
        END_PATTERN = new int[]{3, 1, 1};
    }

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        if (format == BarcodeFormat.ITF) {
            return super.encode(contents, format, width, height, hints);
        }
        throw new IllegalArgumentException("Can only encode ITF, but got " + format);
    }

    public boolean[] encode(String contents) {
        int length = contents.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("The lenght of the input should be even");
        } else if (length > 80) {
            throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
        } else {
            boolean[] result = new boolean[((length * 9) + 9)];
            int pos = OneDimensionalCodeWriter.appendPattern(result, 0, START_PATTERN, true);
            for (int i = 0; i < length; i += 2) {
                int one = Character.digit(contents.charAt(i), 10);
                int two = Character.digit(contents.charAt(i + 1), 10);
                int[] encoding = new int[18];
                for (int j = 0; j < 5; j++) {
                    encoding[j << 1] = ITFReader.PATTERNS[one][j];
                    encoding[(j << 1) + 1] = ITFReader.PATTERNS[two][j];
                }
                pos += OneDimensionalCodeWriter.appendPattern(result, pos, encoding, true);
            }
            OneDimensionalCodeWriter.appendPattern(result, pos, END_PATTERN, true);
            return result;
        }
    }
}
