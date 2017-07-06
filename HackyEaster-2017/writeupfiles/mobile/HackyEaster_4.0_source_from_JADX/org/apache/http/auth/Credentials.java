package org.apache.http.auth;

import java.security.Principal;

public interface Credentials {
    String getPassword();

    Principal getUserPrincipal();
}
