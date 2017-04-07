package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.conn.routing.RouteInfo.LayerType;
import org.apache.http.conn.routing.RouteInfo.TunnelType;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;
import org.apache.http.util.LangUtils;

@NotThreadSafe
public final class RouteTracker implements RouteInfo, Cloneable {
    private boolean connected;
    private LayerType layered;
    private final InetAddress localAddress;
    private HttpHost[] proxyChain;
    private boolean secure;
    private final HttpHost targetHost;
    private TunnelType tunnelled;

    public RouteTracker(HttpHost target, InetAddress local) {
        Args.notNull(target, "Target host");
        this.targetHost = target;
        this.localAddress = local;
        this.tunnelled = TunnelType.PLAIN;
        this.layered = LayerType.PLAIN;
    }

    public void reset() {
        this.connected = false;
        this.proxyChain = null;
        this.tunnelled = TunnelType.PLAIN;
        this.layered = LayerType.PLAIN;
        this.secure = false;
    }

    public RouteTracker(HttpRoute route) {
        this(route.getTargetHost(), route.getLocalAddress());
    }

    public final void connectTarget(boolean secure) {
        Asserts.check(!this.connected, "Already connected");
        this.connected = true;
        this.secure = secure;
    }

    public final void connectProxy(HttpHost proxy, boolean secure) {
        boolean z;
        Args.notNull(proxy, "Proxy host");
        if (this.connected) {
            z = false;
        } else {
            z = true;
        }
        Asserts.check(z, "Already connected");
        this.connected = true;
        this.proxyChain = new HttpHost[]{proxy};
        this.secure = secure;
    }

    public final void tunnelTarget(boolean secure) {
        Asserts.check(this.connected, "No tunnel unless connected");
        Asserts.notNull(this.proxyChain, "No tunnel without proxy");
        this.tunnelled = TunnelType.TUNNELLED;
        this.secure = secure;
    }

    public final void tunnelProxy(HttpHost proxy, boolean secure) {
        Args.notNull(proxy, "Proxy host");
        Asserts.check(this.connected, "No tunnel unless connected");
        Asserts.notNull(this.proxyChain, "No tunnel without proxy");
        HttpHost[] proxies = new HttpHost[(this.proxyChain.length + 1)];
        System.arraycopy(this.proxyChain, 0, proxies, 0, this.proxyChain.length);
        proxies[proxies.length - 1] = proxy;
        this.proxyChain = proxies;
        this.secure = secure;
    }

    public final void layerProtocol(boolean secure) {
        Asserts.check(this.connected, "No layered protocol unless connected");
        this.layered = LayerType.LAYERED;
        this.secure = secure;
    }

    public final HttpHost getTargetHost() {
        return this.targetHost;
    }

    public final InetAddress getLocalAddress() {
        return this.localAddress;
    }

    public final int getHopCount() {
        if (!this.connected) {
            return 0;
        }
        if (this.proxyChain == null) {
            return 1;
        }
        return this.proxyChain.length + 1;
    }

    public final HttpHost getHopTarget(int hop) {
        Args.notNegative(hop, "Hop index");
        int hopcount = getHopCount();
        Args.check(hop < hopcount, "Hop index exceeds tracked route length");
        if (hop < hopcount - 1) {
            return this.proxyChain[hop];
        }
        return this.targetHost;
    }

    public final HttpHost getProxyHost() {
        return this.proxyChain == null ? null : this.proxyChain[0];
    }

    public final boolean isConnected() {
        return this.connected;
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

    public final HttpRoute toRoute() {
        return !this.connected ? null : new HttpRoute(this.targetHost, this.localAddress, this.proxyChain, this.secure, this.tunnelled, this.layered);
    }

    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RouteTracker)) {
            return false;
        }
        RouteTracker that = (RouteTracker) o;
        if (this.connected == that.connected && this.secure == that.secure && this.tunnelled == that.tunnelled && this.layered == that.layered && LangUtils.equals(this.targetHost, that.targetHost) && LangUtils.equals(this.localAddress, that.localAddress) && LangUtils.equals(this.proxyChain, that.proxyChain)) {
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
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(hash, this.connected), this.secure), this.tunnelled), this.layered);
    }

    public final String toString() {
        StringBuilder cab = new StringBuilder((getHopCount() * 30) + 50);
        cab.append("RouteTracker[");
        if (this.localAddress != null) {
            cab.append(this.localAddress);
            cab.append("->");
        }
        cab.append('{');
        if (this.connected) {
            cab.append('c');
        }
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
            for (HttpHost element : this.proxyChain) {
                cab.append(element);
                cab.append("->");
            }
        }
        cab.append(this.targetHost);
        cab.append(']');
        return cab.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
