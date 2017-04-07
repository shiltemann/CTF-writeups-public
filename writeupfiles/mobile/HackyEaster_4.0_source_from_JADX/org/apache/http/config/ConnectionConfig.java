package org.apache.http.config;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import org.apache.http.Consts;
import org.apache.http.annotation.Immutable;
import org.apache.http.util.Args;

@Immutable
public class ConnectionConfig implements Cloneable {
    public static final ConnectionConfig DEFAULT;
    private final int bufferSize;
    private final Charset charset;
    private final int fragmentSizeHint;
    private final CodingErrorAction malformedInputAction;
    private final MessageConstraints messageConstraints;
    private final CodingErrorAction unmappableInputAction;

    public static class Builder {
        private int bufferSize;
        private Charset charset;
        private int fragmentSizeHint;
        private CodingErrorAction malformedInputAction;
        private MessageConstraints messageConstraints;
        private CodingErrorAction unmappableInputAction;

        Builder() {
            this.fragmentSizeHint = -1;
        }

        public Builder setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
            return this;
        }

        public Builder setFragmentSizeHint(int fragmentSizeHint) {
            this.fragmentSizeHint = fragmentSizeHint;
            return this;
        }

        public Builder setCharset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public Builder setMalformedInputAction(CodingErrorAction malformedInputAction) {
            this.malformedInputAction = malformedInputAction;
            if (malformedInputAction != null && this.charset == null) {
                this.charset = Consts.ASCII;
            }
            return this;
        }

        public Builder setUnmappableInputAction(CodingErrorAction unmappableInputAction) {
            this.unmappableInputAction = unmappableInputAction;
            if (unmappableInputAction != null && this.charset == null) {
                this.charset = Consts.ASCII;
            }
            return this;
        }

        public Builder setMessageConstraints(MessageConstraints messageConstraints) {
            this.messageConstraints = messageConstraints;
            return this;
        }

        public ConnectionConfig build() {
            int fragmentHintSize;
            Charset cs = this.charset;
            if (cs == null && !(this.malformedInputAction == null && this.unmappableInputAction == null)) {
                cs = Consts.ASCII;
            }
            int bufSize = this.bufferSize > 0 ? this.bufferSize : AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD;
            if (this.fragmentSizeHint >= 0) {
                fragmentHintSize = this.fragmentSizeHint;
            } else {
                fragmentHintSize = bufSize;
            }
            return new ConnectionConfig(bufSize, fragmentHintSize, cs, this.malformedInputAction, this.unmappableInputAction, this.messageConstraints);
        }
    }

    static {
        DEFAULT = new Builder().build();
    }

    ConnectionConfig(int bufferSize, int fragmentSizeHint, Charset charset, CodingErrorAction malformedInputAction, CodingErrorAction unmappableInputAction, MessageConstraints messageConstraints) {
        this.bufferSize = bufferSize;
        this.fragmentSizeHint = fragmentSizeHint;
        this.charset = charset;
        this.malformedInputAction = malformedInputAction;
        this.unmappableInputAction = unmappableInputAction;
        this.messageConstraints = messageConstraints;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public int getFragmentSizeHint() {
        return this.fragmentSizeHint;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public CodingErrorAction getMalformedInputAction() {
        return this.malformedInputAction;
    }

    public CodingErrorAction getUnmappableInputAction() {
        return this.unmappableInputAction;
    }

    public MessageConstraints getMessageConstraints() {
        return this.messageConstraints;
    }

    protected ConnectionConfig clone() throws CloneNotSupportedException {
        return (ConnectionConfig) super.clone();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[bufferSize=").append(this.bufferSize).append(", fragmentSizeHint=").append(this.fragmentSizeHint).append(", charset=").append(this.charset).append(", malformedInputAction=").append(this.malformedInputAction).append(", unmappableInputAction=").append(this.unmappableInputAction).append(", messageConstraints=").append(this.messageConstraints).append("]");
        return builder.toString();
    }

    public static Builder custom() {
        return new Builder();
    }

    public static Builder copy(ConnectionConfig config) {
        Args.notNull(config, "Connection config");
        return new Builder().setCharset(config.getCharset()).setMalformedInputAction(config.getMalformedInputAction()).setUnmappableInputAction(config.getUnmappableInputAction()).setMessageConstraints(config.getMessageConstraints());
    }
}
