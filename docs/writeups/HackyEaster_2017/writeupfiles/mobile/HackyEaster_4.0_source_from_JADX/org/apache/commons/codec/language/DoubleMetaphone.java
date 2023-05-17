package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.BaseNCodec;
import org.apache.http.message.TokenParser;

public class DoubleMetaphone implements StringEncoder {
    private static final String[] ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER;
    private static final String[] L_R_N_M_B_H_F_V_W_SPACE;
    private static final String[] L_T_K_S_N_M_B_Z;
    private static final String[] SILENT_START;
    private static final String VOWELS = "AEIOUY";
    private int maxCodeLen;

    public class DoubleMetaphoneResult {
        private final StringBuilder alternate;
        private final int maxLength;
        private final StringBuilder primary;

        public DoubleMetaphoneResult(int maxLength) {
            this.primary = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
            this.alternate = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
            this.maxLength = maxLength;
        }

        public void append(char value) {
            appendPrimary(value);
            appendAlternate(value);
        }

        public void append(char primary, char alternate) {
            appendPrimary(primary);
            appendAlternate(alternate);
        }

        public void appendPrimary(char value) {
            if (this.primary.length() < this.maxLength) {
                this.primary.append(value);
            }
        }

        public void appendAlternate(char value) {
            if (this.alternate.length() < this.maxLength) {
                this.alternate.append(value);
            }
        }

        public void append(String value) {
            appendPrimary(value);
            appendAlternate(value);
        }

        public void append(String primary, String alternate) {
            appendPrimary(primary);
            appendAlternate(alternate);
        }

        public void appendPrimary(String value) {
            int addChars = this.maxLength - this.primary.length();
            if (value.length() <= addChars) {
                this.primary.append(value);
            } else {
                this.primary.append(value.substring(0, addChars));
            }
        }

        public void appendAlternate(String value) {
            int addChars = this.maxLength - this.alternate.length();
            if (value.length() <= addChars) {
                this.alternate.append(value);
            } else {
                this.alternate.append(value.substring(0, addChars));
            }
        }

        public String getPrimary() {
            return this.primary.toString();
        }

        public String getAlternate() {
            return this.alternate.toString();
        }

        public boolean isComplete() {
            return this.primary.length() >= this.maxLength && this.alternate.length() >= this.maxLength;
        }
    }

    static {
        SILENT_START = new String[]{"GN", "KN", "PN", "WR", "PS"};
        L_R_N_M_B_H_F_V_W_SPACE = new String[]{"L", "R", "N", "M", "B", "H", "F", "V", "W", " "};
        ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER = new String[]{"ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER"};
        L_T_K_S_N_M_B_Z = new String[]{"L", "T", "K", "S", "N", "M", "B", "Z"};
    }

    public DoubleMetaphone() {
        this.maxCodeLen = 4;
    }

    public String doubleMetaphone(String value) {
        return doubleMetaphone(value, false);
    }

    public String doubleMetaphone(String value, boolean alternate) {
        value = cleanInput(value);
        if (value == null) {
            return null;
        }
        boolean slavoGermanic = isSlavoGermanic(value);
        int index = isSilentStart(value) ? 1 : 0;
        DoubleMetaphoneResult result = new DoubleMetaphoneResult(getMaxCodeLen());
        while (!result.isComplete() && index <= value.length() - 1) {
            switch (value.charAt(index)) {
                case 'A':
                case 'E':
                case 'I':
                case 'O':
                case 'U':
                case 'Y':
                    index = handleAEIOUY(result, index);
                    break;
                case 'B':
                    result.append('P');
                    index = charAt(value, index + 1) == 'B' ? index + 2 : index + 1;
                    break;
                case 'C':
                    index = handleC(value, result, index);
                    break;
                case 'D':
                    index = handleD(value, result, index);
                    break;
                case 'F':
                    result.append('F');
                    index = charAt(value, index + 1) == 'F' ? index + 2 : index + 1;
                    break;
                case 'G':
                    index = handleG(value, result, index, slavoGermanic);
                    break;
                case 'H':
                    index = handleH(value, result, index);
                    break;
                case 'J':
                    index = handleJ(value, result, index, slavoGermanic);
                    break;
                case 'K':
                    result.append('K');
                    index = charAt(value, index + 1) == 'K' ? index + 2 : index + 1;
                    break;
                case BaseNCodec.MIME_CHUNK_SIZE /*76*/:
                    index = handleL(value, result, index);
                    break;
                case 'M':
                    result.append('M');
                    index = conditionM0(value, index) ? index + 2 : index + 1;
                    break;
                case 'N':
                    result.append('N');
                    index = charAt(value, index + 1) == 'N' ? index + 2 : index + 1;
                    break;
                case 'P':
                    index = handleP(value, result, index);
                    break;
                case 'Q':
                    result.append('K');
                    index = charAt(value, index + 1) == 'Q' ? index + 2 : index + 1;
                    break;
                case 'R':
                    index = handleR(value, result, index, slavoGermanic);
                    break;
                case 'S':
                    index = handleS(value, result, index, slavoGermanic);
                    break;
                case 'T':
                    index = handleT(value, result, index);
                    break;
                case 'V':
                    result.append('F');
                    index = charAt(value, index + 1) == 'V' ? index + 2 : index + 1;
                    break;
                case 'W':
                    index = handleW(value, result, index);
                    break;
                case 'X':
                    index = handleX(value, result, index);
                    break;
                case 'Z':
                    index = handleZ(value, result, index, slavoGermanic);
                    break;
                case '\u00c7':
                    result.append('S');
                    index++;
                    break;
                case '\u00d1':
                    result.append('N');
                    index++;
                    break;
                default:
                    index++;
                    break;
            }
        }
        return alternate ? result.getAlternate() : result.getPrimary();
    }

    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return doubleMetaphone((String) obj);
        }
        throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
    }

    public String encode(String value) {
        return doubleMetaphone(value);
    }

    public boolean isDoubleMetaphoneEqual(String value1, String value2) {
        return isDoubleMetaphoneEqual(value1, value2, false);
    }

    public boolean isDoubleMetaphoneEqual(String value1, String value2, boolean alternate) {
        return doubleMetaphone(value1, alternate).equals(doubleMetaphone(value2, alternate));
    }

    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }

    public void setMaxCodeLen(int maxCodeLen) {
        this.maxCodeLen = maxCodeLen;
    }

    private int handleAEIOUY(DoubleMetaphoneResult result, int index) {
        if (index == 0) {
            result.append('A');
        }
        return index + 1;
    }

    private int handleC(String value, DoubleMetaphoneResult result, int index) {
        if (conditionC0(value, index)) {
            result.append('K');
            index += 2;
        } else {
            if (index == 0) {
                if (contains(value, index, 6, "CAESAR")) {
                    result.append('S');
                    index += 2;
                }
            }
            if (contains(value, index, 2, "CH")) {
                index = handleCH(value, result, index);
            } else {
                if (contains(value, index, 2, "CZ")) {
                    if (!contains(value, index - 2, 4, "WICZ")) {
                        result.append('S', 'X');
                        index += 2;
                    }
                }
                if (contains(value, index + 1, 3, "CIA")) {
                    result.append('X');
                    index += 3;
                } else {
                    if (contains(value, index, 2, "CC") && (index != 1 || charAt(value, 0) != 'M')) {
                        return handleCC(value, result, index);
                    }
                    if (contains(value, index, 2, "CK", "CG", "CQ")) {
                        result.append('K');
                        index += 2;
                    } else {
                        if (contains(value, index, 2, "CI", "CE", "CY")) {
                            if (contains(value, index, 3, "CIO", "CIE", "CIA")) {
                                result.append('S', 'X');
                            } else {
                                result.append('S');
                            }
                            index += 2;
                        } else {
                            result.append('K');
                            if (contains(value, index + 1, 2, " C", " Q", " G")) {
                                index += 3;
                            } else {
                                if (contains(value, index + 1, 1, "C", "K", "Q")) {
                                    if (!contains(value, index + 1, 2, "CE", "CI")) {
                                        index += 2;
                                    }
                                }
                                index++;
                            }
                        }
                    }
                }
            }
        }
        return index;
    }

    private int handleCC(String value, DoubleMetaphoneResult result, int index) {
        if (contains(value, index + 2, 1, "I", "E", "H")) {
            if (!contains(value, index + 2, 2, "HU")) {
                if (!(index == 1 && charAt(value, index - 1) == 'A')) {
                    if (!contains(value, index - 1, 5, "UCCEE", "UCCES")) {
                        result.append('X');
                        return index + 3;
                    }
                }
                result.append("KS");
                return index + 3;
            }
        }
        result.append('K');
        return index + 2;
    }

    private int handleCH(String value, DoubleMetaphoneResult result, int index) {
        if (index > 0) {
            if (contains(value, index, 4, "CHAE")) {
                result.append('K', 'X');
                return index + 2;
            }
        }
        if (conditionCH0(value, index)) {
            result.append('K');
            return index + 2;
        } else if (conditionCH1(value, index)) {
            result.append('K');
            return index + 2;
        } else {
            if (index > 0) {
                if (contains(value, 0, 2, "MC")) {
                    result.append('K');
                } else {
                    result.append('X', 'K');
                }
            } else {
                result.append('X');
            }
            return index + 2;
        }
    }

    private int handleD(String value, DoubleMetaphoneResult result, int index) {
        if (contains(value, index, 2, "DG")) {
            if (contains(value, index + 2, 1, "I", "E", "Y")) {
                result.append('J');
                return index + 3;
            }
            result.append("TK");
            return index + 2;
        }
        if (contains(value, index, 2, "DT", "DD")) {
            result.append('T');
            return index + 2;
        }
        result.append('T');
        return index + 1;
    }

    private int handleG(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic) {
        if (charAt(value, index + 1) == 'H') {
            return handleGH(value, result, index);
        }
        if (charAt(value, index + 1) == 'N') {
            if (index == 1 && isVowel(charAt(value, 0)) && !slavoGermanic) {
                result.append("KN", "N");
            } else {
                if (contains(value, index + 2, 2, "EY") || charAt(value, index + 1) == 'Y' || slavoGermanic) {
                    result.append("KN");
                } else {
                    result.append("N", "KN");
                }
            }
            return index + 2;
        }
        if (contains(value, index + 1, 2, "LI") && !slavoGermanic) {
            result.append("KL", "L");
            return index + 2;
        } else if (index == 0 && (charAt(value, index + 1) == 'Y' || contains(value, index + 1, 2, ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER))) {
            result.append('K', 'J');
            return index + 2;
        } else {
            if (contains(value, index + 1, 2, "ER") || charAt(value, index + 1) == 'Y') {
                if (!contains(value, 0, 6, "DANGER", "RANGER", "MANGER")) {
                    if (!contains(value, index - 1, 1, "E", "I")) {
                        if (!contains(value, index - 1, 3, "RGY", "OGY")) {
                            result.append('K', 'J');
                            return index + 2;
                        }
                    }
                }
            }
            if (!contains(value, index + 1, 1, "E", "I", "Y")) {
                if (!contains(value, index - 1, 4, "AGGI", "OGGI")) {
                    if (charAt(value, index + 1) == 'G') {
                        index += 2;
                        result.append('K');
                        return index;
                    }
                    index++;
                    result.append('K');
                    return index;
                }
            }
            if (!contains(value, 0, 4, "VAN ", "VON ")) {
                if (!contains(value, 0, 3, "SCH")) {
                    if (!contains(value, index + 1, 2, "ET")) {
                        if (contains(value, index + 1, 3, "IER")) {
                            result.append('J');
                        } else {
                            result.append('J', 'K');
                        }
                        return index + 2;
                    }
                }
            }
            result.append('K');
            return index + 2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int handleGH(java.lang.String r9, org.apache.commons.codec.language.DoubleMetaphone.DoubleMetaphoneResult r10, int r11) {
        /*
        r8 = this;
        r7 = 75;
        r6 = 3;
        r5 = 0;
        r3 = 2;
        r4 = 1;
        if (r11 <= 0) goto L_0x001a;
    L_0x0008:
        r0 = r11 + -1;
        r0 = r8.charAt(r9, r0);
        r0 = r8.isVowel(r0);
        if (r0 != 0) goto L_0x001a;
    L_0x0014:
        r10.append(r7);
        r11 = r11 + 2;
    L_0x0019:
        return r11;
    L_0x001a:
        if (r11 != 0) goto L_0x0032;
    L_0x001c:
        r0 = r11 + 2;
        r0 = r8.charAt(r9, r0);
        r1 = 73;
        if (r0 != r1) goto L_0x002e;
    L_0x0026:
        r0 = 74;
        r10.append(r0);
    L_0x002b:
        r11 = r11 + 2;
        goto L_0x0019;
    L_0x002e:
        r10.append(r7);
        goto L_0x002b;
    L_0x0032:
        if (r11 <= r4) goto L_0x004a;
    L_0x0034:
        r0 = r11 + -2;
        r1 = new java.lang.String[r6];
        r2 = "B";
        r1[r5] = r2;
        r2 = "H";
        r1[r4] = r2;
        r2 = "D";
        r1[r3] = r2;
        r0 = contains(r9, r0, r4, r1);
        if (r0 != 0) goto L_0x0076;
    L_0x004a:
        if (r11 <= r3) goto L_0x0062;
    L_0x004c:
        r0 = r11 + -3;
        r1 = new java.lang.String[r6];
        r2 = "B";
        r1[r5] = r2;
        r2 = "H";
        r1[r4] = r2;
        r2 = "D";
        r1[r3] = r2;
        r0 = contains(r9, r0, r4, r1);
        if (r0 != 0) goto L_0x0076;
    L_0x0062:
        if (r11 <= r6) goto L_0x0079;
    L_0x0064:
        r0 = r11 + -4;
        r1 = new java.lang.String[r3];
        r2 = "B";
        r1[r5] = r2;
        r2 = "H";
        r1[r4] = r2;
        r0 = contains(r9, r0, r4, r1);
        if (r0 == 0) goto L_0x0079;
    L_0x0076:
        r11 = r11 + 2;
        goto L_0x0019;
    L_0x0079:
        if (r11 <= r3) goto L_0x00ae;
    L_0x007b:
        r0 = r11 + -1;
        r0 = r8.charAt(r9, r0);
        r1 = 85;
        if (r0 != r1) goto L_0x00ae;
    L_0x0085:
        r0 = r11 + -3;
        r1 = 5;
        r1 = new java.lang.String[r1];
        r2 = "C";
        r1[r5] = r2;
        r2 = "G";
        r1[r4] = r2;
        r2 = "L";
        r1[r3] = r2;
        r2 = "R";
        r1[r6] = r2;
        r2 = 4;
        r3 = "T";
        r1[r2] = r3;
        r0 = contains(r9, r0, r4, r1);
        if (r0 == 0) goto L_0x00ae;
    L_0x00a5:
        r0 = 70;
        r10.append(r0);
    L_0x00aa:
        r11 = r11 + 2;
        goto L_0x0019;
    L_0x00ae:
        if (r11 <= 0) goto L_0x00aa;
    L_0x00b0:
        r0 = r11 + -1;
        r0 = r8.charAt(r9, r0);
        r1 = 73;
        if (r0 == r1) goto L_0x00aa;
    L_0x00ba:
        r10.append(r7);
        goto L_0x00aa;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.DoubleMetaphone.handleGH(java.lang.String, org.apache.commons.codec.language.DoubleMetaphone$DoubleMetaphoneResult, int):int");
    }

    private int handleH(String value, DoubleMetaphoneResult result, int index) {
        if ((index != 0 && !isVowel(charAt(value, index - 1))) || !isVowel(charAt(value, index + 1))) {
            return index + 1;
        }
        result.append('H');
        return index + 2;
    }

    private int handleJ(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic) {
        if (!contains(value, index, 4, "JOSE")) {
            if (!contains(value, 0, 4, "SAN ")) {
                if (index == 0) {
                    if (!contains(value, index, 4, "JOSE")) {
                        result.append('J', 'A');
                        if (charAt(value, index + 1) == 'J') {
                            return index + 2;
                        }
                        return index + 1;
                    }
                }
                if (isVowel(charAt(value, index - 1)) && !slavoGermanic && (charAt(value, index + 1) == 'A' || charAt(value, index + 1) == 'O')) {
                    result.append('J', 'H');
                    if (charAt(value, index + 1) == 'J') {
                        return index + 2;
                    }
                    return index + 1;
                }
                if (index == value.length() - 1) {
                    result.append('J', (char) TokenParser.SP);
                } else if (!contains(value, index + 1, 1, L_T_K_S_N_M_B_Z)) {
                    if (!contains(value, index - 1, 1, "S", "K", "L")) {
                        result.append('J');
                    }
                }
                if (charAt(value, index + 1) == 'J') {
                    return index + 1;
                }
                return index + 2;
            }
        }
        if (!((index == 0 && charAt(value, index + 4) == TokenParser.SP) || value.length() == 4)) {
            if (!contains(value, 0, 4, "SAN ")) {
                result.append('J', 'H');
                return index + 1;
            }
        }
        result.append('H');
        return index + 1;
    }

    private int handleL(String value, DoubleMetaphoneResult result, int index) {
        if (charAt(value, index + 1) == 'L') {
            if (conditionL0(value, index)) {
                result.appendPrimary('L');
            } else {
                result.append('L');
            }
            return index + 2;
        }
        index++;
        result.append('L');
        return index;
    }

    private int handleP(String value, DoubleMetaphoneResult result, int index) {
        if (charAt(value, index + 1) == 'H') {
            result.append('F');
            return index + 2;
        }
        result.append('P');
        return contains(value, index + 1, 1, "P", "B") ? index + 2 : index + 1;
    }

    private int handleR(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic) {
        if (index == value.length() - 1 && !slavoGermanic) {
            if (contains(value, index - 2, 2, "IE")) {
                if (!contains(value, index - 4, 2, "ME", "MA")) {
                    result.appendAlternate('R');
                    return charAt(value, index + 1) != 'R' ? index + 2 : index + 1;
                }
            }
        }
        result.append('R');
        if (charAt(value, index + 1) != 'R') {
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int handleS(java.lang.String r10, org.apache.commons.codec.language.DoubleMetaphone.DoubleMetaphoneResult r11, int r12, boolean r13) {
        /*
        r9 = this;
        r8 = 3;
        r7 = 83;
        r6 = 2;
        r5 = 0;
        r4 = 1;
        r0 = r12 + -1;
        r1 = new java.lang.String[r6];
        r2 = "ISL";
        r1[r5] = r2;
        r2 = "YSL";
        r1[r4] = r2;
        r0 = contains(r10, r0, r8, r1);
        if (r0 == 0) goto L_0x001b;
    L_0x0018:
        r12 = r12 + 1;
    L_0x001a:
        return r12;
    L_0x001b:
        if (r12 != 0) goto L_0x0032;
    L_0x001d:
        r0 = 5;
        r1 = new java.lang.String[r4];
        r2 = "SUGAR";
        r1[r5] = r2;
        r0 = contains(r10, r12, r0, r1);
        if (r0 == 0) goto L_0x0032;
    L_0x002a:
        r0 = 88;
        r11.append(r0, r7);
        r12 = r12 + 1;
        goto L_0x001a;
    L_0x0032:
        r0 = new java.lang.String[r4];
        r1 = "SH";
        r0[r5] = r1;
        r0 = contains(r10, r12, r6, r0);
        if (r0 == 0) goto L_0x0066;
    L_0x003e:
        r0 = r12 + 1;
        r1 = 4;
        r2 = 4;
        r2 = new java.lang.String[r2];
        r3 = "HEIM";
        r2[r5] = r3;
        r3 = "HOEK";
        r2[r4] = r3;
        r3 = "HOLM";
        r2[r6] = r3;
        r3 = "HOLZ";
        r2[r8] = r3;
        r0 = contains(r10, r0, r1, r2);
        if (r0 == 0) goto L_0x0060;
    L_0x005a:
        r11.append(r7);
    L_0x005d:
        r12 = r12 + 2;
        goto L_0x001a;
    L_0x0060:
        r0 = 88;
        r11.append(r0);
        goto L_0x005d;
    L_0x0066:
        r0 = new java.lang.String[r6];
        r1 = "SIO";
        r0[r5] = r1;
        r1 = "SIA";
        r0[r4] = r1;
        r0 = contains(r10, r12, r8, r0);
        if (r0 != 0) goto L_0x0083;
    L_0x0076:
        r0 = 4;
        r1 = new java.lang.String[r4];
        r2 = "SIAN";
        r1[r5] = r2;
        r0 = contains(r10, r12, r0, r1);
        if (r0 == 0) goto L_0x0091;
    L_0x0083:
        if (r13 == 0) goto L_0x008b;
    L_0x0085:
        r11.append(r7);
    L_0x0088:
        r12 = r12 + 3;
        goto L_0x001a;
    L_0x008b:
        r0 = 88;
        r11.append(r7, r0);
        goto L_0x0088;
    L_0x0091:
        if (r12 != 0) goto L_0x00ae;
    L_0x0093:
        r0 = r12 + 1;
        r1 = 4;
        r1 = new java.lang.String[r1];
        r2 = "M";
        r1[r5] = r2;
        r2 = "N";
        r1[r4] = r2;
        r2 = "L";
        r1[r6] = r2;
        r2 = "W";
        r1[r8] = r2;
        r0 = contains(r10, r0, r4, r1);
        if (r0 != 0) goto L_0x00bc;
    L_0x00ae:
        r0 = r12 + 1;
        r1 = new java.lang.String[r4];
        r2 = "Z";
        r1[r5] = r2;
        r0 = contains(r10, r0, r4, r1);
        if (r0 == 0) goto L_0x00d6;
    L_0x00bc:
        r0 = 88;
        r11.append(r7, r0);
        r0 = r12 + 1;
        r1 = new java.lang.String[r4];
        r2 = "Z";
        r1[r5] = r2;
        r0 = contains(r10, r0, r4, r1);
        if (r0 == 0) goto L_0x00d3;
    L_0x00cf:
        r12 = r12 + 2;
    L_0x00d1:
        goto L_0x001a;
    L_0x00d3:
        r12 = r12 + 1;
        goto L_0x00d1;
    L_0x00d6:
        r0 = new java.lang.String[r4];
        r1 = "SC";
        r0[r5] = r1;
        r0 = contains(r10, r12, r6, r0);
        if (r0 == 0) goto L_0x00e8;
    L_0x00e2:
        r12 = r9.handleSC(r10, r11, r12);
        goto L_0x001a;
    L_0x00e8:
        r0 = r10.length();
        r0 = r0 + -1;
        if (r12 != r0) goto L_0x011b;
    L_0x00f0:
        r0 = r12 + -2;
        r1 = new java.lang.String[r6];
        r2 = "AI";
        r1[r5] = r2;
        r2 = "OI";
        r1[r4] = r2;
        r0 = contains(r10, r0, r6, r1);
        if (r0 == 0) goto L_0x011b;
    L_0x0102:
        r11.appendAlternate(r7);
    L_0x0105:
        r0 = r12 + 1;
        r1 = new java.lang.String[r6];
        r2 = "S";
        r1[r5] = r2;
        r2 = "Z";
        r1[r4] = r2;
        r0 = contains(r10, r0, r4, r1);
        if (r0 == 0) goto L_0x011f;
    L_0x0117:
        r12 = r12 + 2;
    L_0x0119:
        goto L_0x001a;
    L_0x011b:
        r11.append(r7);
        goto L_0x0105;
    L_0x011f:
        r12 = r12 + 1;
        goto L_0x0119;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.DoubleMetaphone.handleS(java.lang.String, org.apache.commons.codec.language.DoubleMetaphone$DoubleMetaphoneResult, int, boolean):int");
    }

    private int handleSC(String value, DoubleMetaphoneResult result, int index) {
        if (charAt(value, index + 2) == 'H') {
            if (contains(value, index + 3, 2, "OO", "ER", "EN", "UY", "ED", "EM")) {
                if (contains(value, index + 3, 2, "ER", "EN")) {
                    result.append("X", "SK");
                } else {
                    result.append("SK");
                }
            } else if (index != 0 || isVowel(charAt(value, 3)) || charAt(value, 3) == 'W') {
                result.append('X');
            } else {
                result.append('X', 'S');
            }
        } else {
            if (contains(value, index + 2, 1, "I", "E", "Y")) {
                result.append('S');
            } else {
                result.append("SK");
            }
        }
        return index + 3;
    }

    private int handleT(String value, DoubleMetaphoneResult result, int index) {
        if (contains(value, index, 4, "TION")) {
            result.append('X');
            return index + 3;
        }
        if (contains(value, index, 3, "TIA", "TCH")) {
            result.append('X');
            return index + 3;
        }
        if (!contains(value, index, 2, "TH")) {
            if (!contains(value, index, 3, "TTH")) {
                result.append('T');
                return contains(value, index + 1, 1, "T", "D") ? index + 2 : index + 1;
            }
        }
        if (!contains(value, index + 2, 2, "OM", "AM")) {
            if (!contains(value, 0, 4, "VAN ", "VON ")) {
                if (!contains(value, 0, 3, "SCH")) {
                    result.append('0', 'T');
                    return index + 2;
                }
            }
        }
        result.append('T');
        return index + 2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int handleW(java.lang.String r10, org.apache.commons.codec.language.DoubleMetaphone.DoubleMetaphoneResult r11, int r12) {
        /*
        r9 = this;
        r8 = 4;
        r7 = 3;
        r6 = 2;
        r5 = 1;
        r4 = 0;
        r0 = new java.lang.String[r5];
        r1 = "WR";
        r0[r4] = r1;
        r0 = contains(r10, r12, r6, r0);
        if (r0 == 0) goto L_0x0019;
    L_0x0011:
        r0 = 82;
        r11.append(r0);
        r12 = r12 + 2;
    L_0x0018:
        return r12;
    L_0x0019:
        if (r12 != 0) goto L_0x004f;
    L_0x001b:
        r0 = r12 + 1;
        r0 = r9.charAt(r10, r0);
        r0 = r9.isVowel(r0);
        if (r0 != 0) goto L_0x0033;
    L_0x0027:
        r0 = new java.lang.String[r5];
        r1 = "WH";
        r0[r4] = r1;
        r0 = contains(r10, r12, r6, r0);
        if (r0 == 0) goto L_0x004f;
    L_0x0033:
        r0 = r12 + 1;
        r0 = r9.charAt(r10, r0);
        r0 = r9.isVowel(r0);
        if (r0 == 0) goto L_0x0049;
    L_0x003f:
        r0 = 65;
        r1 = 70;
        r11.append(r0, r1);
    L_0x0046:
        r12 = r12 + 1;
        goto L_0x0018;
    L_0x0049:
        r0 = 65;
        r11.append(r0);
        goto L_0x0046;
    L_0x004f:
        r0 = r10.length();
        r0 = r0 + -1;
        if (r12 != r0) goto L_0x0063;
    L_0x0057:
        r0 = r12 + -1;
        r0 = r9.charAt(r10, r0);
        r0 = r9.isVowel(r0);
        if (r0 != 0) goto L_0x008a;
    L_0x0063:
        r0 = r12 + -1;
        r1 = 5;
        r2 = new java.lang.String[r8];
        r3 = "EWSKI";
        r2[r4] = r3;
        r3 = "EWSKY";
        r2[r5] = r3;
        r3 = "OWSKI";
        r2[r6] = r3;
        r3 = "OWSKY";
        r2[r7] = r3;
        r0 = contains(r10, r0, r1, r2);
        if (r0 != 0) goto L_0x008a;
    L_0x007e:
        r0 = new java.lang.String[r5];
        r1 = "SCH";
        r0[r4] = r1;
        r0 = contains(r10, r4, r7, r0);
        if (r0 == 0) goto L_0x0092;
    L_0x008a:
        r0 = 70;
        r11.appendAlternate(r0);
        r12 = r12 + 1;
        goto L_0x0018;
    L_0x0092:
        r0 = new java.lang.String[r6];
        r1 = "WICZ";
        r0[r4] = r1;
        r1 = "WITZ";
        r0[r5] = r1;
        r0 = contains(r10, r12, r8, r0);
        if (r0 == 0) goto L_0x00ad;
    L_0x00a2:
        r0 = "TS";
        r1 = "FX";
        r11.append(r0, r1);
        r12 = r12 + 4;
        goto L_0x0018;
    L_0x00ad:
        r12 = r12 + 1;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.DoubleMetaphone.handleW(java.lang.String, org.apache.commons.codec.language.DoubleMetaphone$DoubleMetaphoneResult, int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int handleX(java.lang.String r8, org.apache.commons.codec.language.DoubleMetaphone.DoubleMetaphoneResult r9, int r10) {
        /*
        r7 = this;
        r6 = 0;
        r5 = 2;
        r4 = 1;
        if (r10 != 0) goto L_0x000d;
    L_0x0005:
        r0 = 83;
        r9.append(r0);
        r10 = r10 + 1;
    L_0x000c:
        return r10;
    L_0x000d:
        r0 = r8.length();
        r0 = r0 + -1;
        if (r10 != r0) goto L_0x003a;
    L_0x0015:
        r0 = r10 + -3;
        r1 = 3;
        r2 = new java.lang.String[r5];
        r3 = "IAU";
        r2[r6] = r3;
        r3 = "EAU";
        r2[r4] = r3;
        r0 = contains(r8, r0, r1, r2);
        if (r0 != 0) goto L_0x003f;
    L_0x0028:
        r0 = r10 + -2;
        r1 = new java.lang.String[r5];
        r2 = "AU";
        r1[r6] = r2;
        r2 = "OU";
        r1[r4] = r2;
        r0 = contains(r8, r0, r5, r1);
        if (r0 != 0) goto L_0x003f;
    L_0x003a:
        r0 = "KS";
        r9.append(r0);
    L_0x003f:
        r0 = r10 + 1;
        r1 = new java.lang.String[r5];
        r2 = "C";
        r1[r6] = r2;
        r2 = "X";
        r1[r4] = r2;
        r0 = contains(r8, r0, r4, r1);
        if (r0 == 0) goto L_0x0054;
    L_0x0051:
        r10 = r10 + 2;
    L_0x0053:
        goto L_0x000c;
    L_0x0054:
        r10 = r10 + 1;
        goto L_0x0053;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.DoubleMetaphone.handleX(java.lang.String, org.apache.commons.codec.language.DoubleMetaphone$DoubleMetaphoneResult, int):int");
    }

    private int handleZ(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic) {
        if (charAt(value, index + 1) == 'H') {
            result.append('J');
            return index + 2;
        }
        if (contains(value, index + 1, 2, "ZO", "ZI", "ZA") || (slavoGermanic && index > 0 && charAt(value, index - 1) != 'T')) {
            result.append("S", "TS");
        } else {
            result.append('S');
        }
        return charAt(value, index + 1) == 'Z' ? index + 2 : index + 1;
    }

    private boolean conditionC0(String value, int index) {
        if (contains(value, index, 4, "CHIA")) {
            return true;
        }
        if (index <= 1 || isVowel(charAt(value, index - 2))) {
            return false;
        }
        if (!contains(value, index - 1, 3, "ACH")) {
            return false;
        }
        char c = charAt(value, index + 2);
        if (c == 'I' || c == 'E') {
            if (!contains(value, index - 2, 6, "BACHER", "MACHER")) {
                return false;
            }
        }
        return true;
    }

    private boolean conditionCH0(String value, int index) {
        if (index != 0) {
            return false;
        }
        if (!contains(value, index + 1, 5, "HARAC", "HARIS")) {
            if (!contains(value, index + 1, 3, "HOR", "HYM", "HIA", "HEM")) {
                return false;
            }
        }
        if (contains(value, 0, 5, "CHORE")) {
            return false;
        }
        return true;
    }

    private boolean conditionCH1(String value, int index) {
        if (!contains(value, 0, 4, "VAN ", "VON ")) {
            if (!contains(value, 0, 3, "SCH")) {
                if (!contains(value, index - 2, 6, "ORCHES", "ARCHIT", "ORCHID")) {
                    if (!contains(value, index + 2, 1, "T", "S")) {
                        if (!contains(value, index - 1, 1, "A", "O", "U", "E") && index != 0) {
                            return false;
                        }
                        if (!(contains(value, index + 2, 1, L_R_N_M_B_H_F_V_W_SPACE) || index + 1 == value.length() - 1)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean conditionL0(java.lang.String r8, int r9) {
        /*
        r7 = this;
        r6 = 4;
        r5 = 2;
        r1 = 0;
        r0 = 1;
        r2 = r8.length();
        r2 = r2 + -3;
        if (r9 != r2) goto L_0x0024;
    L_0x000c:
        r2 = r9 + -1;
        r3 = 3;
        r3 = new java.lang.String[r3];
        r4 = "ILLO";
        r3[r1] = r4;
        r4 = "ILLA";
        r3[r0] = r4;
        r4 = "ALLE";
        r3[r5] = r4;
        r2 = contains(r8, r2, r6, r3);
        if (r2 == 0) goto L_0x0024;
    L_0x0023:
        return r0;
    L_0x0024:
        r2 = r8.length();
        r2 = r2 + -2;
        r3 = new java.lang.String[r5];
        r4 = "AS";
        r3[r1] = r4;
        r4 = "OS";
        r3[r0] = r4;
        r2 = contains(r8, r2, r5, r3);
        if (r2 != 0) goto L_0x0050;
    L_0x003a:
        r2 = r8.length();
        r2 = r2 + -1;
        r3 = new java.lang.String[r5];
        r4 = "A";
        r3[r1] = r4;
        r4 = "O";
        r3[r0] = r4;
        r2 = contains(r8, r2, r0, r3);
        if (r2 == 0) goto L_0x005e;
    L_0x0050:
        r2 = r9 + -1;
        r3 = new java.lang.String[r0];
        r4 = "ALLE";
        r3[r1] = r4;
        r2 = contains(r8, r2, r6, r3);
        if (r2 != 0) goto L_0x0023;
    L_0x005e:
        r0 = r1;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.DoubleMetaphone.conditionL0(java.lang.String, int):boolean");
    }

    private boolean conditionM0(String value, int index) {
        if (charAt(value, index + 1) == 'M') {
            return true;
        }
        if (contains(value, index - 1, 3, "UMB")) {
            if (index + 1 == value.length() - 1) {
                return true;
            }
            if (contains(value, index + 2, 2, "ER")) {
                return true;
            }
        }
        return false;
    }

    private boolean isSlavoGermanic(String value) {
        return value.indexOf(87) > -1 || value.indexOf(75) > -1 || value.indexOf("CZ") > -1 || value.indexOf("WITZ") > -1;
    }

    private boolean isVowel(char ch) {
        return VOWELS.indexOf(ch) != -1;
    }

    private boolean isSilentStart(String value) {
        for (String element : SILENT_START) {
            if (value.startsWith(element)) {
                return true;
            }
        }
        return false;
    }

    private String cleanInput(String input) {
        if (input == null) {
            return null;
        }
        input = input.trim();
        if (input.length() != 0) {
            return input.toUpperCase(Locale.ENGLISH);
        }
        return null;
    }

    protected char charAt(String value, int index) {
        if (index < 0 || index >= value.length()) {
            return '\u0000';
        }
        return value.charAt(index);
    }

    protected static boolean contains(String value, int start, int length, String... criteria) {
        if (start < 0 || start + length > value.length()) {
            return false;
        }
        String target = value.substring(start, start + length);
        for (String element : criteria) {
            if (target.equals(element)) {
                return true;
            }
        }
        return false;
    }
}
