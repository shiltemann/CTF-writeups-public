package org.apache.commons.codec.language;

import java.util.regex.Pattern;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class Nysiis implements StringEncoder {
    private static final char[] CHARS_A;
    private static final char[] CHARS_AF;
    private static final char[] CHARS_C;
    private static final char[] CHARS_FF;
    private static final char[] CHARS_G;
    private static final char[] CHARS_N;
    private static final char[] CHARS_NN;
    private static final char[] CHARS_S;
    private static final char[] CHARS_SSS;
    private static final Pattern PAT_DT_ETC;
    private static final Pattern PAT_EE_IE;
    private static final Pattern PAT_K;
    private static final Pattern PAT_KN;
    private static final Pattern PAT_MAC;
    private static final Pattern PAT_PH_PF;
    private static final Pattern PAT_SCH;
    private static final char SPACE = ' ';
    private static final int TRUE_LENGTH = 6;
    private final boolean strict;

    static {
        CHARS_A = new char[]{'A'};
        CHARS_AF = new char[]{'A', 'F'};
        CHARS_C = new char[]{'C'};
        CHARS_FF = new char[]{'F', 'F'};
        CHARS_G = new char[]{'G'};
        CHARS_N = new char[]{'N'};
        CHARS_NN = new char[]{'N', 'N'};
        CHARS_S = new char[]{'S'};
        CHARS_SSS = new char[]{'S', 'S', 'S'};
        PAT_MAC = Pattern.compile("^MAC");
        PAT_KN = Pattern.compile("^KN");
        PAT_K = Pattern.compile("^K");
        PAT_PH_PF = Pattern.compile("^(PH|PF)");
        PAT_SCH = Pattern.compile("^SCH");
        PAT_EE_IE = Pattern.compile("(EE|IE)$");
        PAT_DT_ETC = Pattern.compile("(DT|RT|RD|NT|ND)$");
    }

    private static boolean isVowel(char c) {
        return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }

    private static char[] transcodeRemaining(char prev, char curr, char next, char aNext) {
        if (curr == 'E' && next == 'V') {
            return CHARS_AF;
        }
        if (isVowel(curr)) {
            return CHARS_A;
        }
        if (curr == 'Q') {
            return CHARS_G;
        }
        if (curr == 'Z') {
            return CHARS_S;
        }
        if (curr == 'M') {
            return CHARS_N;
        }
        if (curr == 'K') {
            if (next == 'N') {
                return CHARS_NN;
            }
            return CHARS_C;
        } else if (curr == 'S' && next == 'C' && aNext == 'H') {
            return CHARS_SSS;
        } else {
            if (curr == 'P' && next == 'H') {
                return CHARS_FF;
            }
            if (curr == 'H' && (!isVowel(prev) || !isVowel(next))) {
                return new char[]{prev};
            } else if (curr == 'W' && isVowel(prev)) {
                return new char[]{prev};
            } else {
                return new char[]{curr};
            }
        }
    }

    public Nysiis() {
        this(true);
    }

    public Nysiis(boolean strict) {
        this.strict = strict;
    }

    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return nysiis((String) obj);
        }
        throw new EncoderException("Parameter supplied to Nysiis encode is not of type java.lang.String");
    }

    public String encode(String str) {
        return nysiis(str);
    }

    public boolean isStrict() {
        return this.strict;
    }

    public String nysiis(String str) {
        if (str == null) {
            return null;
        }
        str = SoundexUtils.clean(str);
        if (str.length() == 0) {
            return str;
        }
        str = PAT_DT_ETC.matcher(PAT_EE_IE.matcher(PAT_SCH.matcher(PAT_PH_PF.matcher(PAT_K.matcher(PAT_KN.matcher(PAT_MAC.matcher(str).replaceFirst("MCC")).replaceFirst("NN")).replaceFirst("C")).replaceFirst("FF")).replaceFirst("SSS")).replaceFirst("Y")).replaceFirst("D");
        StringBuilder key = new StringBuilder(str.length());
        key.append(str.charAt(0));
        char[] chars = str.toCharArray();
        int len = chars.length;
        int i = 1;
        while (i < len) {
            char[] transcoded = transcodeRemaining(chars[i - 1], chars[i], i < len + -1 ? chars[i + 1] : SPACE, i < len + -2 ? chars[i + 2] : SPACE);
            System.arraycopy(transcoded, 0, chars, i, transcoded.length);
            if (chars[i] != chars[i - 1]) {
                key.append(chars[i]);
            }
            i++;
        }
        if (key.length() > 1) {
            char lastChar = key.charAt(key.length() - 1);
            if (lastChar == 'S') {
                key.deleteCharAt(key.length() - 1);
                lastChar = key.charAt(key.length() - 1);
            }
            if (key.length() > 2 && key.charAt(key.length() - 2) == 'A' && lastChar == 'Y') {
                key.deleteCharAt(key.length() - 2);
            }
            if (lastChar == 'A') {
                key.deleteCharAt(key.length() - 1);
            }
        }
        String string = key.toString();
        return isStrict() ? string.substring(0, Math.min(TRUE_LENGTH, string.length())) : string;
    }
}
