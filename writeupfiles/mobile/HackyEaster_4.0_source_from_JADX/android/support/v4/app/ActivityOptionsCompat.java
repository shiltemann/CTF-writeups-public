package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.util.Pair;
import android.view.View;

public class ActivityOptionsCompat {
    public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
    public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";

    @RequiresApi(21)
    @TargetApi(21)
    private static class ActivityOptionsImpl21 extends ActivityOptionsCompat {
        private final ActivityOptionsCompat21 mImpl;

        ActivityOptionsImpl21(ActivityOptionsCompat21 impl) {
            this.mImpl = impl;
        }

        public Bundle toBundle() {
            return this.mImpl.toBundle();
        }

        public void update(ActivityOptionsCompat otherOptions) {
            if (otherOptions instanceof ActivityOptionsImpl21) {
                this.mImpl.update(((ActivityOptionsImpl21) otherOptions).mImpl);
            }
        }
    }

    @RequiresApi(23)
    @TargetApi(23)
    private static class ActivityOptionsImpl23 extends ActivityOptionsCompat {
        private final ActivityOptionsCompat23 mImpl;

        ActivityOptionsImpl23(ActivityOptionsCompat23 impl) {
            this.mImpl = impl;
        }

        public Bundle toBundle() {
            return this.mImpl.toBundle();
        }

        public void update(ActivityOptionsCompat otherOptions) {
            if (otherOptions instanceof ActivityOptionsImpl23) {
                this.mImpl.update(((ActivityOptionsImpl23) otherOptions).mImpl);
            }
        }

        public void requestUsageTimeReport(PendingIntent receiver) {
            this.mImpl.requestUsageTimeReport(receiver);
        }
    }

    @RequiresApi(24)
    @TargetApi(24)
    private static class ActivityOptionsImpl24 extends ActivityOptionsCompat {
        private final ActivityOptionsCompat24 mImpl;

        ActivityOptionsImpl24(ActivityOptionsCompat24 impl) {
            this.mImpl = impl;
        }

        public Bundle toBundle() {
            return this.mImpl.toBundle();
        }

        public void update(ActivityOptionsCompat otherOptions) {
            if (otherOptions instanceof ActivityOptionsImpl24) {
                this.mImpl.update(((ActivityOptionsImpl24) otherOptions).mImpl);
            }
        }

        public ActivityOptionsCompat setLaunchBounds(@Nullable Rect screenSpacePixelRect) {
            return new ActivityOptionsImpl24(this.mImpl.setLaunchBounds(screenSpacePixelRect));
        }

        public Rect getLaunchBounds() {
            return this.mImpl.getLaunchBounds();
        }

        public void requestUsageTimeReport(PendingIntent receiver) {
            this.mImpl.requestUsageTimeReport(receiver);
        }
    }

    @RequiresApi(16)
    @TargetApi(16)
    private static class ActivityOptionsImplJB extends ActivityOptionsCompat {
        private final ActivityOptionsCompatJB mImpl;

        ActivityOptionsImplJB(ActivityOptionsCompatJB impl) {
            this.mImpl = impl;
        }

        public Bundle toBundle() {
            return this.mImpl.toBundle();
        }

        public void update(ActivityOptionsCompat otherOptions) {
            if (otherOptions instanceof ActivityOptionsImplJB) {
                this.mImpl.update(((ActivityOptionsImplJB) otherOptions).mImpl);
            }
        }
    }

    public static ActivityOptionsCompat makeCustomAnimation(Context context, int enterResId, int exitResId) {
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeCustomAnimation(context, enterResId, exitResId));
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeCustomAnimation(context, enterResId, exitResId));
        }
        if (VERSION.SDK_INT >= 21) {
            return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeCustomAnimation(context, enterResId, exitResId));
        }
        if (VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeCustomAnimation(context, enterResId, exitResId));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeScaleUpAnimation(View source, int startX, int startY, int startWidth, int startHeight) {
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
        }
        if (VERSION.SDK_INT >= 21) {
            return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
        }
        if (VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeClipRevealAnimation(View source, int startX, int startY, int width, int height) {
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeClipRevealAnimation(source, startX, startY, width, height));
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeClipRevealAnimation(source, startX, startY, width, height));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY) {
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
        }
        if (VERSION.SDK_INT >= 21) {
            return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
        }
        if (VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity activity, View sharedElement, String sharedElementName) {
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName));
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName));
        }
        if (VERSION.SDK_INT >= 21) {
            return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity activity, Pair<View, String>... sharedElements) {
        if (VERSION.SDK_INT < 21) {
            return new ActivityOptionsCompat();
        }
        View[] views = null;
        String[] names = null;
        if (sharedElements != null) {
            views = new View[sharedElements.length];
            names = new String[sharedElements.length];
            for (int i = 0; i < sharedElements.length; i++) {
                views[i] = (View) sharedElements[i].first;
                names[i] = (String) sharedElements[i].second;
            }
        }
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeSceneTransitionAnimation(activity, views, names));
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeSceneTransitionAnimation(activity, views, names));
        }
        return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeSceneTransitionAnimation(activity, views, names));
    }

    public static ActivityOptionsCompat makeTaskLaunchBehind() {
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeTaskLaunchBehind());
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeTaskLaunchBehind());
        }
        if (VERSION.SDK_INT >= 21) {
            return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeTaskLaunchBehind());
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeBasic() {
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeBasic());
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeBasic());
        }
        return new ActivityOptionsCompat();
    }

    protected ActivityOptionsCompat() {
    }

    public ActivityOptionsCompat setLaunchBounds(@Nullable Rect screenSpacePixelRect) {
        return null;
    }

    @Nullable
    public Rect getLaunchBounds() {
        return null;
    }

    public Bundle toBundle() {
        return null;
    }

    public void update(ActivityOptionsCompat otherOptions) {
    }

    public void requestUsageTimeReport(PendingIntent receiver) {
    }
}
