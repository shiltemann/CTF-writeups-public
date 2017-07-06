package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.annotation.Immutable;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import ps.hacking.hackyeaster.android.BuildConfig;

@Immutable
public class BasicLineParser implements LineParser {
    @Deprecated
    public static final BasicLineParser DEFAULT;
    public static final BasicLineParser INSTANCE;
    protected final ProtocolVersion protocol;

    static {
        DEFAULT = new BasicLineParser();
        INSTANCE = new BasicLineParser();
    }

    public BasicLineParser(ProtocolVersion proto) {
        if (proto == null) {
            proto = HttpVersion.HTTP_1_1;
        }
        this.protocol = proto;
    }

    public BasicLineParser() {
        this(null);
    }

    public static ProtocolVersion parseProtocolVersion(String value, LineParser parser) throws ParseException {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        ParserCursor cursor = new ParserCursor(0, value.length());
        if (parser == null) {
            parser = INSTANCE;
        }
        return parser.parseProtocolVersion(buffer, cursor);
    }

    public ProtocolVersion parseProtocolVersion(CharArrayBuffer buffer, ParserCursor cursor) throws ParseException {
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        String protoname = this.protocol.getProtocol();
        int protolength = protoname.length();
        int indexFrom = cursor.getPos();
        int indexTo = cursor.getUpperBound();
        skipWhitespace(buffer, cursor);
        int i = cursor.getPos();
        if ((i + protolength) + 4 > indexTo) {
            throw new ParseException("Not a valid protocol version: " + buffer.substring(indexFrom, indexTo));
        }
        boolean ok = true;
        int j = 0;
        while (ok && j < protolength) {
            ok = buffer.charAt(i + j) == protoname.charAt(j);
            j++;
        }
        if (ok) {
            ok = buffer.charAt(i + protolength) == '/';
        }
        if (ok) {
            i += protolength + 1;
            int period = buffer.indexOf(46, i, indexTo);
            if (period == -1) {
                throw new ParseException("Invalid protocol version number: " + buffer.substring(indexFrom, indexTo));
            }
            try {
                int major = Integer.parseInt(buffer.substringTrimmed(i, period));
                i = period + 1;
                int blank = buffer.indexOf(32, i, indexTo);
                if (blank == -1) {
                    blank = indexTo;
                }
                try {
                    int minor = Integer.parseInt(buffer.substringTrimmed(i, blank));
                    cursor.updatePos(blank);
                    return createProtocolVersion(major, minor);
                } catch (NumberFormatException e) {
                    throw new ParseException("Invalid protocol minor version number: " + buffer.substring(indexFrom, indexTo));
                }
            } catch (NumberFormatException e2) {
                throw new ParseException("Invalid protocol major version number: " + buffer.substring(indexFrom, indexTo));
            }
        }
        throw new ParseException("Not a valid protocol version: " + buffer.substring(indexFrom, indexTo));
    }

    protected ProtocolVersion createProtocolVersion(int major, int minor) {
        return this.protocol.forVersion(major, minor);
    }

    public boolean hasProtocolVersion(CharArrayBuffer buffer, ParserCursor cursor) {
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        int index = cursor.getPos();
        String protoname = this.protocol.getProtocol();
        int protolength = protoname.length();
        if (buffer.length() < protolength + 4) {
            return false;
        }
        if (index < 0) {
            index = (buffer.length() - 4) - protolength;
        } else if (index == 0) {
            while (index < buffer.length() && HTTP.isWhitespace(buffer.charAt(index))) {
                index++;
            }
        }
        if ((index + protolength) + 4 > buffer.length()) {
            return false;
        }
        boolean ok = true;
        int j = 0;
        while (ok && j < protolength) {
            if (buffer.charAt(index + j) == protoname.charAt(j)) {
                ok = true;
            } else {
                ok = false;
            }
            j++;
        }
        if (ok) {
            if (buffer.charAt(index + protolength) == '/') {
                ok = true;
            } else {
                ok = false;
            }
        }
        return ok;
    }

    public static RequestLine parseRequestLine(String value, LineParser parser) throws ParseException {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        ParserCursor cursor = new ParserCursor(0, value.length());
        if (parser == null) {
            parser = INSTANCE;
        }
        return parser.parseRequestLine(buffer, cursor);
    }

    public RequestLine parseRequestLine(CharArrayBuffer buffer, ParserCursor cursor) throws ParseException {
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        int indexFrom = cursor.getPos();
        int indexTo = cursor.getUpperBound();
        try {
            skipWhitespace(buffer, cursor);
            int i = cursor.getPos();
            int blank = buffer.indexOf(32, i, indexTo);
            if (blank < 0) {
                throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
            }
            String method = buffer.substringTrimmed(i, blank);
            cursor.updatePos(blank);
            skipWhitespace(buffer, cursor);
            i = cursor.getPos();
            blank = buffer.indexOf(32, i, indexTo);
            if (blank < 0) {
                throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
            }
            String uri = buffer.substringTrimmed(i, blank);
            cursor.updatePos(blank);
            ProtocolVersion ver = parseProtocolVersion(buffer, cursor);
            skipWhitespace(buffer, cursor);
            if (cursor.atEnd()) {
                return createRequestLine(method, uri, ver);
            }
            throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
        }
    }

    protected RequestLine createRequestLine(String method, String uri, ProtocolVersion ver) {
        return new BasicRequestLine(method, uri, ver);
    }

    public static StatusLine parseStatusLine(String value, LineParser parser) throws ParseException {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        ParserCursor cursor = new ParserCursor(0, value.length());
        if (parser == null) {
            parser = INSTANCE;
        }
        return parser.parseStatusLine(buffer, cursor);
    }

    public StatusLine parseStatusLine(CharArrayBuffer buffer, ParserCursor cursor) throws ParseException {
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        int indexFrom = cursor.getPos();
        int indexTo = cursor.getUpperBound();
        try {
            String reasonPhrase;
            ProtocolVersion ver = parseProtocolVersion(buffer, cursor);
            skipWhitespace(buffer, cursor);
            int i = cursor.getPos();
            int blank = buffer.indexOf(32, i, indexTo);
            if (blank < 0) {
                blank = indexTo;
            }
            String s = buffer.substringTrimmed(i, blank);
            int j = 0;
            while (j < s.length()) {
                if (Character.isDigit(s.charAt(j))) {
                    j++;
                } else {
                    throw new ParseException("Status line contains invalid status code: " + buffer.substring(indexFrom, indexTo));
                }
            }
            int statusCode = Integer.parseInt(s);
            i = blank;
            if (i < indexTo) {
                reasonPhrase = buffer.substringTrimmed(i, indexTo);
            } else {
                reasonPhrase = BuildConfig.FLAVOR;
            }
            return createStatusLine(ver, statusCode, reasonPhrase);
        } catch (NumberFormatException e) {
            throw new ParseException("Status line contains invalid status code: " + buffer.substring(indexFrom, indexTo));
        } catch (IndexOutOfBoundsException e2) {
            throw new ParseException("Invalid status line: " + buffer.substring(indexFrom, indexTo));
        }
    }

    protected StatusLine createStatusLine(ProtocolVersion ver, int status, String reason) {
        return new BasicStatusLine(ver, status, reason);
    }

    public static Header parseHeader(String value, LineParser parser) throws ParseException {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        if (parser == null) {
            parser = INSTANCE;
        }
        return parser.parseHeader(buffer);
    }

    public Header parseHeader(CharArrayBuffer buffer) throws ParseException {
        return new BufferedHeader(buffer);
    }

    protected void skipWhitespace(CharArrayBuffer buffer, ParserCursor cursor) {
        int pos = cursor.getPos();
        int indexTo = cursor.getUpperBound();
        while (pos < indexTo && HTTP.isWhitespace(buffer.charAt(pos))) {
            pos++;
        }
        cursor.updatePos(pos);
    }
}
