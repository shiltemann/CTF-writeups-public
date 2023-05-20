package ps.hacking.zxing.client.result;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.TokenParser;
import ps.hacking.zxing.Result;

public final class BizcardResultParser extends AbstractDoCoMoResultParser {
    public AddressBookParsedResult parse(Result result) {
        String rawText = ResultParser.getMassagedText(result);
        if (!rawText.startsWith("BIZCARD:")) {
            return null;
        }
        String fullName = buildName(AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("N:", rawText, true), AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("X:", rawText, true));
        String title = AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("T:", rawText, true);
        String org = AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("C:", rawText, true);
        return new AddressBookParsedResult(ResultParser.maybeWrap(fullName), null, buildPhoneNumbers(AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("B:", rawText, true), AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("M:", rawText, true), AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("F:", rawText, true)), null, ResultParser.maybeWrap(AbstractDoCoMoResultParser.matchSingleDoCoMoPrefixedField("E:", rawText, true)), null, null, null, AbstractDoCoMoResultParser.matchDoCoMoPrefixedField("A:", rawText, true), null, org, null, title, null);
    }

    private static String[] buildPhoneNumbers(String number1, String number2, String number3) {
        List<String> numbers = new ArrayList(3);
        if (number1 != null) {
            numbers.add(number1);
        }
        if (number2 != null) {
            numbers.add(number2);
        }
        if (number3 != null) {
            numbers.add(number3);
        }
        int size = numbers.size();
        if (size == 0) {
            return null;
        }
        return (String[]) numbers.toArray(new String[size]);
    }

    private static String buildName(String firstName, String lastName) {
        if (firstName == null) {
            return lastName;
        }
        if (lastName != null) {
            firstName = firstName + TokenParser.SP + lastName;
        }
        return firstName;
    }
}
