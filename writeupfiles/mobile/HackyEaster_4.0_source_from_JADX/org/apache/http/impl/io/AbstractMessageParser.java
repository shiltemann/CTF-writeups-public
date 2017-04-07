package org.apache.http.impl.io;

import android.support.v4.app.NotificationCompat.WearableExtender;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.config.MessageConstraints;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.params.HttpParamConfig;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@NotThreadSafe
public abstract class AbstractMessageParser<T extends HttpMessage> implements HttpMessageParser<T> {
    private static final int HEADERS = 1;
    private static final int HEAD_LINE = 0;
    private final List<CharArrayBuffer> headerLines;
    protected final LineParser lineParser;
    private T message;
    private final MessageConstraints messageConstraints;
    private final SessionInputBuffer sessionBuffer;
    private int state;

    protected abstract T parseHead(SessionInputBuffer sessionInputBuffer) throws IOException, HttpException, ParseException;

    @Deprecated
    public AbstractMessageParser(SessionInputBuffer buffer, LineParser parser, HttpParams params) {
        Args.notNull(buffer, "Session input buffer");
        Args.notNull(params, "HTTP parameters");
        this.sessionBuffer = buffer;
        this.messageConstraints = HttpParamConfig.getMessageConstraints(params);
        if (parser == null) {
            parser = BasicLineParser.INSTANCE;
        }
        this.lineParser = parser;
        this.headerLines = new ArrayList();
        this.state = 0;
    }

    public AbstractMessageParser(SessionInputBuffer buffer, LineParser lineParser, MessageConstraints constraints) {
        this.sessionBuffer = (SessionInputBuffer) Args.notNull(buffer, "Session input buffer");
        if (lineParser == null) {
            lineParser = BasicLineParser.INSTANCE;
        }
        this.lineParser = lineParser;
        if (constraints == null) {
            constraints = MessageConstraints.DEFAULT;
        }
        this.messageConstraints = constraints;
        this.headerLines = new ArrayList();
        this.state = 0;
    }

    public static Header[] parseHeaders(SessionInputBuffer inbuffer, int maxHeaderCount, int maxLineLen, LineParser parser) throws HttpException, IOException {
        List<CharArrayBuffer> headerLines = new ArrayList();
        if (parser == null) {
            parser = BasicLineParser.INSTANCE;
        }
        return parseHeaders(inbuffer, maxHeaderCount, maxLineLen, parser, headerLines);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.apache.http.Header[] parseHeaders(org.apache.http.io.SessionInputBuffer r10, int r11, int r12, org.apache.http.message.LineParser r13, java.util.List<org.apache.http.util.CharArrayBuffer> r14) throws org.apache.http.HttpException, java.io.IOException {
        /*
        r8 = "Session input buffer";
        org.apache.http.util.Args.notNull(r10, r8);
        r8 = "Line parser";
        org.apache.http.util.Args.notNull(r13, r8);
        r8 = "Header line list";
        org.apache.http.util.Args.notNull(r14, r8);
        r2 = 0;
        r7 = 0;
    L_0x0011:
        if (r2 != 0) goto L_0x0044;
    L_0x0013:
        r2 = new org.apache.http.util.CharArrayBuffer;
        r8 = 64;
        r2.<init>(r8);
    L_0x001a:
        r6 = r10.readLine(r2);
        r8 = -1;
        if (r6 == r8) goto L_0x0028;
    L_0x0021:
        r8 = r2.length();
        r9 = 1;
        if (r8 >= r9) goto L_0x0048;
    L_0x0028:
        r8 = r14.size();
        r4 = new org.apache.http.Header[r8];
        r5 = 0;
    L_0x002f:
        r8 = r14.size();
        if (r5 >= r8) goto L_0x00b8;
    L_0x0035:
        r0 = r14.get(r5);
        r0 = (org.apache.http.util.CharArrayBuffer) r0;
        r8 = r13.parseHeader(r0);	 Catch:{ ParseException -> 0x00ad }
        r4[r5] = r8;	 Catch:{ ParseException -> 0x00ad }
        r5 = r5 + 1;
        goto L_0x002f;
    L_0x0044:
        r2.clear();
        goto L_0x001a;
    L_0x0048:
        r8 = 0;
        r8 = r2.charAt(r8);
        r9 = 32;
        if (r8 == r9) goto L_0x005a;
    L_0x0051:
        r8 = 0;
        r8 = r2.charAt(r8);
        r9 = 9;
        if (r8 != r9) goto L_0x00a7;
    L_0x005a:
        if (r7 == 0) goto L_0x00a7;
    L_0x005c:
        r5 = 0;
    L_0x005d:
        r8 = r2.length();
        if (r5 >= r8) goto L_0x006f;
    L_0x0063:
        r1 = r2.charAt(r5);
        r8 = 32;
        if (r1 == r8) goto L_0x0087;
    L_0x006b:
        r8 = 9;
        if (r1 == r8) goto L_0x0087;
    L_0x006f:
        if (r12 <= 0) goto L_0x008a;
    L_0x0071:
        r8 = r7.length();
        r8 = r8 + 1;
        r9 = r2.length();
        r8 = r8 + r9;
        r8 = r8 - r5;
        if (r8 <= r12) goto L_0x008a;
    L_0x007f:
        r8 = new org.apache.http.MessageConstraintException;
        r9 = "Maximum line length limit exceeded";
        r8.<init>(r9);
        throw r8;
    L_0x0087:
        r5 = r5 + 1;
        goto L_0x005d;
    L_0x008a:
        r8 = 32;
        r7.append(r8);
        r8 = r2.length();
        r8 = r8 - r5;
        r7.append(r2, r5, r8);
    L_0x0097:
        if (r11 <= 0) goto L_0x0011;
    L_0x0099:
        r8 = r14.size();
        if (r8 < r11) goto L_0x0011;
    L_0x009f:
        r8 = new org.apache.http.MessageConstraintException;
        r9 = "Maximum header count exceeded";
        r8.<init>(r9);
        throw r8;
    L_0x00a7:
        r14.add(r2);
        r7 = r2;
        r2 = 0;
        goto L_0x0097;
    L_0x00ad:
        r3 = move-exception;
        r8 = new org.apache.http.ProtocolException;
        r9 = r3.getMessage();
        r8.<init>(r9);
        throw r8;
    L_0x00b8:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.io.AbstractMessageParser.parseHeaders(org.apache.http.io.SessionInputBuffer, int, int, org.apache.http.message.LineParser, java.util.List):org.apache.http.Header[]");
    }

    public T parse() throws IOException, HttpException {
        switch (this.state) {
            case WearableExtender.SIZE_DEFAULT /*0*/:
                try {
                    this.message = parseHead(this.sessionBuffer);
                    this.state = HEADERS;
                    break;
                } catch (ParseException px) {
                    throw new ProtocolException(px.getMessage(), px);
                }
            case HEADERS /*1*/:
                break;
            default:
                throw new IllegalStateException("Inconsistent parser state");
        }
        this.message.setHeaders(parseHeaders(this.sessionBuffer, this.messageConstraints.getMaxHeaderCount(), this.messageConstraints.getMaxLineLength(), this.lineParser, this.headerLines));
        T result = this.message;
        this.message = null;
        this.headerLines.clear();
        this.state = 0;
        return result;
    }
}
