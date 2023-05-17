package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Pair;
import android.view.View;

@TargetApi(23)
@RequiresApi(23)
class ActivityOptionsCompat23 {
    private final ActivityOptions mActivityOptions;

    public static ActivityOptionsCompat23 makeCustomAnimation(Context context, int enterResId, int exitResId) {
        return new ActivityOptionsCompat23(ActivityOptions.makeCustomAnimation(context, enterResId, exitResId));
    }

    public static ActivityOptionsCompat23 makeScaleUpAnimation(View source, int startX, int startY, int startWidth, int startHeight) {
        return new ActivityOptionsCompat23(ActivityOptions.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
    }

    public static ActivityOptionsCompat23 makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY) {
        return new ActivityOptionsCompat23(ActivityOptions.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
    }

    public static ActivityOptionsCompat23 makeSceneTransitionAnimation(Activity activity, View sharedElement, String sharedElementName) {
        return new ActivityOptionsCompat23(ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName));
    }

    public static ActivityOptionsCompat23 makeSceneTransitionAnimation(Activity activity, View[] sharedElements, String[] sharedElementNames) {
        Pair[] pairs = null;
        if (sharedElements != null) {
            pairs = new Pair[sharedElements.length];
            for (int i = 0; i < pairs.length; i++) {
                pairs[i] = Pair.create(sharedElements[i], sharedElementNames[i]);
            }
        }
        return new ActivityOptionsCompat23(ActivityOptions.makeSceneTransitionAnimation(activity, pairs));
    }

    public static ActivityOptionsCompat23 makeClipRevealAnimation(View source, int startX, int startY, int width, int height) {
        return new ActivityOptionsCompat23(ActivityOptions.makeClipRevealAnimation(source, startX, startY, width, height));
    }

    public static ActivityOptionsCompat23 makeTaskLaunchBehind() {
        return new ActivityOptionsCompat23(ActivityOptions.makeTaskLaunchBehind());
    }

    public static ActivityOptionsCompat23 makeBasic() {
        return new ActivityOptionsCompat23(ActivityOptions.makeBasic());
    }

    private ActivityOptionsCompat23(ActivityOptions activityOptions) {
        this.mActivityOptions = activityOptions;
    }

    public Bundle toBundle() {
        return this.mActivityOptions.toBundle();
    }

    public void update(ActivityOptionsCompat23 otherOptions) {
        this.mActivityOptions.update(otherOptions.mActivityOptions);
    }

    public void requestUsageTimeReport(PendingIntent receiver) {
        this.mActivityOptions.requestUsageTimeReport(receiver);
    }
}
