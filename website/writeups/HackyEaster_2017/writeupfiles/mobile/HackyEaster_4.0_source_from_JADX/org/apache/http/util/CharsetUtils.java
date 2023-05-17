package org.apache.http.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetUtils {
    public static Charset lookup(String name) {
        Charset charset = null;
        if (name != null) {
            try {
                charset = Charset.forName(name);
            } catch (UnsupportedCharsetException e) {
            }
        }
        return charset;
    }

    public static Charset get(String name) throws UnsupportedEncodingException {
        if (name == null) {
            return null;
        }
        try {
            return Charset.forName(name);
        } catch (UnsupportedCharsetException e) {
            throw new UnsupportedEncodingException(name);
        }
    }
}
