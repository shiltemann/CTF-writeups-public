package org.apache.http.impl.client;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

@NotThreadSafe
@Deprecated
class CloseableHttpResponseProxy implements InvocationHandler {
    private static final Constructor<?> CONSTRUCTOR;
    private final HttpResponse original;

    static {
        try {
            CONSTRUCTOR = Proxy.getProxyClass(CloseableHttpResponseProxy.class.getClassLoader(), new Class[]{CloseableHttpResponse.class}).getConstructor(new Class[]{InvocationHandler.class});
        } catch (NoSuchMethodException ex) {
            throw new IllegalStateException(ex);
        }
    }

    CloseableHttpResponseProxy(HttpResponse original) {
        this.original = original;
    }

    public void close() throws IOException {
        EntityUtils.consume(this.original.getEntity());
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("close")) {
            close();
            return null;
        }
        try {
            return method.invoke(this.original, args);
        } catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            if (cause != null) {
                throw cause;
            }
            throw ex;
        }
    }

    public static CloseableHttpResponse newProxy(HttpResponse original) {
        try {
            return (CloseableHttpResponse) CONSTRUCTOR.newInstance(new Object[]{new CloseableHttpResponseProxy(original)});
        } catch (InstantiationException ex) {
            throw new IllegalStateException(ex);
        } catch (InvocationTargetException ex2) {
            throw new IllegalStateException(ex2);
        } catch (IllegalAccessException ex3) {
            throw new IllegalStateException(ex3);
        }
    }
}
