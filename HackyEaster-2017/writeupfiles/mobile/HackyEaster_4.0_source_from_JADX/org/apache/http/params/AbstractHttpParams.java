package org.apache.http.params;

import java.util.Set;

@Deprecated
public abstract class AbstractHttpParams implements HttpParams, HttpParamsNames {
    protected AbstractHttpParams() {
    }

    public long getLongParameter(String name, long defaultValue) {
        Object param = getParameter(name);
        return param == null ? defaultValue : ((Long) param).longValue();
    }

    public HttpParams setLongParameter(String name, long value) {
        setParameter(name, Long.valueOf(value));
        return this;
    }

    public int getIntParameter(String name, int defaultValue) {
        Object param = getParameter(name);
        return param == null ? defaultValue : ((Integer) param).intValue();
    }

    public HttpParams setIntParameter(String name, int value) {
        setParameter(name, Integer.valueOf(value));
        return this;
    }

    public double getDoubleParameter(String name, double defaultValue) {
        Object param = getParameter(name);
        return param == null ? defaultValue : ((Double) param).doubleValue();
    }

    public HttpParams setDoubleParameter(String name, double value) {
        setParameter(name, Double.valueOf(value));
        return this;
    }

    public boolean getBooleanParameter(String name, boolean defaultValue) {
        Object param = getParameter(name);
        return param == null ? defaultValue : ((Boolean) param).booleanValue();
    }

    public HttpParams setBooleanParameter(String name, boolean value) {
        setParameter(name, value ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public boolean isParameterTrue(String name) {
        return getBooleanParameter(name, false);
    }

    public boolean isParameterFalse(String name) {
        return !getBooleanParameter(name, false);
    }

    public Set<String> getNames() {
        throw new UnsupportedOperationException();
    }
}
