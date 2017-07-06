package com.google.zxing.client.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.google.zxing.DecodeHintType;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.http.message.TokenParser;
import ps.hacking.hackyeaster.android.BuildConfig;

public final class DecodeHintManager {
    private static final Pattern COMMA;
    private static final String TAG;

    static {
        TAG = DecodeHintManager.class.getSimpleName();
        COMMA = Pattern.compile(",");
    }

    private DecodeHintManager() {
    }

    private static Map<String, String> splitQuery(String query) {
        Map<String, String> map = new HashMap();
        int pos = 0;
        while (pos < query.length()) {
            if (query.charAt(pos) == '&') {
                pos++;
            } else {
                int amp = query.indexOf(38, pos);
                int equ = query.indexOf(61, pos);
                String name;
                String text;
                if (amp < 0) {
                    if (equ < 0) {
                        name = Uri.decode(query.substring(pos).replace('+', TokenParser.SP));
                        text = BuildConfig.FLAVOR;
                    } else {
                        name = Uri.decode(query.substring(pos, equ).replace('+', TokenParser.SP));
                        text = Uri.decode(query.substring(equ + 1).replace('+', TokenParser.SP));
                    }
                    if (!map.containsKey(name)) {
                        map.put(name, text);
                    }
                    return map;
                } else if (equ < 0 || equ > amp) {
                    name = Uri.decode(query.substring(pos, amp).replace('+', TokenParser.SP));
                    if (!map.containsKey(name)) {
                        map.put(name, BuildConfig.FLAVOR);
                    }
                    pos = amp + 1;
                } else {
                    name = Uri.decode(query.substring(pos, equ).replace('+', TokenParser.SP));
                    text = Uri.decode(query.substring(equ + 1, amp).replace('+', TokenParser.SP));
                    if (!map.containsKey(name)) {
                        map.put(name, text);
                    }
                    pos = amp + 1;
                }
            }
        }
        return map;
    }

    static Map<DecodeHintType, ?> parseDecodeHints(Uri inputUri) {
        String query = inputUri.getEncodedQuery();
        if (query == null || query.isEmpty()) {
            return null;
        }
        Map<String, String> parameters = splitQuery(query);
        Map<DecodeHintType, ?> hints = new EnumMap(DecodeHintType.class);
        for (DecodeHintType hintType : DecodeHintType.values()) {
            if (!(hintType == DecodeHintType.CHARACTER_SET || hintType == DecodeHintType.NEED_RESULT_POINT_CALLBACK || hintType == DecodeHintType.POSSIBLE_FORMATS)) {
                String parameterText = (String) parameters.get(hintType.name());
                if (parameterText != null) {
                    if (hintType.getValueType().equals(Object.class)) {
                        hints.put(hintType, parameterText);
                    } else if (hintType.getValueType().equals(Void.class)) {
                        hints.put(hintType, Boolean.TRUE);
                    } else if (hintType.getValueType().equals(String.class)) {
                        hints.put(hintType, parameterText);
                    } else if (hintType.getValueType().equals(Boolean.class)) {
                        if (parameterText.isEmpty()) {
                            hints.put(hintType, Boolean.TRUE);
                        } else if ("0".equals(parameterText) || "false".equalsIgnoreCase(parameterText) || "no".equalsIgnoreCase(parameterText)) {
                            hints.put(hintType, Boolean.FALSE);
                        } else {
                            hints.put(hintType, Boolean.TRUE);
                        }
                    } else if (hintType.getValueType().equals(int[].class)) {
                        if (!parameterText.isEmpty() && parameterText.charAt(parameterText.length() - 1) == ',') {
                            parameterText = parameterText.substring(0, parameterText.length() - 1);
                        }
                        String[] values = COMMA.split(parameterText);
                        int[] array = new int[values.length];
                        int i = 0;
                        while (i < values.length) {
                            try {
                                array[i] = Integer.parseInt(values[i]);
                                i++;
                            } catch (NumberFormatException e) {
                                Log.w(TAG, "Skipping array of integers hint " + hintType + " due to invalid numeric value: '" + values[i] + '\'');
                                array = null;
                            }
                        }
                        if (array != null) {
                            hints.put(hintType, array);
                        }
                    } else {
                        Log.w(TAG, "Unsupported hint type '" + hintType + "' of type " + hintType.getValueType());
                    }
                }
            }
        }
        Log.i(TAG, "Hints from the URI: " + hints);
        return hints;
    }

    public static Map<DecodeHintType, Object> parseDecodeHints(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null || extras.isEmpty()) {
            return null;
        }
        Map<DecodeHintType, Object> hints = new EnumMap(DecodeHintType.class);
        for (DecodeHintType hintType : DecodeHintType.values()) {
            if (!(hintType == DecodeHintType.CHARACTER_SET || hintType == DecodeHintType.NEED_RESULT_POINT_CALLBACK || hintType == DecodeHintType.POSSIBLE_FORMATS)) {
                String hintName = hintType.name();
                if (extras.containsKey(hintName)) {
                    if (hintType.getValueType().equals(Void.class)) {
                        hints.put(hintType, Boolean.TRUE);
                    } else {
                        Object hintData = extras.get(hintName);
                        if (hintType.getValueType().isInstance(hintData)) {
                            hints.put(hintType, hintData);
                        } else {
                            Log.w(TAG, "Ignoring hint " + hintType + " because it is not assignable from " + hintData);
                        }
                    }
                }
            }
        }
        Log.i(TAG, "Hints from the Intent: " + hints);
        return hints;
    }
}
