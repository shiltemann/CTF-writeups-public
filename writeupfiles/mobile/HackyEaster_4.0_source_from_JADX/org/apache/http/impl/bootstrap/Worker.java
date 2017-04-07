package org.apache.http.impl.bootstrap;

import org.apache.http.ExceptionLogger;
import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.HttpService;

class Worker implements Runnable {
    private final HttpServerConnection conn;
    private final ExceptionLogger exceptionLogger;
    private final HttpService httpservice;

    Worker(HttpService httpservice, HttpServerConnection conn, ExceptionLogger exceptionLogger) {
        this.httpservice = httpservice;
        this.conn = conn;
        this.exceptionLogger = exceptionLogger;
    }

    public HttpServerConnection getConnection() {
        return this.conn;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r5 = this;
        r2 = new org.apache.http.protocol.BasicHttpContext;	 Catch:{ Exception -> 0x0022 }
        r2.<init>();	 Catch:{ Exception -> 0x0022 }
        r0 = org.apache.http.protocol.HttpCoreContext.adapt(r2);	 Catch:{ Exception -> 0x0022 }
    L_0x0009:
        r3 = java.lang.Thread.interrupted();	 Catch:{ Exception -> 0x0022 }
        if (r3 != 0) goto L_0x002e;
    L_0x000f:
        r3 = r5.conn;	 Catch:{ Exception -> 0x0022 }
        r3 = r3.isOpen();	 Catch:{ Exception -> 0x0022 }
        if (r3 == 0) goto L_0x002e;
    L_0x0017:
        r3 = r5.httpservice;	 Catch:{ Exception -> 0x0022 }
        r4 = r5.conn;	 Catch:{ Exception -> 0x0022 }
        r3.handleRequest(r4, r0);	 Catch:{ Exception -> 0x0022 }
        r2.clear();	 Catch:{ Exception -> 0x0022 }
        goto L_0x0009;
    L_0x0022:
        r1 = move-exception;
        r3 = r5.exceptionLogger;	 Catch:{ all -> 0x0047 }
        r3.log(r1);	 Catch:{ all -> 0x0047 }
        r3 = r5.conn;	 Catch:{ IOException -> 0x0040 }
        r3.shutdown();	 Catch:{ IOException -> 0x0040 }
    L_0x002d:
        return;
    L_0x002e:
        r3 = r5.conn;	 Catch:{ Exception -> 0x0022 }
        r3.close();	 Catch:{ Exception -> 0x0022 }
        r3 = r5.conn;	 Catch:{ IOException -> 0x0039 }
        r3.shutdown();	 Catch:{ IOException -> 0x0039 }
        goto L_0x002d;
    L_0x0039:
        r1 = move-exception;
        r3 = r5.exceptionLogger;
        r3.log(r1);
        goto L_0x002d;
    L_0x0040:
        r1 = move-exception;
        r3 = r5.exceptionLogger;
        r3.log(r1);
        goto L_0x002d;
    L_0x0047:
        r3 = move-exception;
        r4 = r5.conn;	 Catch:{ IOException -> 0x004e }
        r4.shutdown();	 Catch:{ IOException -> 0x004e }
    L_0x004d:
        throw r3;
    L_0x004e:
        r1 = move-exception;
        r4 = r5.exceptionLogger;
        r4.log(r1);
        goto L_0x004d;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.bootstrap.Worker.run():void");
    }
}
