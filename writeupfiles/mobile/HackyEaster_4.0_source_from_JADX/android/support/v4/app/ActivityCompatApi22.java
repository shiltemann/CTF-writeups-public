package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.support.annotation.RequiresApi;

@TargetApi(22)
@RequiresApi(22)
class ActivityCompatApi22 {
    ActivityCompatApi22() {
    }

    public static Uri getReferrer(Activity activity) {
        return activity.getReferrer();
    }
}
