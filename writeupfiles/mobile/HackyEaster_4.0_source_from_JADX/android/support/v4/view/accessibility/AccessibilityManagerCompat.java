package android.support.v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build.VERSION;
import android.support.v4.view.accessibility.AccessibilityManagerCompatIcs.AccessibilityStateChangeListenerWrapper;
import android.support.v4.view.accessibility.AccessibilityManagerCompatKitKat.TouchExplorationStateChangeListenerWrapper;
import android.view.accessibility.AccessibilityManager;
import java.util.Collections;
import java.util.List;

public final class AccessibilityManagerCompat {
    private static final AccessibilityManagerVersionImpl IMPL;

    interface AccessibilityManagerVersionImpl {
        boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener);

        boolean addTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener);

        List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager accessibilityManager, int i);

        List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager accessibilityManager);

        boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager);

        AccessibilityStateChangeListenerWrapper newAccessibilityStateChangeListener(AccessibilityStateChangeListener accessibilityStateChangeListener);

        TouchExplorationStateChangeListenerWrapper newTouchExplorationStateChangeListener(TouchExplorationStateChangeListener touchExplorationStateChangeListener);

        boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener);

        boolean removeTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener);
    }

    public interface AccessibilityStateChangeListener {
        void onAccessibilityStateChanged(boolean z);
    }

    public interface TouchExplorationStateChangeListener {
        void onTouchExplorationStateChanged(boolean z);
    }

    static class AccessibilityManagerStubImpl implements AccessibilityManagerVersionImpl {
        AccessibilityManagerStubImpl() {
        }

        public AccessibilityStateChangeListenerWrapper newAccessibilityStateChangeListener(AccessibilityStateChangeListener listener) {
            return null;
        }

        public boolean addAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListener listener) {
            return false;
        }

        public boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListener listener) {
            return false;
        }

        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager manager, int feedbackTypeFlags) {
            return Collections.emptyList();
        }

        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager manager) {
            return Collections.emptyList();
        }

        public boolean isTouchExplorationEnabled(AccessibilityManager manager) {
            return false;
        }

        public TouchExplorationStateChangeListenerWrapper newTouchExplorationStateChangeListener(TouchExplorationStateChangeListener listener) {
            return null;
        }

        public boolean addTouchExplorationStateChangeListener(AccessibilityManager manager, TouchExplorationStateChangeListener listener) {
            return false;
        }

        public boolean removeTouchExplorationStateChangeListener(AccessibilityManager manager, TouchExplorationStateChangeListener listener) {
            return false;
        }
    }

    @Deprecated
    public static abstract class AccessibilityStateChangeListenerCompat implements AccessibilityStateChangeListener {
    }

    static class AccessibilityManagerIcsImpl extends AccessibilityManagerStubImpl {

        /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerIcsImpl.1 */
        class C01131 implements AccessibilityStateChangeListenerBridge {
            final /* synthetic */ AccessibilityStateChangeListener val$listener;

            C01131(AccessibilityStateChangeListener accessibilityStateChangeListener) {
                this.val$listener = accessibilityStateChangeListener;
            }

            public void onAccessibilityStateChanged(boolean enabled) {
                this.val$listener.onAccessibilityStateChanged(enabled);
            }
        }

        AccessibilityManagerIcsImpl() {
        }

        public AccessibilityStateChangeListenerWrapper newAccessibilityStateChangeListener(AccessibilityStateChangeListener listener) {
            return new AccessibilityStateChangeListenerWrapper(listener, new C01131(listener));
        }

        public boolean addAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListener listener) {
            return AccessibilityManagerCompatIcs.addAccessibilityStateChangeListener(manager, newAccessibilityStateChangeListener(listener));
        }

        public boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListener listener) {
            return AccessibilityManagerCompatIcs.removeAccessibilityStateChangeListener(manager, newAccessibilityStateChangeListener(listener));
        }

        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager manager, int feedbackTypeFlags) {
            return AccessibilityManagerCompatIcs.getEnabledAccessibilityServiceList(manager, feedbackTypeFlags);
        }

        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager manager) {
            return AccessibilityManagerCompatIcs.getInstalledAccessibilityServiceList(manager);
        }

        public boolean isTouchExplorationEnabled(AccessibilityManager manager) {
            return AccessibilityManagerCompatIcs.isTouchExplorationEnabled(manager);
        }
    }

    static class AccessibilityManagerKitKatImpl extends AccessibilityManagerIcsImpl {

        /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat.AccessibilityManagerKitKatImpl.1 */
        class C01141 implements TouchExplorationStateChangeListenerBridge {
            final /* synthetic */ TouchExplorationStateChangeListener val$listener;

            C01141(TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
                this.val$listener = touchExplorationStateChangeListener;
            }

            public void onTouchExplorationStateChanged(boolean enabled) {
                this.val$listener.onTouchExplorationStateChanged(enabled);
            }
        }

        AccessibilityManagerKitKatImpl() {
        }

        public TouchExplorationStateChangeListenerWrapper newTouchExplorationStateChangeListener(TouchExplorationStateChangeListener listener) {
            return new TouchExplorationStateChangeListenerWrapper(listener, new C01141(listener));
        }

        public boolean addTouchExplorationStateChangeListener(AccessibilityManager manager, TouchExplorationStateChangeListener listener) {
            return AccessibilityManagerCompatKitKat.addTouchExplorationStateChangeListener(manager, newTouchExplorationStateChangeListener(listener));
        }

        public boolean removeTouchExplorationStateChangeListener(AccessibilityManager manager, TouchExplorationStateChangeListener listener) {
            return AccessibilityManagerCompatKitKat.removeTouchExplorationStateChangeListener(manager, newTouchExplorationStateChangeListener(listener));
        }
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityManagerKitKatImpl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityManagerIcsImpl();
        } else {
            IMPL = new AccessibilityManagerStubImpl();
        }
    }

    public static boolean addAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListener listener) {
        return IMPL.addAccessibilityStateChangeListener(manager, listener);
    }

    public static boolean removeAccessibilityStateChangeListener(AccessibilityManager manager, AccessibilityStateChangeListener listener) {
        return IMPL.removeAccessibilityStateChangeListener(manager, listener);
    }

    public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager manager) {
        return IMPL.getInstalledAccessibilityServiceList(manager);
    }

    public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager manager, int feedbackTypeFlags) {
        return IMPL.getEnabledAccessibilityServiceList(manager, feedbackTypeFlags);
    }

    public static boolean isTouchExplorationEnabled(AccessibilityManager manager) {
        return IMPL.isTouchExplorationEnabled(manager);
    }

    public static boolean addTouchExplorationStateChangeListener(AccessibilityManager manager, TouchExplorationStateChangeListener listener) {
        return IMPL.addTouchExplorationStateChangeListener(manager, listener);
    }

    public static boolean removeTouchExplorationStateChangeListener(AccessibilityManager manager, TouchExplorationStateChangeListener listener) {
        return IMPL.removeTouchExplorationStateChangeListener(manager, listener);
    }

    private AccessibilityManagerCompat() {
    }
}
