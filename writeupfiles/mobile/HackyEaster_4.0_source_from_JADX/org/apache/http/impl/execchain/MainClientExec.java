package org.apache.http.impl.execchain;

import android.support.v4.app.NotificationCompat.WearableExtender;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthState;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.BasicRouteDirector;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRouteDirector;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.auth.HttpAuthenticator;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

@Immutable
public class MainClientExec implements ClientExecChain {
    private final HttpAuthenticator authenticator;
    private final HttpClientConnectionManager connManager;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log;
    private final AuthenticationStrategy proxyAuthStrategy;
    private final HttpProcessor proxyHttpProcessor;
    private final HttpRequestExecutor requestExecutor;
    private final ConnectionReuseStrategy reuseStrategy;
    private final HttpRouteDirector routeDirector;
    private final AuthenticationStrategy targetAuthStrategy;
    private final UserTokenHandler userTokenHandler;

    public MainClientExec(HttpRequestExecutor requestExecutor, HttpClientConnectionManager connManager, ConnectionReuseStrategy reuseStrategy, ConnectionKeepAliveStrategy keepAliveStrategy, HttpProcessor proxyHttpProcessor, AuthenticationStrategy targetAuthStrategy, AuthenticationStrategy proxyAuthStrategy, UserTokenHandler userTokenHandler) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(requestExecutor, "HTTP request executor");
        Args.notNull(connManager, "Client connection manager");
        Args.notNull(reuseStrategy, "Connection reuse strategy");
        Args.notNull(keepAliveStrategy, "Connection keep alive strategy");
        Args.notNull(proxyHttpProcessor, "Proxy HTTP processor");
        Args.notNull(targetAuthStrategy, "Target authentication strategy");
        Args.notNull(proxyAuthStrategy, "Proxy authentication strategy");
        Args.notNull(userTokenHandler, "User token handler");
        this.authenticator = new HttpAuthenticator();
        this.routeDirector = new BasicRouteDirector();
        this.requestExecutor = requestExecutor;
        this.connManager = connManager;
        this.reuseStrategy = reuseStrategy;
        this.keepAliveStrategy = keepAliveStrategy;
        this.proxyHttpProcessor = proxyHttpProcessor;
        this.targetAuthStrategy = targetAuthStrategy;
        this.proxyAuthStrategy = proxyAuthStrategy;
        this.userTokenHandler = userTokenHandler;
    }

    public MainClientExec(HttpRequestExecutor requestExecutor, HttpClientConnectionManager connManager, ConnectionReuseStrategy reuseStrategy, ConnectionKeepAliveStrategy keepAliveStrategy, AuthenticationStrategy targetAuthStrategy, AuthenticationStrategy proxyAuthStrategy, UserTokenHandler userTokenHandler) {
        this(requestExecutor, connManager, reuseStrategy, keepAliveStrategy, new ImmutableHttpProcessor(new RequestTargetHost()), targetAuthStrategy, proxyAuthStrategy, userTokenHandler);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.client.methods.CloseableHttpResponse execute(org.apache.http.conn.routing.HttpRoute r30, org.apache.http.client.methods.HttpRequestWrapper r31, org.apache.http.client.protocol.HttpClientContext r32, org.apache.http.client.methods.HttpExecutionAware r33) throws java.io.IOException, org.apache.http.HttpException {
        /*
        r29 = this;
        r4 = "HTTP route";
        r0 = r30;
        org.apache.http.util.Args.notNull(r0, r4);
        r4 = "HTTP request";
        r0 = r31;
        org.apache.http.util.Args.notNull(r0, r4);
        r4 = "HTTP context";
        r0 = r32;
        org.apache.http.util.Args.notNull(r0, r4);
        r26 = r32.getTargetAuthState();
        if (r26 != 0) goto L_0x0029;
    L_0x001b:
        r26 = new org.apache.http.auth.AuthState;
        r26.<init>();
        r4 = "http.auth.target-scope";
        r0 = r32;
        r1 = r26;
        r0.setAttribute(r4, r1);
    L_0x0029:
        r5 = r32.getProxyAuthState();
        if (r5 != 0) goto L_0x003b;
    L_0x002f:
        r5 = new org.apache.http.auth.AuthState;
        r5.<init>();
        r4 = "http.auth.proxy-scope";
        r0 = r32;
        r0.setAttribute(r4, r5);
    L_0x003b:
        r0 = r31;
        r4 = r0 instanceof org.apache.http.HttpEntityEnclosingRequest;
        if (r4 == 0) goto L_0x0048;
    L_0x0041:
        r4 = r31;
        r4 = (org.apache.http.HttpEntityEnclosingRequest) r4;
        org.apache.http.impl.execchain.RequestEntityProxy.enhance(r4);
    L_0x0048:
        r28 = r32.getUserToken();
        r0 = r29;
        r4 = r0.connManager;
        r0 = r30;
        r1 = r28;
        r16 = r4.requestConnection(r0, r1);
        if (r33 == 0) goto L_0x0072;
    L_0x005a:
        r4 = r33.isAborted();
        if (r4 == 0) goto L_0x006b;
    L_0x0060:
        r16.cancel();
        r4 = new org.apache.http.impl.execchain.RequestAbortedException;
        r7 = "Request aborted";
        r4.<init>(r7);
        throw r4;
    L_0x006b:
        r0 = r33;
        r1 = r16;
        r0.setCancellable(r1);
    L_0x0072:
        r14 = r32.getRequestConfig();
        r27 = r14.getConnectionRequestTimeout();	 Catch:{ InterruptedException -> 0x00f3, ExecutionException -> 0x0105 }
        if (r27 <= 0) goto L_0x00f0;
    L_0x007c:
        r0 = r27;
        r8 = (long) r0;	 Catch:{ InterruptedException -> 0x00f3, ExecutionException -> 0x0105 }
    L_0x007f:
        r4 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ InterruptedException -> 0x00f3, ExecutionException -> 0x0105 }
        r0 = r16;
        r6 = r0.get(r8, r4);	 Catch:{ InterruptedException -> 0x00f3, ExecutionException -> 0x0105 }
        r4 = "http.connection";
        r0 = r32;
        r0.setAttribute(r4, r6);
        r4 = r14.isStaleConnectionCheckEnabled();
        if (r4 == 0) goto L_0x00b5;
    L_0x0094:
        r4 = r6.isOpen();
        if (r4 == 0) goto L_0x00b5;
    L_0x009a:
        r0 = r29;
        r4 = r0.log;
        r7 = "Stale connection check";
        r4.debug(r7);
        r4 = r6.isStale();
        if (r4 == 0) goto L_0x00b5;
    L_0x00a9:
        r0 = r29;
        r4 = r0.log;
        r7 = "Stale connection detected";
        r4.debug(r7);
        r6.close();
    L_0x00b5:
        r15 = new org.apache.http.impl.execchain.ConnectionHolder;
        r0 = r29;
        r4 = r0.log;
        r0 = r29;
        r7 = r0.connManager;
        r15.<init>(r4, r7, r6);
        if (r33 == 0) goto L_0x00c9;
    L_0x00c4:
        r0 = r33;
        r0.setCancellable(r15);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x00c9:
        r21 = 1;
    L_0x00cb:
        r4 = 1;
        r0 = r21;
        if (r0 <= r4) goto L_0x0116;
    L_0x00d0:
        r4 = org.apache.http.impl.execchain.RequestEntityProxy.isRepeatable(r31);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 != 0) goto L_0x0116;
    L_0x00d6:
        r4 = new org.apache.http.client.NonRepeatableRequestException;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = "Cannot retry request with a non-repeatable request entity.";
        r4.<init>(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        throw r4;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x00de:
        r20 = move-exception;
        r23 = new java.io.InterruptedIOException;
        r4 = "Connection has been shut down";
        r0 = r23;
        r0.<init>(r4);
        r0 = r23;
        r1 = r20;
        r0.initCause(r1);
        throw r23;
    L_0x00f0:
        r8 = 0;
        goto L_0x007f;
    L_0x00f3:
        r22 = move-exception;
        r4 = java.lang.Thread.currentThread();
        r4.interrupt();
        r4 = new org.apache.http.impl.execchain.RequestAbortedException;
        r7 = "Request aborted";
        r0 = r22;
        r4.<init>(r7, r0);
        throw r4;
    L_0x0105:
        r20 = move-exception;
        r13 = r20.getCause();
        if (r13 != 0) goto L_0x010e;
    L_0x010c:
        r13 = r20;
    L_0x010e:
        r4 = new org.apache.http.impl.execchain.RequestAbortedException;
        r7 = "Request execution failed";
        r4.<init>(r7, r13);
        throw r4;
    L_0x0116:
        if (r33 == 0) goto L_0x012b;
    L_0x0118:
        r4 = r33.isAborted();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x012b;
    L_0x011e:
        r4 = new org.apache.http.impl.execchain.RequestAbortedException;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = "Request aborted";
        r4.<init>(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        throw r4;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x0126:
        r20 = move-exception;
        r15.abortConnection();
        throw r20;
    L_0x012b:
        r4 = r6.isOpen();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 != 0) goto L_0x0158;
    L_0x0131:
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = new java.lang.StringBuilder;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7.<init>();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r8 = "Opening connection ";
        r7 = r7.append(r8);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r30;
        r7 = r7.append(r0);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = r7.toString();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4.debug(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = r29;
        r7 = r30;
        r8 = r31;
        r9 = r32;
        r4.establishRoute(r5, r6, r7, r8, r9);	 Catch:{ TunnelRefusedException -> 0x0178 }
    L_0x0158:
        r27 = r14.getSocketTimeout();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r27 < 0) goto L_0x0163;
    L_0x015e:
        r0 = r27;
        r6.setSocketTimeout(r0);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x0163:
        if (r33 == 0) goto L_0x01c4;
    L_0x0165:
        r4 = r33.isAborted();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x01c4;
    L_0x016b:
        r4 = new org.apache.http.impl.execchain.RequestAbortedException;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = "Request aborted";
        r4.<init>(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        throw r4;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x0173:
        r20 = move-exception;
        r15.abortConnection();
        throw r20;
    L_0x0178:
        r20 = move-exception;
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = r4.isDebugEnabled();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x018e;
    L_0x0183:
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = r20.getMessage();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4.debug(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x018e:
        r11 = r20.getResponse();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x0192:
        if (r28 != 0) goto L_0x01a7;
    L_0x0194:
        r0 = r29;
        r4 = r0.userTokenHandler;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r32;
        r28 = r4.getUserToken(r0);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = "http.user-token";
        r0 = r32;
        r1 = r28;
        r0.setAttribute(r4, r1);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x01a7:
        if (r28 == 0) goto L_0x01ae;
    L_0x01a9:
        r0 = r28;
        r15.setState(r0);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x01ae:
        r17 = r11.getEntity();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r17 == 0) goto L_0x01ba;
    L_0x01b4:
        r4 = r17.isStreaming();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 != 0) goto L_0x0386;
    L_0x01ba:
        r15.releaseConnection();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = new org.apache.http.impl.execchain.HttpResponseProxy;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = 0;
        r4.<init>(r11, r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x01c3:
        return r4;
    L_0x01c4:
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = r4.isDebugEnabled();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x01ec;
    L_0x01ce:
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = new java.lang.StringBuilder;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7.<init>();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r8 = "Executing request ";
        r7 = r7.append(r8);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r8 = r31.getRequestLine();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = r7.append(r8);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = r7.toString();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4.debug(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x01ec:
        r4 = "Authorization";
        r0 = r31;
        r4 = r0.containsHeader(r4);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 != 0) goto L_0x022b;
    L_0x01f6:
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = r4.isDebugEnabled();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x021e;
    L_0x0200:
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = new java.lang.StringBuilder;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7.<init>();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r8 = "Target auth state: ";
        r7 = r7.append(r8);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r8 = r26.getState();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = r7.append(r8);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = r7.toString();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4.debug(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x021e:
        r0 = r29;
        r4 = r0.authenticator;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r31;
        r1 = r26;
        r2 = r32;
        r4.generateAuthResponse(r0, r1, r2);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x022b:
        r4 = "Proxy-Authorization";
        r0 = r31;
        r4 = r0.containsHeader(r4);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 != 0) goto L_0x026e;
    L_0x0235:
        r4 = r30.isTunnelled();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 != 0) goto L_0x026e;
    L_0x023b:
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = r4.isDebugEnabled();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x0263;
    L_0x0245:
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = new java.lang.StringBuilder;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7.<init>();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r8 = "Proxy auth state: ";
        r7 = r7.append(r8);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r8 = r5.getState();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = r7.append(r8);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = r7.toString();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4.debug(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x0263:
        r0 = r29;
        r4 = r0.authenticator;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r31;
        r1 = r32;
        r4.generateAuthResponse(r0, r5, r1);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x026e:
        r0 = r29;
        r4 = r0.requestExecutor;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r31;
        r1 = r32;
        r11 = r4.execute(r0, r6, r1);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r29;
        r4 = r0.reuseStrategy;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r32;
        r4 = r4.keepAlive(r11, r0);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x0330;
    L_0x0286:
        r0 = r29;
        r4 = r0.keepAliveStrategy;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r32;
        r18 = r4.getKeepAliveDuration(r11, r0);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = r4.isDebugEnabled();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x02dd;
    L_0x029a:
        r8 = 0;
        r4 = (r18 > r8 ? 1 : (r18 == r8 ? 0 : -1));
        if (r4 <= 0) goto L_0x032d;
    L_0x02a0:
        r4 = new java.lang.StringBuilder;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4.<init>();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = "for ";
        r4 = r4.append(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r18;
        r4 = r4.append(r0);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = " ";
        r4 = r4.append(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = r4.append(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r25 = r4.toString();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x02c1:
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = new java.lang.StringBuilder;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7.<init>();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r8 = "Connection can be kept alive ";
        r7 = r7.append(r8);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r25;
        r7 = r7.append(r0);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = r7.toString();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4.debug(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x02dd:
        r4 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r0 = r18;
        r15.setValidFor(r0, r4);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r15.markReusable();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x02e7:
        r7 = r29;
        r8 = r26;
        r9 = r5;
        r10 = r30;
        r12 = r32;
        r4 = r7.needAuthentication(r8, r9, r10, r11, r12);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x0192;
    L_0x02f6:
        r17 = r11.getEntity();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = r15.isReusable();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x0339;
    L_0x0300:
        org.apache.http.util.EntityUtils.consume(r17);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x0303:
        r24 = r31.getOriginal();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = "Authorization";
        r0 = r24;
        r4 = r0.containsHeader(r4);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 != 0) goto L_0x0318;
    L_0x0311:
        r4 = "Authorization";
        r0 = r31;
        r0.removeHeaders(r4);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x0318:
        r4 = "Proxy-Authorization";
        r0 = r24;
        r4 = r0.containsHeader(r4);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 != 0) goto L_0x0329;
    L_0x0322:
        r4 = "Proxy-Authorization";
        r0 = r31;
        r0.removeHeaders(r4);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x0329:
        r21 = r21 + 1;
        goto L_0x00cb;
    L_0x032d:
        r25 = "indefinitely";
        goto L_0x02c1;
    L_0x0330:
        r15.markNonReusable();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        goto L_0x02e7;
    L_0x0334:
        r20 = move-exception;
        r15.abortConnection();
        throw r20;
    L_0x0339:
        r6.close();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = r5.getState();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = org.apache.http.auth.AuthProtocolState.SUCCESS;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 != r7) goto L_0x0360;
    L_0x0344:
        r4 = r5.getAuthScheme();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x0360;
    L_0x034a:
        r4 = r5.getAuthScheme();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = r4.isConnectionBased();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x0360;
    L_0x0354:
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = "Resetting proxy auth state";
        r4.debug(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r5.reset();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
    L_0x0360:
        r4 = r26.getState();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = org.apache.http.auth.AuthProtocolState.SUCCESS;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 != r7) goto L_0x0303;
    L_0x0368:
        r4 = r26.getAuthScheme();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x0303;
    L_0x036e:
        r4 = r26.getAuthScheme();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4 = r4.isConnectionBased();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        if (r4 == 0) goto L_0x0303;
    L_0x0378:
        r0 = r29;
        r4 = r0.log;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r7 = "Resetting target auth state";
        r4.debug(r7);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r26.reset();	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        goto L_0x0303;
    L_0x0386:
        r4 = new org.apache.http.impl.execchain.HttpResponseProxy;	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        r4.<init>(r11, r15);	 Catch:{ ConnectionShutdownException -> 0x00de, HttpException -> 0x0126, IOException -> 0x0173, RuntimeException -> 0x0334 }
        goto L_0x01c3;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.execchain.MainClientExec.execute(org.apache.http.conn.routing.HttpRoute, org.apache.http.client.methods.HttpRequestWrapper, org.apache.http.client.protocol.HttpClientContext, org.apache.http.client.methods.HttpExecutionAware):org.apache.http.client.methods.CloseableHttpResponse");
    }

    void establishRoute(AuthState proxyAuthState, HttpClientConnection managedConn, HttpRoute route, HttpRequest request, HttpClientContext context) throws HttpException, IOException {
        int timeout = context.getRequestConfig().getConnectTimeout();
        RouteTracker tracker = new RouteTracker(route);
        int step;
        do {
            HttpRoute fact = tracker.toRoute();
            step = this.routeDirector.nextStep(route, fact);
            boolean secure;
            switch (step) {
                case WearableExtender.UNSET_ACTION_INDEX /*-1*/:
                    throw new HttpException("Unable to establish route: planned = " + route + "; current = " + fact);
                case WearableExtender.SIZE_DEFAULT /*0*/:
                    this.connManager.routeComplete(managedConn, route, context);
                    continue;
                case WearableExtender.SIZE_XSMALL /*1*/:
                    this.connManager.connect(managedConn, route, timeout > 0 ? timeout : 0, context);
                    tracker.connectTarget(route.isSecure());
                    continue;
                case ConnPerRouteBean.DEFAULT_MAX_CONNECTIONS_PER_ROUTE /*2*/:
                    this.connManager.connect(managedConn, route, timeout > 0 ? timeout : 0, context);
                    tracker.connectProxy(route.getProxyHost(), false);
                    continue;
                case WearableExtender.SIZE_MEDIUM /*3*/:
                    secure = createTunnelToTarget(proxyAuthState, managedConn, route, request, context);
                    this.log.debug("Tunnel to target created.");
                    tracker.tunnelTarget(secure);
                    continue;
                case WearableExtender.SIZE_LARGE /*4*/:
                    int hop = fact.getHopCount() - 1;
                    secure = createTunnelToProxy(route, hop, context);
                    this.log.debug("Tunnel to proxy created.");
                    tracker.tunnelProxy(route.getHopTarget(hop), secure);
                    continue;
                case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                    this.connManager.upgrade(managedConn, route, context);
                    tracker.layerProtocol(route.isSecure());
                    continue;
                default:
                    throw new IllegalStateException("Unknown step indicator " + step + " from RouteDirector.");
            }
        } while (step > 0);
    }

    private boolean createTunnelToTarget(AuthState proxyAuthState, HttpClientConnection managedConn, HttpRoute route, HttpRequest request, HttpClientContext context) throws HttpException, IOException {
        RequestConfig config = context.getRequestConfig();
        int timeout = config.getConnectTimeout();
        HttpHost target = route.getTargetHost();
        HttpHost proxy = route.getProxyHost();
        HttpResponse response = null;
        HttpRequest connect = new BasicHttpRequest("CONNECT", target.toHostString(), request.getProtocolVersion());
        this.requestExecutor.preProcess(connect, this.proxyHttpProcessor, context);
        while (response == null) {
            if (!managedConn.isOpen()) {
                this.connManager.connect(managedConn, route, timeout > 0 ? timeout : 0, context);
            }
            connect.removeHeaders(AUTH.PROXY_AUTH_RESP);
            this.authenticator.generateAuthResponse(connect, proxyAuthState, context);
            response = this.requestExecutor.execute(connect, managedConn, context);
            if (response.getStatusLine().getStatusCode() < HttpStatus.SC_OK) {
                throw new HttpException("Unexpected response to CONNECT request: " + response.getStatusLine());
            } else if (config.isAuthenticationEnabled() && this.authenticator.isAuthenticationRequested(proxy, response, this.proxyAuthStrategy, proxyAuthState, context) && this.authenticator.handleAuthChallenge(proxy, response, this.proxyAuthStrategy, proxyAuthState, context)) {
                if (this.reuseStrategy.keepAlive(response, context)) {
                    this.log.debug("Connection kept alive");
                    EntityUtils.consume(response.getEntity());
                } else {
                    managedConn.close();
                }
                response = null;
            }
        }
        if (response.getStatusLine().getStatusCode() <= 299) {
            return false;
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            response.setEntity(new BufferedHttpEntity(entity));
        }
        managedConn.close();
        throw new TunnelRefusedException("CONNECT refused by proxy: " + response.getStatusLine(), response);
    }

    private boolean createTunnelToProxy(HttpRoute route, int hop, HttpClientContext context) throws HttpException {
        throw new HttpException("Proxy chains are not supported.");
    }

    private boolean needAuthentication(AuthState targetAuthState, AuthState proxyAuthState, HttpRoute route, HttpResponse response, HttpClientContext context) {
        if (context.getRequestConfig().isAuthenticationEnabled()) {
            HttpHost target = context.getTargetHost();
            if (target == null) {
                target = route.getTargetHost();
            }
            if (target.getPort() < 0) {
                target = new HttpHost(target.getHostName(), route.getTargetHost().getPort(), target.getSchemeName());
            }
            boolean targetAuthRequested = this.authenticator.isAuthenticationRequested(target, response, this.targetAuthStrategy, targetAuthState, context);
            HttpHost proxy = route.getProxyHost();
            if (proxy == null) {
                proxy = route.getTargetHost();
            }
            boolean proxyAuthRequested = this.authenticator.isAuthenticationRequested(proxy, response, this.proxyAuthStrategy, proxyAuthState, context);
            if (targetAuthRequested) {
                return this.authenticator.handleAuthChallenge(target, response, this.targetAuthStrategy, targetAuthState, context);
            } else if (proxyAuthRequested) {
                return this.authenticator.handleAuthChallenge(proxy, response, this.proxyAuthStrategy, proxyAuthState, context);
            }
        }
        return false;
    }
}
