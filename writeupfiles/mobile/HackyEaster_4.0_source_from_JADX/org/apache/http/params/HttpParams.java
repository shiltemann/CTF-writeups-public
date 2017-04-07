package org.apache.http.params;

@Deprecated
public interface HttpParams {
    HttpParams copy();

    boolean getBooleanParameter(String str, boolean z);

    double getDoubleParameter(String str, double d);

    int getIntParameter(String str, int i);

    long getLongParameter(String str, long j);

    Object getParameter(String str);

    boolean isParameterFalse(String str);

    boolean isParameterTrue(String str);

    boolean removeParameter(String str);

    HttpParams setBooleanParameter(String str, boolean z);

    HttpParams setDoubleParameter(String str, double d);

    HttpParams setIntParameter(String str, int i);

    HttpParams setLongParameter(String str, long j);

    HttpParams setParameter(String str, Object obj);
}
