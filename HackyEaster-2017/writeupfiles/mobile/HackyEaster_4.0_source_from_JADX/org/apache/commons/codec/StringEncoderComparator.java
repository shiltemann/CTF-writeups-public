package org.apache.commons.codec;

import java.util.Comparator;

public class StringEncoderComparator implements Comparator {
    private final StringEncoder stringEncoder;

    @Deprecated
    public StringEncoderComparator() {
        this.stringEncoder = null;
    }

    public StringEncoderComparator(StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }

    public int compare(Object o1, Object o2) {
        try {
            return ((Comparable) this.stringEncoder.encode(o1)).compareTo((Comparable) this.stringEncoder.encode(o2));
        } catch (EncoderException e) {
            return 0;
        }
    }
}
