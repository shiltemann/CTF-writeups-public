package android.support.v4.content.res;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

@TargetApi(17)
@RequiresApi(17)
class ConfigurationHelperJellybeanMr1 {
    ConfigurationHelperJellybeanMr1() {
    }

    static int getDensityDpi(@NonNull Resources resources) {
        return resources.getConfiguration().densityDpi;
    }
}
