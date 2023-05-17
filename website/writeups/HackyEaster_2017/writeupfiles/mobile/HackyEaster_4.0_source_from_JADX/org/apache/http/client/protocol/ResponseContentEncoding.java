package org.apache.http.client.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.entity.DecompressingEntity;
import org.apache.http.client.entity.DeflateInputStream;
import org.apache.http.client.entity.InputStreamFactory;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

@Immutable
public class ResponseContentEncoding implements HttpResponseInterceptor {
    private static final InputStreamFactory DEFLATE;
    private static final InputStreamFactory GZIP;
    public static final String UNCOMPRESSED = "http.client.response.uncompressed";
    private final Lookup<InputStreamFactory> decoderRegistry;
    private final boolean ignoreUnknown;

    /* renamed from: org.apache.http.client.protocol.ResponseContentEncoding.1 */
    static class C01431 implements InputStreamFactory {
        C01431() {
        }

        public InputStream create(InputStream instream) throws IOException {
            return new GZIPInputStream(instream);
        }
    }

    /* renamed from: org.apache.http.client.protocol.ResponseContentEncoding.2 */
    static class C01442 implements InputStreamFactory {
        C01442() {
        }

        public InputStream create(InputStream instream) throws IOException {
            return new DeflateInputStream(instream);
        }
    }

    static {
        GZIP = new C01431();
        DEFLATE = new C01442();
    }

    public ResponseContentEncoding(Lookup<InputStreamFactory> decoderRegistry, boolean ignoreUnknown) {
        if (decoderRegistry == null) {
            decoderRegistry = RegistryBuilder.create().register("gzip", GZIP).register("x-gzip", GZIP).register("deflate", DEFLATE).build();
        }
        this.decoderRegistry = decoderRegistry;
        this.ignoreUnknown = ignoreUnknown;
    }

    public ResponseContentEncoding(boolean ignoreUnknown) {
        this(null, ignoreUnknown);
    }

    public ResponseContentEncoding(Lookup<InputStreamFactory> decoderRegistry) {
        this(decoderRegistry, true);
    }

    public ResponseContentEncoding() {
        this(null);
    }

    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
        HttpEntity entity = response.getEntity();
        if (HttpClientContext.adapt(context).getRequestConfig().isContentCompressionEnabled() && entity != null && entity.getContentLength() != 0) {
            Header ceheader = entity.getContentEncoding();
            if (ceheader != null) {
                for (HeaderElement codec : ceheader.getElements()) {
                    String codecname = codec.getName().toLowerCase(Locale.ROOT);
                    InputStreamFactory decoderFactory = (InputStreamFactory) this.decoderRegistry.lookup(codecname);
                    if (decoderFactory != null) {
                        response.setEntity(new DecompressingEntity(response.getEntity(), decoderFactory));
                        response.removeHeaders(HTTP.CONTENT_LEN);
                        response.removeHeaders(HTTP.CONTENT_ENCODING);
                        response.removeHeaders(HttpHeaders.CONTENT_MD5);
                    } else if (!(HTTP.IDENTITY_CODING.equals(codecname) || this.ignoreUnknown)) {
                        throw new HttpException("Unsupported Content-Encoding: " + codec.getName());
                    }
                }
            }
        }
    }
}
