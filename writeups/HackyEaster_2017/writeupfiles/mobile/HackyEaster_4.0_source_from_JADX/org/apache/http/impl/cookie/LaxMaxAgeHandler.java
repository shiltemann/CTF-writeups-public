package org.apache.http.impl.cookie;

import java.util.Date;
import java.util.regex.Pattern;
import org.apache.http.annotation.Immutable;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SM;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Immutable
public class LaxMaxAgeHandler extends AbstractCookieAttributeHandler implements CommonCookieAttributeHandler {
    private static final Pattern MAX_AGE_PATTERN;

    static {
        MAX_AGE_PATTERN = Pattern.compile("^\\-?[0-9]+$");
    }

    public void parse(SetCookie cookie, String value) throws MalformedCookieException {
        Args.notNull(cookie, SM.COOKIE);
        if (!TextUtils.isBlank(value) && MAX_AGE_PATTERN.matcher(value).matches()) {
            try {
                int age = Integer.parseInt(value);
                cookie.setExpiryDate(age >= 0 ? new Date(System.currentTimeMillis() + (((long) age) * 1000)) : new Date(Long.MIN_VALUE));
            } catch (NumberFormatException e) {
            }
        }
    }

    public String getAttributeName() {
        return ClientCookie.MAX_AGE_ATTR;
    }
}
