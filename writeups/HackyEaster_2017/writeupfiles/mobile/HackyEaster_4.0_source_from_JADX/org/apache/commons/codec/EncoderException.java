package org.apache.commons.codec;

public class EncoderException extends Exception {
    private static final long serialVersionUID = 1;

    public EncoderException(String message) {
        super(message);
    }

    public EncoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncoderException(Throwable cause) {
        super(cause);
    }
}
