package org.apache.http.protocol;

import java.util.List;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;

public class HttpProcessorBuilder {
    private ChainBuilder<HttpRequestInterceptor> requestChainBuilder;
    private ChainBuilder<HttpResponseInterceptor> responseChainBuilder;

    public static HttpProcessorBuilder create() {
        return new HttpProcessorBuilder();
    }

    HttpProcessorBuilder() {
    }

    private ChainBuilder<HttpRequestInterceptor> getRequestChainBuilder() {
        if (this.requestChainBuilder == null) {
            this.requestChainBuilder = new ChainBuilder();
        }
        return this.requestChainBuilder;
    }

    private ChainBuilder<HttpResponseInterceptor> getResponseChainBuilder() {
        if (this.responseChainBuilder == null) {
            this.responseChainBuilder = new ChainBuilder();
        }
        return this.responseChainBuilder;
    }

    public HttpProcessorBuilder addFirst(HttpRequestInterceptor e) {
        if (e != null) {
            getRequestChainBuilder().addFirst(e);
        }
        return this;
    }

    public HttpProcessorBuilder addLast(HttpRequestInterceptor e) {
        if (e != null) {
            getRequestChainBuilder().addLast(e);
        }
        return this;
    }

    public HttpProcessorBuilder add(HttpRequestInterceptor e) {
        return addLast(e);
    }

    public HttpProcessorBuilder addAllFirst(HttpRequestInterceptor... e) {
        if (e != null) {
            getRequestChainBuilder().addAllFirst((Object[]) e);
        }
        return this;
    }

    public HttpProcessorBuilder addAllLast(HttpRequestInterceptor... e) {
        if (e != null) {
            getRequestChainBuilder().addAllLast((Object[]) e);
        }
        return this;
    }

    public HttpProcessorBuilder addAll(HttpRequestInterceptor... e) {
        return addAllLast(e);
    }

    public HttpProcessorBuilder addFirst(HttpResponseInterceptor e) {
        if (e != null) {
            getResponseChainBuilder().addFirst(e);
        }
        return this;
    }

    public HttpProcessorBuilder addLast(HttpResponseInterceptor e) {
        if (e != null) {
            getResponseChainBuilder().addLast(e);
        }
        return this;
    }

    public HttpProcessorBuilder add(HttpResponseInterceptor e) {
        return addLast(e);
    }

    public HttpProcessorBuilder addAllFirst(HttpResponseInterceptor... e) {
        if (e != null) {
            getResponseChainBuilder().addAllFirst((Object[]) e);
        }
        return this;
    }

    public HttpProcessorBuilder addAllLast(HttpResponseInterceptor... e) {
        if (e != null) {
            getResponseChainBuilder().addAllLast((Object[]) e);
        }
        return this;
    }

    public HttpProcessorBuilder addAll(HttpResponseInterceptor... e) {
        return addAllLast(e);
    }

    public HttpProcessor build() {
        List list = null;
        List build = this.requestChainBuilder != null ? this.requestChainBuilder.build() : null;
        if (this.responseChainBuilder != null) {
            list = this.responseChainBuilder.build();
        }
        return new ImmutableHttpProcessor(build, list);
    }
}
