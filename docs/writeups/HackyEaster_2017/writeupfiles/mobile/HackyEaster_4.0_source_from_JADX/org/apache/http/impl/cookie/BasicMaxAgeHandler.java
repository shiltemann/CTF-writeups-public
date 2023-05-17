package org.apache.http.impl.cookie;

import java.util.Date;
import org.apache.http.annotation.Immutable;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SM;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;

@Immutable
public class BasicMaxAgeHandler extends AbstractCookieAttributeHandler implements CommonCookieAttributeHandler {
    public void parse(SetCookie cookie, String value) throws MalformedCookieException {
        Args.notNull(cookie, SM.COOKIE);
        if (value == null) {
            throw new MalformedCookieException("Missing value for 'max-age' attribute");
        }
        try {
            int age = Integer.parseInt(value);
            if (age < 0) {
                throw new MalformedCookieException("Negative 'max-age' attribute: " + value);
            }
            cookie.setExpiryDate(new Date(System.currentTimeMillis() + (((long) age) * 1000)));
        } catch (NumberFormatException e) {
            throw new MalformedCookieException("Invalid 'max-age' attribute: " + value);
        }
    }

    public String getAttributeName() {
        return ClientCookie.MAX_AGE_ATTR;
    }
}
