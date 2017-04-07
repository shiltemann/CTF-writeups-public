package org.apache.http.message;

import java.util.BitSet;
import org.apache.http.annotation.Immutable;
import org.apache.http.util.CharArrayBuffer;

@Immutable
public class TokenParser {
    public static final char CR = '\r';
    public static final char DQUOTE = '\"';
    public static final char ESCAPE = '\\';
    public static final char HT = '\t';
    public static final TokenParser INSTANCE;
    public static final char LF = '\n';
    public static final char SP = ' ';

    public static BitSet INIT_BITSET(int... b) {
        BitSet bitset = new BitSet();
        for (int aB : b) {
            bitset.set(aB);
        }
        return bitset;
    }

    public static boolean isWhitespace(char ch) {
        return ch == SP || ch == HT || ch == CR || ch == LF;
    }

    static {
        INSTANCE = new TokenParser();
    }

    public String parseToken(CharArrayBuffer buf, ParserCursor cursor, BitSet delimiters) {
        StringBuilder dst = new StringBuilder();
        boolean whitespace = false;
        while (!cursor.atEnd()) {
            char current = buf.charAt(cursor.getPos());
            if (delimiters != null && delimiters.get(current)) {
                break;
            } else if (isWhitespace(current)) {
                skipWhiteSpace(buf, cursor);
                whitespace = true;
            } else {
                if (whitespace && dst.length() > 0) {
                    dst.append(SP);
                }
                copyContent(buf, cursor, delimiters, dst);
                whitespace = false;
            }
        }
        return dst.toString();
    }

    public String parseValue(CharArrayBuffer buf, ParserCursor cursor, BitSet delimiters) {
        StringBuilder dst = new StringBuilder();
        boolean whitespace = false;
        while (!cursor.atEnd()) {
            char current = buf.charAt(cursor.getPos());
            if (delimiters != null && delimiters.get(current)) {
                break;
            } else if (isWhitespace(current)) {
                skipWhiteSpace(buf, cursor);
                whitespace = true;
            } else if (current == DQUOTE) {
                if (whitespace && dst.length() > 0) {
                    dst.append(SP);
                }
                copyQuotedContent(buf, cursor, dst);
                whitespace = false;
            } else {
                if (whitespace && dst.length() > 0) {
                    dst.append(SP);
                }
                copyUnquotedContent(buf, cursor, delimiters, dst);
                whitespace = false;
            }
        }
        return dst.toString();
    }

    public void skipWhiteSpace(CharArrayBuffer buf, ParserCursor cursor) {
        int pos = cursor.getPos();
        int indexFrom = cursor.getPos();
        int indexTo = cursor.getUpperBound();
        int i = indexFrom;
        while (i < indexTo && isWhitespace(buf.charAt(i))) {
            pos++;
            i++;
        }
        cursor.updatePos(pos);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void copyContent(org.apache.http.util.CharArrayBuffer r7, org.apache.http.message.ParserCursor r8, java.util.BitSet r9, java.lang.StringBuilder r10) {
        /*
        r6 = this;
        r4 = r8.getPos();
        r2 = r8.getPos();
        r3 = r8.getUpperBound();
        r1 = r2;
    L_0x000d:
        if (r1 >= r3) goto L_0x0021;
    L_0x000f:
        r0 = r7.charAt(r1);
        if (r9 == 0) goto L_0x001b;
    L_0x0015:
        r5 = r9.get(r0);
        if (r5 != 0) goto L_0x0021;
    L_0x001b:
        r5 = isWhitespace(r0);
        if (r5 == 0) goto L_0x0025;
    L_0x0021:
        r8.updatePos(r4);
        return;
    L_0x0025:
        r4 = r4 + 1;
        r10.append(r0);
        r1 = r1 + 1;
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.message.TokenParser.copyContent(org.apache.http.util.CharArrayBuffer, org.apache.http.message.ParserCursor, java.util.BitSet, java.lang.StringBuilder):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void copyUnquotedContent(org.apache.http.util.CharArrayBuffer r7, org.apache.http.message.ParserCursor r8, java.util.BitSet r9, java.lang.StringBuilder r10) {
        /*
        r6 = this;
        r4 = r8.getPos();
        r2 = r8.getPos();
        r3 = r8.getUpperBound();
        r1 = r2;
    L_0x000d:
        if (r1 >= r3) goto L_0x0025;
    L_0x000f:
        r0 = r7.charAt(r1);
        if (r9 == 0) goto L_0x001b;
    L_0x0015:
        r5 = r9.get(r0);
        if (r5 != 0) goto L_0x0025;
    L_0x001b:
        r5 = isWhitespace(r0);
        if (r5 != 0) goto L_0x0025;
    L_0x0021:
        r5 = 34;
        if (r0 != r5) goto L_0x0029;
    L_0x0025:
        r8.updatePos(r4);
        return;
    L_0x0029:
        r4 = r4 + 1;
        r10.append(r0);
        r1 = r1 + 1;
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.message.TokenParser.copyUnquotedContent(org.apache.http.util.CharArrayBuffer, org.apache.http.message.ParserCursor, java.util.BitSet, java.lang.StringBuilder):void");
    }

    public void copyQuotedContent(CharArrayBuffer buf, ParserCursor cursor, StringBuilder dst) {
        if (!cursor.atEnd()) {
            int pos = cursor.getPos();
            int indexFrom = cursor.getPos();
            int indexTo = cursor.getUpperBound();
            if (buf.charAt(pos) == DQUOTE) {
                pos++;
                boolean escaped = false;
                int i = indexFrom + 1;
                while (i < indexTo) {
                    char current = buf.charAt(i);
                    if (escaped) {
                        if (!(current == DQUOTE || current == ESCAPE)) {
                            dst.append(ESCAPE);
                        }
                        dst.append(current);
                        escaped = false;
                    } else if (current == DQUOTE) {
                        pos++;
                        break;
                    } else if (current == ESCAPE) {
                        escaped = true;
                    } else if (!(current == CR || current == LF)) {
                        dst.append(current);
                    }
                    i++;
                    pos++;
                }
                cursor.updatePos(pos);
            }
        }
    }
}
