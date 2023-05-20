package org.apache.http.client.utils;

import java.lang.reflect.InvocationTargetException;
import org.apache.http.annotation.Immutable;

@Immutable
public class CloneUtils {
    public static <T> T cloneObject(T obj) throws CloneNotSupportedException {
        T t = null;
        if (obj != null) {
            if (obj instanceof Cloneable) {
                try {
                    try {
                        t = obj.getClass().getMethod("clone", (Class[]) null).invoke(obj, (Object[]) null);
                    } catch (InvocationTargetException ex) {
                        Throwable cause = ex.getCause();
                        if (cause instanceof CloneNotSupportedException) {
                            throw ((CloneNotSupportedException) cause);
                        }
                        throw new Error("Unexpected exception", cause);
                    } catch (IllegalAccessException ex2) {
                        throw new IllegalAccessError(ex2.getMessage());
                    }
                } catch (NoSuchMethodException ex3) {
                    throw new NoSuchMethodError(ex3.getMessage());
                }
            }
            throw new CloneNotSupportedException();
        }
        return t;
    }

    public static Object clone(Object obj) throws CloneNotSupportedException {
        return cloneObject(obj);
    }

    private CloneUtils() {
    }
}
