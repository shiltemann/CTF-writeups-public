package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

final class AI01393xDecoder extends AI01decoder {
    private static final int FIRST_THREE_DIGITS_SIZE = 10;
    private static final int HEADER_SIZE = 8;
    private static final int LAST_DIGIT_SIZE = 2;

    AI01393xDecoder(BitArray information) {
        super(information);
    }

    public String parseInformation() throws NotFoundException {
        if (getInformation().getSize() < 48) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuilder buf = new StringBuilder();
        encodeCompressedGtin(buf, HEADER_SIZE);
        int lastAIdigit = getGeneralDecoder().extractNumericValueFromBitArray(48, LAST_DIGIT_SIZE);
        buf.append("(393");
        buf.append(lastAIdigit);
        buf.append(')');
        int firstThreeDigits = getGeneralDecoder().extractNumericValueFromBitArray(50, FIRST_THREE_DIGITS_SIZE);
        if (firstThreeDigits / 100 == 0) {
            buf.append('0');
        }
        if (firstThreeDigits / FIRST_THREE_DIGITS_SIZE == 0) {
            buf.append('0');
        }
        buf.append(firstThreeDigits);
        buf.append(getGeneralDecoder().decodeGeneralPurposeField(60, null).getNewString());
        return buf.toString();
    }
}
