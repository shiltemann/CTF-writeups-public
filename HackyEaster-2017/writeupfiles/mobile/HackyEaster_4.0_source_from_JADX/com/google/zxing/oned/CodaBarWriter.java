package com.google.zxing.oned;

import android.support.v4.view.MotionEventCompat;
import java.util.Arrays;

public final class CodaBarWriter extends OneDimensionalCodeWriter {
    private static final char[] END_CHARS;
    private static final char[] START_CHARS;

    static {
        START_CHARS = new char[]{'A', 'B', 'C', 'D'};
        END_CHARS = new char[]{'T', 'N', '*', 'E'};
    }

    public boolean[] encode(String contents) {
        if (!CodaBarReader.arrayContains(START_CHARS, Character.toUpperCase(contents.charAt(0)))) {
            throw new IllegalArgumentException("Codabar should start with one of the following: " + Arrays.toString(START_CHARS));
        } else if (CodaBarReader.arrayContains(END_CHARS, Character.toUpperCase(contents.charAt(contents.length() - 1)))) {
            int resultLength = 20;
            char[] charsWhichAreTenLengthEachAfterDecoded = new char[]{'/', ':', '+', '.'};
            int i = 1;
            while (i < contents.length() - 1) {
                if (Character.isDigit(contents.charAt(i)) || contents.charAt(i) == '-' || contents.charAt(i) == '$') {
                    resultLength += 9;
                } else if (CodaBarReader.arrayContains(charsWhichAreTenLengthEachAfterDecoded, contents.charAt(i))) {
                    resultLength += 10;
                } else {
                    throw new IllegalArgumentException("Cannot encode : '" + contents.charAt(i) + '\'');
                }
                i++;
            }
            boolean[] result = new boolean[(resultLength + (contents.length() - 1))];
            int position = 0;
            for (int index = 0; index < contents.length(); index++) {
                boolean color;
                int counter;
                int bit;
                char c = Character.toUpperCase(contents.charAt(index));
                if (index == contents.length() - 1) {
                    switch (c) {
                        case MotionEventCompat.AXIS_GENERIC_11 /*42*/:
                            c = 'C';
                            break;
                        case 'E':
                            c = 'D';
                            break;
                        case 'N':
                            c = 'B';
                            break;
                        case 'T':
                            c = 'A';
                            break;
                    }
                }
                int code = 0;
                i = 0;
                while (i < CodaBarReader.ALPHABET.length) {
                    if (c == CodaBarReader.ALPHABET[i]) {
                        code = CodaBarReader.CHARACTER_ENCODINGS[i];
                        color = true;
                        counter = 0;
                        bit = 0;
                        while (bit < 7) {
                            result[position] = color;
                            position++;
                            if (((code >> (6 - bit)) & 1) != 0 || counter == 1) {
                                color = color;
                                bit++;
                                counter = 0;
                            } else {
                                counter++;
                            }
                        }
                        if (index < contents.length() - 1) {
                            result[position] = false;
                            position++;
                        }
                    } else {
                        i++;
                    }
                }
                color = true;
                counter = 0;
                bit = 0;
                while (bit < 7) {
                    result[position] = color;
                    position++;
                    if (((code >> (6 - bit)) & 1) != 0) {
                    }
                    if (color) {
                    }
                    bit++;
                    counter = 0;
                }
                if (index < contents.length() - 1) {
                    result[position] = false;
                    position++;
                }
            }
            return result;
        } else {
            throw new IllegalArgumentException("Codabar should end with one of the following: " + Arrays.toString(END_CHARS));
        }
    }
}
