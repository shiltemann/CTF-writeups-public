package com.google.zxing.common;

import com.google.zxing.FormatException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.protocol.HTTP;
import ps.hacking.zxing.common.StringUtils;

public enum CharacterSetECI {
    Cp437((String) new int[]{0, 2}, (int) new String[0]),
    ISO8859_1((String) new int[]{1, 3}, (int) new String[]{HTTP.ISO_8859_1}),
    ISO8859_2((String) 4, (int) new String[]{"ISO-8859-2"}),
    ISO8859_3((String) 5, (int) new String[]{"ISO-8859-3"}),
    ISO8859_4((String) 6, (int) new String[]{"ISO-8859-4"}),
    ISO8859_5((String) 7, (int) new String[]{"ISO-8859-5"}),
    ISO8859_6((String) 8, (int) new String[]{"ISO-8859-6"}),
    ISO8859_7((String) 9, (int) new String[]{"ISO-8859-7"}),
    ISO8859_8((String) 10, (int) new String[]{"ISO-8859-8"}),
    ISO8859_9((String) 11, (int) new String[]{"ISO-8859-9"}),
    ISO8859_10((String) 12, (int) new String[]{"ISO-8859-10"}),
    ISO8859_11((String) 13, (int) new String[]{"ISO-8859-11"}),
    ISO8859_13((String) 15, (int) new String[]{"ISO-8859-13"}),
    ISO8859_14((String) 16, (int) new String[]{"ISO-8859-14"}),
    ISO8859_15((String) 17, (int) new String[]{"ISO-8859-15"}),
    ISO8859_16((String) 18, (int) new String[]{"ISO-8859-16"}),
    SJIS((String) 20, (int) new String[]{"Shift_JIS"}),
    Cp1250((String) 21, (int) new String[]{"windows-1250"}),
    Cp1251((String) 22, (int) new String[]{"windows-1251"}),
    Cp1252((String) 23, (int) new String[]{"windows-1252"}),
    Cp1256((String) 24, (int) new String[]{"windows-1256"}),
    UnicodeBigUnmarked((String) 25, (int) new String[]{CharEncoding.UTF_16BE, "UnicodeBig"}),
    UTF8((String) 26, (int) new String[]{Hex.DEFAULT_CHARSET_NAME}),
    ASCII((String) new int[]{27, 170}, (int) new String[]{HTTP.US_ASCII}),
    Big5(28),
    GB18030((String) 29, (int) new String[]{StringUtils.GB2312, "EUC_CN", "GBK"}),
    EUC_KR((String) 30, (int) new String[]{"EUC-KR"});
    
    private static final Map<String, CharacterSetECI> NAME_TO_ECI;
    private static final Map<Integer, CharacterSetECI> VALUE_TO_ECI;
    private final String[] otherEncodingNames;
    private final int[] values;

    static {
        VALUE_TO_ECI = new HashMap();
        NAME_TO_ECI = new HashMap();
        for (CharacterSetECI eci : values()) {
            for (int value : eci.values) {
                VALUE_TO_ECI.put(Integer.valueOf(value), eci);
            }
            NAME_TO_ECI.put(eci.name(), eci);
            for (String name : eci.otherEncodingNames) {
                NAME_TO_ECI.put(name, eci);
            }
        }
    }

    private CharacterSetECI(int value) {
        this(r3, r4, new int[]{value}, new String[0]);
    }

    private CharacterSetECI(int value, String... otherEncodingNames) {
        this.values = new int[]{value};
        this.otherEncodingNames = otherEncodingNames;
    }

    private CharacterSetECI(int[] values, String... otherEncodingNames) {
        this.values = values;
        this.otherEncodingNames = otherEncodingNames;
    }

    public int getValue() {
        return this.values[0];
    }

    public static CharacterSetECI getCharacterSetECIByValue(int value) throws FormatException {
        if (value >= 0 && value < 900) {
            return (CharacterSetECI) VALUE_TO_ECI.get(Integer.valueOf(value));
        }
        throw FormatException.getFormatInstance();
    }

    public static CharacterSetECI getCharacterSetECIByName(String name) {
        return (CharacterSetECI) NAME_TO_ECI.get(name);
    }
}
