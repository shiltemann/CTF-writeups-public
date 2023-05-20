package org.apache.http.ssl;

public class SSLInitializationException extends IllegalStateException {
    private static final long serialVersionUID = -8243587425648536702L;

    public SSLInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
