package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import org.apache.http.annotation.Immutable;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Immutable
public class UsernamePasswordCredentials implements Credentials, Serializable {
    private static final long serialVersionUID = 243343858802739403L;
    private final String password;
    private final BasicUserPrincipal principal;

    public UsernamePasswordCredentials(String usernamePassword) {
        Args.notNull(usernamePassword, "Username:password string");
        int atColon = usernamePassword.indexOf(58);
        if (atColon >= 0) {
            this.principal = new BasicUserPrincipal(usernamePassword.substring(0, atColon));
            this.password = usernamePassword.substring(atColon + 1);
            return;
        }
        this.principal = new BasicUserPrincipal(usernamePassword);
        this.password = null;
    }

    public UsernamePasswordCredentials(String userName, String password) {
        Args.notNull(userName, "Username");
        this.principal = new BasicUserPrincipal(userName);
        this.password = password;
    }

    public Principal getUserPrincipal() {
        return this.principal;
    }

    public String getUserName() {
        return this.principal.getName();
    }

    public String getPassword() {
        return this.password;
    }

    public int hashCode() {
        return this.principal.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UsernamePasswordCredentials) {
            if (LangUtils.equals(this.principal, ((UsernamePasswordCredentials) o).principal)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return this.principal.toString();
    }
}
