package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SharedElementCallback;
import android.app.SharedElementCallback.OnSharedElementsReadyListener;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompatApi21.SharedElementCallback21;
import android.view.View;
import java.util.List;
import java.util.Map;

@TargetApi(23)
@RequiresApi(23)
class ActivityCompatApi23 {

    public interface OnSharedElementsReadyListenerBridge {
        void onSharedElementsReady();
    }

    public interface RequestPermissionsRequestCodeValidator {
        void validateRequestPermissionsRequestCode(int i);
    }

    private static class SharedElementCallbackImpl extends SharedElementCallback {
        private SharedElementCallback23 mCallback;

        /* renamed from: android.support.v4.app.ActivityCompatApi23.SharedElementCallbackImpl.1 */
        class C00991 implements OnSharedElementsReadyListenerBridge {
            final /* synthetic */ OnSharedElementsReadyListener val$listener;

            C00991(OnSharedElementsReadyListener onSharedElementsReadyListener) {
                this.val$listener = onSharedElementsReadyListener;
            }

            public void onSharedElementsReady() {
                this.val$listener.onSharedElementsReady();
            }
        }

        public SharedElementCallbackImpl(SharedElementCallback23 callback) {
            this.mCallback = callback;
        }

        public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            this.mCallback.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            this.mCallback.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        public void onRejectSharedElements(List<View> rejectedSharedElements) {
            this.mCallback.onRejectSharedElements(rejectedSharedElements);
        }

        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            this.mCallback.onMapSharedElements(names, sharedElements);
        }

        public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
            return this.mCallback.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
        }

        public View onCreateSnapshotView(Context context, Parcelable snapshot) {
            return this.mCallback.onCreateSnapshotView(context, snapshot);
        }

        public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
            this.mCallback.onSharedElementsArrived(sharedElementNames, sharedElements, new C00991(listener));
        }
    }

    public static abstract class SharedElementCallback23 extends SharedElementCallback21 {
        public abstract void onSharedElementsArrived(List<String> list, List<View> list2, OnSharedElementsReadyListenerBridge onSharedElementsReadyListenerBridge);
    }

    ActivityCompatApi23() {
    }

    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (activity instanceof RequestPermissionsRequestCodeValidator) {
            ((RequestPermissionsRequestCodeValidator) activity).validateRequestPermissionsRequestCode(requestCode);
        }
        activity.requestPermissions(permissions, requestCode);
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        return activity.shouldShowRequestPermissionRationale(permission);
    }

    public static void setEnterSharedElementCallback(Activity activity, SharedElementCallback23 callback) {
        activity.setEnterSharedElementCallback(createCallback(callback));
    }

    public static void setExitSharedElementCallback(Activity activity, SharedElementCallback23 callback) {
        activity.setExitSharedElementCallback(createCallback(callback));
    }

    private static SharedElementCallback createCallback(SharedElementCallback23 callback) {
        if (callback != null) {
            return new SharedElementCallbackImpl(callback);
        }
        return null;
    }
}
