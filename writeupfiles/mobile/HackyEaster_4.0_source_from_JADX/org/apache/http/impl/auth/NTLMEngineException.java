package org.apache.http.impl.auth;

import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AuthenticationException;

@Immutable
public class NTLMEngineException extends AuthenticationException {
    private static final long serialVersionUID = 6027981323731768824L;

    public NTLMEngineException(String message) {
        super(message);
    }

    public NTLMEngineException(String message, Throwable cause) {
        super(message, cause);
    }
}
