package com.google.zxing.client.result;

public final class ISBNParsedResult extends ParsedResult {
    private final String isbn;

    ISBNParsedResult(String isbn) {
        super(ParsedResultType.ISBN);
        this.isbn = isbn;
    }

    public String getISBN() {
        return this.isbn;
    }

    public String getDisplayResult() {
        return this.isbn;
    }
}
