package com.google.zxing.oned.rss.expanded.decoders;

final class DecodedNumeric extends DecodedObject {
    static final int FNC1 = 10;
    private final int firstDigit;
    private final int secondDigit;

    DecodedNumeric(int newPosition, int firstDigit, int secondDigit) {
        super(newPosition);
        this.firstDigit = firstDigit;
        this.secondDigit = secondDigit;
        if (this.firstDigit < 0 || this.firstDigit > FNC1) {
            throw new IllegalArgumentException("Invalid firstDigit: " + firstDigit);
        } else if (this.secondDigit < 0 || this.secondDigit > FNC1) {
            throw new IllegalArgumentException("Invalid secondDigit: " + secondDigit);
        }
    }

    int getFirstDigit() {
        return this.firstDigit;
    }

    int getSecondDigit() {
        return this.secondDigit;
    }

    int getValue() {
        return (this.firstDigit * FNC1) + this.secondDigit;
    }

    boolean isFirstDigitFNC1() {
        return this.firstDigit == FNC1;
    }

    boolean isSecondDigitFNC1() {
        return this.secondDigit == FNC1;
    }

    boolean isAnyFNC1() {
        return this.firstDigit == FNC1 || this.secondDigit == FNC1;
    }
}
