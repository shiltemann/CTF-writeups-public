package org.apache.http.impl.io;

import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.io.HttpMessageWriter;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.LineFormatter;

@Immutable
public class DefaultHttpResponseWriterFactory implements HttpMessageWriterFactory<HttpResponse> {
    public static final DefaultHttpResponseWriterFactory INSTANCE;
    private final LineFormatter lineFormatter;

    static {
        INSTANCE = new DefaultHttpResponseWriterFactory();
    }

    public DefaultHttpResponseWriterFactory(LineFormatter lineFormatter) {
        if (lineFormatter == null) {
            lineFormatter = BasicLineFormatter.INSTANCE;
        }
        this.lineFormatter = lineFormatter;
    }

    public DefaultHttpResponseWriterFactory() {
        this(null);
    }

    public HttpMessageWriter<HttpResponse> create(SessionOutputBuffer buffer) {
        return new DefaultHttpResponseWriter(buffer, this.lineFormatter);
    }
}
