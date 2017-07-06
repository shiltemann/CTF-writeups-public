package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;
import org.apache.http.annotation.Immutable;
import org.apache.http.message.TokenParser;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Immutable
public class NTUserPrincipal implements Principal, Serializable {
    private static final long serialVersionUID = -6870169797924406894L;
    private final String domain;
    private final String ntname;
    private final String username;

    public NTUserPrincipal(String domain, String username) {
        Args.notNull(username, "User name");
        this.username = username;
        if (domain != null) {
            this.domain = domain.toUpperCase(Locale.ROOT);
        } else {
            this.domain = null;
        }
        if (this.domain == null || this.domain.isEmpty()) {
            this.ntname = this.username;
            return;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.domain);
        buffer.append(TokenParser.ESCAPE);
        buffer.append(this.username);
        this.ntname = buffer.toString();
    }

    public String getName() {
        return this.ntname;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getUsername() {
        return this.username;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, this.username), this.domain);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof NTUserPrincipal) {
            NTUserPrincipal that = (NTUserPrincipal) o;
            if (LangUtils.equals(this.username, that.username) && LangUtils.equals(this.domain, that.domain)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return this.ntname;
    }
}
