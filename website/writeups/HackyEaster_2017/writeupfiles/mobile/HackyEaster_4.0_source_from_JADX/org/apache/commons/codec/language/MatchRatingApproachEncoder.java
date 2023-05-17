package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.http.message.TokenParser;

public class MatchRatingApproachEncoder implements StringEncoder {
    private static final String[] DOUBLE_CONSONANT;
    private static final int EIGHT = 8;
    private static final int ELEVEN = 11;
    private static final String EMPTY = "";
    private static final int FIVE = 5;
    private static final int FOUR = 4;
    private static final int ONE = 1;
    private static final String PLAIN_ASCII = "AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu";
    private static final int SEVEN = 7;
    private static final int SIX = 6;
    private static final String SPACE = " ";
    private static final int THREE = 3;
    private static final int TWELVE = 12;
    private static final int TWO = 2;
    private static final String UNICODE = "\u00c0\u00e0\u00c8\u00e8\u00cc\u00ec\u00d2\u00f2\u00d9\u00f9\u00c1\u00e1\u00c9\u00e9\u00cd\u00ed\u00d3\u00f3\u00da\u00fa\u00dd\u00fd\u00c2\u00e2\u00ca\u00ea\u00ce\u00ee\u00d4\u00f4\u00db\u00fb\u0176\u0177\u00c3\u00e3\u00d5\u00f5\u00d1\u00f1\u00c4\u00e4\u00cb\u00eb\u00cf\u00ef\u00d6\u00f6\u00dc\u00fc\u0178\u00ff\u00c5\u00e5\u00c7\u00e7\u0150\u0151\u0170\u0171";

    static {
        DOUBLE_CONSONANT = new String[]{"BB", "CC", "DD", "FF", "GG", "HH", "JJ", "KK", "LL", "MM", "NN", "PP", "QQ", "RR", "SS", "TT", "VV", "WW", "XX", "YY", "ZZ"};
    }

    String cleanName(String name) {
        String upperName = name.toUpperCase(Locale.ENGLISH);
        String[] charsToTrim = new String[FIVE];
        charsToTrim[0] = "\\-";
        charsToTrim[ONE] = "[&]";
        charsToTrim[TWO] = "\\'";
        charsToTrim[THREE] = "\\.";
        charsToTrim[FOUR] = "[\\,]";
        String[] arr$ = charsToTrim;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$ += ONE) {
            upperName = upperName.replaceAll(arr$[i$], EMPTY);
        }
        return removeAccents(upperName).replaceAll("\\s+", EMPTY);
    }

    public final Object encode(Object pObject) throws EncoderException {
        if (pObject instanceof String) {
            return encode((String) pObject);
        }
        throw new EncoderException("Parameter supplied to Match Rating Approach encoder is not of type java.lang.String");
    }

    public final String encode(String name) {
        if (name == null || EMPTY.equalsIgnoreCase(name) || SPACE.equalsIgnoreCase(name) || name.length() == ONE) {
            return EMPTY;
        }
        return getFirst3Last3(removeDoubleConsonants(removeVowels(cleanName(name))));
    }

    String getFirst3Last3(String name) {
        int nameLength = name.length();
        if (nameLength <= SIX) {
            return name;
        }
        String firstThree = name.substring(0, THREE);
        return firstThree + name.substring(nameLength - 3, nameLength);
    }

    int getMinRating(int sumLength) {
        if (sumLength <= FOUR) {
            return FIVE;
        }
        if (sumLength >= FIVE && sumLength <= SEVEN) {
            return FOUR;
        }
        if (sumLength >= EIGHT && sumLength <= ELEVEN) {
            return THREE;
        }
        if (sumLength == TWELVE) {
            return TWO;
        }
        return ONE;
    }

    public boolean isEncodeEquals(String name1, String name2) {
        boolean z = true;
        if (name1 == null || EMPTY.equalsIgnoreCase(name1) || SPACE.equalsIgnoreCase(name1) || name2 == null || EMPTY.equalsIgnoreCase(name2) || SPACE.equalsIgnoreCase(name2) || name1.length() == ONE || name2.length() == ONE) {
            return false;
        }
        if (name1.equalsIgnoreCase(name2)) {
            return true;
        }
        name1 = cleanName(name1);
        name2 = cleanName(name2);
        name1 = removeVowels(name1);
        name2 = removeVowels(name2);
        name1 = removeDoubleConsonants(name1);
        name2 = removeDoubleConsonants(name2);
        name1 = getFirst3Last3(name1);
        name2 = getFirst3Last3(name2);
        if (Math.abs(name1.length() - name2.length()) >= THREE) {
            return false;
        }
        if (leftToRightThenRightToLeftProcessing(name1, name2) < getMinRating(Math.abs(name1.length() + name2.length()))) {
            z = false;
        }
        return z;
    }

    boolean isVowel(String letter) {
        return letter.equalsIgnoreCase("E") || letter.equalsIgnoreCase("A") || letter.equalsIgnoreCase("O") || letter.equalsIgnoreCase("I") || letter.equalsIgnoreCase("U");
    }

    int leftToRightThenRightToLeftProcessing(String name1, String name2) {
        char[] name1Char = name1.toCharArray();
        char[] name2Char = name2.toCharArray();
        int name1Size = name1.length() - 1;
        int name2Size = name2.length() - 1;
        String name1LtRStart = EMPTY;
        String name1LtREnd = EMPTY;
        String name2RtLStart = EMPTY;
        String name2RtLEnd = EMPTY;
        int i = 0;
        while (i < name1Char.length && i <= name2Size) {
            name1LtRStart = name1.substring(i, i + ONE);
            name1LtREnd = name1.substring(name1Size - i, (name1Size - i) + ONE);
            name2RtLStart = name2.substring(i, i + ONE);
            name2RtLEnd = name2.substring(name2Size - i, (name2Size - i) + ONE);
            if (name1LtRStart.equals(name2RtLStart)) {
                name1Char[i] = TokenParser.SP;
                name2Char[i] = TokenParser.SP;
            }
            if (name1LtREnd.equals(name2RtLEnd)) {
                name1Char[name1Size - i] = TokenParser.SP;
                name2Char[name2Size - i] = TokenParser.SP;
            }
            i += ONE;
        }
        String strA = new String(name1Char).replaceAll("\\s+", EMPTY);
        String strB = new String(name2Char).replaceAll("\\s+", EMPTY);
        if (strA.length() > strB.length()) {
            return Math.abs(6 - strA.length());
        }
        return Math.abs(6 - strB.length());
    }

    String removeAccents(String accentedWord) {
        if (accentedWord == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int n = accentedWord.length();
        for (int i = 0; i < n; i += ONE) {
            char c = accentedWord.charAt(i);
            int pos = UNICODE.indexOf(c);
            if (pos > -1) {
                sb.append(PLAIN_ASCII.charAt(pos));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    String removeDoubleConsonants(String name) {
        String replacedName = name.toUpperCase();
        String[] arr$ = DOUBLE_CONSONANT;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$ += ONE) {
            String dc = arr$[i$];
            if (replacedName.contains(dc)) {
                replacedName = replacedName.replace(dc, dc.substring(0, ONE));
            }
        }
        return replacedName;
    }

    String removeVowels(String name) {
        String firstLetter = name.substring(0, ONE);
        name = name.replaceAll("A", EMPTY).replaceAll("E", EMPTY).replaceAll("I", EMPTY).replaceAll("O", EMPTY).replaceAll("U", EMPTY).replaceAll("\\s{2,}\\b", SPACE);
        if (isVowel(firstLetter)) {
            return firstLetter + name;
        }
        return name;
    }
}
