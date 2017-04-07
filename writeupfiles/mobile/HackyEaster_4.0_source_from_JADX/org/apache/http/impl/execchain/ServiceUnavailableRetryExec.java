package org.apache.http.impl.execchain;

import java.io.IOException;
import java.io.InterruptedIOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.Args;

@Immutable
public class ServiceUnavailableRetryExec implements ClientExecChain {
    private final Log log;
    private final ClientExecChain requestExecutor;
    private final ServiceUnavailableRetryStrategy retryStrategy;

    public ServiceUnavailableRetryExec(ClientExecChain requestExecutor, ServiceUnavailableRetryStrategy retryStrategy) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(requestExecutor, "HTTP request executor");
        Args.notNull(retryStrategy, "Retry strategy");
        this.requestExecutor = requestExecutor;
        this.retryStrategy = retryStrategy;
    }

    public CloseableHttpResponse execute(HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware) throws IOException, HttpException {
        Header[] origheaders = request.getAllHeaders();
        int c = 1;
        while (true) {
            CloseableHttpResponse response = this.requestExecutor.execute(route, request, context, execAware);
            if (!this.retryStrategy.retryRequest(response, c, context)) {
                return response;
            }
            response.close();
            long nextInterval = this.retryStrategy.getRetryInterval();
            if (nextInterval > 0) {
                try {
                    this.log.trace("Wait for " + nextInterval);
                    Thread.sleep(nextInterval);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new InterruptedIOException();
                } catch (RuntimeException ex) {
                    response.close();
                    throw ex;
                }
            }
            request.setHeaders(origheaders);
            c++;
        }
    }
}
