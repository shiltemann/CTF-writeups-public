package org.apache.commons.codec;

public interface StringDecoder extends Decoder {
    String decode(String str) throws DecoderException;
}
