package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Map;

public final class EmailAddressResultParser extends ResultParser {
    public EmailAddressParsedResult parse(Result result) {
        String rawText = ResultParser.getMassagedText(result);
        String emailAddress;
        if (rawText.startsWith("mailto:") || rawText.startsWith("MAILTO:")) {
            emailAddress = rawText.substring(7);
            int queryStart = emailAddress.indexOf(63);
            if (queryStart >= 0) {
                emailAddress = emailAddress.substring(0, queryStart);
            }
            Map<String, String> nameValues = ResultParser.parseNameValuePairs(rawText);
            String subject = null;
            String body = null;
            if (nameValues != null) {
                if (emailAddress.length() == 0) {
                    emailAddress = (String) nameValues.get("to");
                }
                subject = (String) nameValues.get("subject");
                body = (String) nameValues.get("body");
            }
            return new EmailAddressParsedResult(emailAddress, subject, body, rawText);
        } else if (!EmailDoCoMoResultParser.isBasicallyValidEmailAddress(rawText)) {
            return null;
        } else {
            emailAddress = rawText;
            return new EmailAddressParsedResult(emailAddress, null, null, "mailto:" + emailAddress);
        }
    }
}
