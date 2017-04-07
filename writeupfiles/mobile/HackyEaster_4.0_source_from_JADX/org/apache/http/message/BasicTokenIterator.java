package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.HeaderIterator;
import org.apache.http.ParseException;
import org.apache.http.TokenIterator;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.util.Args;

@NotThreadSafe
public class BasicTokenIterator implements TokenIterator {
    public static final String HTTP_SEPARATORS = " ,;=()<>@:\\\"/[]?{}\t";
    protected String currentHeader;
    protected String currentToken;
    protected final HeaderIterator headerIt;
    protected int searchPos;

    public BasicTokenIterator(HeaderIterator headerIterator) {
        this.headerIt = (HeaderIterator) Args.notNull(headerIterator, "Header iterator");
        this.searchPos = findNext(-1);
    }

    public boolean hasNext() {
        return this.currentToken != null;
    }

    public String nextToken() throws NoSuchElementException, ParseException {
        if (this.currentToken == null) {
            throw new NoSuchElementException("Iteration already finished.");
        }
        String result = this.currentToken;
        this.searchPos = findNext(this.searchPos);
        return result;
    }

    public final Object next() throws NoSuchElementException, ParseException {
        return nextToken();
    }

    public final void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Removing tokens is not supported.");
    }

    protected int findNext(int pos) throws ParseException {
        int from = pos;
        if (from >= 0) {
            from = findTokenSeparator(from);
        } else if (!this.headerIt.hasNext()) {
            return -1;
        } else {
            this.currentHeader = this.headerIt.nextHeader().getValue();
            from = 0;
        }
        int start = findTokenStart(from);
        if (start < 0) {
            this.currentToken = null;
            return -1;
        }
        int end = findTokenEnd(start);
        this.currentToken = createToken(this.currentHeader, start, end);
        return end;
    }

    protected String createToken(String value, int start, int end) {
        return value.substring(start, end);
    }

    protected int findTokenStart(int pos) {
        int from = Args.notNegative(pos, "Search position");
        boolean found = false;
        while (!found && this.currentHeader != null) {
            int to = this.currentHeader.length();
            while (!found && from < to) {
                char ch = this.currentHeader.charAt(from);
                if (isTokenSeparator(ch) || isWhitespace(ch)) {
                    from++;
                } else if (isTokenChar(this.currentHeader.charAt(from))) {
                    found = true;
                } else {
                    throw new ParseException("Invalid character before token (pos " + from + "): " + this.currentHeader);
                }
            }
            if (!found) {
                if (this.headerIt.hasNext()) {
                    this.currentHeader = this.headerIt.nextHeader().getValue();
                    from = 0;
                } else {
                    this.currentHeader = null;
                }
            }
        }
        return found ? from : -1;
    }

    protected int findTokenSeparator(int pos) {
        int from = Args.notNegative(pos, "Search position");
        boolean found = false;
        int to = this.currentHeader.length();
        while (!found && from < to) {
            char ch = this.currentHeader.charAt(from);
            if (isTokenSeparator(ch)) {
                found = true;
            } else if (isWhitespace(ch)) {
                from++;
            } else if (isTokenChar(ch)) {
                throw new ParseException("Tokens without separator (pos " + from + "): " + this.currentHeader);
            } else {
                throw new ParseException("Invalid character after token (pos " + from + "): " + this.currentHeader);
            }
        }
        return from;
    }

    protected int findTokenEnd(int from) {
        Args.notNegative(from, "Search position");
        int to = this.currentHeader.length();
        int end = from + 1;
        while (end < to && isTokenChar(this.currentHeader.charAt(end))) {
            end++;
        }
        return end;
    }

    protected boolean isTokenSeparator(char ch) {
        return ch == ',';
    }

    protected boolean isWhitespace(char ch) {
        return ch == '\t' || Character.isSpaceChar(ch);
    }

    protected boolean isTokenChar(char ch) {
        if (Character.isLetterOrDigit(ch)) {
            return true;
        }
        if (Character.isISOControl(ch)) {
            return false;
        }
        if (isHttpSeparator(ch)) {
            return false;
        }
        return true;
    }

    protected boolean isHttpSeparator(char ch) {
        return HTTP_SEPARATORS.indexOf(ch) >= 0;
    }
}
