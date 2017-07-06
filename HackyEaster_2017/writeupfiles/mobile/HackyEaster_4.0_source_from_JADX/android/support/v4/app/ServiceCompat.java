package android.support.v4.app;

import android.app.Service;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.os.BuildCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ServiceCompat {
    static final ServiceCompatImpl IMPL;
    public static final int START_STICKY = 1;
    public static final int STOP_FOREGROUND_DETACH = 2;
    public static final int STOP_FOREGROUND_REMOVE = 1;

    interface ServiceCompatImpl {
        void stopForeground(Service service, int i);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StopForegroundFlags {
    }

    static class Api24ServiceCompatImpl implements ServiceCompatImpl {
        Api24ServiceCompatImpl() {
        }

        public void stopForeground(Service service, int flags) {
            ServiceCompatApi24.stopForeground(service, flags);
        }
    }

    static class BaseServiceCompatImpl implements ServiceCompatImpl {
        BaseServiceCompatImpl() {
        }

        public void stopForeground(Service service, int flags) {
            service.stopForeground((flags & ServiceCompat.STOP_FOREGROUND_REMOVE) != 0);
        }
    }

    private ServiceCompat() {
    }

    static {
        if (BuildCompat.isAtLeastN()) {
            IMPL = new Api24ServiceCompatImpl();
        } else {
            IMPL = new BaseServiceCompatImpl();
        }
    }

    public static void stopForeground(Service service, int flags) {
        IMPL.stopForeground(service, flags);
    }
}
