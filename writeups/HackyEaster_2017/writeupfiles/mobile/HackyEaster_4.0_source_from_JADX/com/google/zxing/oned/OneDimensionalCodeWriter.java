package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public abstract class OneDimensionalCodeWriter implements Writer {
    public abstract boolean[] encode(String str);

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        return encode(contents, format, width, height, null);
    }

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        if (contents.length() == 0) {
            throw new IllegalArgumentException("Found empty contents");
        } else if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Negative size is not allowed. Input: " + width + 'x' + height);
        } else {
            int sidesMargin = getDefaultMargin();
            if (hints != null) {
                Integer sidesMarginInt = (Integer) hints.get(EncodeHintType.MARGIN);
                if (sidesMarginInt != null) {
                    sidesMargin = sidesMarginInt.intValue();
                }
            }
            return renderResult(encode(contents), width, height, sidesMargin);
        }
    }

    private static BitMatrix renderResult(boolean[] code, int width, int height, int sidesMargin) {
        int inputWidth = code.length;
        int fullWidth = inputWidth + sidesMargin;
        int outputWidth = Math.max(width, fullWidth);
        int outputHeight = Math.max(1, height);
        int multiple = outputWidth / fullWidth;
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        BitMatrix output = new BitMatrix(outputWidth, outputHeight);
        int inputX = 0;
        int outputX = leftPadding;
        while (inputX < inputWidth) {
            if (code[inputX]) {
                output.setRegion(outputX, 0, multiple, outputHeight);
            }
            inputX++;
            outputX += multiple;
        }
        return output;
    }

    protected static int appendPattern(boolean[] target, int pos, int[] pattern, boolean startColor) {
        boolean color = startColor;
        int numAdded = 0;
        int[] arr$ = pattern;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            int len = arr$[i$];
            int j = 0;
            int pos2 = pos;
            while (j < len) {
                pos = pos2 + 1;
                target[pos2] = color;
                j++;
                pos2 = pos;
            }
            numAdded += len;
            if (color) {
                color = false;
            } else {
                color = true;
            }
            i$++;
            pos = pos2;
        }
        return numAdded;
    }

    public int getDefaultMargin() {
        return 10;
    }
}
