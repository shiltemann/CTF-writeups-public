package android.support.v4.content.res;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
class ResourcesCompatApi23 {
    ResourcesCompatApi23() {
    }

    public static int getColor(Resources res, int id, Theme theme) throws NotFoundException {
        return res.getColor(id, theme);
    }

    public static ColorStateList getColorStateList(Resources res, int id, Theme theme) throws NotFoundException {
        return res.getColorStateList(id, theme);
    }
}
