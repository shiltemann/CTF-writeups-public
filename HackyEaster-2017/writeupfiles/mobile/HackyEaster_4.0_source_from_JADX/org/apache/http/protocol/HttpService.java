package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpServerConnection;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.ProtocolException;
import org.apache.http.UnsupportedHttpVersionException;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

@Immutable
public class HttpService {
    private volatile ConnectionReuseStrategy connStrategy;
    private volatile HttpExpectationVerifier expectationVerifier;
    private volatile HttpRequestHandlerMapper handlerMapper;
    private volatile HttpParams params;
    private volatile HttpProcessor processor;
    private volatile HttpResponseFactory responseFactory;

    @Deprecated
    private static class HttpRequestHandlerResolverAdapter implements HttpRequestHandlerMapper {
        private final HttpRequestHandlerResolver resolver;

        public HttpRequestHandlerResolverAdapter(HttpRequestHandlerResolver resolver) {
            this.resolver = resolver;
        }

        public HttpRequestHandler lookup(HttpRequest request) {
            return this.resolver.lookup(request.getRequestLine().getUri());
        }
    }

    @Deprecated
    public HttpService(HttpProcessor processor, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory, HttpRequestHandlerResolver handlerResolver, HttpExpectationVerifier expectationVerifier, HttpParams params) {
        this(processor, connStrategy, responseFactory, new HttpRequestHandlerResolverAdapter(handlerResolver), expectationVerifier);
        this.params = params;
    }

    @Deprecated
    public HttpService(HttpProcessor processor, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory, HttpRequestHandlerResolver handlerResolver, HttpParams params) {
        this(processor, connStrategy, responseFactory, new HttpRequestHandlerResolverAdapter(handlerResolver), null);
        this.params = params;
    }

    @Deprecated
    public HttpService(HttpProcessor proc, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory) {
        this.params = null;
        this.processor = null;
        this.handlerMapper = null;
        this.connStrategy = null;
        this.responseFactory = null;
        this.expectationVerifier = null;
        setHttpProcessor(proc);
        setConnReuseStrategy(connStrategy);
        setResponseFactory(responseFactory);
    }

    public HttpService(HttpProcessor processor, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory, HttpRequestHandlerMapper handlerMapper, HttpExpectationVerifier expectationVerifier) {
        this.params = null;
        this.processor = null;
        this.handlerMapper = null;
        this.connStrategy = null;
        this.responseFactory = null;
        this.expectationVerifier = null;
        this.processor = (HttpProcessor) Args.notNull(processor, "HTTP processor");
        if (connStrategy == null) {
            connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
        }
        this.connStrategy = connStrategy;
        if (responseFactory == null) {
            responseFactory = DefaultHttpResponseFactory.INSTANCE;
        }
        this.responseFactory = responseFactory;
        this.handlerMapper = handlerMapper;
        this.expectationVerifier = expectationVerifier;
    }

    public HttpService(HttpProcessor processor, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory, HttpRequestHandlerMapper handlerMapper) {
        this(processor, connStrategy, responseFactory, handlerMapper, null);
    }

    public HttpService(HttpProcessor processor, HttpRequestHandlerMapper handlerMapper) {
        this(processor, null, null, handlerMapper, null);
    }

    @Deprecated
    public void setHttpProcessor(HttpProcessor processor) {
        Args.notNull(processor, "HTTP processor");
        this.processor = processor;
    }

    @Deprecated
    public void setConnReuseStrategy(ConnectionReuseStrategy connStrategy) {
        Args.notNull(connStrategy, "Connection reuse strategy");
        this.connStrategy = connStrategy;
    }

    @Deprecated
    public void setResponseFactory(HttpResponseFactory responseFactory) {
        Args.notNull(responseFactory, "Response factory");
        this.responseFactory = responseFactory;
    }

    @Deprecated
    public void setParams(HttpParams params) {
        this.params = params;
    }

    @Deprecated
    public void setHandlerResolver(HttpRequestHandlerResolver handlerResolver) {
        this.handlerMapper = new HttpRequestHandlerResolverAdapter(handlerResolver);
    }

    @Deprecated
    public void setExpectationVerifier(HttpExpectationVerifier expectationVerifier) {
        this.expectationVerifier = expectationVerifier;
    }

    @Deprecated
    public HttpParams getParams() {
        return this.params;
    }

    public void handleRequest(HttpServerConnection conn, HttpContext context) throws IOException, HttpException {
        context.setAttribute(HttpCoreContext.HTTP_CONNECTION, conn);
        HttpRequest request = null;
        HttpResponse response = null;
        try {
            request = conn.receiveRequestHeader();
            if (request instanceof HttpEntityEnclosingRequest) {
                if (((HttpEntityEnclosingRequest) request).expectContinue()) {
                    response = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, 100, context);
                    if (this.expectationVerifier != null) {
                        try {
                            this.expectationVerifier.verify(request, response, context);
                        } catch (HttpException ex) {
                            response = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, HttpStatus.SC_INTERNAL_SERVER_ERROR, context);
                            handleException(ex, response);
                        }
                    }
                    if (response.getStatusLine().getStatusCode() < HttpStatus.SC_OK) {
                        conn.sendResponseHeader(response);
                        conn.flush();
                        response = null;
                        conn.receiveRequestEntity((HttpEntityEnclosingRequest) request);
                    }
                } else {
                    conn.receiveRequestEntity((HttpEntityEnclosingRequest) request);
                }
            }
            context.setAttribute(HttpCoreContext.HTTP_REQUEST, request);
            if (response == null) {
                response = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, context);
                this.processor.process(request, context);
                doService(request, response, context);
            }
            if (request instanceof HttpEntityEnclosingRequest) {
                EntityUtils.consume(((HttpEntityEnclosingRequest) request).getEntity());
            }
        } catch (HttpException ex2) {
            response = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, HttpStatus.SC_INTERNAL_SERVER_ERROR, context);
            handleException(ex2, response);
        }
        context.setAttribute(HttpCoreContext.HTTP_RESPONSE, response);
        this.processor.process(response, context);
        conn.sendResponseHeader(response);
        if (canResponseHaveBody(request, response)) {
            conn.sendResponseEntity(response);
        }
        conn.flush();
        if (!this.connStrategy.keepAlive(response, context)) {
            conn.close();
        }
    }

    private boolean canResponseHaveBody(HttpRequest request, HttpResponse response) {
        if (request != null && HttpHead.METHOD_NAME.equalsIgnoreCase(request.getRequestLine().getMethod())) {
            return false;
        }
        int status = response.getStatusLine().getStatusCode();
        if (status < HttpStatus.SC_OK || status == HttpStatus.SC_NO_CONTENT || status == HttpStatus.SC_NOT_MODIFIED || status == HttpStatus.SC_RESET_CONTENT) {
            return false;
        }
        return true;
    }

    protected void handleException(HttpException ex, HttpResponse response) {
        if (ex instanceof MethodNotSupportedException) {
            response.setStatusCode(HttpStatus.SC_NOT_IMPLEMENTED);
        } else if (ex instanceof UnsupportedHttpVersionException) {
            response.setStatusCode(HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED);
        } else if (ex instanceof ProtocolException) {
            response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
        } else {
            response.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
        String message = ex.getMessage();
        if (message == null) {
            message = ex.toString();
        }
        ByteArrayEntity entity = new ByteArrayEntity(EncodingUtils.getAsciiBytes(message));
        entity.setContentType("text/plain; charset=US-ASCII");
        response.setEntity(entity);
    }

    protected void doService(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        HttpRequestHandler handler = null;
        if (this.handlerMapper != null) {
            handler = this.handlerMapper.lookup(request);
        }
        if (handler != null) {
            handler.handle(request, response, context);
        } else {
            response.setStatusCode(HttpStatus.SC_NOT_IMPLEMENTED);
        }
    }
}
