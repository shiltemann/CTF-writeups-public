package android.support.v4.os;

import android.annotation.TargetApi;
import android.os.Parcelable.Creator;
import android.support.annotation.RequiresApi;

@TargetApi(13)
@RequiresApi(13)
/* compiled from: ParcelableCompatHoneycombMR2 */
class ParcelableCompatCreatorHoneycombMR2Stub {
    ParcelableCompatCreatorHoneycombMR2Stub() {
    }

    static <T> Creator<T> instantiate(ParcelableCompatCreatorCallbacks<T> callbacks) {
        return new ParcelableCompatCreatorHoneycombMR2(callbacks);
    }
}
