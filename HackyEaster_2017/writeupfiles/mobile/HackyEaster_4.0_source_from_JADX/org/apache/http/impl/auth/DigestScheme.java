package org.apache.http.impl.auth;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BufferedHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EncodingUtils;

@NotThreadSafe
public class DigestScheme extends RFC2617Scheme {
    private static final char[] HEXADECIMAL;
    private static final int QOP_AUTH = 2;
    private static final int QOP_AUTH_INT = 1;
    private static final int QOP_MISSING = 0;
    private static final int QOP_UNKNOWN = -1;
    private static final long serialVersionUID = 3883908186234566916L;
    private String a1;
    private String a2;
    private String cnonce;
    private boolean complete;
    private String lastNonce;
    private long nounceCount;

    static {
        HEXADECIMAL = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    }

    public DigestScheme(Charset credentialsCharset) {
        super(credentialsCharset);
        this.complete = false;
    }

    @Deprecated
    public DigestScheme(ChallengeState challengeState) {
        super(challengeState);
    }

    public DigestScheme() {
        this(Consts.ASCII);
    }

    public void processChallenge(Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        this.complete = true;
        if (getParameters().isEmpty()) {
            throw new MalformedChallengeException("Authentication challenge is empty");
        }
    }

    public boolean isComplete() {
        if ("true".equalsIgnoreCase(getParameter("stale"))) {
            return false;
        }
        return this.complete;
    }

    public String getSchemeName() {
        return "digest";
    }

    public boolean isConnectionBased() {
        return false;
    }

    public void overrideParamter(String name, String value) {
        getParameters().put(name, value);
    }

    @Deprecated
    public Header authenticate(Credentials credentials, HttpRequest request) throws AuthenticationException {
        return authenticate(credentials, request, new BasicHttpContext());
    }

    public Header authenticate(Credentials credentials, HttpRequest request, HttpContext context) throws AuthenticationException {
        Args.notNull(credentials, "Credentials");
        Args.notNull(request, "HTTP request");
        if (getParameter("realm") == null) {
            throw new AuthenticationException("missing realm in challenge");
        } else if (getParameter("nonce") == null) {
            throw new AuthenticationException("missing nonce in challenge");
        } else {
            getParameters().put("methodname", request.getRequestLine().getMethod());
            getParameters().put("uri", request.getRequestLine().getUri());
            if (getParameter("charset") == null) {
                getParameters().put("charset", getCredentialsCharset(request));
            }
            return createDigestHeader(credentials, request);
        }
    }

    private static MessageDigest createMessageDigest(String digAlg) throws UnsupportedDigestAlgorithmException {
        try {
            return MessageDigest.getInstance(digAlg);
        } catch (Exception e) {
            throw new UnsupportedDigestAlgorithmException("Unsupported algorithm in HTTP Digest authentication: " + digAlg);
        }
    }

    private Header createDigestHeader(Credentials credentials, HttpRequest request) throws AuthenticationException {
        String uri = getParameter("uri");
        String realm = getParameter("realm");
        String nonce = getParameter("nonce");
        String opaque = getParameter("opaque");
        String method = getParameter("methodname");
        String algorithm = getParameter("algorithm");
        if (algorithm == null) {
            algorithm = MessageDigestAlgorithms.MD5;
        }
        Set<String> hashSet = new HashSet(8);
        int qop = QOP_UNKNOWN;
        String qoplist = getParameter("qop");
        if (qoplist != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(qoplist, ",");
            while (stringTokenizer.hasMoreTokens()) {
                hashSet.add(stringTokenizer.nextToken().trim().toLowerCase(Locale.ROOT));
            }
            if (request instanceof HttpEntityEnclosingRequest) {
                if (hashSet.contains("auth-int")) {
                    qop = QOP_AUTH_INT;
                }
            }
            if (hashSet.contains("auth")) {
                qop = QOP_AUTH;
            }
        } else {
            qop = QOP_MISSING;
        }
        if (qop == QOP_UNKNOWN) {
            throw new AuthenticationException("None of the qop methods is supported: " + qoplist);
        }
        String charset = getParameter("charset");
        if (charset == null) {
            charset = HTTP.ISO_8859_1;
        }
        String digAlg = algorithm;
        if (digAlg.equalsIgnoreCase("MD5-sess")) {
            digAlg = MessageDigestAlgorithms.MD5;
        }
        try {
            String digestValue;
            MessageDigest digester = createMessageDigest(digAlg);
            String uname = credentials.getUserPrincipal().getName();
            String pwd = credentials.getPassword();
            if (nonce.equals(this.lastNonce)) {
                this.nounceCount++;
            } else {
                this.nounceCount = 1;
                this.cnonce = null;
                this.lastNonce = nonce;
            }
            Appendable stringBuilder = new StringBuilder(AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
            Formatter formatter = new Formatter(stringBuilder, Locale.US);
            Long[] lArr = new Object[QOP_AUTH_INT];
            lArr[QOP_MISSING] = Long.valueOf(this.nounceCount);
            formatter.format("%08x", lArr);
            formatter.close();
            String nc = stringBuilder.toString();
            if (this.cnonce == null) {
                this.cnonce = createCnonce();
            }
            this.a1 = null;
            this.a2 = null;
            if (algorithm.equalsIgnoreCase("MD5-sess")) {
                stringBuilder.setLength(QOP_MISSING);
                stringBuilder.append(uname).append(':').append(realm).append(':').append(pwd);
                String checksum = encode(digester.digest(EncodingUtils.getBytes(stringBuilder.toString(), charset)));
                stringBuilder.setLength(QOP_MISSING);
                stringBuilder.append(checksum).append(':').append(nonce).append(':').append(this.cnonce);
                this.a1 = stringBuilder.toString();
            } else {
                stringBuilder.setLength(QOP_MISSING);
                stringBuilder.append(uname).append(':').append(realm).append(':').append(pwd);
                this.a1 = stringBuilder.toString();
            }
            String hasha1 = encode(digester.digest(EncodingUtils.getBytes(this.a1, charset)));
            if (qop == QOP_AUTH) {
                this.a2 = method + ':' + uri;
            } else if (qop == QOP_AUTH_INT) {
                HttpEntity entity = null;
                if (request instanceof HttpEntityEnclosingRequest) {
                    entity = ((HttpEntityEnclosingRequest) request).getEntity();
                }
                if (entity == null || entity.isRepeatable()) {
                    HttpEntityDigester entityDigester = new HttpEntityDigester(digester);
                    if (entity != null) {
                        try {
                            entity.writeTo(entityDigester);
                        } catch (IOException ex) {
                            throw new AuthenticationException("I/O error reading entity content", ex);
                        }
                    }
                    entityDigester.close();
                    this.a2 = method + ':' + uri + ':' + encode(entityDigester.getDigest());
                } else {
                    if (hashSet.contains("auth")) {
                        qop = QOP_AUTH;
                        this.a2 = method + ':' + uri;
                    } else {
                        throw new AuthenticationException("Qop auth-int cannot be used with a non-repeatable entity");
                    }
                }
            } else {
                this.a2 = method + ':' + uri;
            }
            String hasha2 = encode(digester.digest(EncodingUtils.getBytes(this.a2, charset)));
            if (qop == 0) {
                stringBuilder.setLength(QOP_MISSING);
                stringBuilder.append(hasha1).append(':').append(nonce).append(':').append(hasha2);
                digestValue = stringBuilder.toString();
            } else {
                stringBuilder.setLength(QOP_MISSING);
                stringBuilder.append(hasha1).append(':').append(nonce).append(':').append(nc).append(':').append(this.cnonce).append(':').append(qop == QOP_AUTH_INT ? "auth-int" : "auth").append(':').append(hasha2);
                digestValue = stringBuilder.toString();
            }
            String digest = encode(digester.digest(EncodingUtils.getAsciiBytes(digestValue)));
            CharArrayBuffer buffer = new CharArrayBuffer(AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
            if (isProxy()) {
                buffer.append(AUTH.PROXY_AUTH_RESP);
            } else {
                buffer.append(AUTH.WWW_AUTH_RESP);
            }
            buffer.append(": Digest ");
            List<BasicNameValuePair> arrayList = new ArrayList(20);
            arrayList.add(new BasicNameValuePair("username", uname));
            arrayList.add(new BasicNameValuePair("realm", realm));
            arrayList.add(new BasicNameValuePair("nonce", nonce));
            arrayList.add(new BasicNameValuePair("uri", uri));
            arrayList.add(new BasicNameValuePair("response", digest));
            if (qop != 0) {
                arrayList.add(new BasicNameValuePair("qop", qop == QOP_AUTH_INT ? "auth-int" : "auth"));
                arrayList.add(new BasicNameValuePair("nc", nc));
                arrayList.add(new BasicNameValuePair("cnonce", this.cnonce));
            }
            arrayList.add(new BasicNameValuePair("algorithm", algorithm));
            if (opaque != null) {
                arrayList.add(new BasicNameValuePair("opaque", opaque));
            }
            for (int i = QOP_MISSING; i < arrayList.size(); i += QOP_AUTH_INT) {
                boolean noQuotes;
                BasicHeaderValueFormatter basicHeaderValueFormatter;
                boolean z;
                BasicNameValuePair param = (BasicNameValuePair) arrayList.get(i);
                if (i > 0) {
                    buffer.append(", ");
                }
                String name = param.getName();
                if (!"nc".equals(name)) {
                    if (!"qop".equals(name)) {
                        if (!"algorithm".equals(name)) {
                            noQuotes = false;
                            basicHeaderValueFormatter = BasicHeaderValueFormatter.INSTANCE;
                            if (noQuotes) {
                                z = true;
                            } else {
                                z = false;
                            }
                            basicHeaderValueFormatter.formatNameValuePair(buffer, (NameValuePair) param, z);
                        }
                    }
                }
                noQuotes = true;
                basicHeaderValueFormatter = BasicHeaderValueFormatter.INSTANCE;
                if (noQuotes) {
                    z = false;
                } else {
                    z = true;
                }
                basicHeaderValueFormatter.formatNameValuePair(buffer, (NameValuePair) param, z);
            }
            return new BufferedHeader(buffer);
        } catch (UnsupportedDigestAlgorithmException e) {
            throw new AuthenticationException("Unsuppported digest algorithm: " + digAlg);
        }
    }

    String getCnonce() {
        return this.cnonce;
    }

    String getA1() {
        return this.a1;
    }

    String getA2() {
        return this.a2;
    }

    static String encode(byte[] binaryData) {
        int n = binaryData.length;
        char[] buffer = new char[(n * QOP_AUTH)];
        for (int i = QOP_MISSING; i < n; i += QOP_AUTH_INT) {
            int low = binaryData[i] & 15;
            buffer[i * QOP_AUTH] = HEXADECIMAL[(binaryData[i] & 240) >> 4];
            buffer[(i * QOP_AUTH) + QOP_AUTH_INT] = HEXADECIMAL[low];
        }
        return new String(buffer);
    }

    public static String createCnonce() {
        byte[] tmp = new byte[8];
        new SecureRandom().nextBytes(tmp);
        return encode(tmp);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DIGEST [complete=").append(this.complete).append(", nonce=").append(this.lastNonce).append(", nc=").append(this.nounceCount).append("]");
        return builder.toString();
    }
}
