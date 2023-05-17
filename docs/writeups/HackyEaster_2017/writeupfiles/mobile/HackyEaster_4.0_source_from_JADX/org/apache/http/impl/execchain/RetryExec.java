package org.apache.http.impl.execchain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.util.Args;

@Immutable
public class RetryExec implements ClientExecChain {
    private final Log log;
    private final ClientExecChain requestExecutor;
    private final HttpRequestRetryHandler retryHandler;

    public RetryExec(ClientExecChain requestExecutor, HttpRequestRetryHandler retryHandler) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(requestExecutor, "HTTP request executor");
        Args.notNull(retryHandler, "HTTP request retry handler");
        this.requestExecutor = requestExecutor;
        this.retryHandler = retryHandler;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.client.methods.CloseableHttpResponse execute(org.apache.http.conn.routing.HttpRoute r8, org.apache.http.client.methods.HttpRequestWrapper r9, org.apache.http.client.protocol.HttpClientContext r10, org.apache.http.client.methods.HttpExecutionAware r11) throws java.io.IOException, org.apache.http.HttpException {
        /*
        r7 = this;
        r4 = "HTTP route";
        org.apache.http.util.Args.notNull(r8, r4);
        r4 = "HTTP request";
        org.apache.http.util.Args.notNull(r9, r4);
        r4 = "HTTP context";
        org.apache.http.util.Args.notNull(r10, r4);
        r2 = r9.getAllHeaders();
        r1 = 1;
    L_0x0014:
        r4 = r7.requestExecutor;	 Catch:{ IOException -> 0x001b }
        r4 = r4.execute(r8, r9, r10, r11);	 Catch:{ IOException -> 0x001b }
        return r4;
    L_0x001b:
        r0 = move-exception;
        if (r11 == 0) goto L_0x002c;
    L_0x001e:
        r4 = r11.isAborted();
        if (r4 == 0) goto L_0x002c;
    L_0x0024:
        r4 = r7.log;
        r5 = "Request has been aborted";
        r4.debug(r5);
        throw r0;
    L_0x002c:
        r4 = r7.retryHandler;
        r4 = r4.retryRequest(r0, r1, r10);
        if (r4 == 0) goto L_0x00c1;
    L_0x0034:
        r4 = r7.log;
        r4 = r4.isInfoEnabled();
        if (r4 == 0) goto L_0x0074;
    L_0x003c:
        r4 = r7.log;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "I/O exception (";
        r5 = r5.append(r6);
        r6 = r0.getClass();
        r6 = r6.getName();
        r5 = r5.append(r6);
        r6 = ") caught when processing request to ";
        r5 = r5.append(r6);
        r5 = r5.append(r8);
        r6 = ": ";
        r5 = r5.append(r6);
        r6 = r0.getMessage();
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.info(r5);
    L_0x0074:
        r4 = r7.log;
        r4 = r4.isDebugEnabled();
        if (r4 == 0) goto L_0x0085;
    L_0x007c:
        r4 = r7.log;
        r5 = r0.getMessage();
        r4.debug(r5, r0);
    L_0x0085:
        r4 = org.apache.http.impl.execchain.RequestEntityProxy.isRepeatable(r9);
        if (r4 != 0) goto L_0x009a;
    L_0x008b:
        r4 = r7.log;
        r5 = "Cannot retry non-repeatable request";
        r4.debug(r5);
        r4 = new org.apache.http.client.NonRepeatableRequestException;
        r5 = "Cannot retry request with a non-repeatable request entity";
        r4.<init>(r5, r0);
        throw r4;
    L_0x009a:
        r9.setHeaders(r2);
        r4 = r7.log;
        r4 = r4.isInfoEnabled();
        if (r4 == 0) goto L_0x00bd;
    L_0x00a5:
        r4 = r7.log;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "Retrying request to ";
        r5 = r5.append(r6);
        r5 = r5.append(r8);
        r5 = r5.toString();
        r4.info(r5);
    L_0x00bd:
        r1 = r1 + 1;
        goto L_0x0014;
    L_0x00c1:
        r4 = r0 instanceof org.apache.http.NoHttpResponseException;
        if (r4 == 0) goto L_0x00ed;
    L_0x00c5:
        r3 = new org.apache.http.NoHttpResponseException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = r8.getTargetHost();
        r5 = r5.toHostString();
        r4 = r4.append(r5);
        r5 = " failed to respond";
        r4 = r4.append(r5);
        r4 = r4.toString();
        r3.<init>(r4);
        r4 = r0.getStackTrace();
        r3.setStackTrace(r4);
        throw r3;
    L_0x00ed:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.execchain.RetryExec.execute(org.apache.http.conn.routing.HttpRoute, org.apache.http.client.methods.HttpRequestWrapper, org.apache.http.client.protocol.HttpClientContext, org.apache.http.client.methods.HttpExecutionAware):org.apache.http.client.methods.CloseableHttpResponse");
    }
}
