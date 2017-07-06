package ps.hacking.zxing;

public final class ChecksumException extends ReaderException {
    private static final ChecksumException instance;

    static {
        instance = new ChecksumException();
    }

    private ChecksumException() {
    }

    public static ChecksumException getChecksumInstance() {
        return instance;
    }
}
