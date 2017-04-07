package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SM;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory.SecurityLevel;
import org.apache.http.message.BasicHeaderElement;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.apache.http.message.BufferedHeader;
import org.apache.http.message.ParserCursor;
import org.apache.http.protocol.HttpDateGenerator;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@ThreadSafe
public class BrowserCompatSpec extends CookieSpecBase {
    private static final String[] DEFAULT_DATE_PATTERNS;

    /* renamed from: org.apache.http.impl.cookie.BrowserCompatSpec.1 */
    class C01611 extends BasicPathHandler {
        C01611() {
        }

        public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
        }
    }

    static {
        DEFAULT_DATE_PATTERNS = new String[]{HttpDateGenerator.PATTERN_RFC1123, DateUtils.PATTERN_RFC1036, DateUtils.PATTERN_ASCTIME, "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"};
    }

    public BrowserCompatSpec(String[] datepatterns, SecurityLevel securityLevel) {
        C01611 c01611;
        String[] strArr;
        CommonCookieAttributeHandler[] commonCookieAttributeHandlerArr = new CommonCookieAttributeHandler[7];
        commonCookieAttributeHandlerArr[0] = new BrowserCompatVersionAttributeHandler();
        commonCookieAttributeHandlerArr[1] = new BasicDomainHandler();
        if (securityLevel == SecurityLevel.SECURITYLEVEL_IE_MEDIUM) {
            c01611 = new C01611();
        } else {
            c01611 = new BasicPathHandler();
        }
        commonCookieAttributeHandlerArr[2] = c01611;
        commonCookieAttributeHandlerArr[3] = new BasicMaxAgeHandler();
        commonCookieAttributeHandlerArr[4] = new BasicSecureHandler();
        commonCookieAttributeHandlerArr[5] = new BasicCommentHandler();
        if (datepatterns != null) {
            strArr = (String[]) datepatterns.clone();
        } else {
            strArr = DEFAULT_DATE_PATTERNS;
        }
        commonCookieAttributeHandlerArr[6] = new BasicExpiresHandler(strArr);
        super(commonCookieAttributeHandlerArr);
    }

    public BrowserCompatSpec(String[] datepatterns) {
        this(datepatterns, SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    public BrowserCompatSpec() {
        this(null, SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    public List<Cookie> parse(Header header, CookieOrigin origin) throws MalformedCookieException {
        Args.notNull(header, "Header");
        Args.notNull(origin, "Cookie origin");
        if (header.getName().equalsIgnoreCase(SM.SET_COOKIE)) {
            HeaderElement[] helems = header.getElements();
            boolean versioned = false;
            boolean netscape = false;
            for (HeaderElement helem : helems) {
                if (helem.getParameterByName(ClientCookie.VERSION_ATTR) != null) {
                    versioned = true;
                }
                if (helem.getParameterByName(ClientCookie.EXPIRES_ATTR) != null) {
                    netscape = true;
                }
            }
            if (!netscape && versioned) {
                return parse(helems, origin);
            }
            CharArrayBuffer buffer;
            ParserCursor cursor;
            String s;
            NetscapeDraftHeaderParser parser = NetscapeDraftHeaderParser.DEFAULT;
            if (header instanceof FormattedHeader) {
                buffer = ((FormattedHeader) header).getBuffer();
                cursor = new ParserCursor(((FormattedHeader) header).getValuePos(), buffer.length());
            } else {
                s = header.getValue();
                if (s == null) {
                    throw new MalformedCookieException("Header value is null");
                }
                buffer = new CharArrayBuffer(s.length());
                buffer.append(s);
                cursor = new ParserCursor(0, buffer.length());
            }
            HeaderElement elem = parser.parseHeader(buffer, cursor);
            String name = elem.getName();
            String value = elem.getValue();
            if (name == null || name.isEmpty()) {
                throw new MalformedCookieException("Cookie name may not be empty");
            }
            BasicClientCookie cookie = new BasicClientCookie(name, value);
            cookie.setPath(CookieSpecBase.getDefaultPath(origin));
            cookie.setDomain(CookieSpecBase.getDefaultDomain(origin));
            NameValuePair[] attribs = elem.getParameters();
            for (int j = attribs.length - 1; j >= 0; j--) {
                NameValuePair attrib = attribs[j];
                s = attrib.getName().toLowerCase(Locale.ROOT);
                cookie.setAttribute(s, attrib.getValue());
                CookieAttributeHandler handler = findAttribHandler(s);
                if (handler != null) {
                    handler.parse(cookie, attrib.getValue());
                }
            }
            if (netscape) {
                cookie.setVersion(0);
            }
            return Collections.singletonList(cookie);
        }
        throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
    }

    private static boolean isQuoteEnclosed(String s) {
        return s != null && s.startsWith("\"") && s.endsWith("\"");
    }

    public List<Header> formatCookies(List<Cookie> cookies) {
        Args.notEmpty((Collection) cookies, "List of cookies");
        CharArrayBuffer buffer = new CharArrayBuffer(cookies.size() * 20);
        buffer.append(SM.COOKIE);
        buffer.append(": ");
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = (Cookie) cookies.get(i);
            if (i > 0) {
                buffer.append("; ");
            }
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (cookie.getVersion() <= 0 || isQuoteEnclosed(cookieValue)) {
                buffer.append(cookieName);
                buffer.append("=");
                if (cookieValue != null) {
                    buffer.append(cookieValue);
                }
            } else {
                BasicHeaderValueFormatter.INSTANCE.formatHeaderElement(buffer, new BasicHeaderElement(cookieName, cookieValue), false);
            }
        }
        List<Header> headers = new ArrayList(1);
        headers.add(new BufferedHeader(buffer));
        return headers;
    }

    public int getVersion() {
        return 0;
    }

    public Header getVersionHeader() {
        return null;
    }

    public String toString() {
        return CookiePolicy.BROWSER_COMPATIBILITY;
    }
}
