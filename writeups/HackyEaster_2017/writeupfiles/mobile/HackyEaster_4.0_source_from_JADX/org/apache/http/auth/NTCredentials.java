package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;
import org.apache.http.annotation.Immutable;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Immutable
public class NTCredentials implements Credentials, Serializable {
    private static final long serialVersionUID = -7385699315228907265L;
    private final String password;
    private final NTUserPrincipal principal;
    private final String workstation;

    public NTCredentials(String usernamePassword) {
        String username;
        Args.notNull(usernamePassword, "Username:password string");
        int atColon = usernamePassword.indexOf(58);
        if (atColon >= 0) {
            username = usernamePassword.substring(0, atColon);
            this.password = usernamePassword.substring(atColon + 1);
        } else {
            username = usernamePassword;
            this.password = null;
        }
        int atSlash = username.indexOf(47);
        if (atSlash >= 0) {
            this.principal = new NTUserPrincipal(username.substring(0, atSlash).toUpperCase(Locale.ROOT), username.substring(atSlash + 1));
        } else {
            this.principal = new NTUserPrincipal(null, username.substring(atSlash + 1));
        }
        this.workstation = null;
    }

    public NTCredentials(String userName, String password, String workstation, String domain) {
        Args.notNull(userName, "User name");
        this.principal = new NTUserPrincipal(domain, userName);
        this.password = password;
        if (workstation != null) {
            this.workstation = workstation.toUpperCase(Locale.ROOT);
        } else {
            this.workstation = null;
        }
    }

    public Principal getUserPrincipal() {
        return this.principal;
    }

    public String getUserName() {
        return this.principal.getUsername();
    }

    public String getPassword() {
        return this.password;
    }

    public String getDomain() {
        return this.principal.getDomain();
    }

    public String getWorkstation() {
        return this.workstation;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, this.principal), this.workstation);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof NTCredentials) {
            NTCredentials that = (NTCredentials) o;
            if (LangUtils.equals(this.principal, that.principal) && LangUtils.equals(this.workstation, that.workstation)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[principal: ");
        buffer.append(this.principal);
        buffer.append("][workstation: ");
        buffer.append(this.workstation);
        buffer.append("]");
        return buffer.toString();
    }
}
