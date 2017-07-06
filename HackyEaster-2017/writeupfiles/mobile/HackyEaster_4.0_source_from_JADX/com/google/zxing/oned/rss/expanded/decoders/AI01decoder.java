package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

abstract class AI01decoder extends AbstractExpandedDecoder {
    protected static final int GTIN_SIZE = 40;

    AI01decoder(BitArray information) {
        super(information);
    }

    protected final void encodeCompressedGtin(StringBuilder buf, int currentPos) {
        buf.append("(01)");
        int initialPosition = buf.length();
        buf.append('9');
        encodeCompressedGtinWithoutAI(buf, currentPos, initialPosition);
    }

    protected final void encodeCompressedGtinWithoutAI(StringBuilder buf, int currentPos, int initialBufferPosition) {
        for (int i = 0; i < 4; i++) {
            int currentBlock = getGeneralDecoder().extractNumericValueFromBitArray((i * 10) + currentPos, 10);
            if (currentBlock / 100 == 0) {
                buf.append('0');
            }
            if (currentBlock / 10 == 0) {
                buf.append('0');
            }
            buf.append(currentBlock);
        }
        appendCheckDigit(buf, initialBufferPosition);
    }

    private static void appendCheckDigit(StringBuilder buf, int currentPos) {
        int checkDigit = 0;
        for (int i = 0; i < 13; i++) {
            int digit = buf.charAt(i + currentPos) - 48;
            if ((i & 1) == 0) {
                digit *= 3;
            }
            checkDigit += digit;
        }
        checkDigit = 10 - (checkDigit % 10);
        if (checkDigit == 10) {
            checkDigit = 0;
        }
        buf.append(checkDigit);
    }
}
