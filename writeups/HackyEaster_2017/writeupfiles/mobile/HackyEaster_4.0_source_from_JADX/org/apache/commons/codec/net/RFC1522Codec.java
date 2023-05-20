package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;
import ps.hacking.hackyeaster.android.BuildConfig;

abstract class RFC1522Codec {
    protected static final String POSTFIX = "?=";
    protected static final String PREFIX = "=?";
    protected static final char SEP = '?';

    protected abstract byte[] doDecoding(byte[] bArr) throws DecoderException;

    protected abstract byte[] doEncoding(byte[] bArr) throws EncoderException;

    protected abstract String getEncoding();

    RFC1522Codec() {
    }

    protected String encodeText(String text, Charset charset) throws EncoderException {
        if (text == null) {
            return null;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(PREFIX);
        buffer.append(charset);
        buffer.append(SEP);
        buffer.append(getEncoding());
        buffer.append(SEP);
        buffer.append(StringUtils.newStringUsAscii(doEncoding(text.getBytes(charset))));
        buffer.append(POSTFIX);
        return buffer.toString();
    }

    protected String encodeText(String text, String charsetName) throws EncoderException, UnsupportedEncodingException {
        if (text == null) {
            return null;
        }
        return encodeText(text, Charset.forName(charsetName));
    }

    protected String decodeText(String text) throws DecoderException, UnsupportedEncodingException {
        if (text == null) {
            return null;
        }
        if (text.startsWith(PREFIX) && text.endsWith(POSTFIX)) {
            int terminator = text.length() - 2;
            int to = text.indexOf(63, 2);
            if (to == terminator) {
                throw new DecoderException("RFC 1522 violation: charset token not found");
            }
            String charset = text.substring(2, to);
            if (charset.equals(BuildConfig.FLAVOR)) {
                throw new DecoderException("RFC 1522 violation: charset not specified");
            }
            int from = to + 1;
            to = text.indexOf(63, from);
            if (to == terminator) {
                throw new DecoderException("RFC 1522 violation: encoding token not found");
            }
            String encoding = text.substring(from, to);
            if (getEncoding().equalsIgnoreCase(encoding)) {
                from = to + 1;
                return new String(doDecoding(StringUtils.getBytesUsAscii(text.substring(from, text.indexOf(63, from)))), charset);
            }
            throw new DecoderException("This codec cannot decode " + encoding + " encoded content");
        }
        throw new DecoderException("RFC 1522 violation: malformed encoded content");
    }
}
