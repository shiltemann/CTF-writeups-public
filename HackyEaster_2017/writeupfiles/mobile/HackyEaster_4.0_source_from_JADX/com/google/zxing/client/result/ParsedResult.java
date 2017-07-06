package com.google.zxing.client.result;

public abstract class ParsedResult {
    private final ParsedResultType type;

    public abstract String getDisplayResult();

    protected ParsedResult(ParsedResultType type) {
        this.type = type;
    }

    public final ParsedResultType getType() {
        return this.type;
    }

    public final String toString() {
        return getDisplayResult();
    }

    public static void maybeAppend(String value, StringBuilder result) {
        if (value != null && value.length() > 0) {
            if (result.length() > 0) {
                result.append('\n');
            }
            result.append(value);
        }
    }

    public static void maybeAppend(String[] value, StringBuilder result) {
        if (value != null) {
            for (String s : value) {
                if (s != null && s.length() > 0) {
                    if (result.length() > 0) {
                        result.append('\n');
                    }
                    result.append(s);
                }
            }
        }
    }
}
