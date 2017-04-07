package org.apache.http.params;

@Deprecated
public interface CoreProtocolPNames {
    public static final String HTTP_CONTENT_CHARSET = "http.protocol.content-charset";
    public static final String HTTP_ELEMENT_CHARSET = "http.protocol.element-charset";
    public static final String HTTP_MALFORMED_INPUT_ACTION = "http.malformed.input.action";
    public static final String HTTP_UNMAPPABLE_INPUT_ACTION = "http.unmappable.input.action";
    public static final String ORIGIN_SERVER = "http.origin-server";
    public static final String PROTOCOL_VERSION = "http.protocol.version";
    public static final String STRICT_TRANSFER_ENCODING = "http.protocol.strict-transfer-encoding";
    public static final String USER_AGENT = "http.useragent";
    public static final String USE_EXPECT_CONTINUE = "http.protocol.expect-continue";
    public static final String WAIT_FOR_CONTINUE = "http.protocol.wait-for-continue";
}
