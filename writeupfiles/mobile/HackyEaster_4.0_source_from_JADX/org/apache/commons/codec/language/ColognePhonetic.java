package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class ColognePhonetic implements StringEncoder {
    private static final char[] AEIJOUY;
    private static final char[] AHKLOQRUX;
    private static final char[] AHOUKQX;
    private static final char[] CKQ;
    private static final char[] GKQ;
    private static final char[][] PREPROCESS_MAP;
    private static final char[] SCZ;
    private static final char[] SZ;
    private static final char[] TDX;
    private static final char[] WFPV;

    private abstract class CologneBuffer {
        protected final char[] data;
        protected int length;

        protected abstract char[] copyData(int i, int i2);

        public CologneBuffer(char[] data) {
            this.length = 0;
            this.data = data;
            this.length = data.length;
        }

        public CologneBuffer(int buffSize) {
            this.length = 0;
            this.data = new char[buffSize];
            this.length = 0;
        }

        public int length() {
            return this.length;
        }

        public String toString() {
            return new String(copyData(0, this.length));
        }
    }

    private class CologneInputBuffer extends CologneBuffer {
        public CologneInputBuffer(char[] data) {
            super(data);
        }

        public void addLeft(char ch) {
            this.length++;
            this.data[getNextPos()] = ch;
        }

        protected char[] copyData(int start, int length) {
            char[] newData = new char[length];
            System.arraycopy(this.data, (this.data.length - this.length) + start, newData, 0, length);
            return newData;
        }

        public char getNextChar() {
            return this.data[getNextPos()];
        }

        protected int getNextPos() {
            return this.data.length - this.length;
        }

        public char removeNext() {
            char ch = getNextChar();
            this.length--;
            return ch;
        }
    }

    private class CologneOutputBuffer extends CologneBuffer {
        public CologneOutputBuffer(int buffSize) {
            super(buffSize);
        }

        public void addRight(char chr) {
            this.data[this.length] = chr;
            this.length++;
        }

        protected char[] copyData(int start, int length) {
            char[] newData = new char[length];
            System.arraycopy(this.data, start, newData, 0, length);
            return newData;
        }
    }

    static {
        AEIJOUY = new char[]{'A', 'E', 'I', 'J', 'O', 'U', 'Y'};
        SCZ = new char[]{'S', 'C', 'Z'};
        WFPV = new char[]{'W', 'F', 'P', 'V'};
        GKQ = new char[]{'G', 'K', 'Q'};
        CKQ = new char[]{'C', 'K', 'Q'};
        AHKLOQRUX = new char[]{'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X'};
        SZ = new char[]{'S', 'Z'};
        AHOUKQX = new char[]{'A', 'H', 'O', 'U', 'K', 'Q', 'X'};
        TDX = new char[]{'T', 'D', 'X'};
        PREPROCESS_MAP = new char[][]{new char[]{'\u00c4', 'A'}, new char[]{'\u00dc', 'U'}, new char[]{'\u00d6', 'O'}, new char[]{'\u00df', 'S'}};
    }

    private static boolean arrayContains(char[] arr, char key) {
        for (char element : arr) {
            if (element == key) {
                return true;
            }
        }
        return false;
    }

    public String colognePhonetic(String text) {
        if (text == null) {
            return null;
        }
        text = preprocess(text);
        CologneOutputBuffer output = new CologneOutputBuffer(text.length() * 2);
        CologneInputBuffer input = new CologneInputBuffer(text.toCharArray());
        char lastChar = '-';
        char lastCode = '/';
        int rightLength = input.length();
        while (rightLength > 0) {
            char nextChar;
            char code;
            char chr = input.removeNext();
            rightLength = input.length();
            if (rightLength > 0) {
                nextChar = input.getNextChar();
            } else {
                nextChar = '-';
            }
            if (arrayContains(AEIJOUY, chr)) {
                code = '0';
            } else if (chr == 'H' || chr < 'A' || chr > 'Z') {
                if (lastCode != '/') {
                    code = '-';
                }
            } else if (chr == 'B' || (chr == 'P' && nextChar != 'H')) {
                code = '1';
            } else if ((chr == 'D' || chr == 'T') && !arrayContains(SCZ, nextChar)) {
                code = '2';
            } else if (arrayContains(WFPV, chr)) {
                code = '3';
            } else if (arrayContains(GKQ, chr)) {
                code = '4';
            } else if (chr == 'X' && !arrayContains(CKQ, lastChar)) {
                code = '4';
                input.addLeft('S');
                rightLength++;
            } else if (chr == 'S' || chr == 'Z') {
                code = '8';
            } else if (chr == 'C') {
                if (lastCode == '/') {
                    if (arrayContains(AHKLOQRUX, nextChar)) {
                        code = '4';
                    } else {
                        code = '8';
                    }
                } else if (arrayContains(SZ, lastChar) || !arrayContains(AHOUKQX, nextChar)) {
                    code = '8';
                } else {
                    code = '4';
                }
            } else if (arrayContains(TDX, chr)) {
                code = '8';
            } else if (chr == 'R') {
                code = '7';
            } else if (chr == 'L') {
                code = '5';
            } else if (chr == 'M' || chr == 'N') {
                code = '6';
            } else {
                code = chr;
            }
            if (code != '-' && ((lastCode != code && (code != '0' || lastCode == '/')) || code < '0' || code > '8')) {
                output.addRight(code);
            }
            lastChar = chr;
            lastCode = code;
        }
        return output.toString();
    }

    public Object encode(Object object) throws EncoderException {
        if (object instanceof String) {
            return encode((String) object);
        }
        throw new EncoderException("This method's parameter was expected to be of the type " + String.class.getName() + ". But actually it was of the type " + object.getClass().getName() + ".");
    }

    public String encode(String text) {
        return colognePhonetic(text);
    }

    public boolean isEncodeEqual(String text1, String text2) {
        return colognePhonetic(text1).equals(colognePhonetic(text2));
    }

    private String preprocess(String text) {
        char[] chrs = text.toUpperCase(Locale.GERMAN).toCharArray();
        for (int index = 0; index < chrs.length; index++) {
            if (chrs[index] > 'Z') {
                for (char[] element : PREPROCESS_MAP) {
                    if (chrs[index] == element[0]) {
                        chrs[index] = element[1];
                        break;
                    }
                }
            }
        }
        return new String(chrs);
    }
}
