package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Service;
import android.support.annotation.RequiresApi;

@TargetApi(24)
@RequiresApi(24)
class ServiceCompatApi24 {
    ServiceCompatApi24() {
    }

    public static void stopForeground(Service service, int flags) {
        service.stopForeground(flags);
    }
}
