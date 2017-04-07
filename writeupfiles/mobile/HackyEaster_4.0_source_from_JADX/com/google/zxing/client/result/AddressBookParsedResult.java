package com.google.zxing.client.result;

public final class AddressBookParsedResult extends ParsedResult {
    private final String[] addressTypes;
    private final String[] addresses;
    private final String birthday;
    private final String[] emailTypes;
    private final String[] emails;
    private final String instantMessenger;
    private final String[] names;
    private final String note;
    private final String org;
    private final String[] phoneNumbers;
    private final String[] phoneTypes;
    private final String pronunciation;
    private final String title;
    private final String url;

    public AddressBookParsedResult(String[] names, String pronunciation, String[] phoneNumbers, String[] phoneTypes, String[] emails, String[] emailTypes, String instantMessenger, String note, String[] addresses, String[] addressTypes, String org, String birthday, String title, String url) {
        super(ParsedResultType.ADDRESSBOOK);
        this.names = names;
        this.pronunciation = pronunciation;
        this.phoneNumbers = phoneNumbers;
        this.phoneTypes = phoneTypes;
        this.emails = emails;
        this.emailTypes = emailTypes;
        this.instantMessenger = instantMessenger;
        this.note = note;
        this.addresses = addresses;
        this.addressTypes = addressTypes;
        this.org = org;
        this.birthday = birthday;
        this.title = title;
        this.url = url;
    }

    public String[] getNames() {
        return this.names;
    }

    public String getPronunciation() {
        return this.pronunciation;
    }

    public String[] getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public String[] getPhoneTypes() {
        return this.phoneTypes;
    }

    public String[] getEmails() {
        return this.emails;
    }

    public String[] getEmailTypes() {
        return this.emailTypes;
    }

    public String getInstantMessenger() {
        return this.instantMessenger;
    }

    public String getNote() {
        return this.note;
    }

    public String[] getAddresses() {
        return this.addresses;
    }

    public String[] getAddressTypes() {
        return this.addressTypes;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrg() {
        return this.org;
    }

    public String getURL() {
        return this.url;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getDisplayResult() {
        StringBuilder result = new StringBuilder(100);
        ParsedResult.maybeAppend(this.names, result);
        ParsedResult.maybeAppend(this.pronunciation, result);
        ParsedResult.maybeAppend(this.title, result);
        ParsedResult.maybeAppend(this.org, result);
        ParsedResult.maybeAppend(this.addresses, result);
        ParsedResult.maybeAppend(this.phoneNumbers, result);
        ParsedResult.maybeAppend(this.emails, result);
        ParsedResult.maybeAppend(this.instantMessenger, result);
        ParsedResult.maybeAppend(this.url, result);
        ParsedResult.maybeAppend(this.birthday, result);
        ParsedResult.maybeAppend(this.note, result);
        return result.toString();
    }
}
