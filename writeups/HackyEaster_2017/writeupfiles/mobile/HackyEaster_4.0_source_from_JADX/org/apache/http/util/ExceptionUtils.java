package org.apache.http.util;

import java.lang.reflect.Method;

@Deprecated
public final class ExceptionUtils {
    private static final Method INIT_CAUSE_METHOD;

    static {
        INIT_CAUSE_METHOD = getInitCauseMethod();
    }

    private static Method getInitCauseMethod() {
        try {
            return Throwable.class.getMethod("initCause", new Class[]{Throwable.class});
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static void initCause(Throwable throwable, Throwable cause) {
        if (INIT_CAUSE_METHOD != null) {
            try {
                INIT_CAUSE_METHOD.invoke(throwable, new Object[]{cause});
            } catch (Exception e) {
            }
        }
    }

    private ExceptionUtils() {
    }
}
