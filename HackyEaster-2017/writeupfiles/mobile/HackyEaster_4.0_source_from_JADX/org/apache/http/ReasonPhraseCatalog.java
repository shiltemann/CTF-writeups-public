package org.apache.http;

import java.util.Locale;

public interface ReasonPhraseCatalog {
    String getReason(int i, Locale locale);
}
