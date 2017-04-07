package com.google.zxing;

public final class NotFoundException extends ReaderException {
    private static final NotFoundException instance;

    static {
        instance = new NotFoundException();
    }

    private NotFoundException() {
    }

    public static NotFoundException getNotFoundInstance() {
        return instance;
    }
}
