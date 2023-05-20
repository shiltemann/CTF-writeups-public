package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class TelResultParser extends ResultParser {
    public TelParsedResult parse(Result result) {
        String rawText = ResultParser.getMassagedText(result);
        if (!rawText.startsWith("tel:") && !rawText.startsWith("TEL:")) {
            return null;
        }
        String telURI;
        if (rawText.startsWith("TEL:")) {
            telURI = "tel:" + rawText.substring(4);
        } else {
            telURI = rawText;
        }
        int queryStart = rawText.indexOf(63, 4);
        return new TelParsedResult(queryStart < 0 ? rawText.substring(4) : rawText.substring(4, queryStart), telURI, null);
    }
}
