package org.apache.http;

public interface ExceptionLogger {
    public static final ExceptionLogger NO_OP;
    public static final ExceptionLogger STD_ERR;

    /* renamed from: org.apache.http.ExceptionLogger.1 */
    static class C01361 implements ExceptionLogger {
        C01361() {
        }

        public void log(Exception ex) {
        }
    }

    /* renamed from: org.apache.http.ExceptionLogger.2 */
    static class C01372 implements ExceptionLogger {
        C01372() {
        }

        public void log(Exception ex) {
            ex.printStackTrace();
        }
    }

    void log(Exception exception);

    static {
        NO_OP = new C01361();
        STD_ERR = new C01372();
    }
}
