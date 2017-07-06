package com.google.zxing.client.result;

abstract class AbstractDoCoMoResultParser extends ResultParser {
    AbstractDoCoMoResultParser() {
    }

    static String[] matchDoCoMoPrefixedField(String prefix, String rawText, boolean trim) {
        return ResultParser.matchPrefixedField(prefix, rawText, ';', trim);
    }

    static String matchSingleDoCoMoPrefixedField(String prefix, String rawText, boolean trim) {
        return ResultParser.matchSinglePrefixedField(prefix, rawText, ';', trim);
    }
}
