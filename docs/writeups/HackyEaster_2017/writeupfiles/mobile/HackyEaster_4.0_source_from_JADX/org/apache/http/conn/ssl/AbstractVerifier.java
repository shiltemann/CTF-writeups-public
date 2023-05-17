package org.apache.http.conn.ssl;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

@Deprecated
public abstract class AbstractVerifier implements X509HostnameVerifier {
    static final String[] BAD_COUNTRY_2LDS;
    private final Log log;

    public AbstractVerifier() {
        this.log = LogFactory.getLog(getClass());
    }

    static {
        BAD_COUNTRY_2LDS = new String[]{"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", "ne", "net", "or", "org"};
        Arrays.sort(BAD_COUNTRY_2LDS);
    }

    public final void verify(String host, SSLSocket ssl) throws IOException {
        Args.notNull(host, HTTP.TARGET_HOST);
        SSLSession session = ssl.getSession();
        if (session == null) {
            ssl.getInputStream().available();
            session = ssl.getSession();
            if (session == null) {
                ssl.startHandshake();
                session = ssl.getSession();
            }
        }
        verify(host, session.getPeerCertificates()[0]);
    }

    public final boolean verify(String host, SSLSession session) {
        try {
            verify(host, session.getPeerCertificates()[0]);
            return true;
        } catch (SSLException ex) {
            if (!this.log.isDebugEnabled()) {
                return false;
            }
            this.log.debug(ex.getMessage(), ex);
            return false;
        }
    }

    public final void verify(String host, X509Certificate cert) throws SSLException {
        String[] strArr = null;
        int subjectType = (InetAddressUtils.isIPv4Address(host) || InetAddressUtils.isIPv6Address(host)) ? 7 : 2;
        List<String> subjectAlts = DefaultHostnameVerifier.extractSubjectAlts(cert, subjectType);
        String[] strArr2 = DefaultHostnameVerifier.extractCN(cert.getSubjectX500Principal().getName("RFC2253")) != null ? new String[]{DefaultHostnameVerifier.extractCN(cert.getSubjectX500Principal().getName("RFC2253"))} : null;
        if (!(subjectAlts == null || subjectAlts.isEmpty())) {
            strArr = (String[]) subjectAlts.toArray(new String[subjectAlts.size()]);
        }
        verify(host, strArr2, strArr);
    }

    public final void verify(String host, String[] cns, String[] subjectAlts, boolean strictWithSubDomains) throws SSLException {
        String cn;
        String normalizedHost;
        List<String> subjectAltList = null;
        if (cns == null || cns.length <= 0) {
            cn = null;
        } else {
            cn = cns[0];
        }
        if (subjectAlts != null && subjectAlts.length > 0) {
            subjectAltList = Arrays.asList(subjectAlts);
        }
        if (InetAddressUtils.isIPv6Address(host)) {
            normalizedHost = DefaultHostnameVerifier.normaliseAddress(host.toLowerCase(Locale.ROOT));
        } else {
            normalizedHost = host;
        }
        if (subjectAltList != null) {
            for (String subjectAlt : subjectAltList) {
                String normalizedAltSubject;
                if (InetAddressUtils.isIPv6Address(subjectAlt)) {
                    normalizedAltSubject = DefaultHostnameVerifier.normaliseAddress(subjectAlt);
                } else {
                    normalizedAltSubject = subjectAlt;
                }
                if (matchIdentity(normalizedHost, normalizedAltSubject, strictWithSubDomains)) {
                    return;
                }
            }
            throw new SSLException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAltList);
        } else if (cn != null) {
            String normalizedCN;
            if (InetAddressUtils.isIPv6Address(cn)) {
                normalizedCN = DefaultHostnameVerifier.normaliseAddress(cn);
            } else {
                normalizedCN = cn;
            }
            if (!matchIdentity(normalizedHost, normalizedCN, strictWithSubDomains)) {
                throw new SSLException("Certificate for <" + host + "> doesn't match " + "common name of the certificate subject: " + cn);
            }
        } else {
            throw new SSLException("Certificate subject for <" + host + "> doesn't contain " + "a common name and does not have alternative names");
        }
    }

    private static boolean matchIdentity(String host, String identity, boolean strict) {
        boolean z = true;
        if (host == null) {
            return false;
        }
        boolean doWildcard;
        String normalizedHost = host.toLowerCase(Locale.ROOT);
        String normalizedIdentity = identity.toLowerCase(Locale.ROOT);
        String[] parts = normalizedIdentity.split("\\.");
        if (parts.length < 3 || !parts[0].endsWith("*") || (strict && !validCountryWildcard(parts))) {
            doWildcard = false;
        } else {
            doWildcard = true;
        }
        if (!doWildcard) {
            return normalizedHost.equals(normalizedIdentity);
        }
        boolean match;
        String firstpart = parts[0];
        if (firstpart.length() > 1) {
            String prefix = firstpart.substring(0, firstpart.length() - 1);
            String suffix = normalizedIdentity.substring(firstpart.length());
            String hostSuffix = normalizedHost.substring(prefix.length());
            if (normalizedHost.startsWith(prefix) && hostSuffix.endsWith(suffix)) {
                match = true;
            } else {
                match = false;
            }
        } else {
            match = normalizedHost.endsWith(normalizedIdentity.substring(1));
        }
        if (!match || (strict && countDots(normalizedHost) != countDots(normalizedIdentity))) {
            z = false;
        }
        return z;
    }

    private static boolean validCountryWildcard(String[] parts) {
        if (parts.length == 3 && parts[2].length() == 2 && Arrays.binarySearch(BAD_COUNTRY_2LDS, parts[1]) >= 0) {
            return false;
        }
        return true;
    }

    public static boolean acceptableCountryWildcard(String cn) {
        return validCountryWildcard(cn.split("\\."));
    }

    public static String[] getCNs(X509Certificate cert) {
        try {
            if (DefaultHostnameVerifier.extractCN(cert.getSubjectX500Principal().toString()) == null) {
                return null;
            }
            return new String[]{DefaultHostnameVerifier.extractCN(cert.getSubjectX500Principal().toString())};
        } catch (SSLException e) {
            return null;
        }
    }

    public static String[] getDNSSubjectAlts(X509Certificate cert) {
        List<String> subjectAlts = DefaultHostnameVerifier.extractSubjectAlts(cert, 2);
        return (subjectAlts == null || subjectAlts.isEmpty()) ? null : (String[]) subjectAlts.toArray(new String[subjectAlts.size()]);
    }

    public static int countDots(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                count++;
            }
        }
        return count;
    }
}
