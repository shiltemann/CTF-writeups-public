package android.support.v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.RequiresApi;

@TargetApi(16)
@RequiresApi(16)
class AccessibilityServiceInfoCompatJellyBean {
    AccessibilityServiceInfoCompatJellyBean() {
    }

    public static String loadDescription(AccessibilityServiceInfo info, PackageManager pm) {
        return info.loadDescription(pm);
    }
}
