package org.apache.http.cookie;

import org.apache.http.params.HttpParams;

@Deprecated
public interface CookieSpecFactory {
    CookieSpec newInstance(HttpParams httpParams);
}
