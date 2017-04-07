package ps.hacking.zxing.client.result;

import java.util.regex.Pattern;
import ps.hacking.zxing.Result;

public final class EmailDoCoMoResultParser extends AbstractDoCoMoResultParser {
    private static final Pattern ATEXT_ALPHANUMERIC;

    static {
        ATEXT_ALPHANUMERIC = Pattern.compile("[a-zA-Z0-9@.!#$%&'*+\\-/=?^_`{|}~]+");
    }

    public EmailAddressParsedResult parse(Result result) {
        String rawText = ResultParser.getMassagedText(result);
        if (!rawText.startsWith("MATMSG:")) {
            return null;
        }
        String[] rawTo = AbstractDoCoMoResultParser.matchDoCoMoPrefixedField("TO:", rawText, true);
        if (rawTo == null) {
            return null;
        }
        String to = rawTo[0];
        if (isBasicallyValidEmailAddress(to)) {
            return new EmailAddressParsedResult(to, AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("SUB:", rawText, false), AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("BODY:", rawText, false), "mailto:" + to);
        }
        return null;
    }

    static boolean isBasicallyValidEmailAddress(String email) {
        return email != null && ATEXT_ALPHANUMERIC.matcher(email).matches() && email.indexOf(64) >= 0;
    }
}
