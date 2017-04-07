package org.apache.http.impl.cookie;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.SetCookie;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

@NotThreadSafe
public class BasicClientCookie implements SetCookie, ClientCookie, Cloneable, Serializable {
    private static final long serialVersionUID = -3869795591041535538L;
    private Map<String, String> attribs;
    private String cookieComment;
    private String cookieDomain;
    private Date cookieExpiryDate;
    private String cookiePath;
    private int cookieVersion;
    private Date creationDate;
    private boolean isSecure;
    private final String name;
    private String value;

    public BasicClientCookie(String name, String value) {
        Args.notNull(name, "Name");
        this.name = name;
        this.attribs = new HashMap();
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return this.cookieComment;
    }

    public void setComment(String comment) {
        this.cookieComment = comment;
    }

    public String getCommentURL() {
        return null;
    }

    public Date getExpiryDate() {
        return this.cookieExpiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.cookieExpiryDate = expiryDate;
    }

    public boolean isPersistent() {
        return this.cookieExpiryDate != null;
    }

    public String getDomain() {
        return this.cookieDomain;
    }

    public void setDomain(String domain) {
        if (domain != null) {
            this.cookieDomain = domain.toLowerCase(Locale.ROOT);
        } else {
            this.cookieDomain = null;
        }
    }

    public String getPath() {
        return this.cookiePath;
    }

    public void setPath(String path) {
        this.cookiePath = path;
    }

    public boolean isSecure() {
        return this.isSecure;
    }

    public void setSecure(boolean secure) {
        this.isSecure = secure;
    }

    public int[] getPorts() {
        return null;
    }

    public int getVersion() {
        return this.cookieVersion;
    }

    public void setVersion(int version) {
        this.cookieVersion = version;
    }

    public boolean isExpired(Date date) {
        Args.notNull(date, HTTP.DATE_HEADER);
        return this.cookieExpiryDate != null && this.cookieExpiryDate.getTime() <= date.getTime();
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setAttribute(String name, String value) {
        this.attribs.put(name, value);
    }

    public String getAttribute(String name) {
        return (String) this.attribs.get(name);
    }

    public boolean containsAttribute(String name) {
        return this.attribs.containsKey(name);
    }

    public boolean removeAttribute(String name) {
        return this.attribs.remove(name) != null;
    }

    public Object clone() throws CloneNotSupportedException {
        BasicClientCookie clone = (BasicClientCookie) super.clone();
        clone.attribs = new HashMap(this.attribs);
        return clone;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[version: ");
        buffer.append(Integer.toString(this.cookieVersion));
        buffer.append("]");
        buffer.append("[name: ");
        buffer.append(this.name);
        buffer.append("]");
        buffer.append("[value: ");
        buffer.append(this.value);
        buffer.append("]");
        buffer.append("[domain: ");
        buffer.append(this.cookieDomain);
        buffer.append("]");
        buffer.append("[path: ");
        buffer.append(this.cookiePath);
        buffer.append("]");
        buffer.append("[expiry: ");
        buffer.append(this.cookieExpiryDate);
        buffer.append("]");
        return buffer.toString();
    }
}
