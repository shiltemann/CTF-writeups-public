package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;

class Utils {
    Utils() {
    }

    static int digit16(byte b) throws DecoderException {
        int i = Character.digit((char) b, 16);
        if (i != -1) {
            return i;
        }
        throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + b);
    }
}
