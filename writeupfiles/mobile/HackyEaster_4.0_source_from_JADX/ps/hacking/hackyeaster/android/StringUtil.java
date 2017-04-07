package ps.hacking.hackyeaster.android;

import android.support.v4.view.MotionEventCompat;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.message.TokenParser;
import org.apache.http.protocol.HTTP;
import ps.hacking.zxing.qrcode.encoder.QRCode;

public class StringUtil {
    public static String escapeJsonValue(String string, boolean removeApos) {
        if (string == null || string.length() == 0) {
            return BuildConfig.FLAVOR;
        }
        int len = string.length();
        StringBuilder sb = new StringBuilder(len + 4);
        for (int i = 0; i < len; i++) {
            char c = string.charAt(i);
            switch (c) {
                case QRCode.NUM_MASK_PATTERNS /*8*/:
                    sb.append("\\b");
                    break;
                case HTTP.HT /*9*/:
                    sb.append("\\t");
                    break;
                case HTTP.LF /*10*/:
                    sb.append("\\n");
                    break;
                case MotionEventCompat.AXIS_RX /*12*/:
                    sb.append("\\f");
                    break;
                case HTTP.CR /*13*/:
                    sb.append("\\r");
                    break;
                case MotionEventCompat.AXIS_GENERIC_3 /*34*/:
                case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
                case '\\':
                    sb.append(TokenParser.ESCAPE);
                    sb.append(c);
                    break;
                default:
                    if (c >= TokenParser.SP) {
                        if (c != '\'' || !removeApos) {
                            sb.append(c);
                            break;
                        }
                        break;
                    }
                    String t = "000" + Integer.toHexString(c);
                    sb.append("\\u" + t.substring(t.length() - 4));
                    break;
                    break;
            }
        }
        return sb.toString();
    }

    public static String m10e(String s) {
        return new String(Base64.encodeBase64(rot13(new String(Hex.encodeHex(s.getBytes()))).getBytes()));
    }

    public static String m9d(String s) {
        try {
            return new String(Hex.decodeHex(rot13(new String(Base64.decodeBase64(s.getBytes()))).toCharArray()));
        } catch (DecoderException e) {
            return BuildConfig.FLAVOR;
        }
    }

    private static String rot13(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'm') {
                c = (char) (c + 13);
            } else if (c >= 'A' && c <= 'M') {
                c = (char) (c + 13);
            } else if (c >= 'n' && c <= 'z') {
                c = (char) (c - 13);
            } else if (c >= 'N' && c <= 'Z') {
                c = (char) (c - 13);
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
