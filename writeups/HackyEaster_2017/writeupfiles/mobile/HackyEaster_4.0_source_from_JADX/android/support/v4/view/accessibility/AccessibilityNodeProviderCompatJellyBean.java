package android.support.v4.view.accessibility;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

@TargetApi(16)
@RequiresApi(16)
class AccessibilityNodeProviderCompatJellyBean {

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompatJellyBean.1 */
    static class C00251 extends AccessibilityNodeProvider {
        final /* synthetic */ AccessibilityNodeInfoBridge val$bridge;

        C00251(AccessibilityNodeInfoBridge accessibilityNodeInfoBridge) {
            this.val$bridge = accessibilityNodeInfoBridge;
        }

        public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
            return (AccessibilityNodeInfo) this.val$bridge.createAccessibilityNodeInfo(virtualViewId);
        }

        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
            return this.val$bridge.findAccessibilityNodeInfosByText(text, virtualViewId);
        }

        public boolean performAction(int virtualViewId, int action, Bundle arguments) {
            return this.val$bridge.performAction(virtualViewId, action, arguments);
        }
    }

    interface AccessibilityNodeInfoBridge {
        Object createAccessibilityNodeInfo(int i);

        List<Object> findAccessibilityNodeInfosByText(String str, int i);

        boolean performAction(int i, int i2, Bundle bundle);
    }

    AccessibilityNodeProviderCompatJellyBean() {
    }

    public static Object newAccessibilityNodeProviderBridge(AccessibilityNodeInfoBridge bridge) {
        return new C00251(bridge);
    }
}
