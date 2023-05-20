package org.apache.http.impl.io;

import org.apache.http.HttpRequest;
import org.apache.http.annotation.Immutable;
import org.apache.http.io.HttpMessageWriter;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.LineFormatter;

@Immutable
public class DefaultHttpRequestWriterFactory implements HttpMessageWriterFactory<HttpRequest> {
    public static final DefaultHttpRequestWriterFactory INSTANCE;
    private final LineFormatter lineFormatter;

    static {
        INSTANCE = new DefaultHttpRequestWriterFactory();
    }

    public DefaultHttpRequestWriterFactory(LineFormatter lineFormatter) {
        if (lineFormatter == null) {
            lineFormatter = BasicLineFormatter.INSTANCE;
        }
        this.lineFormatter = lineFormatter;
    }

    public DefaultHttpRequestWriterFactory() {
        this(null);
    }

    public HttpMessageWriter<HttpRequest> create(SessionOutputBuffer buffer) {
        return new DefaultHttpRequestWriter(buffer, this.lineFormatter);
    }
}
