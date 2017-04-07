package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.annotation.Immutable;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.params.CookieSpecPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Immutable
@Deprecated
public class RFC2109SpecFactory implements CookieSpecFactory, CookieSpecProvider {
    private final CookieSpec cookieSpec;

    public RFC2109SpecFactory(String[] datepatterns, boolean oneHeader) {
        this.cookieSpec = new RFC2109Spec(datepatterns, oneHeader);
    }

    public RFC2109SpecFactory() {
        this(null, false);
    }

    public CookieSpec newInstance(HttpParams params) {
        if (params == null) {
            return new RFC2109Spec();
        }
        String[] patterns = null;
        Collection<?> param = (Collection) params.getParameter(CookieSpecPNames.DATE_PATTERNS);
        if (param != null) {
            patterns = (String[]) param.toArray(new String[param.size()]);
        }
        return new RFC2109Spec(patterns, params.getBooleanParameter(CookieSpecPNames.SINGLE_COOKIE_HEADER, false));
    }

    public CookieSpec create(HttpContext context) {
        return this.cookieSpec;
    }
}
