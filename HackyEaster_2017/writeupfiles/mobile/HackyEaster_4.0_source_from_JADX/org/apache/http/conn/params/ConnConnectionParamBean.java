package org.apache.http.conn.params;

import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

@Deprecated
public class ConnConnectionParamBean extends HttpAbstractParamBean {
    public ConnConnectionParamBean(HttpParams params) {
        super(params);
    }

    @Deprecated
    public void setMaxStatusLineGarbage(int maxStatusLineGarbage) {
        this.params.setIntParameter(ConnConnectionPNames.MAX_STATUS_LINE_GARBAGE, maxStatusLineGarbage);
    }
}
