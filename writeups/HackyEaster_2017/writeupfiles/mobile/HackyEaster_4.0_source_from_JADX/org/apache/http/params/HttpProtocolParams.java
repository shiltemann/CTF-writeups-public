package org.apache.http.params;

import java.nio.charset.CodingErrorAction;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

@Deprecated
public final class HttpProtocolParams implements CoreProtocolPNames {
    private HttpProtocolParams() {
    }

    public static String getHttpElementCharset(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        String charset = (String) params.getParameter(CoreProtocolPNames.HTTP_ELEMENT_CHARSET);
        if (charset == null) {
            return HTTP.DEF_PROTOCOL_CHARSET.name();
        }
        return charset;
    }

    public static void setHttpElementCharset(HttpParams params, String charset) {
        Args.notNull(params, "HTTP parameters");
        params.setParameter(CoreProtocolPNames.HTTP_ELEMENT_CHARSET, charset);
    }

    public static String getContentCharset(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        String charset = (String) params.getParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET);
        if (charset == null) {
            return HTTP.DEF_CONTENT_CHARSET.name();
        }
        return charset;
    }

    public static void setContentCharset(HttpParams params, String charset) {
        Args.notNull(params, "HTTP parameters");
        params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, charset);
    }

    public static ProtocolVersion getVersion(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        Object param = params.getParameter(CoreProtocolPNames.PROTOCOL_VERSION);
        if (param == null) {
            return HttpVersion.HTTP_1_1;
        }
        return (ProtocolVersion) param;
    }

    public static void setVersion(HttpParams params, ProtocolVersion version) {
        Args.notNull(params, "HTTP parameters");
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, version);
    }

    public static String getUserAgent(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return (String) params.getParameter(CoreProtocolPNames.USER_AGENT);
    }

    public static void setUserAgent(HttpParams params, String useragent) {
        Args.notNull(params, "HTTP parameters");
        params.setParameter(CoreProtocolPNames.USER_AGENT, useragent);
    }

    public static boolean useExpectContinue(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        return params.getBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
    }

    public static void setUseExpectContinue(HttpParams params, boolean b) {
        Args.notNull(params, "HTTP parameters");
        params.setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, b);
    }

    public static CodingErrorAction getMalformedInputAction(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        Object param = params.getParameter(CoreProtocolPNames.HTTP_MALFORMED_INPUT_ACTION);
        if (param == null) {
            return CodingErrorAction.REPORT;
        }
        return (CodingErrorAction) param;
    }

    public static void setMalformedInputAction(HttpParams params, CodingErrorAction action) {
        Args.notNull(params, "HTTP parameters");
        params.setParameter(CoreProtocolPNames.HTTP_MALFORMED_INPUT_ACTION, action);
    }

    public static CodingErrorAction getUnmappableInputAction(HttpParams params) {
        Args.notNull(params, "HTTP parameters");
        Object param = params.getParameter(CoreProtocolPNames.HTTP_UNMAPPABLE_INPUT_ACTION);
        if (param == null) {
            return CodingErrorAction.REPORT;
        }
        return (CodingErrorAction) param;
    }

    public static void setUnmappableInputAction(HttpParams params, CodingErrorAction action) {
        Args.notNull(params, "HTTP parameters");
        params.setParameter(CoreProtocolPNames.HTTP_UNMAPPABLE_INPUT_ACTION, action);
    }
}
