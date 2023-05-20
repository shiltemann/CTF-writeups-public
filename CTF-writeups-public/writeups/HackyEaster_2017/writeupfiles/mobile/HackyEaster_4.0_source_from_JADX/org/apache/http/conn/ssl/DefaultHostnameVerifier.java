package org.apache.http.conn.ssl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.util.DomainType;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.conn.util.PublicSuffixMatcher;

@Immutable
public final class DefaultHostnameVerifier implements HostnameVerifier {
    static final int DNS_NAME_TYPE = 2;
    static final int IP_ADDRESS_TYPE = 7;
    private final Log log;
    private final PublicSuffixMatcher publicSuffixMatcher;

    public DefaultHostnameVerifier(PublicSuffixMatcher publicSuffixMatcher) {
        this.log = LogFactory.getLog(getClass());
        this.publicSuffixMatcher = publicSuffixMatcher;
    }

    public DefaultHostnameVerifier() {
        this(null);
    }

    public boolean verify(String host, SSLSession session) {
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

    public void verify(String host, X509Certificate cert) throws SSLException {
        boolean ipv4 = InetAddressUtils.isIPv4Address(host);
        boolean ipv6 = InetAddressUtils.isIPv6Address(host);
        int subjectType = (ipv4 || ipv6) ? IP_ADDRESS_TYPE : DNS_NAME_TYPE;
        List<String> subjectAlts = extractSubjectAlts(cert, subjectType);
        if (subjectAlts == null || subjectAlts.isEmpty()) {
            String cn = extractCN(cert.getSubjectX500Principal().getName("RFC2253"));
            if (cn == null) {
                throw new SSLException("Certificate subject for <" + host + "> doesn't contain " + "a common name and does not have alternative names");
            }
            matchCN(host, cn, this.publicSuffixMatcher);
        } else if (ipv4) {
            matchIPAddress(host, subjectAlts);
        } else if (ipv6) {
            matchIPv6Address(host, subjectAlts);
        } else {
            matchDNSName(host, subjectAlts, this.publicSuffixMatcher);
        }
    }

    static void matchIPAddress(String host, List<String> subjectAlts) throws SSLException {
        int i = 0;
        while (i < subjectAlts.size()) {
            if (!host.equals((String) subjectAlts.get(i))) {
                i++;
            } else {
                return;
            }
        }
        throw new SSLException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAlts);
    }

    static void matchIPv6Address(String host, List<String> subjectAlts) throws SSLException {
        String normalisedHost = normaliseAddress(host);
        int i = 0;
        while (i < subjectAlts.size()) {
            if (!normalisedHost.equals(normaliseAddress((String) subjectAlts.get(i)))) {
                i++;
            } else {
                return;
            }
        }
        throw new SSLException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAlts);
    }

    static void matchDNSName(String host, List<String> subjectAlts, PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
        String normalizedHost = host.toLowerCase(Locale.ROOT);
        int i = 0;
        while (i < subjectAlts.size()) {
            if (!matchIdentityStrict(normalizedHost, ((String) subjectAlts.get(i)).toLowerCase(Locale.ROOT), publicSuffixMatcher)) {
                i++;
            } else {
                return;
            }
        }
        throw new SSLException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAlts);
    }

    static void matchCN(String host, String cn, PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
        if (!matchIdentityStrict(host, cn, publicSuffixMatcher)) {
            throw new SSLException("Certificate for <" + host + "> doesn't match " + "common name of the certificate subject: " + cn);
        }
    }

    static boolean matchDomainRoot(String host, String domainRoot) {
        if (domainRoot == null || !host.endsWith(domainRoot)) {
            return false;
        }
        if (host.length() == domainRoot.length() || host.charAt((host.length() - domainRoot.length()) - 1) == '.') {
            return true;
        }
        return false;
    }

    private static boolean matchIdentity(String host, String identity, PublicSuffixMatcher publicSuffixMatcher, boolean strict) {
        if (publicSuffixMatcher != null && host.contains(".") && !matchDomainRoot(host, publicSuffixMatcher.getDomainRoot(identity, DomainType.ICANN))) {
            return false;
        }
        int asteriskIdx = identity.indexOf(42);
        if (asteriskIdx == -1) {
            return host.equalsIgnoreCase(identity);
        }
        String prefix = identity.substring(0, asteriskIdx);
        String suffix = identity.substring(asteriskIdx + 1);
        if (!prefix.isEmpty() && !host.startsWith(prefix)) {
            return false;
        }
        if (!suffix.isEmpty() && !host.endsWith(suffix)) {
            return false;
        }
        if (strict && host.substring(prefix.length(), host.length() - suffix.length()).contains(".")) {
            return false;
        }
        return true;
    }

    static boolean matchIdentity(String host, String identity, PublicSuffixMatcher publicSuffixMatcher) {
        return matchIdentity(host, identity, publicSuffixMatcher, false);
    }

    static boolean matchIdentity(String host, String identity) {
        return matchIdentity(host, identity, null, false);
    }

    static boolean matchIdentityStrict(String host, String identity, PublicSuffixMatcher publicSuffixMatcher) {
        return matchIdentity(host, identity, publicSuffixMatcher, true);
    }

    static boolean matchIdentityStrict(String host, String identity) {
        return matchIdentity(host, identity, null, true);
    }

    static String extractCN(String subjectPrincipal) throws SSLException {
        String str = null;
        if (subjectPrincipal != null) {
            try {
                List<Rdn> rdns = new LdapName(subjectPrincipal).getRdns();
                for (int i = rdns.size() - 1; i >= 0; i--) {
                    Attribute cn = ((Rdn) rdns.get(i)).toAttributes().get("cn");
                    if (cn != null) {
                        try {
                            Object value = cn.get();
                            if (value != null) {
                                str = value.toString();
                                break;
                            }
                        } catch (NoSuchElementException e) {
                        } catch (NamingException e2) {
                        }
                    }
                }
            } catch (InvalidNameException e3) {
                throw new SSLException(subjectPrincipal + " is not a valid X500 distinguished name");
            }
        }
        return str;
    }

    static List<String> extractSubjectAlts(X509Certificate cert, int subjectType) {
        Collection<List<?>> c = null;
        try {
            c = cert.getSubjectAlternativeNames();
        } catch (CertificateParsingException e) {
        }
        List<String> subjectAltList = null;
        if (c != null) {
            for (List<?> list : c) {
                if (((Integer) list.get(0)).intValue() == subjectType) {
                    String s = (String) list.get(1);
                    if (subjectAltList == null) {
                        subjectAltList = new ArrayList();
                    }
                    subjectAltList.add(s);
                }
            }
        }
        return subjectAltList;
    }

    static String normaliseAddress(String hostname) {
        if (hostname != null) {
            try {
                hostname = InetAddress.getByName(hostname).getHostAddress();
            } catch (UnknownHostException e) {
            }
        }
        return hostname;
    }
}
