package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

@Deprecated
public class Caverphone implements StringEncoder {
    private final Caverphone2 encoder;

    public Caverphone() {
        this.encoder = new Caverphone2();
    }

    public String caverphone(String source) {
        return this.encoder.encode(source);
    }

    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return caverphone((String) obj);
        }
        throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
    }

    public String encode(String str) {
        return caverphone(str);
    }

    public boolean isCaverphoneEqual(String str1, String str2) {
        return caverphone(str1).equals(caverphone(str2));
    }
}
