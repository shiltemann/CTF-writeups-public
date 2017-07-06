package org.apache.http.io;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;

public interface HttpMessageParser<T extends HttpMessage> {
    T parse() throws IOException, HttpException;
}
