package android.support.v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;

@TargetApi(14)
@RequiresApi(14)
class MotionEventCompatICS {
    MotionEventCompatICS() {
    }

    public static int getButtonState(MotionEvent event) {
        return event.getButtonState();
    }
}
