package com.google.zxing.client.result;

public final class SMSParsedResult extends ParsedResult {
    private final String body;
    private final String[] numbers;
    private final String subject;
    private final String[] vias;

    public SMSParsedResult(String number, String via, String subject, String body) {
        super(ParsedResultType.SMS);
        this.numbers = new String[]{number};
        this.vias = new String[]{via};
        this.subject = subject;
        this.body = body;
    }

    public SMSParsedResult(String[] numbers, String[] vias, String subject, String body) {
        super(ParsedResultType.SMS);
        this.numbers = numbers;
        this.vias = vias;
        this.subject = subject;
        this.body = body;
    }

    public String getSMSURI() {
        boolean hasBody;
        boolean hasSubject;
        StringBuilder result = new StringBuilder();
        result.append("sms:");
        boolean first = true;
        int i = 0;
        while (i < this.numbers.length) {
            if (first) {
                first = false;
            } else {
                result.append(',');
            }
            result.append(this.numbers[i]);
            if (!(this.vias == null || this.vias[i] == null)) {
                result.append(";via=");
                result.append(this.vias[i]);
            }
            i++;
        }
        if (this.body != null) {
            hasBody = true;
        } else {
            hasBody = false;
        }
        if (this.subject != null) {
            hasSubject = true;
        } else {
            hasSubject = false;
        }
        if (hasBody || hasSubject) {
            result.append('?');
            if (hasBody) {
                result.append("body=");
                result.append(this.body);
            }
            if (hasSubject) {
                if (hasBody) {
                    result.append('&');
                }
                result.append("subject=");
                result.append(this.subject);
            }
        }
        return result.toString();
    }

    public String[] getNumbers() {
        return this.numbers;
    }

    public String[] getVias() {
        return this.vias;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getBody() {
        return this.body;
    }

    public String getDisplayResult() {
        StringBuilder result = new StringBuilder(100);
        ParsedResult.maybeAppend(this.numbers, result);
        ParsedResult.maybeAppend(this.subject, result);
        ParsedResult.maybeAppend(this.body, result);
        return result.toString();
    }
}
