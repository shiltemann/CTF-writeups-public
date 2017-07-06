package org.apache.http.message;

import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.annotation.Immutable;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Immutable
public class BasicLineFormatter implements LineFormatter {
    @Deprecated
    public static final BasicLineFormatter DEFAULT;
    public static final BasicLineFormatter INSTANCE;

    static {
        DEFAULT = new BasicLineFormatter();
        INSTANCE = new BasicLineFormatter();
    }

    protected CharArrayBuffer initBuffer(CharArrayBuffer charBuffer) {
        CharArrayBuffer buffer = charBuffer;
        if (buffer == null) {
            return new CharArrayBuffer(64);
        }
        buffer.clear();
        return buffer;
    }

    public static String formatProtocolVersion(ProtocolVersion version, LineFormatter formatter) {
        if (formatter == null) {
            formatter = INSTANCE;
        }
        return formatter.appendProtocolVersion(null, version).toString();
    }

    public CharArrayBuffer appendProtocolVersion(CharArrayBuffer buffer, ProtocolVersion version) {
        Args.notNull(version, "Protocol version");
        CharArrayBuffer result = buffer;
        int len = estimateProtocolVersionLen(version);
        if (result == null) {
            result = new CharArrayBuffer(len);
        } else {
            result.ensureCapacity(len);
        }
        result.append(version.getProtocol());
        result.append('/');
        result.append(Integer.toString(version.getMajor()));
        result.append('.');
        result.append(Integer.toString(version.getMinor()));
        return result;
    }

    protected int estimateProtocolVersionLen(ProtocolVersion version) {
        return version.getProtocol().length() + 4;
    }

    public static String formatRequestLine(RequestLine reqline, LineFormatter formatter) {
        if (formatter == null) {
            formatter = INSTANCE;
        }
        return formatter.formatRequestLine(null, reqline).toString();
    }

    public CharArrayBuffer formatRequestLine(CharArrayBuffer buffer, RequestLine reqline) {
        Args.notNull(reqline, "Request line");
        CharArrayBuffer result = initBuffer(buffer);
        doFormatRequestLine(result, reqline);
        return result;
    }

    protected void doFormatRequestLine(CharArrayBuffer buffer, RequestLine reqline) {
        String method = reqline.getMethod();
        String uri = reqline.getUri();
        buffer.ensureCapacity((((method.length() + 1) + uri.length()) + 1) + estimateProtocolVersionLen(reqline.getProtocolVersion()));
        buffer.append(method);
        buffer.append((char) TokenParser.SP);
        buffer.append(uri);
        buffer.append((char) TokenParser.SP);
        appendProtocolVersion(buffer, reqline.getProtocolVersion());
    }

    public static String formatStatusLine(StatusLine statline, LineFormatter formatter) {
        if (formatter == null) {
            formatter = INSTANCE;
        }
        return formatter.formatStatusLine(null, statline).toString();
    }

    public CharArrayBuffer formatStatusLine(CharArrayBuffer buffer, StatusLine statline) {
        Args.notNull(statline, "Status line");
        CharArrayBuffer result = initBuffer(buffer);
        doFormatStatusLine(result, statline);
        return result;
    }

    protected void doFormatStatusLine(CharArrayBuffer buffer, StatusLine statline) {
        int len = ((estimateProtocolVersionLen(statline.getProtocolVersion()) + 1) + 3) + 1;
        String reason = statline.getReasonPhrase();
        if (reason != null) {
            len += reason.length();
        }
        buffer.ensureCapacity(len);
        appendProtocolVersion(buffer, statline.getProtocolVersion());
        buffer.append((char) TokenParser.SP);
        buffer.append(Integer.toString(statline.getStatusCode()));
        buffer.append((char) TokenParser.SP);
        if (reason != null) {
            buffer.append(reason);
        }
    }

    public static String formatHeader(Header header, LineFormatter formatter) {
        if (formatter == null) {
            formatter = INSTANCE;
        }
        return formatter.formatHeader(null, header).toString();
    }

    public CharArrayBuffer formatHeader(CharArrayBuffer buffer, Header header) {
        Args.notNull(header, "Header");
        if (header instanceof FormattedHeader) {
            return ((FormattedHeader) header).getBuffer();
        }
        CharArrayBuffer result = initBuffer(buffer);
        doFormatHeader(result, header);
        return result;
    }

    protected void doFormatHeader(CharArrayBuffer buffer, Header header) {
        String name = header.getName();
        String value = header.getValue();
        int len = name.length() + 2;
        if (value != null) {
            len += value.length();
        }
        buffer.ensureCapacity(len);
        buffer.append(name);
        buffer.append(": ");
        if (value != null) {
            buffer.append(value);
        }
    }
}
