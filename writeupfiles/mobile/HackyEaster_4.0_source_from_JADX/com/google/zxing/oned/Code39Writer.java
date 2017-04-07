package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class Code39Writer extends OneDimensionalCodeWriter {
    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        if (format == BarcodeFormat.CODE_39) {
            return super.encode(contents, format, width, height, hints);
        }
        throw new IllegalArgumentException("Can only encode CODE_39, but got " + format);
    }

    public boolean[] encode(String contents) {
        int length = contents.length();
        if (length > 80) {
            throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
        }
        int i;
        int[] widths = new int[9];
        int codeWidth = length + 25;
        for (i = 0; i < length; i++) {
            toIntArray(Code39Reader.CHARACTER_ENCODINGS["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(contents.charAt(i))], widths);
            for (int width : widths) {
                codeWidth += width;
            }
        }
        boolean[] result = new boolean[codeWidth];
        toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], widths);
        int pos = OneDimensionalCodeWriter.appendPattern(result, 0, widths, true);
        int[] narrowWhite = new int[]{1};
        pos += OneDimensionalCodeWriter.appendPattern(result, pos, narrowWhite, false);
        for (i = length - 1; i >= 0; i--) {
            toIntArray(Code39Reader.CHARACTER_ENCODINGS["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(contents.charAt(i))], widths);
            pos += OneDimensionalCodeWriter.appendPattern(result, pos, widths, true);
            pos += OneDimensionalCodeWriter.appendPattern(result, pos, narrowWhite, false);
        }
        toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], widths);
        pos += OneDimensionalCodeWriter.appendPattern(result, pos, widths, true);
        return result;
    }

    private static void toIntArray(int a, int[] toReturn) {
        for (int i = 0; i < 9; i++) {
            toReturn[i] = (a & (1 << i)) == 0 ? 1 : 2;
        }
    }
}
