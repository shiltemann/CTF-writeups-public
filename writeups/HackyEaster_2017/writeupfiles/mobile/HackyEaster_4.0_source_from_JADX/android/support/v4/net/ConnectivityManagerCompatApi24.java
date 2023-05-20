package android.support.v4.net;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.support.annotation.RequiresApi;

@TargetApi(24)
@RequiresApi(24)
class ConnectivityManagerCompatApi24 {
    ConnectivityManagerCompatApi24() {
    }

    public static int getRestrictBackgroundStatus(ConnectivityManager cm) {
        return cm.getRestrictBackgroundStatus();
    }
}
