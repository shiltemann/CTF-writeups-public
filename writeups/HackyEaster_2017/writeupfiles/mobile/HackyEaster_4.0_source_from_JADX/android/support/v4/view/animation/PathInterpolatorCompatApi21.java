package android.support.v4.view.animation;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.support.annotation.RequiresApi;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

@TargetApi(21)
@RequiresApi(21)
class PathInterpolatorCompatApi21 {
    private PathInterpolatorCompatApi21() {
    }

    public static Interpolator create(Path path) {
        return new PathInterpolator(path);
    }

    public static Interpolator create(float controlX, float controlY) {
        return new PathInterpolator(controlX, controlY);
    }

    public static Interpolator create(float controlX1, float controlY1, float controlX2, float controlY2) {
        return new PathInterpolator(controlX1, controlY1, controlX2, controlY2);
    }
}
