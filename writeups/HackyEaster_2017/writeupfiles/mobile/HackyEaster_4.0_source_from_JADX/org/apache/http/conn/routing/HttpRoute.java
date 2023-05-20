package org.apache.http.conn.routing;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.routing.RouteInfo.LayerType;
import org.apache.http.conn.routing.RouteInfo.TunnelType;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Immutable
public final class HttpRoute implements RouteInfo, Cloneable {
    private final LayerType layered;
    private final InetAddress localAddress;
    private final List<HttpHost> proxyChain;
    private final boolean secure;
    private final HttpHost targetHost;
    private final TunnelType tunnelled;

    private HttpRoute(HttpHost target, InetAddress local, List<HttpHost> proxies, boolean secure, TunnelType tunnelled, LayerType layered) {
        Args.notNull(target, "Target host");
        this.targetHost = normalize(target);
        this.localAddress = local;
        if (proxies == null || proxies.isEmpty()) {
            this.proxyChain = null;
        } else {
            this.proxyChain = new ArrayList(proxies);
        }
        if (tunnelled == TunnelType.TUNNELLED) {
            Args.check(this.proxyChain != null, "Proxy required if tunnelled");
        }
        this.secure = secure;
        if (tunnelled == null) {
            tunnelled = TunnelType.PLAIN;
        }
        this.tunnelled = tunnelled;
        if (layered == null) {
            layered = LayerType.PLAIN;
        }
        this.layered = layered;
    }

    private static int getDefaultPort(String schemeName) {
        if (HttpHost.DEFAULT_SCHEME_NAME.equalsIgnoreCase(schemeName)) {
            return 80;
        }
        if ("https".equalsIgnoreCase(schemeName)) {
            return 443;
        }
        return -1;
    }

    private static HttpHost normalize(HttpHost target) {
        if (target.getPort() >= 0) {
            return target;
        }
        InetAddress address = target.getAddress();
        String schemeName = target.getSchemeName();
        if (address != null) {
            return new HttpHost(address, getDefaultPort(schemeName), schemeName);
        }
        return new HttpHost(target.getHostName(), getDefaultPort(schemeName), schemeName);
    }

    public HttpRoute(HttpHost target, InetAddress local, HttpHost[] proxies, boolean secure, TunnelType tunnelled, LayerType layered) {
        this(target, local, proxies != null ? Arrays.asList(proxies) : null, secure, tunnelled, layered);
    }

    public HttpRoute(HttpHost target, InetAddress local, HttpHost proxy, boolean secure, TunnelType tunnelled, LayerType layered) {
        this(target, local, proxy != null ? Collections.singletonList(proxy) : null, secure, tunnelled, layered);
    }

    public HttpRoute(HttpHost target, InetAddress local, boolean secure) {
        this(target, local, Collections.emptyList(), secure, TunnelType.PLAIN, LayerType.PLAIN);
    }

    public HttpRoute(HttpHost target) {
        this(target, null, Collections.emptyList(), false, TunnelType.PLAIN, LayerType.PLAIN);
    }

    public HttpRoute(HttpHost target, InetAddress local, HttpHost proxy, boolean secure) {
        this(target, local, Collections.singletonList(Args.notNull(proxy, "Proxy host")), secure, secure ? TunnelType.TUNNELLED : TunnelType.PLAIN, secure ? LayerType.LAYERED : LayerType.PLAIN);
    }

    public HttpRoute(HttpHost target, HttpHost proxy) {
        this(target, null, proxy, false);
    }

    public final HttpHost getTargetHost() {
        return this.targetHost;
    }

    public final InetAddress getLocalAddress() {
        return this.localAddress;
    }

    public final InetSocketAddress getLocalSocketAddress() {
        return this.localAddress != null ? new InetSocketAddress(this.localAddress, 0) : null;
    }

    public final int getHopCount() {
        return this.proxyChain != null ? this.proxyChain.size() + 1 : 1;
    }

    public final HttpHost getHopTarget(int hop) {
        Args.notNegative(hop, "Hop index");
        int hopcount = getHopCount();
        Args.check(hop < hopcount, "Hop index exceeds tracked route length");
        if (hop < hopcount - 1) {
            return (HttpHost) this.proxyChain.get(hop);
        }
        return this.targetHost;
    }

    public final HttpHost getProxyHost() {
        return (this.proxyChain == null || this.proxyChain.isEmpty()) ? null : (HttpHost) this.proxyChain.get(0);
    }

    public final TunnelType getTunnelType() {
        return this.tunnelled;
    }

    public final boolean isTunnelled() {
        return this.tunnelled == TunnelType.TUNNELLED;
    }

    public final LayerType getLayerType() {
        return this.layered;
    }

    public final boolean isLayered() {
        return this.layered == LayerType.LAYERED;
    }

    public final boolean isSecure() {
        return this.secure;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HttpRoute)) {
            return false;
        }
        HttpRoute that = (HttpRoute) obj;
        if (this.secure == that.secure && this.tunnelled == that.tunnelled && this.layered == that.layered && LangUtils.equals(this.targetHost, that.targetHost) && LangUtils.equals(this.localAddress, that.localAddress) && LangUtils.equals(this.proxyChain, that.proxyChain)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int hash = LangUtils.hashCode(LangUtils.hashCode(17, this.targetHost), this.localAddress);
        if (this.proxyChain != null) {
            for (Object element : this.proxyChain) {
                hash = LangUtils.hashCode(hash, element);
            }
        }
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(hash, this.secure), this.tunnelled), this.layered);
    }

    public final String toString() {
        StringBuilder cab = new StringBuilder((getHopCount() * 30) + 50);
        if (this.localAddress != null) {
            cab.append(this.localAddress);
            cab.append("->");
        }
        cab.append('{');
        if (this.tunnelled == TunnelType.TUNNELLED) {
            cab.append('t');
        }
        if (this.layered == LayerType.LAYERED) {
            cab.append('l');
        }
        if (this.secure) {
            cab.append('s');
        }
        cab.append("}->");
        if (this.proxyChain != null) {
            for (HttpHost aProxyChain : this.proxyChain) {
                cab.append(aProxyChain);
                cab.append("->");
            }
        }
        cab.append(this.targetHost);
        return cab.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
