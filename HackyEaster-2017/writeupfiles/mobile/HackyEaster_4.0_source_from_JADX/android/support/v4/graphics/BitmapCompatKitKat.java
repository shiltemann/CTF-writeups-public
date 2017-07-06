package android.support.v4.graphics;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.support.annotation.RequiresApi;

@TargetApi(19)
@RequiresApi(19)
class BitmapCompatKitKat {
    BitmapCompatKitKat() {
    }

    static int getAllocationByteCount(Bitmap bitmap) {
        return bitmap.getAllocationByteCount();
    }
}
