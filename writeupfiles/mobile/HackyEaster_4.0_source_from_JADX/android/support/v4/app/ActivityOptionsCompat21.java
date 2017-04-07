package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Pair;
import android.view.View;

@TargetApi(21)
@RequiresApi(21)
class ActivityOptionsCompat21 {
    private final ActivityOptions mActivityOptions;

    public static ActivityOptionsCompat21 makeCustomAnimation(Context context, int enterResId, int exitResId) {
        return new ActivityOptionsCompat21(ActivityOptions.makeCustomAnimation(context, enterResId, exitResId));
    }

    public static ActivityOptionsCompat21 makeScaleUpAnimation(View source, int startX, int startY, int startWidth, int startHeight) {
        return new ActivityOptionsCompat21(ActivityOptions.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
    }

    public static ActivityOptionsCompat21 makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY) {
        return new ActivityOptionsCompat21(ActivityOptions.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
    }

    public static ActivityOptionsCompat21 makeSceneTransitionAnimation(Activity activity, View sharedElement, String sharedElementName) {
        return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName));
    }

    public static ActivityOptionsCompat21 makeSceneTransitionAnimation(Activity activity, View[] sharedElements, String[] sharedElementNames) {
        Pair[] pairs = null;
        if (sharedElements != null) {
            pairs = new Pair[sharedElements.length];
            for (int i = 0; i < pairs.length; i++) {
                pairs[i] = Pair.create(sharedElements[i], sharedElementNames[i]);
            }
        }
        return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation(activity, pairs));
    }

    public static ActivityOptionsCompat21 makeTaskLaunchBehind() {
        return new ActivityOptionsCompat21(ActivityOptions.makeTaskLaunchBehind());
    }

    private ActivityOptionsCompat21(ActivityOptions activityOptions) {
        this.mActivityOptions = activityOptions;
    }

    public Bundle toBundle() {
        return this.mActivityOptions.toBundle();
    }

    public void update(ActivityOptionsCompat21 otherOptions) {
        this.mActivityOptions.update(otherOptions.mActivityOptions);
    }
}
