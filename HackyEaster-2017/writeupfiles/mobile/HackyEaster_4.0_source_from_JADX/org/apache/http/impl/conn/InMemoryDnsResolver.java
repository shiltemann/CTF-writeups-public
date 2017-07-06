package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.DnsResolver;
import org.apache.http.util.Args;

public class InMemoryDnsResolver implements DnsResolver {
    private final Map<String, InetAddress[]> dnsMap;
    private final Log log;

    public InMemoryDnsResolver() {
        this.log = LogFactory.getLog(InMemoryDnsResolver.class);
        this.dnsMap = new ConcurrentHashMap();
    }

    public void add(String host, InetAddress... ips) {
        Args.notNull(host, "Host name");
        Args.notNull(ips, "Array of IP addresses");
        this.dnsMap.put(host, ips);
    }

    public InetAddress[] resolve(String host) throws UnknownHostException {
        InetAddress[] resolvedAddresses = (InetAddress[]) this.dnsMap.get(host);
        if (this.log.isInfoEnabled()) {
            this.log.info("Resolving " + host + " to " + Arrays.deepToString(resolvedAddresses));
        }
        if (resolvedAddresses != null) {
            return resolvedAddresses;
        }
        throw new UnknownHostException(host + " cannot be resolved");
    }
}
