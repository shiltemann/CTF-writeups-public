package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeLayeredSocketFactory;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
@ThreadSafe
public class DefaultClientConnectionOperator implements ClientConnectionOperator {
    protected final DnsResolver dnsResolver;
    private final Log log;
    protected final SchemeRegistry schemeRegistry;

    public DefaultClientConnectionOperator(SchemeRegistry schemes) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(schemes, "Scheme registry");
        this.schemeRegistry = schemes;
        this.dnsResolver = new SystemDefaultDnsResolver();
    }

    public DefaultClientConnectionOperator(SchemeRegistry schemes, DnsResolver dnsResolver) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(schemes, "Scheme registry");
        Args.notNull(dnsResolver, "DNS resolver");
        this.schemeRegistry = schemes;
        this.dnsResolver = dnsResolver;
    }

    public OperatedClientConnection createConnection() {
        return new DefaultClientConnection();
    }

    private SchemeRegistry getSchemeRegistry(HttpContext context) {
        SchemeRegistry reg = (SchemeRegistry) context.getAttribute(ClientContext.SCHEME_REGISTRY);
        if (reg == null) {
            return this.schemeRegistry;
        }
        return reg;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void openConnection(org.apache.http.conn.OperatedClientConnection r20, org.apache.http.HttpHost r21, java.net.InetAddress r22, org.apache.http.protocol.HttpContext r23, org.apache.http.params.HttpParams r24) throws java.io.IOException {
        /*
        r19 = this;
        r16 = "Connection";
        r0 = r20;
        r1 = r16;
        org.apache.http.util.Args.notNull(r0, r1);
        r16 = "Target host";
        r0 = r21;
        r1 = r16;
        org.apache.http.util.Args.notNull(r0, r1);
        r16 = "HTTP parameters";
        r0 = r24;
        r1 = r16;
        org.apache.http.util.Args.notNull(r0, r1);
        r16 = r20.isOpen();
        if (r16 != 0) goto L_0x00db;
    L_0x0021:
        r16 = 1;
    L_0x0023:
        r17 = "Connection must not be open";
        org.apache.http.util.Asserts.check(r16, r17);
        r0 = r19;
        r1 = r23;
        r11 = r0.getSchemeRegistry(r1);
        r16 = r21.getSchemeName();
        r0 = r16;
        r13 = r11.getScheme(r0);
        r14 = r13.getSchemeSocketFactory();
        r16 = r21.getHostName();
        r0 = r19;
        r1 = r16;
        r4 = r0.resolveHostname(r1);
        r16 = r21.getPort();
        r0 = r16;
        r10 = r13.resolvePort(r0);
        r7 = 0;
    L_0x0055:
        r0 = r4.length;
        r16 = r0;
        r0 = r16;
        if (r7 >= r0) goto L_0x00da;
    L_0x005c:
        r3 = r4[r7];
        r0 = r4.length;
        r16 = r0;
        r16 = r16 + -1;
        r0 = r16;
        if (r7 != r0) goto L_0x00df;
    L_0x0067:
        r8 = 1;
    L_0x0068:
        r0 = r24;
        r15 = r14.createSocket(r0);
        r0 = r20;
        r1 = r21;
        r0.opening(r15, r1);
        r12 = new org.apache.http.conn.HttpInetSocketAddress;
        r0 = r21;
        r12.<init>(r0, r3, r10);
        r9 = 0;
        if (r22 == 0) goto L_0x008a;
    L_0x007f:
        r9 = new java.net.InetSocketAddress;
        r16 = 0;
        r0 = r22;
        r1 = r16;
        r9.<init>(r0, r1);
    L_0x008a:
        r0 = r19;
        r0 = r0.log;
        r16 = r0;
        r16 = r16.isDebugEnabled();
        if (r16 == 0) goto L_0x00b4;
    L_0x0096:
        r0 = r19;
        r0 = r0.log;
        r16 = r0;
        r17 = new java.lang.StringBuilder;
        r17.<init>();
        r18 = "Connecting to ";
        r17 = r17.append(r18);
        r0 = r17;
        r17 = r0.append(r12);
        r17 = r17.toString();
        r16.debug(r17);
    L_0x00b4:
        r0 = r24;
        r5 = r14.connectSocket(r15, r12, r9, r0);	 Catch:{ ConnectException -> 0x00e1, ConnectTimeoutException -> 0x00e5 }
        if (r15 == r5) goto L_0x00c4;
    L_0x00bc:
        r15 = r5;
        r0 = r20;
        r1 = r21;
        r0.opening(r15, r1);	 Catch:{ ConnectException -> 0x00e1, ConnectTimeoutException -> 0x00e5 }
    L_0x00c4:
        r0 = r19;
        r1 = r23;
        r2 = r24;
        r0.prepareSocket(r15, r1, r2);	 Catch:{ ConnectException -> 0x00e1, ConnectTimeoutException -> 0x00e5 }
        r16 = r14.isSecure(r15);	 Catch:{ ConnectException -> 0x00e1, ConnectTimeoutException -> 0x00e5 }
        r0 = r20;
        r1 = r16;
        r2 = r24;
        r0.openCompleted(r1, r2);	 Catch:{ ConnectException -> 0x00e1, ConnectTimeoutException -> 0x00e5 }
    L_0x00da:
        return;
    L_0x00db:
        r16 = 0;
        goto L_0x0023;
    L_0x00df:
        r8 = 0;
        goto L_0x0068;
    L_0x00e1:
        r6 = move-exception;
        if (r8 == 0) goto L_0x00e9;
    L_0x00e4:
        throw r6;
    L_0x00e5:
        r6 = move-exception;
        if (r8 == 0) goto L_0x00e9;
    L_0x00e8:
        throw r6;
    L_0x00e9:
        r0 = r19;
        r0 = r0.log;
        r16 = r0;
        r16 = r16.isDebugEnabled();
        if (r16 == 0) goto L_0x011f;
    L_0x00f5:
        r0 = r19;
        r0 = r0.log;
        r16 = r0;
        r17 = new java.lang.StringBuilder;
        r17.<init>();
        r18 = "Connect to ";
        r17 = r17.append(r18);
        r0 = r17;
        r17 = r0.append(r12);
        r18 = " timed out. ";
        r17 = r17.append(r18);
        r18 = "Connection will be retried using another IP address";
        r17 = r17.append(r18);
        r17 = r17.toString();
        r16.debug(r17);
    L_0x011f:
        r7 = r7 + 1;
        goto L_0x0055;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.DefaultClientConnectionOperator.openConnection(org.apache.http.conn.OperatedClientConnection, org.apache.http.HttpHost, java.net.InetAddress, org.apache.http.protocol.HttpContext, org.apache.http.params.HttpParams):void");
    }

    public void updateSecureConnection(OperatedClientConnection conn, HttpHost target, HttpContext context, HttpParams params) throws IOException {
        Args.notNull(conn, HTTP.CONN_DIRECTIVE);
        Args.notNull(target, "Target host");
        Args.notNull(params, "Parameters");
        Asserts.check(conn.isOpen(), "Connection must be open");
        Scheme schm = getSchemeRegistry(context).getScheme(target.getSchemeName());
        Asserts.check(schm.getSchemeSocketFactory() instanceof SchemeLayeredSocketFactory, "Socket factory must implement SchemeLayeredSocketFactory");
        SchemeLayeredSocketFactory lsf = (SchemeLayeredSocketFactory) schm.getSchemeSocketFactory();
        Socket sock = lsf.createLayeredSocket(conn.getSocket(), target.getHostName(), schm.resolvePort(target.getPort()), params);
        prepareSocket(sock, context, params);
        conn.update(sock, target, lsf.isSecure(sock), params);
    }

    protected void prepareSocket(Socket sock, HttpContext context, HttpParams params) throws IOException {
        sock.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(params));
        sock.setSoTimeout(HttpConnectionParams.getSoTimeout(params));
        int linger = HttpConnectionParams.getLinger(params);
        if (linger >= 0) {
            sock.setSoLinger(linger > 0, linger);
        }
    }

    protected InetAddress[] resolveHostname(String host) throws UnknownHostException {
        return this.dnsResolver.resolve(host);
    }
}
