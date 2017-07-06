package android.support.v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;

@TargetApi(16)
@RequiresApi(16)
class AccessibilityEventCompatJellyBean {
    AccessibilityEventCompatJellyBean() {
    }

    public static void setMovementGranularity(AccessibilityEvent event, int granularity) {
        event.setMovementGranularity(granularity);
    }

    public static int getMovementGranularity(AccessibilityEvent event) {
        return event.getMovementGranularity();
    }

    public static void setAction(AccessibilityEvent event, int action) {
        event.setAction(action);
    }

    public static int getAction(AccessibilityEvent event) {
        return event.getAction();
    }
}
