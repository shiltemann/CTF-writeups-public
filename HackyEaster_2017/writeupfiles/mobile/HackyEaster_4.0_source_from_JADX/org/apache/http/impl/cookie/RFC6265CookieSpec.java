package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookiePriorityComparator;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SM;
import org.apache.http.message.BufferedHeader;
import org.apache.http.message.ParserCursor;
import org.apache.http.message.TokenParser;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@ThreadSafe
public class RFC6265CookieSpec implements CookieSpec {
    private static final char COMMA_CHAR = ',';
    private static final char DQUOTE_CHAR = '\"';
    private static final char EQUAL_CHAR = '=';
    private static final char ESCAPE_CHAR = '\\';
    private static final char PARAM_DELIMITER = ';';
    private static final BitSet SPECIAL_CHARS;
    private static final BitSet TOKEN_DELIMS;
    private static final BitSet VALUE_DELIMS;
    private final Map<String, CookieAttributeHandler> attribHandlerMap;
    private final CookieAttributeHandler[] attribHandlers;
    private final TokenParser tokenParser;

    static {
        TOKEN_DELIMS = TokenParser.INIT_BITSET(61, 59);
        VALUE_DELIMS = TokenParser.INIT_BITSET(59);
        SPECIAL_CHARS = TokenParser.INIT_BITSET(32, 34, 44, 59, 92);
    }

    protected RFC6265CookieSpec(CommonCookieAttributeHandler... handlers) {
        this.attribHandlers = (CookieAttributeHandler[]) handlers.clone();
        this.attribHandlerMap = new ConcurrentHashMap(handlers.length);
        for (CommonCookieAttributeHandler handler : handlers) {
            this.attribHandlerMap.put(handler.getAttributeName().toLowerCase(Locale.ROOT), handler);
        }
        this.tokenParser = TokenParser.INSTANCE;
    }

    static String getDefaultPath(CookieOrigin origin) {
        String defaultPath = origin.getPath();
        int lastSlashIndex = defaultPath.lastIndexOf(47);
        if (lastSlashIndex < 0) {
            return defaultPath;
        }
        if (lastSlashIndex == 0) {
            lastSlashIndex = 1;
        }
        return defaultPath.substring(0, lastSlashIndex);
    }

    static String getDefaultDomain(CookieOrigin origin) {
        return origin.getHost();
    }

    public final List<Cookie> parse(Header header, CookieOrigin origin) throws MalformedCookieException {
        Args.notNull(header, "Header");
        Args.notNull(origin, "Cookie origin");
        if (header.getName().equalsIgnoreCase(SM.SET_COOKIE)) {
            CharArrayBuffer buffer;
            ParserCursor cursor;
            if (header instanceof FormattedHeader) {
                buffer = ((FormattedHeader) header).getBuffer();
                cursor = new ParserCursor(((FormattedHeader) header).getValuePos(), buffer.length());
            } else {
                String s = header.getValue();
                if (s == null) {
                    throw new MalformedCookieException("Header value is null");
                }
                buffer = new CharArrayBuffer(s.length());
                buffer.append(s);
                cursor = new ParserCursor(0, buffer.length());
            }
            String name = this.tokenParser.parseToken(buffer, cursor, TOKEN_DELIMS);
            if (name.length() == 0) {
                throw new MalformedCookieException("Cookie name is invalid: '" + header.toString() + "'");
            } else if (cursor.atEnd()) {
                throw new MalformedCookieException("Cookie value is invalid: '" + header.toString() + "'");
            } else {
                int valueDelim = buffer.charAt(cursor.getPos());
                cursor.updatePos(cursor.getPos() + 1);
                if (valueDelim != 61) {
                    throw new MalformedCookieException("Cookie value is invalid: '" + header.toString() + "'");
                }
                String paramValue;
                String value = this.tokenParser.parseValue(buffer, cursor, VALUE_DELIMS);
                if (!cursor.atEnd()) {
                    cursor.updatePos(cursor.getPos() + 1);
                }
                BasicClientCookie cookie = new BasicClientCookie(name, value);
                cookie.setPath(getDefaultPath(origin));
                cookie.setDomain(getDefaultDomain(origin));
                cookie.setCreationDate(new Date());
                Map<String, String> attribMap = new LinkedHashMap();
                while (!cursor.atEnd()) {
                    String paramName = this.tokenParser.parseToken(buffer, cursor, TOKEN_DELIMS).toLowerCase(Locale.ROOT);
                    paramValue = null;
                    if (!cursor.atEnd()) {
                        int paramDelim = buffer.charAt(cursor.getPos());
                        cursor.updatePos(cursor.getPos() + 1);
                        if (paramDelim == 61) {
                            paramValue = this.tokenParser.parseToken(buffer, cursor, VALUE_DELIMS);
                            if (!cursor.atEnd()) {
                                cursor.updatePos(cursor.getPos() + 1);
                            }
                        }
                    }
                    cookie.setAttribute(paramName, paramValue);
                    attribMap.put(paramName, paramValue);
                }
                if (attribMap.containsKey(ClientCookie.MAX_AGE_ATTR)) {
                    attribMap.remove(ClientCookie.EXPIRES_ATTR);
                }
                for (Entry<String, String> entry : attribMap.entrySet()) {
                    paramValue = (String) entry.getValue();
                    CookieAttributeHandler handler = (CookieAttributeHandler) this.attribHandlerMap.get((String) entry.getKey());
                    if (handler != null) {
                        handler.parse(cookie, paramValue);
                    }
                }
                return Collections.singletonList(cookie);
            }
        }
        throw new MalformedCookieException("Unrecognized cookie header: '" + header.toString() + "'");
    }

    public final void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
        Args.notNull(cookie, SM.COOKIE);
        Args.notNull(origin, "Cookie origin");
        for (CookieAttributeHandler handler : this.attribHandlers) {
            handler.validate(cookie, origin);
        }
    }

    public final boolean match(Cookie cookie, CookieOrigin origin) {
        Args.notNull(cookie, SM.COOKIE);
        Args.notNull(origin, "Cookie origin");
        for (CookieAttributeHandler handler : this.attribHandlers) {
            if (!handler.match(cookie, origin)) {
                return false;
            }
        }
        return true;
    }

    public List<Header> formatCookies(List<Cookie> cookies) {
        List<? extends Cookie> sortedCookies;
        Args.notEmpty((Collection) cookies, "List of cookies");
        if (cookies.size() > 1) {
            sortedCookies = new ArrayList(cookies);
            Collections.sort(sortedCookies, CookiePriorityComparator.INSTANCE);
        } else {
            sortedCookies = cookies;
        }
        CharArrayBuffer buffer = new CharArrayBuffer(sortedCookies.size() * 20);
        buffer.append(SM.COOKIE);
        buffer.append(": ");
        for (int n = 0; n < sortedCookies.size(); n++) {
            Cookie cookie = (Cookie) sortedCookies.get(n);
            if (n > 0) {
                buffer.append((char) PARAM_DELIMITER);
                buffer.append((char) TokenParser.SP);
            }
            buffer.append(cookie.getName());
            String s = cookie.getValue();
            if (s != null) {
                buffer.append((char) EQUAL_CHAR);
                if (containsSpecialChar(s)) {
                    buffer.append((char) DQUOTE_CHAR);
                    for (int i = 0; i < s.length(); i++) {
                        char ch = s.charAt(i);
                        if (ch == DQUOTE_CHAR || ch == ESCAPE_CHAR) {
                            buffer.append((char) ESCAPE_CHAR);
                        }
                        buffer.append(ch);
                    }
                    buffer.append((char) DQUOTE_CHAR);
                } else {
                    buffer.append(s);
                }
            }
        }
        List<Header> headers = new ArrayList(1);
        headers.add(new BufferedHeader(buffer));
        return headers;
    }

    boolean containsSpecialChar(CharSequence s) {
        return containsChars(s, SPECIAL_CHARS);
    }

    boolean containsChars(CharSequence s, BitSet chars) {
        for (int i = 0; i < s.length(); i++) {
            if (chars.get(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public final int getVersion() {
        return 0;
    }

    public final Header getVersionHeader() {
        return null;
    }
}
