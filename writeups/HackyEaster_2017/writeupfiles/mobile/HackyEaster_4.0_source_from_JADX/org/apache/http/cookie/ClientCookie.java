package org.apache.http.cookie;

import org.apache.http.annotation.Obsolete;

public interface ClientCookie extends Cookie {
    @Obsolete
    public static final String COMMENTURL_ATTR = "commenturl";
    @Obsolete
    public static final String COMMENT_ATTR = "comment";
    @Obsolete
    public static final String DISCARD_ATTR = "discard";
    public static final String DOMAIN_ATTR = "domain";
    public static final String EXPIRES_ATTR = "expires";
    public static final String MAX_AGE_ATTR = "max-age";
    public static final String PATH_ATTR = "path";
    @Obsolete
    public static final String PORT_ATTR = "port";
    public static final String SECURE_ATTR = "secure";
    @Obsolete
    public static final String VERSION_ATTR = "version";

    boolean containsAttribute(String str);

    String getAttribute(String str);
}
