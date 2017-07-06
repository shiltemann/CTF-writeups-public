package org.apache.http.impl.cookie;

import java.io.IOException;
import java.io.Reader;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.util.PublicSuffixList;

@Immutable
@Deprecated
public class PublicSuffixListParser {
    private final PublicSuffixFilter filter;
    private final org.apache.http.conn.util.PublicSuffixListParser parser;

    PublicSuffixListParser(PublicSuffixFilter filter) {
        this.filter = filter;
        this.parser = new org.apache.http.conn.util.PublicSuffixListParser();
    }

    public void parse(Reader reader) throws IOException {
        PublicSuffixList suffixList = this.parser.parse(reader);
        this.filter.setPublicSuffixes(suffixList.getRules());
        this.filter.setExceptions(suffixList.getExceptions());
    }
}
