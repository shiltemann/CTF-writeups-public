package android.support.v4.content;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.RequiresApi;

@TargetApi(11)
@RequiresApi(11)
class IntentCompatHoneycomb {
    IntentCompatHoneycomb() {
    }

    public static Intent makeMainActivity(ComponentName mainActivity) {
        return Intent.makeMainActivity(mainActivity);
    }

    public static Intent makeRestartActivityTask(ComponentName mainActivity) {
        return Intent.makeRestartActivityTask(mainActivity);
    }
}
