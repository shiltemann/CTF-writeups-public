package org.apache.http.params;

import org.apache.http.HttpVersion;

@Deprecated
public class HttpProtocolParamBean extends HttpAbstractParamBean {
    public HttpProtocolParamBean(HttpParams params) {
        super(params);
    }

    public void setHttpElementCharset(String httpElementCharset) {
        HttpProtocolParams.setHttpElementCharset(this.params, httpElementCharset);
    }

    public void setContentCharset(String contentCharset) {
        HttpProtocolParams.setContentCharset(this.params, contentCharset);
    }

    public void setVersion(HttpVersion version) {
        HttpProtocolParams.setVersion(this.params, version);
    }

    public void setUserAgent(String userAgent) {
        HttpProtocolParams.setUserAgent(this.params, userAgent);
    }

    public void setUseExpectContinue(boolean useExpectContinue) {
        HttpProtocolParams.setUseExpectContinue(this.params, useExpectContinue);
    }
}
