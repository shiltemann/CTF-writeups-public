package com.google.zxing;

public final class FormatException extends ReaderException {
    private static final FormatException instance;

    static {
        instance = new FormatException();
    }

    private FormatException() {
    }

    public static FormatException getFormatInstance() {
        return instance;
    }
}
