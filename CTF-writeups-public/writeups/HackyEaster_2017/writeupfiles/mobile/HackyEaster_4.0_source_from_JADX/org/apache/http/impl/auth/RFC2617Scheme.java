package org.apache.http.impl.auth;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.CharsetUtils;

@NotThreadSafe
public abstract class RFC2617Scheme extends AuthSchemeBase implements Serializable {
    private static final long serialVersionUID = -2845454858205884623L;
    private transient Charset credentialsCharset;
    private final Map<String, String> params;

    @Deprecated
    public RFC2617Scheme(ChallengeState challengeState) {
        super(challengeState);
        this.params = new HashMap();
        this.credentialsCharset = Consts.ASCII;
    }

    public RFC2617Scheme(Charset credentialsCharset) {
        this.params = new HashMap();
        if (credentialsCharset == null) {
            credentialsCharset = Consts.ASCII;
        }
        this.credentialsCharset = credentialsCharset;
    }

    public RFC2617Scheme() {
        this(Consts.ASCII);
    }

    public Charset getCredentialsCharset() {
        return this.credentialsCharset != null ? this.credentialsCharset : Consts.ASCII;
    }

    String getCredentialsCharset(HttpRequest request) {
        String charset = (String) request.getParams().getParameter(AuthPNames.CREDENTIAL_CHARSET);
        if (charset == null) {
            return getCredentialsCharset().name();
        }
        return charset;
    }

    protected void parseChallenge(CharArrayBuffer buffer, int pos, int len) throws MalformedChallengeException {
        HeaderElement[] elements = BasicHeaderValueParser.INSTANCE.parseElements(buffer, new ParserCursor(pos, buffer.length()));
        this.params.clear();
        for (HeaderElement element : elements) {
            this.params.put(element.getName().toLowerCase(Locale.ROOT), element.getValue());
        }
    }

    protected Map<String, String> getParameters() {
        return this.params;
    }

    public String getParameter(String name) {
        if (name == null) {
            return null;
        }
        return (String) this.params.get(name.toLowerCase(Locale.ROOT));
    }

    public String getRealm() {
        return getParameter("realm");
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(this.credentialsCharset.name());
        out.writeObject(this.challengeState);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.credentialsCharset = CharsetUtils.get(in.readUTF());
        if (this.credentialsCharset == null) {
            this.credentialsCharset = Consts.ASCII;
        }
        this.challengeState = (ChallengeState) in.readObject();
    }

    private void readObjectNoData() throws ObjectStreamException {
    }
}
