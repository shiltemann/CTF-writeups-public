package android.support.v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@TargetApi(18)
@RequiresApi(18)
class AccessibilityServiceInfoCompatJellyBeanMr2 {
    AccessibilityServiceInfoCompatJellyBeanMr2() {
    }

    public static int getCapabilities(AccessibilityServiceInfo info) {
        return info.getCapabilities();
    }
}
