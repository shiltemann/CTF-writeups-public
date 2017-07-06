package org.apache.http.conn.util;

import java.util.Collections;
import java.util.List;
import org.apache.http.annotation.Immutable;
import org.apache.http.util.Args;

@Immutable
public final class PublicSuffixList {
    private final List<String> exceptions;
    private final List<String> rules;
    private final DomainType type;

    public PublicSuffixList(DomainType type, List<String> rules, List<String> exceptions) {
        this.type = (DomainType) Args.notNull(type, "Domain type");
        this.rules = Collections.unmodifiableList((List) Args.notNull(rules, "Domain suffix rules"));
        if (exceptions == null) {
            exceptions = Collections.emptyList();
        }
        this.exceptions = Collections.unmodifiableList(exceptions);
    }

    public PublicSuffixList(List<String> rules, List<String> exceptions) {
        this(DomainType.UNKNOWN, rules, exceptions);
    }

    public DomainType getType() {
        return this.type;
    }

    public List<String> getRules() {
        return this.rules;
    }

    public List<String> getExceptions() {
        return this.exceptions;
    }
}
