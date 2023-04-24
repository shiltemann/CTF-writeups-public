package org.apache.http.impl.client;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.auth.AuthState;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.client.params.HttpClientParamConfig;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.auth.HttpAuthenticator;
import org.apache.http.impl.auth.KerberosSchemeFactory;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParamConfig;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;

public class ProxyClient {
    private final AuthSchemeRegistry authSchemeRegistry;
    private final HttpAuthenticator authenticator;
    private final HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory;
    private final ConnectionConfig connectionConfig;
    private final HttpProcessor httpProcessor;
    private final AuthState proxyAuthState;
    private final ProxyAuthenticationStrategy proxyAuthStrategy;
    private final RequestConfig requestConfig;
    private final HttpRequestExecutor requestExec;
    private final ConnectionReuseStrategy reuseStrategy;

    public ProxyClient(HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory, ConnectionConfig connectionConfig, RequestConfig requestConfig) {
        if (connFactory == null) {
            connFactory = ManagedHttpClientConnectionFactory.INSTANCE;
        }
        this.connFactory = connFactory;
        if (connectionConfig == null) {
            connectionConfig = ConnectionConfig.DEFAULT;
        }
        this.connectionConfig = connectionConfig;
        if (requestConfig == null) {
            requestConfig = RequestConfig.DEFAULT;
        }
        this.requestConfig = requestConfig;
        this.httpProcessor = new ImmutableHttpProcessor(new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent());
        this.requestExec = new HttpRequestExecutor();
        this.proxyAuthStrategy = new ProxyAuthenticationStrategy();
        this.authenticator = new HttpAuthenticator();
        this.proxyAuthState = new AuthState();
        this.authSchemeRegistry = new AuthSchemeRegistry();
        this.authSchemeRegistry.register(AuthPolicy.BASIC, new BasicSchemeFactory());
        this.authSchemeRegistry.register(AuthPolicy.DIGEST, new DigestSchemeFactory());
        this.authSchemeRegistry.register(AuthPolicy.NTLM, new NTLMSchemeFactory());
        this.authSchemeRegistry.register(AuthPolicy.SPNEGO, new SPNegoSchemeFactory());
        this.authSchemeRegistry.register(AuthPolicy.KERBEROS, new KerberosSchemeFactory());
        this.reuseStrategy = new DefaultConnectionReuseStrategy();
    }

    @Deprecated
    public ProxyClient(HttpParams params) {
        this(null, HttpParamConfig.getConnectionConfig(params), HttpClientParamConfig.getRequestConfig(params));
    }

    public ProxyClient(RequestConfig requestConfig) {
        this(null, null, requestConfig);
    }

    public ProxyClient() {
        this(null, null, null);
    }

    @Deprecated
    public HttpParams getParams() {
        return new BasicHttpParams();
    }

    @Deprecated
    public AuthSchemeRegistry getAuthSchemeRegistry() {
        return this.authSchemeRegistry;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.net.Socket tunnel(org.apache.http.HttpHost r17, org.apache.http.HttpHost r18, org.apache.http.auth.Credentials r19) throws java.io.IOException, org.apache.http.HttpException {
        /*
        r16 = this;
        r3 = "Proxy host";
        r0 = r17;
        org.apache.http.util.Args.notNull(r0, r3);
        r3 = "Target host";
        r0 = r18;
        org.apache.http.util.Args.notNull(r0, r3);
        r3 = "Credentials";
        r0 = r19;
        org.apache.http.util.Args.notNull(r0, r3);
        r2 = r18;
        r3 = r2.getPort();
        if (r3 > 0) goto L_0x002d;
    L_0x001d:
        r13 = new org.apache.http.HttpHost;
        r3 = r2.getHostName();
        r4 = 80;
        r6 = r2.getSchemeName();
        r13.<init>(r3, r4, r6);
        r2 = r13;
    L_0x002d:
        r1 = new org.apache.http.conn.routing.HttpRoute;
        r0 = r16;
        r3 = r0.requestConfig;
        r3 = r3.getLocalAddress();
        r5 = 0;
        r6 = org.apache.http.conn.routing.RouteInfo.TunnelType.TUNNELLED;
        r7 = org.apache.http.conn.routing.RouteInfo.LayerType.PLAIN;
        r4 = r17;
        r1.<init>(r2, r3, r4, r5, r6, r7);
        r0 = r16;
        r3 = r0.connFactory;
        r0 = r16;
        r4 = r0.connectionConfig;
        r9 = r3.create(r1, r4);
        r9 = (org.apache.http.conn.ManagedHttpClientConnection) r9;
        r8 = new org.apache.http.protocol.BasicHttpContext;
        r8.<init>();
        r10 = new org.apache.http.message.BasicHttpRequest;
        r3 = "CONNECT";
        r4 = r2.toHostString();
        r6 = org.apache.http.HttpVersion.HTTP_1_1;
        r10.<init>(r3, r4, r6);
        r11 = new org.apache.http.impl.client.BasicCredentialsProvider;
        r11.<init>();
        r3 = new org.apache.http.auth.AuthScope;
        r0 = r17;
        r3.<init>(r0);
        r0 = r19;
        r11.setCredentials(r3, r0);
        r3 = "http.target_host";
        r0 = r18;
        r8.setAttribute(r3, r0);
        r3 = "http.connection";
        r8.setAttribute(r3, r9);
        r3 = "http.request";
        r8.setAttribute(r3, r10);
        r3 = "http.route";
        r8.setAttribute(r3, r1);
        r3 = "http.auth.proxy-scope";
        r0 = r16;
        r4 = r0.proxyAuthState;
        r8.setAttribute(r3, r4);
        r3 = "http.auth.credentials-provider";
        r8.setAttribute(r3, r11);
        r3 = "http.authscheme-registry";
        r0 = r16;
        r4 = r0.authSchemeRegistry;
        r8.setAttribute(r3, r4);
        r3 = "http.request-config";
        r0 = r16;
        r4 = r0.requestConfig;
        r8.setAttribute(r3, r4);
        r0 = r16;
        r3 = r0.requestExec;
        r0 = r16;
        r4 = r0.httpProcessor;
        r3.preProcess(r10, r4, r8);
    L_0x00b3:
        r3 = r9.isOpen();
        if (r3 != 0) goto L_0x00c9;
    L_0x00b9:
        r14 = new java.net.Socket;
        r3 = r17.getHostName();
        r4 = r17.getPort();
        r14.<init>(r3, r4);
        r9.bind(r14);
    L_0x00c9:
        r0 = r16;
        r3 = r0.authenticator;
        r0 = r16;
        r4 = r0.proxyAuthState;
        r3.generateAuthResponse(r10, r4, r8);
        r0 = r16;
        r3 = r0.requestExec;
        r5 = r3.execute(r10, r9, r8);
        r3 = r5.getStatusLine();
        r15 = r3.getStatusCode();
        r3 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r15 >= r3) goto L_0x0105;
    L_0x00e8:
        r3 = new org.apache.http.HttpException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r6 = "Unexpected response to CONNECT request: ";
        r4 = r4.append(r6);
        r6 = r5.getStatusLine();
        r4 = r4.append(r6);
        r4 = r4.toString();
        r3.<init>(r4);
        throw r3;
    L_0x0105:
        r0 = r16;
        r3 = r0.authenticator;
        r0 = r16;
        r6 = r0.proxyAuthStrategy;
        r0 = r16;
        r7 = r0.proxyAuthState;
        r4 = r17;
        r3 = r3.isAuthenticationRequested(r4, r5, r6, r7, r8);
        if (r3 == 0) goto L_0x0149;
    L_0x0119:
        r0 = r16;
        r3 = r0.authenticator;
        r0 = r16;
        r6 = r0.proxyAuthStrategy;
        r0 = r16;
        r7 = r0.proxyAuthState;
        r4 = r17;
        r3 = r3.handleAuthChallenge(r4, r5, r6, r7, r8);
        if (r3 == 0) goto L_0x0149;
    L_0x012d:
        r0 = r16;
        r3 = r0.reuseStrategy;
        r3 = r3.keepAlive(r5, r8);
        if (r3 == 0) goto L_0x0145;
    L_0x0137:
        r12 = r5.getEntity();
        org.apache.http.util.EntityUtils.consume(r12);
    L_0x013e:
        r3 = "Proxy-Authorization";
        r10.removeHeaders(r3);
        goto L_0x00b3;
    L_0x0145:
        r9.close();
        goto L_0x013e;
    L_0x0149:
        r3 = r5.getStatusLine();
        r15 = r3.getStatusCode();
        r3 = 299; // 0x12b float:4.19E-43 double:1.477E-321;
        if (r15 <= r3) goto L_0x0183;
    L_0x0155:
        r12 = r5.getEntity();
        if (r12 == 0) goto L_0x0163;
    L_0x015b:
        r3 = new org.apache.http.entity.BufferedHttpEntity;
        r3.<init>(r12);
        r5.setEntity(r3);
    L_0x0163:
        r9.close();
        r3 = new org.apache.http.impl.execchain.TunnelRefusedException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r6 = "CONNECT refused by proxy: ";
        r4 = r4.append(r6);
        r6 = r5.getStatusLine();
        r4 = r4.append(r6);
        r4 = r4.toString();
        r3.<init>(r4, r5);
        throw r3;
    L_0x0183:
        r3 = r9.getSocket();
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.client.ProxyClient.tunnel(org.apache.http.HttpHost, org.apache.http.HttpHost, org.apache.http.auth.Credentials):java.net.Socket");
    }
}
