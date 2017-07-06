package org.apache.http;

import java.nio.charset.CharacterCodingException;

public class MessageConstraintException extends CharacterCodingException {
    private static final long serialVersionUID = 6077207720446368695L;
    private final String message;

    public MessageConstraintException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
