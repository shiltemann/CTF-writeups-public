package com.google.zxing.oned.rss.expanded;

import com.google.zxing.common.BitArray;
import java.util.List;

final class BitArrayBuilder {
    private BitArrayBuilder() {
    }

    static BitArray buildBitArray(List<ExpandedPair> pairs) {
        int i;
        int charNumber = (pairs.size() << 1) - 1;
        if (((ExpandedPair) pairs.get(pairs.size() - 1)).getRightChar() == null) {
            charNumber--;
        }
        BitArray binary = new BitArray(charNumber * 12);
        int accPos = 0;
        int firstValue = ((ExpandedPair) pairs.get(0)).getRightChar().getValue();
        for (i = 11; i >= 0; i--) {
            if (((1 << i) & firstValue) != 0) {
                binary.set(accPos);
            }
            accPos++;
        }
        for (i = 1; i < pairs.size(); i++) {
            int j;
            ExpandedPair currentPair = (ExpandedPair) pairs.get(i);
            int leftValue = currentPair.getLeftChar().getValue();
            for (j = 11; j >= 0; j--) {
                if (((1 << j) & leftValue) != 0) {
                    binary.set(accPos);
                }
                accPos++;
            }
            if (currentPair.getRightChar() != null) {
                int rightValue = currentPair.getRightChar().getValue();
                for (j = 11; j >= 0; j--) {
                    if (((1 << j) & rightValue) != 0) {
                        binary.set(accPos);
                    }
                    accPos++;
                }
            }
        }
        return binary;
    }
}
