package org.apache.http.impl.client;

import android.support.v4.app.NotificationCompat.WearableExtender;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.AuthProtocolState;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.RequestDirector;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.methods.AbortableHttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.BasicRouteDirector;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRouteDirector;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

@NotThreadSafe
@Deprecated
public class DefaultRequestDirector implements RequestDirector {
    private final HttpAuthenticator authenticator;
    protected final ClientConnectionManager connManager;
    private int execCount;
    protected final HttpProcessor httpProcessor;
    protected final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log;
    protected ManagedClientConnection managedConn;
    private final int maxRedirects;
    protected final HttpParams params;
    @Deprecated
    protected final AuthenticationHandler proxyAuthHandler;
    protected final AuthState proxyAuthState;
    protected final AuthenticationStrategy proxyAuthStrategy;
    private int redirectCount;
    @Deprecated
    protected final RedirectHandler redirectHandler;
    protected final RedirectStrategy redirectStrategy;
    protected final HttpRequestExecutor requestExec;
    protected final HttpRequestRetryHandler retryHandler;
    protected final ConnectionReuseStrategy reuseStrategy;
    protected final HttpRoutePlanner routePlanner;
    @Deprecated
    protected final AuthenticationHandler targetAuthHandler;
    protected final AuthState targetAuthState;
    protected final AuthenticationStrategy targetAuthStrategy;
    protected final UserTokenHandler userTokenHandler;
    private HttpHost virtualHost;

    @Deprecated
    public DefaultRequestDirector(HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectHandler redirectHandler, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler userTokenHandler, HttpParams params) {
        this(LogFactory.getLog(DefaultRequestDirector.class), requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, new DefaultRedirectStrategyAdaptor(redirectHandler), new AuthenticationStrategyAdaptor(targetAuthHandler), new AuthenticationStrategyAdaptor(proxyAuthHandler), userTokenHandler, params);
    }

    @Deprecated
    public DefaultRequestDirector(Log log, HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectStrategy redirectStrategy, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler userTokenHandler, HttpParams params) {
        this(LogFactory.getLog(DefaultRequestDirector.class), requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, redirectStrategy, new AuthenticationStrategyAdaptor(targetAuthHandler), new AuthenticationStrategyAdaptor(proxyAuthHandler), userTokenHandler, params);
    }

    public DefaultRequestDirector(Log log, HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectStrategy redirectStrategy, AuthenticationStrategy targetAuthStrategy, AuthenticationStrategy proxyAuthStrategy, UserTokenHandler userTokenHandler, HttpParams params) {
        Args.notNull(log, "Log");
        Args.notNull(requestExec, "Request executor");
        Args.notNull(conman, "Client connection manager");
        Args.notNull(reustrat, "Connection reuse strategy");
        Args.notNull(kastrat, "Connection keep alive strategy");
        Args.notNull(rouplan, "Route planner");
        Args.notNull(httpProcessor, "HTTP protocol processor");
        Args.notNull(retryHandler, "HTTP request retry handler");
        Args.notNull(redirectStrategy, "Redirect strategy");
        Args.notNull(targetAuthStrategy, "Target authentication strategy");
        Args.notNull(proxyAuthStrategy, "Proxy authentication strategy");
        Args.notNull(userTokenHandler, "User token handler");
        Args.notNull(params, "HTTP parameters");
        this.log = log;
        this.authenticator = new HttpAuthenticator(log);
        this.requestExec = requestExec;
        this.connManager = conman;
        this.reuseStrategy = reustrat;
        this.keepAliveStrategy = kastrat;
        this.routePlanner = rouplan;
        this.httpProcessor = httpProcessor;
        this.retryHandler = retryHandler;
        this.redirectStrategy = redirectStrategy;
        this.targetAuthStrategy = targetAuthStrategy;
        this.proxyAuthStrategy = proxyAuthStrategy;
        this.userTokenHandler = userTokenHandler;
        this.params = params;
        if (redirectStrategy instanceof DefaultRedirectStrategyAdaptor) {
            this.redirectHandler = ((DefaultRedirectStrategyAdaptor) redirectStrategy).getHandler();
        } else {
            this.redirectHandler = null;
        }
        if (targetAuthStrategy instanceof AuthenticationStrategyAdaptor) {
            this.targetAuthHandler = ((AuthenticationStrategyAdaptor) targetAuthStrategy).getHandler();
        } else {
            this.targetAuthHandler = null;
        }
        if (proxyAuthStrategy instanceof AuthenticationStrategyAdaptor) {
            this.proxyAuthHandler = ((AuthenticationStrategyAdaptor) proxyAuthStrategy).getHandler();
        } else {
            this.proxyAuthHandler = null;
        }
        this.managedConn = null;
        this.execCount = 0;
        this.redirectCount = 0;
        this.targetAuthState = new AuthState();
        this.proxyAuthState = new AuthState();
        this.maxRedirects = this.params.getIntParameter(ClientPNames.MAX_REDIRECTS, 100);
    }

    private RequestWrapper wrapRequest(HttpRequest request) throws ProtocolException {
        if (request instanceof HttpEntityEnclosingRequest) {
            return new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest) request);
        }
        return new RequestWrapper(request);
    }

    protected void rewriteRequestURI(RequestWrapper request, HttpRoute route) throws ProtocolException {
        try {
            URI uri = request.getURI();
            if (route.getProxyHost() == null || route.isTunnelled()) {
                if (uri.isAbsolute()) {
                    uri = URIUtils.rewriteURI(uri, null, true);
                } else {
                    uri = URIUtils.rewriteURI(uri);
                }
            } else if (uri.isAbsolute()) {
                uri = URIUtils.rewriteURI(uri);
            } else {
                uri = URIUtils.rewriteURI(uri, route.getTargetHost(), true);
            }
            request.setURI(uri);
        } catch (URISyntaxException ex) {
            throw new ProtocolException("Invalid URI: " + request.getRequestLine().getUri(), ex);
        }
    }

    public HttpResponse execute(HttpHost targetHost, HttpRequest request, HttpContext context) throws HttpException, IOException {
        context.setAttribute(HttpClientContext.TARGET_AUTH_STATE, this.targetAuthState);
        context.setAttribute(HttpClientContext.PROXY_AUTH_STATE, this.proxyAuthState);
        HttpHost target = targetHost;
        HttpRequest orig = request;
        RequestWrapper origWrapper = wrapRequest(orig);
        origWrapper.setParams(this.params);
        HttpRoute origRoute = determineRoute(target, origWrapper, context);
        this.virtualHost = (HttpHost) origWrapper.getParams().getParameter(ClientPNames.VIRTUAL_HOST);
        if (this.virtualHost != null) {
            if (this.virtualHost.getPort() == -1) {
                int port = (target != null ? target : origRoute.getTargetHost()).getPort();
                if (port != -1) {
                    this.virtualHost = new HttpHost(this.virtualHost.getHostName(), port, this.virtualHost.getSchemeName());
                }
            }
        }
        RoutedRequest routedRequest = new RoutedRequest(origWrapper, origRoute);
        boolean reuse = false;
        boolean done = false;
        HttpResponse response = null;
        while (!done) {
            try {
                RoutedRequest roureq;
                RequestWrapper wrapper = roureq.getRequest();
                HttpRoute route = roureq.getRoute();
                Object userToken = context.getAttribute(HttpClientContext.USER_TOKEN);
                if (this.managedConn == null) {
                    ClientConnectionRequest connRequest = this.connManager.requestConnection(route, userToken);
                    if (orig instanceof AbortableHttpRequest) {
                        ((AbortableHttpRequest) orig).setConnectionRequest(connRequest);
                    }
                    this.managedConn = connRequest.getConnection(HttpClientParams.getConnectionManagerTimeout(this.params), TimeUnit.MILLISECONDS);
                    if (HttpConnectionParams.isStaleCheckingEnabled(this.params)) {
                        if (this.managedConn.isOpen()) {
                            this.log.debug("Stale connection check");
                            if (this.managedConn.isStale()) {
                                this.log.debug("Stale connection detected");
                                this.managedConn.close();
                            }
                        }
                    }
                }
                if (orig instanceof AbortableHttpRequest) {
                    ((AbortableHttpRequest) orig).setReleaseTrigger(this.managedConn);
                }
                try {
                    tryConnect(roureq, context);
                    String userinfo = wrapper.getURI().getUserInfo();
                    if (userinfo != null) {
                        this.targetAuthState.update(new BasicScheme(), new UsernamePasswordCredentials(userinfo));
                    }
                    if (this.virtualHost != null) {
                        target = this.virtualHost;
                    } else {
                        URI requestURI = wrapper.getURI();
                        if (requestURI.isAbsolute()) {
                            target = URIUtils.extractHost(requestURI);
                        }
                    }
                    if (target == null) {
                        target = route.getTargetHost();
                    }
                    wrapper.resetHeaders();
                    rewriteRequestURI(wrapper, route);
                    context.setAttribute(HttpCoreContext.HTTP_TARGET_HOST, target);
                    context.setAttribute(HttpClientContext.HTTP_ROUTE, route);
                    context.setAttribute(HttpCoreContext.HTTP_CONNECTION, this.managedConn);
                    this.requestExec.preProcess(wrapper, this.httpProcessor, context);
                    response = tryExecute(roureq, context);
                    if (response != null) {
                        response.setParams(this.params);
                        this.requestExec.postProcess(response, this.httpProcessor, context);
                        reuse = this.reuseStrategy.keepAlive(response, context);
                        if (reuse) {
                            long duration = this.keepAliveStrategy.getKeepAliveDuration(response, context);
                            if (this.log.isDebugEnabled()) {
                                String s;
                                if (duration > 0) {
                                    s = "for " + duration + " " + TimeUnit.MILLISECONDS;
                                } else {
                                    s = "indefinitely";
                                }
                                this.log.debug("Connection can be kept alive " + s);
                            }
                            this.managedConn.setIdleDuration(duration, TimeUnit.MILLISECONDS);
                        }
                        RoutedRequest followup = handleResponse(roureq, response, context);
                        if (followup == null) {
                            done = true;
                        } else {
                            if (reuse) {
                                EntityUtils.consume(response.getEntity());
                                this.managedConn.markReusable();
                            } else {
                                this.managedConn.close();
                                if (this.proxyAuthState.getState().compareTo(AuthProtocolState.CHALLENGED) > 0) {
                                    if (this.proxyAuthState.getAuthScheme() != null) {
                                        if (this.proxyAuthState.getAuthScheme().isConnectionBased()) {
                                            this.log.debug("Resetting proxy auth state");
                                            this.proxyAuthState.reset();
                                        }
                                    }
                                }
                                if (this.targetAuthState.getState().compareTo(AuthProtocolState.CHALLENGED) > 0) {
                                    if (this.targetAuthState.getAuthScheme() != null) {
                                        if (this.targetAuthState.getAuthScheme().isConnectionBased()) {
                                            this.log.debug("Resetting target auth state");
                                            this.targetAuthState.reset();
                                        }
                                    }
                                }
                            }
                            if (!followup.getRoute().equals(roureq.getRoute())) {
                                releaseConnection();
                            }
                            roureq = followup;
                        }
                        if (this.managedConn != null) {
                            if (userToken == null) {
                                userToken = this.userTokenHandler.getUserToken(context);
                                context.setAttribute(HttpClientContext.USER_TOKEN, userToken);
                            }
                            if (userToken != null) {
                                this.managedConn.setState(userToken);
                            }
                        }
                    }
                } catch (TunnelRefusedException ex) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug(ex.getMessage());
                    }
                    response = ex.getResponse();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new InterruptedIOException();
            } catch (ConnectionShutdownException ex2) {
                InterruptedIOException ioex = new InterruptedIOException("Connection has been shut down");
                ioex.initCause(ex2);
                throw ioex;
            } catch (HttpException ex3) {
                abortConnection();
                throw ex3;
            } catch (IOException ex4) {
                abortConnection();
                throw ex4;
            } catch (RuntimeException ex5) {
                abortConnection();
                throw ex5;
            }
        }
        if (response == null || response.getEntity() == null || !response.getEntity().isStreaming()) {
            if (reuse) {
                this.managedConn.markReusable();
            }
            releaseConnection();
        } else {
            response.setEntity(new BasicManagedEntity(response.getEntity(), this.managedConn, reuse));
        }
        return response;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void tryConnect(org.apache.http.impl.client.RoutedRequest r8, org.apache.http.protocol.HttpContext r9) throws org.apache.http.HttpException, java.io.IOException {
        /*
        r7 = this;
        r2 = r8.getRoute();
        r3 = r8.getRequest();
        r0 = 0;
    L_0x0009:
        r4 = "http.request";
        r9.setAttribute(r4, r3);
        r0 = r0 + 1;
        r4 = r7.managedConn;	 Catch:{ IOException -> 0x002f }
        r4 = r4.isOpen();	 Catch:{ IOException -> 0x002f }
        if (r4 != 0) goto L_0x0023;
    L_0x0018:
        r4 = r7.managedConn;	 Catch:{ IOException -> 0x002f }
        r5 = r7.params;	 Catch:{ IOException -> 0x002f }
        r4.open(r2, r9, r5);	 Catch:{ IOException -> 0x002f }
    L_0x001f:
        r7.establishRoute(r2, r9);	 Catch:{ IOException -> 0x002f }
        return;
    L_0x0023:
        r4 = r7.managedConn;	 Catch:{ IOException -> 0x002f }
        r5 = r7.params;	 Catch:{ IOException -> 0x002f }
        r5 = org.apache.http.params.HttpConnectionParams.getSoTimeout(r5);	 Catch:{ IOException -> 0x002f }
        r4.setSocketTimeout(r5);	 Catch:{ IOException -> 0x002f }
        goto L_0x001f;
    L_0x002f:
        r1 = move-exception;
        r4 = r7.managedConn;	 Catch:{ IOException -> 0x00a9 }
        r4.close();	 Catch:{ IOException -> 0x00a9 }
    L_0x0035:
        r4 = r7.retryHandler;
        r4 = r4.retryRequest(r1, r0, r9);
        if (r4 == 0) goto L_0x00a8;
    L_0x003d:
        r4 = r7.log;
        r4 = r4.isInfoEnabled();
        if (r4 == 0) goto L_0x0009;
    L_0x0045:
        r4 = r7.log;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "I/O exception (";
        r5 = r5.append(r6);
        r6 = r1.getClass();
        r6 = r6.getName();
        r5 = r5.append(r6);
        r6 = ") caught when connecting to ";
        r5 = r5.append(r6);
        r5 = r5.append(r2);
        r6 = ": ";
        r5 = r5.append(r6);
        r6 = r1.getMessage();
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.info(r5);
        r4 = r7.log;
        r4 = r4.isDebugEnabled();
        if (r4 == 0) goto L_0x008e;
    L_0x0085:
        r4 = r7.log;
        r5 = r1.getMessage();
        r4.debug(r5, r1);
    L_0x008e:
        r4 = r7.log;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "Retrying connect to ";
        r5 = r5.append(r6);
        r5 = r5.append(r2);
        r5 = r5.toString();
        r4.info(r5);
        goto L_0x0009;
    L_0x00a8:
        throw r1;
    L_0x00a9:
        r4 = move-exception;
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.client.DefaultRequestDirector.tryConnect(org.apache.http.impl.client.RoutedRequest, org.apache.http.protocol.HttpContext):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.apache.http.HttpResponse tryExecute(org.apache.http.impl.client.RoutedRequest r10, org.apache.http.protocol.HttpContext r11) throws org.apache.http.HttpException, java.io.IOException {
        /*
        r9 = this;
        r5 = r10.getRequest();
        r3 = r10.getRoute();
        r1 = 0;
        r2 = 0;
    L_0x000a:
        r6 = r9.execCount;
        r6 = r6 + 1;
        r9.execCount = r6;
        r5.incrementExecCount();
        r6 = r5.isRepeatable();
        if (r6 != 0) goto L_0x0032;
    L_0x0019:
        r6 = r9.log;
        r7 = "Cannot retry non-repeatable request";
        r6.debug(r7);
        if (r2 == 0) goto L_0x002a;
    L_0x0022:
        r6 = new org.apache.http.client.NonRepeatableRequestException;
        r7 = "Cannot retry request with a non-repeatable request entity.  The cause lists the reason the original request failed.";
        r6.<init>(r7, r2);
        throw r6;
    L_0x002a:
        r6 = new org.apache.http.client.NonRepeatableRequestException;
        r7 = "Cannot retry request with a non-repeatable request entity.";
        r6.<init>(r7);
        throw r6;
    L_0x0032:
        r6 = r9.managedConn;	 Catch:{ IOException -> 0x0087 }
        r6 = r6.isOpen();	 Catch:{ IOException -> 0x0087 }
        if (r6 != 0) goto L_0x004e;
    L_0x003a:
        r6 = r3.isTunnelled();	 Catch:{ IOException -> 0x0087 }
        if (r6 != 0) goto L_0x007f;
    L_0x0040:
        r6 = r9.log;	 Catch:{ IOException -> 0x0087 }
        r7 = "Reopening the direct connection.";
        r6.debug(r7);	 Catch:{ IOException -> 0x0087 }
        r6 = r9.managedConn;	 Catch:{ IOException -> 0x0087 }
        r7 = r9.params;	 Catch:{ IOException -> 0x0087 }
        r6.open(r3, r11, r7);	 Catch:{ IOException -> 0x0087 }
    L_0x004e:
        r6 = r9.log;	 Catch:{ IOException -> 0x0087 }
        r6 = r6.isDebugEnabled();	 Catch:{ IOException -> 0x0087 }
        if (r6 == 0) goto L_0x0076;
    L_0x0056:
        r6 = r9.log;	 Catch:{ IOException -> 0x0087 }
        r7 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0087 }
        r7.<init>();	 Catch:{ IOException -> 0x0087 }
        r8 = "Attempt ";
        r7 = r7.append(r8);	 Catch:{ IOException -> 0x0087 }
        r8 = r9.execCount;	 Catch:{ IOException -> 0x0087 }
        r7 = r7.append(r8);	 Catch:{ IOException -> 0x0087 }
        r8 = " to execute request";
        r7 = r7.append(r8);	 Catch:{ IOException -> 0x0087 }
        r7 = r7.toString();	 Catch:{ IOException -> 0x0087 }
        r6.debug(r7);	 Catch:{ IOException -> 0x0087 }
    L_0x0076:
        r6 = r9.requestExec;	 Catch:{ IOException -> 0x0087 }
        r7 = r9.managedConn;	 Catch:{ IOException -> 0x0087 }
        r1 = r6.execute(r5, r7, r11);	 Catch:{ IOException -> 0x0087 }
    L_0x007e:
        return r1;
    L_0x007f:
        r6 = r9.log;	 Catch:{ IOException -> 0x0087 }
        r7 = "Proxied connection. Need to start over.";
        r6.debug(r7);	 Catch:{ IOException -> 0x0087 }
        goto L_0x007e;
    L_0x0087:
        r0 = move-exception;
        r6 = r9.log;
        r7 = "Closing the connection.";
        r6.debug(r7);
        r6 = r9.managedConn;	 Catch:{ IOException -> 0x0141 }
        r6.close();	 Catch:{ IOException -> 0x0141 }
    L_0x0094:
        r6 = r9.retryHandler;
        r7 = r5.getExecCount();
        r6 = r6.retryRequest(r0, r7, r11);
        if (r6 == 0) goto L_0x0114;
    L_0x00a0:
        r6 = r9.log;
        r6 = r6.isInfoEnabled();
        if (r6 == 0) goto L_0x00e0;
    L_0x00a8:
        r6 = r9.log;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = "I/O exception (";
        r7 = r7.append(r8);
        r8 = r0.getClass();
        r8 = r8.getName();
        r7 = r7.append(r8);
        r8 = ") caught when processing request to ";
        r7 = r7.append(r8);
        r7 = r7.append(r3);
        r8 = ": ";
        r7 = r7.append(r8);
        r8 = r0.getMessage();
        r7 = r7.append(r8);
        r7 = r7.toString();
        r6.info(r7);
    L_0x00e0:
        r6 = r9.log;
        r6 = r6.isDebugEnabled();
        if (r6 == 0) goto L_0x00f1;
    L_0x00e8:
        r6 = r9.log;
        r7 = r0.getMessage();
        r6.debug(r7, r0);
    L_0x00f1:
        r6 = r9.log;
        r6 = r6.isInfoEnabled();
        if (r6 == 0) goto L_0x0111;
    L_0x00f9:
        r6 = r9.log;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = "Retrying request to ";
        r7 = r7.append(r8);
        r7 = r7.append(r3);
        r7 = r7.toString();
        r6.info(r7);
    L_0x0111:
        r2 = r0;
        goto L_0x000a;
    L_0x0114:
        r6 = r0 instanceof org.apache.http.NoHttpResponseException;
        if (r6 == 0) goto L_0x0140;
    L_0x0118:
        r4 = new org.apache.http.NoHttpResponseException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = r3.getTargetHost();
        r7 = r7.toHostString();
        r6 = r6.append(r7);
        r7 = " failed to respond";
        r6 = r6.append(r7);
        r6 = r6.toString();
        r4.<init>(r6);
        r6 = r0.getStackTrace();
        r4.setStackTrace(r6);
        throw r4;
    L_0x0140:
        throw r0;
    L_0x0141:
        r6 = move-exception;
        goto L_0x0094;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.client.DefaultRequestDirector.tryExecute(org.apache.http.impl.client.RoutedRequest, org.apache.http.protocol.HttpContext):org.apache.http.HttpResponse");
    }

    protected void releaseConnection() {
        try {
            this.managedConn.releaseConnection();
        } catch (IOException ignored) {
            this.log.debug("IOException releasing connection", ignored);
        }
        this.managedConn = null;
    }

    protected HttpRoute determineRoute(HttpHost targetHost, HttpRequest request, HttpContext context) throws HttpException {
        HttpRoutePlanner httpRoutePlanner = this.routePlanner;
        if (targetHost == null) {
            targetHost = (HttpHost) request.getParams().getParameter(ClientPNames.DEFAULT_HOST);
        }
        return httpRoutePlanner.determineRoute(targetHost, request, context);
    }

    protected void establishRoute(HttpRoute route, HttpContext context) throws HttpException, IOException {
        HttpRouteDirector rowdy = new BasicRouteDirector();
        int step;
        do {
            HttpRoute fact = this.managedConn.getRoute();
            step = rowdy.nextStep(route, fact);
            boolean secure;
            switch (step) {
                case WearableExtender.UNSET_ACTION_INDEX /*-1*/:
                    throw new HttpException("Unable to establish route: planned = " + route + "; current = " + fact);
                case WearableExtender.SIZE_DEFAULT /*0*/:
                    break;
                case WearableExtender.SIZE_XSMALL /*1*/:
                case ConnPerRouteBean.DEFAULT_MAX_CONNECTIONS_PER_ROUTE /*2*/:
                    this.managedConn.open(route, context, this.params);
                    continue;
                case WearableExtender.SIZE_MEDIUM /*3*/:
                    secure = createTunnelToTarget(route, context);
                    this.log.debug("Tunnel to target created.");
                    this.managedConn.tunnelTarget(secure, this.params);
                    continue;
                case WearableExtender.SIZE_LARGE /*4*/:
                    int hop = fact.getHopCount() - 1;
                    secure = createTunnelToProxy(route, hop, context);
                    this.log.debug("Tunnel to proxy created.");
                    this.managedConn.tunnelProxy(route.getHopTarget(hop), secure, this.params);
                    continue;
                case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                    this.managedConn.layerProtocol(context, this.params);
                    continue;
                default:
                    throw new IllegalStateException("Unknown step indicator " + step + " from RouteDirector.");
            }
        } while (step > 0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected boolean createTunnelToTarget(org.apache.http.conn.routing.HttpRoute r11, org.apache.http.protocol.HttpContext r12) throws org.apache.http.HttpException, java.io.IOException {
        /*
        r10 = this;
        r1 = r11.getProxyHost();
        r9 = r11.getTargetHost();
        r2 = 0;
    L_0x0009:
        r0 = r10.managedConn;
        r0 = r0.isOpen();
        if (r0 != 0) goto L_0x0018;
    L_0x0011:
        r0 = r10.managedConn;
        r3 = r10.params;
        r0.open(r11, r12, r3);
    L_0x0018:
        r6 = r10.createConnectRequest(r11, r12);
        r0 = r10.params;
        r6.setParams(r0);
        r0 = "http.target_host";
        r12.setAttribute(r0, r9);
        r0 = "http.route";
        r12.setAttribute(r0, r11);
        r0 = "http.proxy_host";
        r12.setAttribute(r0, r1);
        r0 = "http.connection";
        r3 = r10.managedConn;
        r12.setAttribute(r0, r3);
        r0 = "http.request";
        r12.setAttribute(r0, r6);
        r0 = r10.requestExec;
        r3 = r10.httpProcessor;
        r0.preProcess(r6, r3, r12);
        r0 = r10.requestExec;
        r3 = r10.managedConn;
        r2 = r0.execute(r6, r3, r12);
        r0 = r10.params;
        r2.setParams(r0);
        r0 = r10.requestExec;
        r3 = r10.httpProcessor;
        r0.postProcess(r2, r3, r12);
        r0 = r2.getStatusLine();
        r8 = r0.getStatusCode();
        r0 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r8 >= r0) goto L_0x0080;
    L_0x0063:
        r0 = new org.apache.http.HttpException;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Unexpected response to CONNECT request: ";
        r3 = r3.append(r4);
        r4 = r2.getStatusLine();
        r3 = r3.append(r4);
        r3 = r3.toString();
        r0.<init>(r3);
        throw r0;
    L_0x0080:
        r0 = r10.params;
        r0 = org.apache.http.client.params.HttpClientParams.isAuthenticating(r0);
        if (r0 == 0) goto L_0x0009;
    L_0x0088:
        r0 = r10.authenticator;
        r3 = r10.proxyAuthStrategy;
        r4 = r10.proxyAuthState;
        r5 = r12;
        r0 = r0.isAuthenticationRequested(r1, r2, r3, r4, r5);
        if (r0 == 0) goto L_0x00c1;
    L_0x0095:
        r0 = r10.authenticator;
        r3 = r10.proxyAuthStrategy;
        r4 = r10.proxyAuthState;
        r5 = r12;
        r0 = r0.authenticate(r1, r2, r3, r4, r5);
        if (r0 == 0) goto L_0x00c1;
    L_0x00a2:
        r0 = r10.reuseStrategy;
        r0 = r0.keepAlive(r2, r12);
        if (r0 == 0) goto L_0x00ba;
    L_0x00aa:
        r0 = r10.log;
        r3 = "Connection kept alive";
        r0.debug(r3);
        r7 = r2.getEntity();
        org.apache.http.util.EntityUtils.consume(r7);
        goto L_0x0009;
    L_0x00ba:
        r0 = r10.managedConn;
        r0.close();
        goto L_0x0009;
    L_0x00c1:
        r0 = r2.getStatusLine();
        r8 = r0.getStatusCode();
        r0 = 299; // 0x12b float:4.19E-43 double:1.477E-321;
        if (r8 <= r0) goto L_0x00fd;
    L_0x00cd:
        r7 = r2.getEntity();
        if (r7 == 0) goto L_0x00db;
    L_0x00d3:
        r0 = new org.apache.http.entity.BufferedHttpEntity;
        r0.<init>(r7);
        r2.setEntity(r0);
    L_0x00db:
        r0 = r10.managedConn;
        r0.close();
        r0 = new org.apache.http.impl.client.TunnelRefusedException;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "CONNECT refused by proxy: ";
        r3 = r3.append(r4);
        r4 = r2.getStatusLine();
        r3 = r3.append(r4);
        r3 = r3.toString();
        r0.<init>(r3, r2);
        throw r0;
    L_0x00fd:
        r0 = r10.managedConn;
        r0.markReusable();
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.client.DefaultRequestDirector.createTunnelToTarget(org.apache.http.conn.routing.HttpRoute, org.apache.http.protocol.HttpContext):boolean");
    }

    protected boolean createTunnelToProxy(HttpRoute route, int hop, HttpContext context) throws HttpException, IOException {
        throw new HttpException("Proxy chains are not supported.");
    }

    protected HttpRequest createConnectRequest(HttpRoute route, HttpContext context) {
        HttpHost target = route.getTargetHost();
        String host = target.getHostName();
        int port = target.getPort();
        if (port < 0) {
            port = this.connManager.getSchemeRegistry().getScheme(target.getSchemeName()).getDefaultPort();
        }
        StringBuilder buffer = new StringBuilder(host.length() + 6);
        buffer.append(host);
        buffer.append(':');
        buffer.append(Integer.toString(port));
        return new BasicHttpRequest("CONNECT", buffer.toString(), HttpProtocolParams.getVersion(this.params));
    }

    protected RoutedRequest handleResponse(RoutedRequest roureq, HttpResponse response, HttpContext context) throws HttpException, IOException {
        HttpRoute route = roureq.getRoute();
        RequestWrapper request = roureq.getRequest();
        HttpParams params = request.getParams();
        if (HttpClientParams.isAuthenticating(params)) {
            HttpHost target = (HttpHost) context.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
            if (target == null) {
                target = route.getTargetHost();
            }
            if (target.getPort() < 0) {
                target = new HttpHost(target.getHostName(), this.connManager.getSchemeRegistry().getScheme(target).getDefaultPort(), target.getSchemeName());
            }
            boolean targetAuthRequested = this.authenticator.isAuthenticationRequested(target, response, this.targetAuthStrategy, this.targetAuthState, context);
            HttpHost proxy = route.getProxyHost();
            if (proxy == null) {
                proxy = route.getTargetHost();
            }
            boolean proxyAuthRequested = this.authenticator.isAuthenticationRequested(proxy, response, this.proxyAuthStrategy, this.proxyAuthState, context);
            if (targetAuthRequested) {
                if (this.authenticator.authenticate(target, response, this.targetAuthStrategy, this.targetAuthState, context)) {
                    return roureq;
                }
            }
            if (proxyAuthRequested) {
                if (this.authenticator.authenticate(proxy, response, this.proxyAuthStrategy, this.proxyAuthState, context)) {
                    return roureq;
                }
            }
        }
        if (!HttpClientParams.isRedirecting(params) || !this.redirectStrategy.isRedirected(request, response, context)) {
            return null;
        }
        if (this.redirectCount >= this.maxRedirects) {
            throw new RedirectException("Maximum redirects (" + this.maxRedirects + ") exceeded");
        }
        this.redirectCount++;
        this.virtualHost = null;
        HttpUriRequest redirect = this.redirectStrategy.getRedirect(request, response, context);
        redirect.setHeaders(request.getOriginal().getAllHeaders());
        URI uri = redirect.getURI();
        HttpHost newTarget = URIUtils.extractHost(uri);
        if (newTarget == null) {
            throw new ProtocolException("Redirect URI does not specify a valid host name: " + uri);
        }
        if (!route.getTargetHost().equals(newTarget)) {
            this.log.debug("Resetting target auth state");
            this.targetAuthState.reset();
            AuthScheme authScheme = this.proxyAuthState.getAuthScheme();
            if (authScheme != null && authScheme.isConnectionBased()) {
                this.log.debug("Resetting proxy auth state");
                this.proxyAuthState.reset();
            }
        }
        RequestWrapper wrapper = wrapRequest(redirect);
        wrapper.setParams(params);
        HttpRoute newRoute = determineRoute(newTarget, wrapper, context);
        RoutedRequest newRequest = new RoutedRequest(wrapper, newRoute);
        if (this.log.isDebugEnabled()) {
            this.log.debug("Redirecting to '" + uri + "' via " + newRoute);
        }
        return newRequest;
    }

    private void abortConnection() {
        ManagedClientConnection mcc = this.managedConn;
        if (mcc != null) {
            this.managedConn = null;
            try {
                mcc.abortConnection();
            } catch (IOException ex) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug(ex.getMessage(), ex);
                }
            }
            try {
                mcc.releaseConnection();
            } catch (IOException ignored) {
                this.log.debug("Error releasing connection", ignored);
            }
        }
    }
}
