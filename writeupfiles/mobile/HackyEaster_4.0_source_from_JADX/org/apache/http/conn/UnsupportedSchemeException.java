package org.apache.http.conn;

import java.io.IOException;
import org.apache.http.annotation.Immutable;

@Immutable
public class UnsupportedSchemeException extends IOException {
    private static final long serialVersionUID = 3597127619218687636L;

    public UnsupportedSchemeException(String message) {
        super(message);
    }
}
