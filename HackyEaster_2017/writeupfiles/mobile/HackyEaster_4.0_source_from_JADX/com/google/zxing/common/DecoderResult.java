package com.google.zxing.common;

import java.util.List;

public final class DecoderResult {
    private final List<byte[]> byteSegments;
    private final String ecLevel;
    private final byte[] rawBytes;
    private final String text;

    public DecoderResult(byte[] rawBytes, String text, List<byte[]> byteSegments, String ecLevel) {
        this.rawBytes = rawBytes;
        this.text = text;
        this.byteSegments = byteSegments;
        this.ecLevel = ecLevel;
    }

    public byte[] getRawBytes() {
        return this.rawBytes;
    }

    public String getText() {
        return this.text;
    }

    public List<byte[]> getByteSegments() {
        return this.byteSegments;
    }

    public String getECLevel() {
        return this.ecLevel;
    }
}
