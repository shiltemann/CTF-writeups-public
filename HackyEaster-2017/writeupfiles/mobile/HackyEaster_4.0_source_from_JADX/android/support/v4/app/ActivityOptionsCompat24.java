package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Pair;
import android.view.View;

@TargetApi(24)
@RequiresApi(24)
class ActivityOptionsCompat24 {
    private final ActivityOptions mActivityOptions;

    public static ActivityOptionsCompat24 makeCustomAnimation(Context context, int enterResId, int exitResId) {
        return new ActivityOptionsCompat24(ActivityOptions.makeCustomAnimation(context, enterResId, exitResId));
    }

    public static ActivityOptionsCompat24 makeScaleUpAnimation(View source, int startX, int startY, int startWidth, int startHeight) {
        return new ActivityOptionsCompat24(ActivityOptions.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
    }

    public static ActivityOptionsCompat24 makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY) {
        return new ActivityOptionsCompat24(ActivityOptions.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
    }

    public static ActivityOptionsCompat24 makeSceneTransitionAnimation(Activity activity, View sharedElement, String sharedElementName) {
        return new ActivityOptionsCompat24(ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName));
    }

    public static ActivityOptionsCompat24 makeSceneTransitionAnimation(Activity activity, View[] sharedElements, String[] sharedElementNames) {
        Pair[] pairs = null;
        if (sharedElements != null) {
            pairs = new Pair[sharedElements.length];
            for (int i = 0; i < pairs.length; i++) {
                pairs[i] = Pair.create(sharedElements[i], sharedElementNames[i]);
            }
        }
        return new ActivityOptionsCompat24(ActivityOptions.makeSceneTransitionAnimation(activity, pairs));
    }

    public static ActivityOptionsCompat24 makeClipRevealAnimation(View source, int startX, int startY, int width, int height) {
        return new ActivityOptionsCompat24(ActivityOptions.makeClipRevealAnimation(source, startX, startY, width, height));
    }

    public static ActivityOptionsCompat24 makeTaskLaunchBehind() {
        return new ActivityOptionsCompat24(ActivityOptions.makeTaskLaunchBehind());
    }

    public static ActivityOptionsCompat24 makeBasic() {
        return new ActivityOptionsCompat24(ActivityOptions.makeBasic());
    }

    private ActivityOptionsCompat24(ActivityOptions activityOptions) {
        this.mActivityOptions = activityOptions;
    }

    public ActivityOptionsCompat24 setLaunchBounds(@Nullable Rect screenSpacePixelRect) {
        return new ActivityOptionsCompat24(this.mActivityOptions.setLaunchBounds(screenSpacePixelRect));
    }

    public Rect getLaunchBounds() {
        return this.mActivityOptions.getLaunchBounds();
    }

    public Bundle toBundle() {
        return this.mActivityOptions.toBundle();
    }

    public void update(ActivityOptionsCompat24 otherOptions) {
        this.mActivityOptions.update(otherOptions.mActivityOptions);
    }

    public void requestUsageTimeReport(PendingIntent receiver) {
        this.mActivityOptions.requestUsageTimeReport(receiver);
    }
}
