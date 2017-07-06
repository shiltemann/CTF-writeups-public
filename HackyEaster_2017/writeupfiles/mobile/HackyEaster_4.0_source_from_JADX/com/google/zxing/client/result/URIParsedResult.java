package com.google.zxing.client.result;

import java.util.regex.Pattern;

public final class URIParsedResult extends ParsedResult {
    private static final Pattern USER_IN_HOST;
    private final String title;
    private final String uri;

    static {
        USER_IN_HOST = Pattern.compile(":/*([^/@]+)@[^/]+");
    }

    public URIParsedResult(String uri, String title) {
        super(ParsedResultType.URI);
        this.uri = massageURI(uri);
        this.title = title;
    }

    public String getURI() {
        return this.uri;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isPossiblyMaliciousURI() {
        return USER_IN_HOST.matcher(this.uri).find();
    }

    public String getDisplayResult() {
        StringBuilder result = new StringBuilder(30);
        ParsedResult.maybeAppend(this.title, result);
        ParsedResult.maybeAppend(this.uri, result);
        return result.toString();
    }

    private static String massageURI(String uri) {
        uri = uri.trim();
        int protocolEnd = uri.indexOf(58);
        if (protocolEnd < 0) {
            return "http://" + uri;
        }
        if (isColonFollowedByPortNumber(uri, protocolEnd)) {
            return "http://" + uri;
        }
        return uri;
    }

    private static boolean isColonFollowedByPortNumber(String uri, int protocolEnd) {
        int nextSlash = uri.indexOf(47, protocolEnd + 1);
        if (nextSlash < 0) {
            nextSlash = uri.length();
        }
        if (nextSlash <= protocolEnd + 1) {
            return false;
        }
        int x = protocolEnd + 1;
        while (x < nextSlash) {
            if (uri.charAt(x) < '0' || uri.charAt(x) > '9') {
                return false;
            }
            x++;
        }
        return true;
    }
}
