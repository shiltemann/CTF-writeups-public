package android.support.v4.net;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.view.MotionEventCompat;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.protocol.HTTP;
import ps.hacking.hackyeaster.android.BuildConfig;

@TargetApi(13)
@RequiresApi(13)
class ConnectivityManagerCompatHoneycombMR2 {
    ConnectivityManagerCompatHoneycombMR2() {
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return true;
        }
        switch (info.getType()) {
            case WearableExtender.SIZE_DEFAULT /*0*/:
            case ConnPerRouteBean.DEFAULT_MAX_CONNECTIONS_PER_ROUTE /*2*/:
            case WearableExtender.SIZE_MEDIUM /*3*/:
            case WearableExtender.SIZE_LARGE /*4*/:
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
            case MotionEventCompat.AXIS_TOOL_MAJOR /*6*/:
                return true;
            case WearableExtender.SIZE_XSMALL /*1*/:
            case BuildConfig.VERSION_CODE /*7*/:
            case HTTP.HT /*9*/:
                return false;
            default:
                return true;
        }
    }
}
