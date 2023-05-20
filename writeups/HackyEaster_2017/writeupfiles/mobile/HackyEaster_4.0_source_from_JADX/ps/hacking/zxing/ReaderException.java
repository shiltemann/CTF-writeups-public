package ps.hacking.zxing;

public abstract class ReaderException extends Exception {
    ReaderException() {
    }

    public final Throwable fillInStackTrace() {
        return null;
    }
}
