package ps.hacking.zxing.client.result;

import org.apache.http.message.TokenParser;
import ps.hacking.zxing.Result;

public final class AddressBookDoCoMoResultParser extends AbstractDoCoMoResultParser {
    public AddressBookParsedResult parse(Result result) {
        String rawText = ResultParser.getMassagedText(result);
        if (!rawText.startsWith("MECARD:")) {
            return null;
        }
        String[] rawName = AbstractDoCoMoResultParser.matchDoCoMoPrefixedField("N:", rawText, true);
        if (rawName == null) {
            return null;
        }
        String name = parseName(rawName[0]);
        String pronunciation = AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("SOUND:", rawText, true);
        String[] phoneNumbers = AbstractDoCoMoResultParser.matchDoCoMoPrefixedField("TEL:", rawText, true);
        String[] emails = AbstractDoCoMoResultParser.matchDoCoMoPrefixedField("EMAIL:", rawText, true);
        String note = AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("NOTE:", rawText, false);
        String[] addresses = AbstractDoCoMoResultParser.matchDoCoMoPrefixedField("ADR:", rawText, true);
        String birthday = AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("BDAY:", rawText, true);
        if (!(birthday == null || ResultParser.isStringOfDigits(birthday, 8))) {
            birthday = null;
        }
        String url = AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("URL:", rawText, true);
        return new AddressBookParsedResult(ResultParser.maybeWrap(name), pronunciation, phoneNumbers, null, emails, null, null, note, addresses, null, AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("ORG:", rawText, true), birthday, null, url);
    }

    private static String parseName(String name) {
        int comma = name.indexOf(44);
        if (comma >= 0) {
            return name.substring(comma + 1) + TokenParser.SP + name.substring(0, comma);
        }
        return name;
    }
}
