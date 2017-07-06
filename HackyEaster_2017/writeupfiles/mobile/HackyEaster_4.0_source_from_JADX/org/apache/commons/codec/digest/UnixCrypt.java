package org.apache.commons.codec.digest;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.Random;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpStatus;
import ps.hacking.hackyeaster.android.BuildConfig;

public class UnixCrypt {
    private static final int[] CON_SALT;
    private static final int[] COV2CHAR;
    private static final char[] SALT_CHARS;
    private static final boolean[] SHIFT2;
    private static final int[][] SKB;
    private static final int[][] SPTRANS;

    static {
        CON_SALT = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 0, 0, 0, 0, 0};
        COV2CHAR = new int[]{46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, HttpStatus.SC_SWITCHING_PROTOCOLS, HttpStatus.SC_PROCESSING, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
        SALT_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789./".toCharArray();
        SHIFT2 = new boolean[]{false, false, true, true, true, true, true, true, false, true, true, true, true, true, true, false};
        SKB = new int[][]{new int[]{0, 16, 536870912, 536870928, AccessibilityNodeInfoCompat.ACTION_CUT, 65552, 536936448, 536936464, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, 2064, 536872960, 536872976, 67584, 67600, 536938496, 536938512, 32, 48, 536870944, 536870960, 65568, 65584, 536936480, 536936496, 2080, 2096, 536872992, 536873008, 67616, 67632, 536938528, 536938544, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 524304, 537395200, 537395216, 589824, 589840, 537460736, 537460752, 526336, 526352, 537397248, 537397264, 591872, 591888, 537462784, 537462800, 524320, 524336, 537395232, 537395248, 589856, 589872, 537460768, 537460784, 526368, 526384, 537397280, 537397296, 591904, 591920, 537462816, 537462832}, new int[]{0, 33554432, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 33562624, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, 35651584, 2105344, 35659776, 4, 33554436, 8196, 33562628, 2097156, 35651588, 2105348, 35659780, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, 33555456, 9216, 33563648, 2098176, 35652608, 2106368, 35660800, 1028, 33555460, 9220, 33563652, 2098180, 35652612, 2106372, 35660804, 268435456, 301989888, 268443648, 301998080, 270532608, 304087040, 270540800, 304095232, 268435460, 301989892, 268443652, 301998084, 270532612, 304087044, 270540804, 304095236, 268436480, 301990912, 268444672, 301999104, 270533632, 304088064, 270541824, 304096256, 268436484, 301990916, 268444676, 301999108, 270533636, 304088068, 270541828, 304096260}, new int[]{0, 1, AccessibilityNodeInfoCompat.ACTION_EXPAND, 262145, AccessibilityEventCompat.TYPE_ASSIST_READING_CONTEXT, 16777217, 17039360, 17039361, 2, 3, 262146, 262147, 16777218, 16777219, 17039362, 17039363, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, InputDeviceCompat.SOURCE_DPAD, 262656, 262657, 16777728, 16777729, 17039872, 17039873, 514, 515, 262658, 262659, 16777730, 16777731, 17039874, 17039875, 134217728, 134217729, 134479872, 134479873, 150994944, 150994945, 151257088, 151257089, 134217730, 134217731, 134479874, 134479875, 150994946, 150994947, 151257090, 151257091, 134218240, 134218241, 134480384, 134480385, 150995456, 150995457, 151257600, 151257601, 134218242, 134218243, 134480386, 134480387, 150995458, 150995459, 151257602, 151257603}, new int[]{0, AccessibilityNodeInfoCompat.ACTION_DISMISS, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY, 1048832, 8, InputDeviceCompat.SOURCE_TOUCHPAD, 264, 1048840, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, 1052672, 4352, 1052928, 4104, 1052680, 4360, 1052936, 67108864, 68157440, 67109120, 68157696, 67108872, 68157448, 67109128, 68157704, 67112960, 68161536, 67113216, 68161792, 67112968, 68161544, 67113224, 68161800, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, 1179648, 131328, 1179904, 131080, 1179656, 131336, 1179912, 135168, 1183744, 135424, 1184000, 135176, 1183752, 135432, 1184008, 67239936, 68288512, 67240192, 68288768, 67239944, 68288520, 67240200, 68288776, 67244032, 68292608, 67244288, 68292864, 67244040, 68292616, 67244296, 68292872}, new int[]{0, 268435456, AccessibilityNodeInfoCompat.ACTION_CUT, 268500992, 4, 268435460, InputDeviceCompat.SOURCE_TRACKBALL, 268500996, 536870912, 805306368, 536936448, 805371904, 536870916, 805306372, 536936452, 805371908, AccessibilityNodeInfoCompat.ACTION_DISMISS, 269484032, 1114112, 269549568, 1048580, 269484036, 1114116, 269549572, 537919488, 806354944, 537985024, 806420480, 537919492, 806354948, 537985028, 806420484, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, 268439552, 69632, 268505088, 4100, 268439556, 69636, 268505092, 536875008, 805310464, 536940544, 805376000, 536875012, 805310468, 536940548, 805376004, 1052672, 269488128, 1118208, 269553664, 1052676, 269488132, 1118212, 269553668, 537923584, 806359040, 537989120, 806424576, 537923588, 806359044, 537989124, 806424580}, new int[]{0, 134217728, 8, 134217736, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, 134218752, 1032, 134218760, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, 134348800, 131080, 134348808, 132096, 134349824, 132104, 134349832, 1, 134217729, 9, 134217737, InputDeviceCompat.SOURCE_GAMEPAD, 134218753, 1033, 134218761, 131073, 134348801, 131081, 134348809, 132097, 134349825, 132105, 134349833, 33554432, 167772160, 33554440, 167772168, 33555456, 167773184, 33555464, 167773192, 33685504, 167903232, 33685512, 167903240, 33686528, 167904256, 33686536, 167904264, InputDeviceCompat.SOURCE_HDMI, 167772161, 33554441, 167772169, 33555457, 167773185, 33555465, 167773193, 33685505, 167903233, 33685513, 167903241, 33686529, 167904257, 33686537, 167904265}, new int[]{0, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 524544, AccessibilityEventCompat.TYPE_ASSIST_READING_CONTEXT, 16777472, 17301504, 17301760, 16, 272, 524304, 524560, InputDeviceCompat.SOURCE_JOYSTICK, 16777488, 17301520, 17301776, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, 2097408, 2621440, 2621696, 18874368, 18874624, 19398656, 19398912, 2097168, 2097424, 2621456, 2621712, 18874384, 18874640, 19398672, 19398928, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, 768, 524800, 525056, 16777728, 16777984, 17302016, 17302272, 528, 784, 524816, 525072, 16777744, 16778000, 17302032, 17302288, 2097664, 2097920, 2621952, 2622208, 18874880, 18875136, 19399168, 19399424, 2097680, 2097936, 2621968, 2622224, 18874896, 18875152, 19399184, 19399440}, new int[]{0, 67108864, AccessibilityNodeInfoCompat.ACTION_EXPAND, 67371008, 2, 67108866, 262146, 67371010, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 67117056, 270336, 67379200, InputDeviceCompat.SOURCE_MOUSE, 67117058, 270338, 67379202, 32, 67108896, 262176, 67371040, 34, 67108898, 262178, 67371042, 8224, 67117088, 270368, 67379232, 8226, 67117090, 270370, 67379234, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, 67110912, 264192, 67373056, 2050, 67110914, 264194, 67373058, 10240, 67119104, 272384, 67381248, 10242, 67119106, 272386, 67381250, 2080, 67110944, 264224, 67373088, 2082, 67110946, 264226, 67373090, 10272, 67119136, 272416, 67381280, 10274, 67119138, 272418, 67381282}};
        SPTRANS = new int[][]{new int[]{8520192, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, -2139095040, -2138963456, AccessibilityEventCompat.TYPE_VIEW_CONTEXT_CLICKED, -2147352064, -2147352576, -2139095040, -2147352064, 8520192, 8519680, -2147483136, -2139094528, AccessibilityEventCompat.TYPE_VIEW_CONTEXT_CLICKED, 0, -2147352576, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, Integer.MIN_VALUE, 8389120, 131584, -2138963456, 8519680, -2147483136, 8389120, Integer.MIN_VALUE, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, 131584, -2138963968, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, -2139094528, -2138963968, 0, 0, -2138963456, 8389120, -2147352576, 8520192, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, -2147483136, 8389120, -2138963968, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, 131584, -2139095040, -2147352064, Integer.MIN_VALUE, -2139095040, 8519680, -2138963456, 131584, 8519680, -2139094528, AccessibilityEventCompat.TYPE_VIEW_CONTEXT_CLICKED, -2147483136, -2147352576, 0, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, AccessibilityEventCompat.TYPE_VIEW_CONTEXT_CLICKED, -2139094528, 8520192, Integer.MIN_VALUE, -2138963968, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, -2147352064}, new int[]{268705796, 0, 270336, 268697600, 268435460, 8196, 268443648, 270336, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 268697604, 4, 268443648, 262148, 268705792, 268697600, 4, AccessibilityNodeInfoCompat.ACTION_EXPAND, 268443652, 268697604, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 270340, 268435456, 0, 262148, 268443652, 270340, 268705792, 268435460, 268435456, AccessibilityNodeInfoCompat.ACTION_EXPAND, 8196, 268705796, 262148, 268705792, 268443648, 270340, 268705796, 262148, 268435460, 0, 268435456, 8196, AccessibilityNodeInfoCompat.ACTION_EXPAND, 268697604, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 268435456, 270340, 268443652, 268705792, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, 0, 268435460, 4, 268705796, 270336, 268697600, 268697604, AccessibilityNodeInfoCompat.ACTION_EXPAND, 8196, 268443648, 268443652, 4, 268697600, 270336}, new int[]{1090519040, 16842816, 64, 1090519104, 1073807360, AccessibilityEventCompat.TYPE_ASSIST_READING_CONTEXT, 1090519104, 65600, 16777280, AccessibilityNodeInfoCompat.ACTION_CUT, 16842752, 1073741824, 1090584640, 1073741888, 1073741824, 1090584576, 0, 1073807360, 16842816, 64, 1073741888, 1090584640, AccessibilityNodeInfoCompat.ACTION_CUT, 1090519040, 1090584576, 16777280, 1073807424, 16842752, 65600, 0, AccessibilityEventCompat.TYPE_ASSIST_READING_CONTEXT, 1073807424, 16842816, 64, 1073741824, AccessibilityNodeInfoCompat.ACTION_CUT, 1073741888, 1073807360, 16842752, 1090519104, 0, 16842816, 65600, 1090584576, 1073807360, AccessibilityEventCompat.TYPE_ASSIST_READING_CONTEXT, 1090584640, 1073741824, 1073807424, 1090519040, AccessibilityEventCompat.TYPE_ASSIST_READING_CONTEXT, 1090584640, AccessibilityNodeInfoCompat.ACTION_CUT, 16777280, 1090519104, 65600, 16777280, 0, 1090584576, 1073741888, 1090519040, 1073807424, 64, 16842752}, new int[]{1049602, 67109888, 2, 68158466, 0, 68157440, 67109890, 1048578, 68158464, 67108866, 67108864, 1026, 67108866, 1049602, AccessibilityNodeInfoCompat.ACTION_DISMISS, 67108864, 68157442, 1049600, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, 2, 1049600, 67109890, 68157440, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, 1026, 0, 1048578, 68158464, 67109888, 68157442, 68158466, AccessibilityNodeInfoCompat.ACTION_DISMISS, 68157442, 1026, AccessibilityNodeInfoCompat.ACTION_DISMISS, 67108866, 1049600, 67109888, 2, 68157440, 67109890, 0, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, 1048578, 0, 68157442, 68158464, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, 67108864, 68158466, 1049602, AccessibilityNodeInfoCompat.ACTION_DISMISS, 68158466, 2, 67109888, 1049602, 1048578, 1049600, 68157440, 67109890, 1026, 67108864, 67108866, 68158464}, new int[]{33554432, AccessibilityNodeInfoCompat.ACTION_COPY, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY, 33571080, 33570824, 33554688, 16648, 33570816, AccessibilityNodeInfoCompat.ACTION_COPY, 8, 33554440, 16640, 33554696, 33570824, 33571072, 0, 16640, 33554432, 16392, 264, 33554688, 16648, 0, 33554440, 8, 33554696, 33571080, 16392, 33570816, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY, 264, 33571072, 33571072, 33554696, 16392, 33570816, AccessibilityNodeInfoCompat.ACTION_COPY, 8, 33554440, 33554688, 33554432, 16640, 33571080, 0, 16648, 33554432, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY, 16392, 33554696, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY, 0, 33571080, 33570824, 33571072, 264, AccessibilityNodeInfoCompat.ACTION_COPY, 16640, 33570824, 33554688, 264, 8, 16648, 33570816, 33554440}, new int[]{536870928, 524304, 0, 537397248, 524304, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, 536872976, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 2064, 537397264, 526336, 536870912, 536872960, 536870928, 537395200, 526352, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 536872976, 537395216, 0, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, 16, 537397248, 537395216, 537397264, 537395200, 536870912, 2064, 16, 526336, 526352, 536872960, 2064, 536870912, 536872960, 526352, 537397248, 524304, 0, 536872960, 536870912, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, 537395216, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 524304, 537397264, 526336, 16, 537397264, 526336, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, 536872976, 536870928, 537395200, 526352, 0, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, 536870928, 536872976, 537397248, 537395200, 2064, 16, 537395216}, new int[]{AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS, 4194432, 4194305, 4198529, 4097, 4224, 0, AccessibilityEventCompat.TYPE_WINDOWS_CHANGED, 4194433, 129, 4198400, 1, 4198528, 4198400, 129, 4194433, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, 4097, 4198529, 0, 4194432, 4194305, 4224, 4198401, 4225, 4198528, 1, 4225, 4198401, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS, AccessibilityEventCompat.TYPE_WINDOWS_CHANGED, 4225, 4198400, 4198401, 129, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS, AccessibilityEventCompat.TYPE_WINDOWS_CHANGED, 4198401, 4194433, 4225, 4224, 0, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS, 4194305, 1, 4194432, 0, 4194433, 4194432, 4224, 129, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, 4198529, AccessibilityEventCompat.TYPE_WINDOWS_CHANGED, 4198528, 1, 4097, 4198529, 4194305, 4198528, 4198400, 4097}, new int[]{136314912, 136347648, 32800, 0, 134250496, 2097184, 136314880, 136347680, 32, 134217728, 2129920, 32800, 2129952, 134250528, 134217760, 136314880, AccessibilityNodeInfoCompat.ACTION_PASTE, 2129952, 2097184, 134250496, 136347680, 134217760, 0, 2129920, 134217728, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, 134250528, 136314912, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, AccessibilityNodeInfoCompat.ACTION_PASTE, 136347648, 32, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, AccessibilityNodeInfoCompat.ACTION_PASTE, 134217760, 136347680, 32800, 134217728, 0, 2129920, 136314912, 134250528, 134250496, 2097184, 136347648, 32, 2097184, 134250496, 136347680, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, 136314880, 134217760, 2129920, 32800, 134250528, 136314880, 32, 136347648, 2129952, 0, 134217728, 136314912, AccessibilityNodeInfoCompat.ACTION_PASTE, 2129952}};
    }

    public static String crypt(byte[] original) {
        return crypt(original, null);
    }

    public static String crypt(byte[] original, String salt) {
        if (salt == null) {
            Random randomGenerator = new Random();
            int numSaltChars = SALT_CHARS.length;
            salt = BuildConfig.FLAVOR + SALT_CHARS[randomGenerator.nextInt(numSaltChars)] + SALT_CHARS[randomGenerator.nextInt(numSaltChars)];
        } else {
            if (!salt.matches("^[./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]{2,}$")) {
                throw new IllegalArgumentException("Invalid salt value: " + salt);
            }
        }
        StringBuilder buffer = new StringBuilder("             ");
        char charZero = salt.charAt(0);
        char charOne = salt.charAt(1);
        buffer.setCharAt(0, charZero);
        buffer.setCharAt(1, charOne);
        int eSwap0 = CON_SALT[charZero];
        int eSwap1 = CON_SALT[charOne] << 4;
        byte[] key = new byte[8];
        int i = 0;
        while (true) {
            int length = key.length;
            if (i >= r0) {
                break;
            }
            key[i] = (byte) 0;
            i++;
        }
        i = 0;
        while (true) {
            length = key.length;
            if (i >= r0) {
                break;
            }
            length = original.length;
            if (i >= r0) {
                break;
            }
            key[i] = (byte) (original[i] << 1);
            i++;
        }
        int[] out = body(desSetKey(key), eSwap0, eSwap1);
        byte[] b = new byte[9];
        intToFourBytes(out[0], b, 0);
        intToFourBytes(out[1], b, 4);
        b[8] = (byte) 0;
        int y = 0;
        int u = AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS;
        for (i = 2; i < 13; i++) {
            int c = 0;
            for (int j = 0; j < 6; j++) {
                c <<= 1;
                if ((b[y] & u) != 0) {
                    c |= 1;
                }
                u >>>= 1;
                if (u == 0) {
                    y++;
                    u = AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS;
                }
                buffer.setCharAt(i, (char) COV2CHAR[c]);
            }
        }
        return buffer.toString();
    }

    public static String crypt(String original) {
        return crypt(original.getBytes(Charsets.UTF_8));
    }

    public static String crypt(String original, String salt) {
        return crypt(original.getBytes(Charsets.UTF_8), salt);
    }

    private static int[] body(int[] schedule, int eSwap0, int eSwap1) {
        int t;
        int left = 0;
        int right = 0;
        for (int j = 0; j < 25; j++) {
            for (int i = 0; i < 32; i += 4) {
                left = dEncrypt(left, right, i, eSwap0, eSwap1, schedule);
                right = dEncrypt(right, left, i + 2, eSwap0, eSwap1, schedule);
            }
            t = left;
            left = right;
            right = t;
        }
        t = right;
        int[] results = new int[2];
        permOp((left >>> 1) | (left << 31), (t >>> 1) | (t << 31), 1, 1431655765, results);
        permOp(results[1], results[0], 8, 16711935, results);
        permOp(results[1], results[0], 2, 858993459, results);
        permOp(results[1], results[0], 16, SupportMenu.USER_MASK, results);
        permOp(results[1], results[0], 4, 252645135, results);
        right = results[0];
        return new int[]{results[1], right};
    }

    private static int byteToUnsigned(byte b) {
        byte value = b;
        return value < null ? value + AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY : value;
    }

    private static int dEncrypt(int el, int r, int s, int e0, int e1, int[] sArr) {
        int v = r ^ (r >>> 16);
        int u = v & e0;
        v &= e1;
        u = (((u << 16) ^ u) ^ r) ^ sArr[s];
        int t = (((v << 16) ^ v) ^ r) ^ sArr[s + 1];
        t = (t >>> 4) | (t << 28);
        return el ^ (((((((SPTRANS[1][t & 63] | SPTRANS[3][(t >>> 8) & 63]) | SPTRANS[5][(t >>> 16) & 63]) | SPTRANS[7][(t >>> 24) & 63]) | SPTRANS[0][u & 63]) | SPTRANS[2][(u >>> 8) & 63]) | SPTRANS[4][(u >>> 16) & 63]) | SPTRANS[6][(u >>> 24) & 63]);
    }

    private static int[] desSetKey(byte[] key) {
        int[] schedule = new int[32];
        int[] results = new int[2];
        permOp(fourBytesToInt(key, 4), fourBytesToInt(key, 0), 4, 252645135, results);
        int d = results[0];
        permOp(hPermOp(d, -2, -859045888), hPermOp(results[1], -2, -859045888), 1, 1431655765, results);
        permOp(results[1], results[0], 8, 16711935, results);
        permOp(results[1], results[0], 1, 1431655765, results);
        d = results[0];
        int c = results[1];
        d = ((((d & MotionEventCompat.ACTION_MASK) << 16) | (MotionEventCompat.ACTION_POINTER_INDEX_MASK & d)) | ((16711680 & d) >>> 16)) | ((-268435456 & c) >>> 4);
        c &= 268435455;
        int j = 0;
        for (int i = 0; i < 16; i++) {
            if (SHIFT2[i]) {
                c = (c >>> 2) | (c << 26);
                d = (d >>> 2) | (d << 26);
            } else {
                c = (c >>> 1) | (c << 27);
                d = (d >>> 1) | (d << 27);
            }
            c &= 268435455;
            d &= 268435455;
            int s = ((SKB[0][c & 63] | SKB[1][((c >>> 6) & 3) | ((c >>> 7) & 60)]) | SKB[2][((c >>> 13) & 15) | ((c >>> 14) & 48)]) | SKB[3][(((c >>> 20) & 1) | ((c >>> 21) & 6)) | ((c >>> 22) & 56)];
            int t = ((SKB[4][d & 63] | SKB[5][((d >>> 7) & 3) | ((d >>> 8) & 60)]) | SKB[6][(d >>> 15) & 63]) | SKB[7][((d >>> 21) & 15) | ((d >>> 22) & 48)];
            int j2 = j + 1;
            schedule[j] = (t << 16) | (SupportMenu.USER_MASK & s);
            s = (s >>> 16) | (SupportMenu.CATEGORY_MASK & t);
            j = j2 + 1;
            schedule[j2] = (s << 4) | (s >>> 28);
        }
        return schedule;
    }

    private static int fourBytesToInt(byte[] b, int offset) {
        int offset2 = offset + 1;
        offset = offset2 + 1;
        offset2 = offset + 1;
        offset = offset2 + 1;
        return ((byteToUnsigned(b[offset]) | (byteToUnsigned(b[offset2]) << 8)) | (byteToUnsigned(b[offset]) << 16)) | (byteToUnsigned(b[offset2]) << 24);
    }

    private static int hPermOp(int a, int n, int m) {
        int t = ((a << (16 - n)) ^ a) & m;
        return (a ^ t) ^ (t >>> (16 - n));
    }

    private static void intToFourBytes(int iValue, byte[] b, int offset) {
        int i = offset + 1;
        b[offset] = (byte) (iValue & MotionEventCompat.ACTION_MASK);
        offset = i + 1;
        b[i] = (byte) ((iValue >>> 8) & MotionEventCompat.ACTION_MASK);
        i = offset + 1;
        b[offset] = (byte) ((iValue >>> 16) & MotionEventCompat.ACTION_MASK);
        offset = i + 1;
        b[i] = (byte) ((iValue >>> 24) & MotionEventCompat.ACTION_MASK);
    }

    private static void permOp(int a, int b, int n, int m, int[] results) {
        int t = ((a >>> n) ^ b) & m;
        b ^= t;
        results[0] = a ^ (t << n);
        results[1] = b;
    }
}
