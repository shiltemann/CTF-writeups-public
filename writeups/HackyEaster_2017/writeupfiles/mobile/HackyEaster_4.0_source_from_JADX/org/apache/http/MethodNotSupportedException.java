package org.apache.http;

public class MethodNotSupportedException extends HttpException {
    private static final long serialVersionUID = 3365359036840171201L;

    public MethodNotSupportedException(String message) {
        super(message);
    }

    public MethodNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
