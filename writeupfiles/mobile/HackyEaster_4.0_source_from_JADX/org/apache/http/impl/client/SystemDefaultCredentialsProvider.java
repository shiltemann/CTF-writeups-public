package org.apache.http.impl.client;

import java.net.Authenticator;
import java.net.Authenticator.RequestorType;
import java.net.PasswordAuthentication;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpHost;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.util.Args;

@ThreadSafe
public class SystemDefaultCredentialsProvider implements CredentialsProvider {
    private static final Map<String, String> SCHEME_MAP;
    private final BasicCredentialsProvider internal;

    static {
        SCHEME_MAP = new ConcurrentHashMap();
        SCHEME_MAP.put(AuthPolicy.BASIC.toUpperCase(Locale.ROOT), AuthPolicy.BASIC);
        SCHEME_MAP.put(AuthPolicy.DIGEST.toUpperCase(Locale.ROOT), AuthPolicy.DIGEST);
        SCHEME_MAP.put(AuthPolicy.NTLM.toUpperCase(Locale.ROOT), AuthPolicy.NTLM);
        SCHEME_MAP.put(AuthPolicy.SPNEGO.toUpperCase(Locale.ROOT), "SPNEGO");
        SCHEME_MAP.put(AuthPolicy.KERBEROS.toUpperCase(Locale.ROOT), AuthPolicy.KERBEROS);
    }

    private static String translateScheme(String key) {
        if (key == null) {
            return null;
        }
        String s = (String) SCHEME_MAP.get(key);
        return s == null ? key : s;
    }

    public SystemDefaultCredentialsProvider() {
        this.internal = new BasicCredentialsProvider();
    }

    public void setCredentials(AuthScope authscope, Credentials credentials) {
        this.internal.setCredentials(authscope, credentials);
    }

    private static PasswordAuthentication getSystemCreds(AuthScope authscope, RequestorType requestorType) {
        String hostname = authscope.getHost();
        int port = authscope.getPort();
        HttpHost origin = authscope.getOrigin();
        String protocol = origin != null ? origin.getSchemeName() : port == 443 ? "https" : HttpHost.DEFAULT_SCHEME_NAME;
        return Authenticator.requestPasswordAuthentication(hostname, null, port, protocol, null, translateScheme(authscope.getScheme()), null, requestorType);
    }

    public Credentials getCredentials(AuthScope authscope) {
        Args.notNull(authscope, "Auth scope");
        Credentials localcreds = this.internal.getCredentials(authscope);
        if (localcreds != null) {
            return localcreds;
        }
        if (authscope.getHost() != null) {
            PasswordAuthentication systemcreds = getSystemCreds(authscope, RequestorType.SERVER);
            if (systemcreds == null) {
                systemcreds = getSystemCreds(authscope, RequestorType.PROXY);
            }
            if (systemcreds != null) {
                String domain = System.getProperty("http.auth.ntlm.domain");
                if (domain != null) {
                    return new NTCredentials(systemcreds.getUserName(), new String(systemcreds.getPassword()), null, domain);
                }
                if (AuthPolicy.NTLM.equalsIgnoreCase(authscope.getScheme())) {
                    return new NTCredentials(systemcreds.getUserName(), new String(systemcreds.getPassword()), null, null);
                }
                return new UsernamePasswordCredentials(systemcreds.getUserName(), new String(systemcreds.getPassword()));
            }
        }
        return null;
    }

    public void clear() {
        this.internal.clear();
    }
}
