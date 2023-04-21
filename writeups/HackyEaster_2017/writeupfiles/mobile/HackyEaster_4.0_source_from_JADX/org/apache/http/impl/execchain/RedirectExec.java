package org.apache.http.impl.execchain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.util.Args;

@ThreadSafe
public class RedirectExec implements ClientExecChain {
    private final Log log;
    private final RedirectStrategy redirectStrategy;
    private final ClientExecChain requestExecutor;
    private final HttpRoutePlanner routePlanner;

    public RedirectExec(ClientExecChain requestExecutor, HttpRoutePlanner routePlanner, RedirectStrategy redirectStrategy) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(requestExecutor, "HTTP client request executor");
        Args.notNull(routePlanner, "HTTP route planner");
        Args.notNull(redirectStrategy, "HTTP redirect strategy");
        this.requestExecutor = requestExecutor;
        this.routePlanner = routePlanner;
        this.redirectStrategy = redirectStrategy;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.client.methods.CloseableHttpResponse execute(org.apache.http.conn.routing.HttpRoute r24, org.apache.http.client.methods.HttpRequestWrapper r25, org.apache.http.client.protocol.HttpClientContext r26, org.apache.http.client.methods.HttpExecutionAware r27) throws java.io.IOException, org.apache.http.HttpException {
        /*
        r23 = this;
        r20 = "HTTP route";
        r0 = r24;
        r1 = r20;
        org.apache.http.util.Args.notNull(r0, r1);
        r20 = "HTTP request";
        r0 = r25;
        r1 = r20;
        org.apache.http.util.Args.notNull(r0, r1);
        r20 = "HTTP context";
        r0 = r26;
        r1 = r20;
        org.apache.http.util.Args.notNull(r0, r1);
        r16 = r26.getRedirectLocations();
        if (r16 == 0) goto L_0x0024;
    L_0x0021:
        r16.clear();
    L_0x0024:
        r5 = r26.getRequestConfig();
        r20 = r5.getMaxRedirects();
        if (r20 <= 0) goto L_0x008d;
    L_0x002e:
        r10 = r5.getMaxRedirects();
    L_0x0032:
        r7 = r24;
        r6 = r25;
        r15 = 0;
    L_0x0037:
        r0 = r23;
        r0 = r0.requestExecutor;
        r20 = r0;
        r0 = r20;
        r1 = r26;
        r2 = r27;
        r17 = r0.execute(r7, r6, r1, r2);
        r20 = r5.isRedirectsEnabled();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        if (r20 == 0) goto L_0x01b4;
    L_0x004d:
        r0 = r23;
        r0 = r0.redirectStrategy;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r0;
        r21 = r6.getOriginal();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r0 = r20;
        r1 = r21;
        r2 = r17;
        r3 = r26;
        r20 = r0.isRedirected(r1, r2, r3);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        if (r20 == 0) goto L_0x01b4;
    L_0x0065:
        if (r15 < r10) goto L_0x0090;
    L_0x0067:
        r20 = new org.apache.http.client.RedirectException;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r21 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r21.<init>();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r22 = "Maximum redirects (";
        r21 = r21.append(r22);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r0 = r21;
        r21 = r0.append(r10);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r22 = ") exceeded";
        r21 = r21.append(r22);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r21 = r21.toString();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20.<init>(r21);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        throw r20;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
    L_0x0088:
        r8 = move-exception;
        r17.close();
        throw r8;
    L_0x008d:
        r10 = 50;
        goto L_0x0032;
    L_0x0090:
        r15 = r15 + 1;
        r0 = r23;
        r0 = r0.redirectStrategy;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r0;
        r21 = r6.getOriginal();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r0 = r20;
        r1 = r21;
        r2 = r17;
        r3 = r26;
        r14 = r0.getRedirect(r1, r2, r3);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r14.headerIterator();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r20.hasNext();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        if (r20 != 0) goto L_0x00bf;
    L_0x00b2:
        r12 = r25.getOriginal();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r12.getAllHeaders();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r0 = r20;
        r14.setHeaders(r0);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
    L_0x00bf:
        r6 = org.apache.http.client.methods.HttpRequestWrapper.wrap(r14);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r0 = r6 instanceof org.apache.http.HttpEntityEnclosingRequest;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r0;
        if (r20 == 0) goto L_0x00d1;
    L_0x00c9:
        r0 = r6;
        r0 = (org.apache.http.HttpEntityEnclosingRequest) r0;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r0;
        org.apache.http.impl.execchain.RequestEntityProxy.enhance(r20);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
    L_0x00d1:
        r19 = r6.getURI();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r11 = org.apache.http.client.utils.URIUtils.extractHost(r19);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        if (r11 != 0) goto L_0x00fd;
    L_0x00db:
        r20 = new org.apache.http.ProtocolException;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r21 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r21.<init>();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r22 = "Redirect URI does not specify a valid host name: ";
        r21 = r21.append(r22);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r0 = r21;
        r1 = r19;
        r21 = r0.append(r1);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r21 = r21.toString();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20.<init>(r21);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        throw r20;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
    L_0x00f8:
        r8 = move-exception;
        r17.close();
        throw r8;
    L_0x00fd:
        r20 = r7.getTargetHost();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r0 = r20;
        r20 = r0.equals(r11);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        if (r20 != 0) goto L_0x013d;
    L_0x0109:
        r18 = r26.getTargetAuthState();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        if (r18 == 0) goto L_0x011d;
    L_0x010f:
        r0 = r23;
        r0 = r0.log;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r0;
        r21 = "Resetting target auth state";
        r20.debug(r21);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r18.reset();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
    L_0x011d:
        r13 = r26.getProxyAuthState();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        if (r13 == 0) goto L_0x013d;
    L_0x0123:
        r4 = r13.getAuthScheme();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        if (r4 == 0) goto L_0x013d;
    L_0x0129:
        r20 = r4.isConnectionBased();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        if (r20 == 0) goto L_0x013d;
    L_0x012f:
        r0 = r23;
        r0 = r0.log;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r0;
        r21 = "Resetting proxy auth state";
        r20.debug(r21);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r13.reset();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
    L_0x013d:
        r0 = r23;
        r0 = r0.routePlanner;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r0;
        r0 = r20;
        r1 = r26;
        r7 = r0.determineRoute(r11, r6, r1);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r0 = r23;
        r0 = r0.log;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r0;
        r20 = r20.isDebugEnabled();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        if (r20 == 0) goto L_0x0183;
    L_0x0157:
        r0 = r23;
        r0 = r0.log;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20 = r0;
        r21 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r21.<init>();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r22 = "Redirecting to '";
        r21 = r21.append(r22);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r0 = r21;
        r1 = r19;
        r21 = r0.append(r1);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r22 = "' via ";
        r21 = r21.append(r22);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r0 = r21;
        r21 = r0.append(r7);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r21 = r21.toString();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r20.debug(r21);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
    L_0x0183:
        r20 = r17.getEntity();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        org.apache.http.util.EntityUtils.consume(r20);	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        r17.close();	 Catch:{ RuntimeException -> 0x0088, IOException -> 0x00f8, HttpException -> 0x018f }
        goto L_0x0037;
    L_0x018f:
        r8 = move-exception;
        r20 = r17.getEntity();	 Catch:{ IOException -> 0x019b }
        org.apache.http.util.EntityUtils.consume(r20);	 Catch:{ IOException -> 0x019b }
        r17.close();
    L_0x019a:
        throw r8;
    L_0x019b:
        r9 = move-exception;
        r0 = r23;
        r0 = r0.log;	 Catch:{ all -> 0x01af }
        r20 = r0;
        r21 = "I/O error while releasing connection";
        r0 = r20;
        r1 = r21;
        r0.debug(r1, r9);	 Catch:{ all -> 0x01af }
        r17.close();
        goto L_0x019a;
    L_0x01af:
        r20 = move-exception;
        r17.close();
        throw r20;
    L_0x01b4:
        return r17;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.execchain.RedirectExec.execute(org.apache.http.conn.routing.HttpRoute, org.apache.http.client.methods.HttpRequestWrapper, org.apache.http.client.protocol.HttpClientContext, org.apache.http.client.methods.HttpExecutionAware):org.apache.http.client.methods.CloseableHttpResponse");
    }
}
